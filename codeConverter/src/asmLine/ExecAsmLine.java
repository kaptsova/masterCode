package asmLine;

import java.util.ArrayList;

import codeConverter.OperandType;
import commonTypes.ExecutionStatus;

public class ExecAsmLine extends AsmLine implements SyntaxCheckable{
	
	ExecutionStatus execStatus = ExecutionStatus.illegalStatus;

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
	
	public boolean checkAssemblerLine(ArrayList<OpCode> opCodeList)
	{
		// Status variables
		boolean mnemonicOk = false;
		boolean operandTypesOk = false;
		boolean asmLineOk = false;

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
		
		asmLineOk = mnemonicOk && operandTypesOk;
		
		return mnemonicOk;
	}
	
	// Check if actual operand types coincide with the declared
	private boolean checkOperandTypes(OpCode opCode)
	{
		boolean operandTypesOk = true;
		// Define actual operand types
		//TODO: add Operand Type enum to OpCode class
		opIn1Type = defineOperandType(opCode.getOpIn1Type(), mOperandIn1);
		opIn2Type = defineOperandType(opCode.getOpIn2Type(), mOperandIn2);
		opOutType = defineOperandType(opCode.getOpIn1Type(), mOperandOut);
		
		if (opIn1Type.equals(OperandType.illegalOperand) || opIn2Type.equals(OperandType.illegalOperand) || opOutType.equals(OperandType.illegalOperand))
			operandTypesOk = false;
					
		return operandTypesOk;
	}
	
	private OperandType defineOperandType(OperandType opCodeOperandType, String operand)
	{
		OperandType asmLineOperandType = OperandType.illegalOperand;
		
		boolean status = false;
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
		default:
			status = false;
			//TODO:  handle error 	
		}
		if (status == true)
			asmLineOperandType = opCodeOperandType;
		else
			asmLineOperandType = OperandType.illegalOperand;
				
		return asmLineOperandType;
	}
	

}
