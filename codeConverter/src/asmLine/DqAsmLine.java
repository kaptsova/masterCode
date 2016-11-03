package asmLine;

import codeConverter.OperandType;
import commonTypes.CommandType;

public class DqAsmLine extends AsmLine implements SyntaxCheckable{

	public DqAsmLine(){
		super();
	}
	public DqAsmLine(String initString, int initIndex) {
		super(initString, initIndex);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean initializeAsmLine() {
		boolean status = true;
		// Define command type and types of all operands
		//TODO: add check functions to ensure that operands
		cmdType = CommandType.dqCommand;
		if (isVariable(mOperandIn1))
			opIn1Type = OperandType.variableOperand;
		else 
			return false;
		
		if (isDelimiter(mOperandIn2))
			opIn2Type = OperandType.delimiterOperand;
		else 
			return false;
		if (isHexadecimal(mOperandOut))
			opOutType = OperandType.hexadecimalOperand;
		else 
			return false;
		
		return status;
	}

}
