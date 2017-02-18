package asmLine;

import java.util.ArrayList;

import commonTypes.ExecutionStatus;
import errorHandler.ErrorMessage;
import errorHandler.ErrorType;
import operand.Operand;
import operand.OperandType;

public class ExecutableLine extends AsmLine{
	
	ExecutionStatus execStatus = ExecutionStatus.illegalStatus;
	private boolean asmLineOk = false;
	
	private Operand operandIn1;
	private Operand operandIn2;
	private Operand operandOut;
	
	private boolean isResultForwarding = false;
	
	private Operator operator;
	public Operator getOperator(){
		return operator;
	}

	
	private static String filePath;
	
	public static void setFilePath(String initFilePath) {
		filePath = initFilePath;
	}	

	public ExecutableLine(String initString, int initIndex) {
		super(initString, initIndex);
		execStatus = ExecutionStatus.notPermittedStatus;
		
		operandIn1 = new Operand(mOperandIn1);
		operandIn2 = new Operand(mOperandIn2);
		operandOut = new Operand(mOperandOut);
		operator   = new Operator(mOperator);
	}
	
	public boolean checkAndSetupAssemblerLine(ArrayList<OpCode> opCodeList, ArrayList<DeclarationLine> declarations)
	{
		// Status variables
		boolean mnemonicOk = false;
		boolean operandTypesOk = false;
		boolean operandVariablesOk = false;

		// Check if mnemonic is declared among available		
		OpCode foundOpCode = null;
		for (OpCode opCode :  opCodeList)
		{
			String mnemonic = opCode.getMnemonic();
			mnemonicOk = checkMnemonic(mnemonic);
			// If mnemonic is found in the list -> save it
			if (mnemonicOk)
			{
				foundOpCode = opCode;
				setOperator(opCode);
				break;
			}
		}
		// If mnemonic is wrong -> assembler line is wrong
		if (mnemonicOk == false)
			return false;
		else
			operandTypesOk = checkOperandTypes(foundOpCode);
		
		// If (at least) operand type is wrong -> assembler line is wrong
		if (operandTypesOk == false)
			return false;
		else
			operandVariablesOk = checkOperandVariables(declarations);
		
		asmLineOk = mnemonicOk && operandTypesOk && operandVariablesOk;
		setOperator(foundOpCode);
		
		return asmLineOk;
	}

	private void setOperator(OpCode opCode) {
		operator.wbCommand = opCode.getWbCommand();
		operator.exCommand = opCode.getExCommand();
		operator.aluDelay = opCode.getAluDelay();
		operator.pipeDelay = opCode.getPipeDelay();
		operator.setOperations();
		
	}
	
	/*Check if actual operand types coincide with the declared
	 * @param opCode contains information about declared types of operands for given mnemonic
	 */
	private boolean checkOperandTypes(OpCode opCode)
	{
		boolean operandTypesOk = true;
		// Define actual operand types	
		defineOperandType(opCode.getOpIn1Type(), operandIn1);
		defineOperandType(opCode.getOpIn2Type(), operandIn2);
		defineOperandType(opCode.getOpOutType(), operandOut);
				
		if (!typesFound())
			operandTypesOk = false;
					
		return operandTypesOk;
	}
	
	
	
	
	
	private boolean defineOperandType(OperandType opCodeOperandType, Operand operand){
		
		boolean status = false;
		// check if declared operand type coincide with actual 
		switch (opCodeOperandType)
		{
		case delimiterOperand:
			status = isDelimiter(operand.getName());		
			break;
		case variableOperand:
			status = isVariable(operand.getName());	
			break;
		case hexadecimalOperand:
			status = isHexadecimal(operand.getName());
			break;
		case portIndexOperand:
			status = isPortIndex(operand.getName());
			break;
		default:
			status = false;
			//TODO:  handle error 	
		}
		if (status == true){
			operand.setOpType(opCodeOperandType);
			return true;
		}
		else{
			operand.setOpType(OperandType.illegalOperand);
			//TODO handle errors somewhere file paths are known
			ErrorMessage errMsg = new ErrorMessage(ErrorType.wrongOperandTypeError, getIndex(), filePath);
			errMsg.printToFile();
			return false;
		}						
	}
	
