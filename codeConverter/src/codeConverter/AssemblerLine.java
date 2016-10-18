package codeConverter;

import java.util.ArrayList;

public class AssemblerLine {
	
	private String mOperator;
	private String mOperandIn1;
	private String mOperandIn2;
	private String mOperandOut;
	private int index = -1;
	
	public String getmOperator() {
		return mOperator;
	}

	public String getmOperandIn1() {
		return mOperandIn1;
	}

	public String getmOperandIn2() {
		return mOperandIn2;
	}

	public String getmOperandOut() {
		return mOperandOut;
	}

	public int getIndex() {
		return index;
	}
	
	public AssemblerLine(String initString, int initIndex)
	{
		String[] initArray = initString.split("[\\s\\xA0]+", 4);
		if (initArray.length == 4)
		{
			mOperator = initArray[0];
			mOperandIn1 = initArray[1];
			mOperandIn2 = initArray[2];
			//TODO add check to ensure mOperandOut doesn't contain spaces
			mOperandOut = initArray[3];
			index = initIndex;
		}
		else
		{
			System.out.println("Wrong number of operands");
		}
	}
	
	public boolean checkAssemblerLine(ArrayList<OpCode> opCodeList)
	{
		// Status variables
		boolean status = false;
		boolean mnemonicFound = false;
		boolean operandTypesOk = false;
		// Check if mnemonic is declared among available		
		OpCode foundOpCode = null;
		for (OpCode opCode :  opCodeList)
		{
			String mnemonic = opCode.getMnemonic();
			mnemonicFound = checkMnemonic(mnemonic);
			// If mnemonic is found in the list -> save it
			if (mnemonicFound)
			{
				foundOpCode = opCode;
				break;
			}
		}
		// If mnemonic is wrong -> assembler line is wrong
		if (mnemonicFound == false)
			return false;
		else
			operandTypesOk = checkOperandTypes(foundOpCode);		
		
		return mnemonicFound;
	}
	
	private boolean checkMnemonic(String mnemonic)
	{
		if (mOperator.equals(mnemonic))
			return true;
		else 
			return false;
	}
	
	
	
	private boolean checkOperandTypes(OpCode opCode)
	{
		boolean status = false;
		
		return status;
	}
	
	private boolean isVariable(String operand)
	{
		boolean status = false;
		
		return status;
	}
	
	private boolean isHexadecimal(String operand)
	{
		boolean status = false;
		
		return status;
	}
	
	private boolean isDelimiter(String operand)
	{
		boolean status = false;
		
		return status;
	}
	
	
	
	public static void main(String[] args)
	{
		AssemblerLine myLine = new AssemblerLine("ADD A f B C", 3);
		System.out.println(myLine.mOperator + ": " + myLine.mOperandIn1 + " -" + myLine.mOperandIn2 + " -" + myLine.mOperandOut);
	}

}
