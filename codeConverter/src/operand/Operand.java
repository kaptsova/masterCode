package operand;

import java.util.ArrayList;

import asmLine.DeclarationLine;

public class Operand {

	private ArrayList<Integer> readAccessList = new ArrayList<Integer>();
	private ArrayList<Integer> writeAccessList = new ArrayList<Integer>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OperandType getOpType() {
		return opType;
	}

	public void setOpType(OperandType opType) {
		this.opType = opType;
	}

	public int getAddressIndex() {
		return addressIndex;
	}

	public void setAddressIndex(int addressIndex) {
		this.addressIndex = addressIndex;
	}

	public boolean isFound() {
		return isFound;
	}

	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}


	private String name = "";
	private OperandType opType = OperandType.illegalOperand;
	
	private int addressIndex = -1;
	private String addressString = "00000000";
	
	public String getAddressString() {
		return addressString;
	}

	public void setAddressString(String addressString) {
		this.addressString = addressString;
	}


	private boolean isFound = false;
	
	public Operand() {
		// TODO Auto-generated constructor stub
	}
	
	public Operand(String initName){
		name = initName;
	}
	
	public String toString(){
		return String.format("%s - %s", name, opType);
	}

	public boolean findVariable(DeclarationLine dq){
		String variableName = dq.getVariableName();
		if (!isFound()){
			if ((this.getOpType() == OperandType.variableOperand) &&(this.getName().equalsIgnoreCase(variableName))){			
				this.setFound(true); 
				this.setAddressIndex(dq.getVariableAddress());
				this.setAddressString(dq.getVariableAddressString());
				return true;
			}
			else if (this.getOpType() != OperandType.variableOperand){
				this.setFound(true);
				return false;
			}
			else 
				return false;
		}
		else	
			return false;
	}
	
	public static void main(String[] args) {

	}

}
