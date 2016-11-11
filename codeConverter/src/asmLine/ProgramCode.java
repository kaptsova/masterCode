package asmLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import codeConverter.Parser;
import commonTypes.CommandType;
import commonTypes.PrecisionType;
import fileSystem.FileSystem;
import memorySystem.DataMemoryFile;
import memorySystem.ProgramMemoryFile;

public class ProgramCode {

	private ArrayList<AsmLine> programCode = new ArrayList<AsmLine>();
	private ArrayList<DqAsmLine> declarationCode = new ArrayList<DqAsmLine>();
	
	public ArrayList<DqAsmLine> getDeclarationCode() {
		return declarationCode;
	}

	private ArrayList<ExecAsmLine> executableCode = new ArrayList<ExecAsmLine>();
	public ArrayList<ExecAsmLine> getExecutableCode() {
		return executableCode;
	}
	private ArrayList<OpCode> opCodeList = new ArrayList<OpCode>();
	
	DataMemoryFile datamem = new DataMemoryFile();
	ProgramMemoryFile progmem = new ProgramMemoryFile();
	
	
	public ProgramCode() {
		// TODO Auto-generated constructor stub
	}
	
	public void readProgramCode(final String programCodePath, PrecisionType prType) {
		// Read the program code from file and fill the 
		HashMap <Integer, String> programCodeStrings = new HashMap <Integer, String>();
		programCodeStrings = Parser.readFromFile(programCodePath);
						 
		for (Map.Entry<Integer, String> e : programCodeStrings.entrySet())
        {
	    	AsmLine instance = AsmLine.createAsmLine(e.getValue(), e.getKey(), prType);
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
	
	public void handleProgramCode(FileSystem fileSystem, PrecisionType precType){		
		checkExecutableCode();
		checkDeclarations();
		datamem.initializeMemoryFile(this, precType);
		datamem.toString();
		datamem.fillMemoryFile(fileSystem.getDataMemoryFilePath());
	}
	
	private void checkExecutableCode(){
		for (ExecAsmLine ex: executableCode){
			ex.checkAssemblerLine(opCodeList, declarationCode);
			//System.out.println(ex.getStatusInfo());
		}
	}
	
	private void checkDeclarations(){
		for (DqAsmLine dq: declarationCode){
			dq.fillLineList(executableCode);	
			System.out.println(dq.toString());
			dq.printReadAccessList();
			dq.printWriteAccessList();			
		}
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
	
	public String toString(){
		for (AsmLine asmLine : programCode){
			System.out.print(asmLine.toString());
		}
		return "";
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
		pcode.readProgramCode("tests\\dependent.txt", PrecisionType.doublePrecision);
		pcode.readOperationCodes("tests\\opcodes_dbl.txt");
		pcode.toString();
		
		
		pcode.printDeclarations();
		pcode.printExecutable();
		pcode.printOpCodes();
				
	}
	
}
