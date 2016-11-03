package codeConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import asmLine.AsmLine;
import asmLine.OpCode;
import asmLine.ProgramCode;
import commonTypes.ApplicationType;
import commonTypes.PrecisionType;
import fileSystem.FileSystem;

public class CodeConverter {
	
	public boolean errorOccured = false;
	private ApplicationType appType = ApplicationType.consoleApplication;
	//TODO Implement method to get precision type
	protected PrecisionType prType = PrecisionType.doublePrecision;
	
	
	public static void main(String[] args)
	{
		CodeConverter codeConverter = new CodeConverter();
		codeConverter.start();
	}
	
	public void start(){
		// initialize file system
		FileSystem fileSystem = FileSystem.getInstance(appType);
		fileSystem.initializeFileSystem();
		fileSystem.toString();
		
		executeProgramCode(fileSystem);
		
	}

	private void executeProgramCode(FileSystem fileSystem) {
		int numberOfFiles = fileSystem.getNumberOfFiles();
		ArrayList<String> filePaths = fileSystem.getInputFilePathList();
		ProgramCode pcode = new ProgramCode();
		pcode.readOperationCodes(fileSystem.getOpCodeFilePath());
		
		for (int i = 0; i < numberOfFiles; i++){
			
			String programCodePath = filePaths.get(i);			
			pcode.readProgramCode(programCodePath);
			
			pcode.printAllInfo();
			
			System.out.println("______________________________");
		}
	}
	
	public void old_Start(){
		FileSystem fs = FileSystem.getInstance(ApplicationType.consoleApplication);
		fs.initializeFileSystem();
		fs.toString();
		
		
		
		/*ArrayList<AsmLine> programCodeList = new ArrayList<AsmLine>();
		readProgramCode(programCodePathList.get(0), programCodeList);
		
		ArrayList<OpCode> opCodeList = new ArrayList<OpCode>();
		String opCodePath = FilePath.getOpCodeFilePath();
		readOperationCodes(opCodePath, opCodeList);
		
		if (checkMnemonics(programCodeList, opCodeList))
			Device.println("Yeah");
		else 
			Device.println("No");
		
		
		for (AsmLine e: programCodeList)
		{
			System.out.println(e.getmOperator()+ " " + e.checkAssemblerLine(opCodeList));
		}*/
	}
	
	
	private static boolean checkMnemonics(ArrayList<AsmLine> programCodeList, ArrayList<OpCode> opCodeList)
	{
		boolean status = false;
		String operator = "";
		for (int i = 0; i < programCodeList.size(); i++)
		{
			status = false;
			AsmLine AsmLine = programCodeList.get(i);
			operator = AsmLine.getmOperator();
			
			if (operator.equalsIgnoreCase("dq"))
				status = true;
			else
			{
				status = isRegularCommand(opCodeList, status, operator);
			}
			
			if (status == false)
			{
				return status;
			}
		}
		return status;
	}


	private static boolean isRegularCommand(ArrayList<OpCode> opCodeList, boolean status, String operator) {
		for (int j = 0; j < opCodeList.size(); j++)
		{
			OpCode opCode = opCodeList.get(j);
			String mnemonic = opCode.getMnemonic();
			if (operator.equalsIgnoreCase(mnemonic))
			{	
				status = true;
			}
		}
		return status;
	}

	

	
	

}
