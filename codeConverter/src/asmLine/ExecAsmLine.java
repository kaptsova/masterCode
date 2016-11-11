package asmLine;

import java.util.ArrayList;

import codeConverter.OperandType;
import commonTypes.ExecutionStatus;
import errorHandler.ErrorMessage;
import errorHandler.ErrorType;

public class ExecAsmLine extends AsmLine implements SyntaxCheckable{
	
	ExecutionStatus execStatus = ExecutionStatus.illegalStatus;
	private boolean asmLineOk = false;

	String opIn1AddressString = "00000000";
	String opIn2AddressString = "00000000";
	String opOutAddressString = "00000000";
	
	public ExecAsmLine(String initString, int initIndex) {
		super(initString, initIndex);
		execStatus = ExecutionStatus.notPermittedStatus;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean initializeAsmLine() {
		// TODO Auto-generated method stub
		
		return false;
	}
	
	public boolean checkAssemblerLine(ArrayList<OpCode> opCodeList, ArrayList<DqAsmLine> declarations)
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
		
		return mnemonicOk;
	}
	
	/*Check if actual operand types coincide with the declared
	 * @param opCode contains information about declared types of operands for given mnemonic
	 */
	private boolean checkOperandTypes(OpCode opCode)
	{
		boolean operandTypesOk = true;
		// Define actual operand types
		opIn1Type = defineOperandType(opCode.getOpIn1Type(), mOperandIn1);
		opIn2Type = defineOperandType(opCode.getOpIn2Type(), mOperandIn2);
		opOutType = defineOperandType(opCode.getOpOutType(), mOperandOut);
		
		if (opIn1Type.equals(OperandType.illegalOperand) || opIn2Type.equals(OperandType.illegalOperand) || opOutType.equals(OperandType.illegalOperand))
			operandTypesOk = false;
					
		return operandTypesOk;
	}
	
	private OperandType defineOperandType(OperandType opCodeOperandType, String operand)
	{
		OperandType asmLineOperandType = OperandType.illegalOperand;
		
		boolean status = false;
		// check if declared operand type coincide with actual 
		switch (opCodeOperandType)
		{
		case delimiterOperand:
			status = isDelimiter(operand);		
			break;
		case variableOperand:
			status = isVariable(operand);	
			break;
		case hexadecimalOperand:
			status = isHexadecimal(operand);
			break;
		case portIndexOperand:
			status = isPortIndex(operand);
			break;
		default:
			status = false;
			//TODO:  handle error 	
		}
		if (status == true)
			asmLineOperandType = opCodeOperandType;
		else{
			asmLineOperandType = OperandType.illegalOperand;
			//TODO handle errors somewhere file paths are known
			ErrorMessage errMsg = new ErrorMessage(ErrorType.wrongOperandTypeError, getIndex());
			errMsg.printToFile("tests\\meww.txt");
		}
				
		return asmLineOperandType;
	}
	
	/* Check if variables used in an assembler command were declared before
	 * TODO try to fill list for declaration code  from here
	 */
	
	// TODO разберись с индексами, чтобы сразу добавлять в объявления переменных
	// сохраняй номер первого найденного объявления
	private boolean checkOperandVariables(ArrayList<DqAsmLine> declarations){
		boolean operandVariablesOk = true;
		
		boolean operandIn1Found = false;
		boolean operandIn2Found = false;
		boolean operandOutFound = false;
		
		for (DqAsmLine dq : declarations){
			
			// Check all operands
			if (!operandIn1Found){
				operandIn1Found = findVariable(opIn1Type, mOperandIn1,dq);				
			}
			if (!operandIn2Found){
				operandIn2Found = findVariable(opIn2Type, mOperandIn2,dq);
			}
			if (!operandOutFound){
				operandOutFound = findVariable(opOutType, mOperandOut,dq);
			}
			
			if (operandIn1Found && operandIn2Found && operandOutFound)
				return true;		
		}
		
		// TODO handle error in errorMessage.java class
		if (!operandIn1Found || !operandIn2Found || !operandOutFound){
			operandVariablesOk = false;
			System.out.format("Line %d Operands %s %b %b %b", getIndex(), mOperandIn1, operandIn1Found, operandIn2Found, operandOutFound );
		}
			
		return operandVariablesOk;
	}

	private boolean findVariable(OperandType operandType, String operand, DqAsmLine dq) {
		String variableName = dq.getVariableName();
		boolean variableFound = false;
		
		if ((operandType == OperandType.variableOperand) &&(operand.equalsIgnoreCase(variableName))){
			variableFound = true;
		}
		else if (operandType != OperandType.variableOperand)
			return true;
		
		return variableFound;
	}
	
	public String getStatusInfo(){
		return mOperator + " found = " + asmLineOk + " " + opIn1Type + " " + opIn2Type + " " + opOutType; 
	}
}