	// TODO handle error in errorMessage.java class
		private boolean typesFound(){
			if (operandIn1.getOpType().equals(OperandType.illegalOperand)){
				ErrorMessage errMsg = new ErrorMessage(ErrorType.wrongOpIn1TypeError, getIndex(), filePath);
				errMsg.printToFile();
				return false;
			}
			if (operandIn2.getOpType().equals(OperandType.illegalOperand)){
				ErrorMessage errMsg = new ErrorMessage(ErrorType.wrongOpIn2TypeError, getIndex(), filePath);
				errMsg.printToFile();
				return false;
			}
			if (operandOut.getOpType().equals(OperandType.illegalOperand)){
				ErrorMessage errMsg = new ErrorMessage(ErrorType.wrongOpOutTypeError, getIndex(), filePath);
				errMsg.printToFile();
				return false;
			}
			
			return true;
		}
	
	
	
	/* Check if variables used in an assembler command were declared before
	 * @param declarations - variables declared in program code
	 */
	private boolean checkOperandVariables(ArrayList<DeclarationLine> declarations){
		boolean operandVariablesOk = true;
		
		
		for (DeclarationLine dq : declarations){
			if (operandIn1.findVariable(dq))
				dq.addReadIndex(getIndex());
			
			if (operandIn2.findVariable(dq))
				dq.addReadIndex(getIndex());
			
			if (operandOut.findVariable(dq))
				dq.addWriteIndex(getIndex());
						
			boolean operandsFound = operandIn1.isFound() && operandIn2.isFound() && operandOut.isFound();
			
			if (operandsFound)
				return true;		
		}
		
		// TODO handle error in errorMessage.java class
		if (!operandsFound()){
			operandVariablesOk = false;
			System.out.format("Line %d", getIndex());
		}

		
			
		return operandVariablesOk;
	}
	
	// TODO handle error in errorMessage.java class
	private boolean operandsFound(){
		if (!operandIn1.isFound()){
			ErrorMessage errMsg = new ErrorMessage(ErrorType.opIn1NotFoundError, getIndex(), filePath);
			errMsg.printToFile();
			return false;
		}
		if (!operandIn2.isFound()){
			ErrorMessage errMsg = new ErrorMessage(ErrorType.opIn2NotFoundError, getIndex(), filePath);
			errMsg.printToFile();
			return false;
		}
		if (!operandOut.isFound()){
			ErrorMessage errMsg = new ErrorMessage(ErrorType.opOutNotFoundError, getIndex(), filePath);
			errMsg.printToFile();
			return false;
		}

		
		return true;
	}
	
	
	public String getStatusInfo(){
		return mOperator + " found = " + asmLineOk + " " + operandIn1.getOpType() + " " + operandIn2.getOpType()  + " " + operandIn2.getOpType() ; 
	}
	
	public class Operator {
		private String mnemonic = "";
		private String wbCommand = "";
		private String exCommand = "";
		private int aluDelay = 0;
		private int pipeDelay = 0;
		private String calculationOperation;
		private String writeBackOperation;
		private boolean calculationExecuted = false;
		private boolean writeBackExecuted = false;
		private final String wbNone = "00111";
		
		public int getAluDelay() {
			return aluDelay;
		}

		public int getPipeDelay() {
			return pipeDelay;
		}
		
		public Operator(String operator){
			mnemonic = operator;
		}
		
		public String binaryToHex(String binaryString) {
			int decimalString = Integer.parseInt(binaryString,2);
			String hexString = Integer.toString(decimalString,16);
			String nullString = "00000000".substring(hexString.length());
						
			return nullString + hexString;
		}
		
		public void setCalculationOperation(){
			
			String wbAddr = "00000000";
			String wbCom  = wbCommand.substring(3);
			String addr2  = operandIn2.getAddressString();
			String addr1  = operandIn1.getAddressString();
			String exCom  = exCommand.substring(5);
			
			String binaryOperation = wbAddr + wbCom + addr2 + addr1 + exCom; 
			calculationOperation = binaryToHex(binaryOperation);
			
			System.out.println(calculationOperation);
		}
				
		public void setWriteBackOperation(){
			
			String wbAddr = operandOut.getAddressString();
			String wbCom  = wbNone;
			String addr2  = "00000000";
			String addr1  = "00000000";
			String exCom  = exCommand.substring(5);
			
			String binaryOperation = wbAddr + wbCom + addr2 + addr1 + exCom; 
			writeBackOperation =  binaryToHex(binaryOperation);
			System.out.println(writeBackOperation);
		}
		
		public void setOperations(){
			setCalculationOperation();
			setWriteBackOperation();
		}
		
		public String getCalculationOperation(){
			return calculationOperation;
		}
		
		public String getWriteBackOperation(){
			return writeBackOperation;
		}
		
		public boolean isCalculationExecuted() {
			return calculationExecuted == true;
		}
		
		public boolean isWriteBackExecuted() {
			return writeBackExecuted == true;
		}
	}
	
}
