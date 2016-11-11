package asmLine;

import java.util.ArrayList;

import codeConverter.OperandType;
import commonTypes.CommandType;

public class DqAsmLine extends AsmLine implements SyntaxCheckable{


	private String variableName = mOperandIn1;
	public String getVariableName() {
		return variableName;
	}

	
	private String variableAddressString = "";
	private int variableAddress = 0;
	
	private String nullString = "00000000";
	
	private ArrayList<Integer> accessList = new ArrayList<Integer>();
	private ArrayList<Integer> readAccessList = new ArrayList<Integer>();
	private ArrayList<Integer> writeAccessList = new ArrayList<Integer>();
	
	//TODO change name of "index" variable to be more reasonable :)
	private int index = -1;
	
	public static int getNumberOfVariables() {
		return numberOfVariables;
	}
	public static int getNumberOfInitLines() {
		return numberOfInitLines;
	}

	private static int numberOfVariables = 0;
	private static int numberOfInitLines = 2;
	
	
	public DqAsmLine(){
		super();
		initializeAsmLine();
		numberOfVariables++;
	}
	public DqAsmLine(String initString, int initIndex) {
		super(initString, initIndex);
		// TODO Auto-generated constructor stub
		initializeAsmLine();
			
		setVariableAddress();
		numberOfVariables++;
	}
	
	private void setVariableAddress() {
		variableAddress = numberOfVariables;
		String address = Integer.toString(variableAddress, 2);
		int addressLength = address.length();
		StringBuilder sb = new StringBuilder();
		
		sb = sb.append(nullString, 0, 8 - addressLength);
		sb = sb.append(address);
		
		variableAddressString = sb.toString();
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
	
	public void fillLineList(ArrayList<ExecAsmLine> executableCode){

		for (ExecAsmLine ex : executableCode){
			if (variableName.equalsIgnoreCase(ex.mOperandIn1)){
				readAccessList.add(ex.getIndex());
			}
			if (variableName.equalsIgnoreCase(ex.mOperandIn2)){
				readAccessList.add(ex.getIndex());
			}
			if (variableName.equalsIgnoreCase(ex.mOperandOut)){
				writeAccessList.add(ex.getIndex());
			}
		}		
	}
	
	// TODO Decode 
	public void addInfo(boolean operandIn1Found, boolean operandIn2Found, boolean operandOutFound, int index){
		if (operandIn1Found) {
			readAccessList.add(index);
		}
		if (operandIn2Found) {
			readAccessList.add(index);
		}
		if (operandOutFound) {
			writeAccessList.add(index);
		}

	}
	
	
	public void addLineIndex(int lineIndex){
		accessList.add(lineIndex);
	}
	
	public int getNextLineIndex(){
		return accessList.get(index);
	}
	
	public void printWriteAccessList(){
		System.out.println("write-");
		for (Integer i : writeAccessList){
			System.out.println(i + " ");
		}
	}
	
	public void printReadAccessList(){
		System.out.println("read-");
		for (Integer i : readAccessList){
			System.out.println(i + " ");
		}
	}
	
	public String toString(){
		String info = String.format("%s = %s address %d = %s", mOperandIn1, mOperandOut, variableAddress, variableAddressString);
		return info;
	}

}
