package asmLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import codeConverter.Parser;
import commonTypes.CommandType;

public class ProgramCode {

	private ArrayList<AsmLine> programCode = new ArrayList<AsmLine>();
	private ArrayList<DqAsmLine> declarationCode = new ArrayList<DqAsmLine>();
	private ArrayList<ExecAsmLine> executableCode = new ArrayList<ExecAsmLine>();
	private ArrayList<OpCode> opCodeList = new ArrayList<OpCode>();
	
	
	public ProgramCode() {
		// TODO Auto-generated constructor stub
	}
	
	public void readProgramCode(final String programCodePath) {
		// Read the program code from file and fill the 
		HashMap <Integer, String> programCodeStrings = new HashMap <Integer, String>();
		programCodeStrings = Parser.readFromFile(programCodePath);
						 
		for (Map.Entry<Integer, String> e : programCodeStrings.entrySet())
        {
	    	AsmLine instance = AsmLine.createAsmLine(e.getValue(), e.getKey());
	    	if (instance.getCmdType().equals(CommandType.dqCommand)){
	    		declarationCode.add((DqAsmLine) instance);
	    	}
	    	else {
	    		executableCode.add((ExecAsmLine) instance);
	    	}
	    	programCode.add(instance);	  
	    }
	}
	
	public void readOperationCodes(final String opCodesPath) 
	{
		HashMap <Integer, String> opCodeStrings = new HashMap <Integer, String>();
		opCodeStrings = Parser.readFromFile(opCodesPath);
		
		for (Map.Entry<Integer, String> e : opCodeStrings.entrySet())
        {
	    	OpCode instance = new OpCode(e.getValue());
	    	opCodeList.add(instance);	
	    
	    }
	}
	
	private void handleProgramCode(){
		checkDeclarations();
		checkExecutableCode();
	}
	
	private void checkExecutableCode(){
		for (ExecAsmLine ex: executableCode){
		}
	}
	
	private void checkDeclarations(){
		for (DqAsmLine dq: declarationCode){
			
		}
	}
	
	
	public String toString(){
		for (AsmLine asmLine : programCode){
			System.out.print(asmLine.toString());
		}
		return "";
	}
	
	public void printDeclarations(){		
		System.out.println("All the declarations");
		
		for (DqAsmLine dq : declarationCode){
			System.out.print("Dq " + "\t" + dq.toString());
		}
	}
	
	public void printExecutable(){
		System.out.println("All executable code");
		for (ExecAsmLine exec : executableCode){
			System.out.print("Ex " + "\t" + exec.toString());
		}
	}
	
	public void printOpCodes(){
		System.out.println("All operation codes");
		for (OpCode opCode : opCodeList){
			System.out.print("opC\t" + opCode.toString());
		}
	}
	
	public void clearExecutable(){
		executableCode.clear();
	}
	
	public void printAllInfo(){
		printDeclarations();
		printExecutable();
		printOpCodes();
	}

	public static void main(String [] args){		
		ProgramCode pcode = new ProgramCode();
		pcode.readProgramCode("tests\\simple.txt");
		pcode.readOperationCodes("tests\\opcodes_dbl.txt");
		pcode.toString();
		
		
		pcode.printDeclarations();
		pcode.printExecutable();
		pcode.printOpCodes();
	}
	
}
