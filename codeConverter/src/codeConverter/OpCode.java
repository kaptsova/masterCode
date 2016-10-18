package codeConverter;

//TODO: implement Iterable <OpCode> interface 
public class OpCode {
	// Private members
	private String mnemonic;
	private String wbCommand;
	private String exCommand;
	
	private CommandType cmdType = CommandType.illegalCommand;
	private OperandType op1Type = OperandType.illegalOperand;
	private OperandType op2Type = OperandType.illegalOperand;
	private OperandType op3Type = OperandType.illegalOperand;
	
	public CommandType getCmdType() {
		return cmdType;
	}
	private int aluDelay = 0;
	public String getMnemonic() {
		return mnemonic;
	}
	public String getWbCommand() {
		return wbCommand;
	}
	public String getExCommand() {
		return exCommand;
	}
	public int getAluDelay() {
		return aluDelay;
	}
	public int getPipeDelay() {
		return pipeDelay;
	}
	public char getOpIn1Type() {
		return opIn1Type;
	}
	public char getOpIn2Type() {
		return opIn2Type;
	}
	public char getOpOutType() {
		return opOutType;
	}
	private int pipeDelay = 0;
	private char opIn1Type = 'd';
	private char opIn2Type = 'd';
	private char opOutType = 'd';
	
	public OpCode(String initString)
	{		
		/* Attention : regex splitting the words including all
		standard whitespace-characters and Unicode non-breaking
		space xA0*/
		String[] initArray = initString.split("[\\s\\xA0]+", 8);
		mnemonic = initArray[0];
		wbCommand = initArray[1];
		exCommand = initArray[2];
		/* For all integer members of opCode class - convert from char */
		aluDelay = Character.getNumericValue(initArray[3].charAt(0));
		pipeDelay = Character.getNumericValue(initArray[4].charAt(0));
		/* For all char members of opCode class - convert from string */
		opIn1Type = initArray[5].charAt(0);
		opIn2Type = initArray[6].charAt(0);
		opOutType = initArray[7].charAt(0);	
		
		defineCommandType();
	}
	
	private void defineCommandType()
	{
		System.out.println("Define Command Type");
		// TODO handle all command types
	}
	
	
	
	public static void main(String[] args)
	{
		OpCode myOpCode = new OpCode("in 00000000    00000000    0           3             c   d   v");
		System.out.println(myOpCode.mnemonic + " " + myOpCode.wbCommand + " " + myOpCode.exCommand + " " + myOpCode.aluDelay + " " + myOpCode.pipeDelay + " " + myOpCode.opIn1Type +  " " + myOpCode.opIn2Type + " " + myOpCode.opOutType);
	}

}
