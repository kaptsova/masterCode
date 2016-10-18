package codeConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CodeConverter {
	
	
	public static void main(String[] args)
	{
		// Paths to program code and to the list of available operation codes
		final String programCodePath = "D:\\textFiles\\simple.txt";
		final String opCodesDblPath = "D:\\textFiles\\opCodes_dbl.txt";
		final String opCodesSglPath = "D:\\textFiles\\opCodes_sgl.txt";
				
		FilePath.initializeFilePathes();
		
		ArrayList<AssemblerLine> programCodeList = new ArrayList<AssemblerLine>();
		readProgramCode(programCodePath, programCodeList);
		
		ArrayList<OpCode> opCodeList = new ArrayList<OpCode>();
		readOperationCodes(opCodesDblPath, opCodeList);
		
		if (checkMnemonics(programCodeList, opCodeList))
			Device.println("Yeah");
		else 
			Device.println("No");
		
	}
	
	
	private static boolean checkMnemonics(ArrayList<AssemblerLine> programCodeList, ArrayList<OpCode> opCodeList)
	{
		boolean status = false;
		String operator = "";
		for (int i = 0; i < programCodeList.size(); i++)
		{
			status = false;
			AssemblerLine assemblerLine = programCodeList.get(i);
			operator = assemblerLine.getmOperator();
			
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

	private static void readOperationCodes(final String opCodesPath, ArrayList<OpCode> opCodeList) 
	{
		HashMap <Integer, String> opCodeStrings = new HashMap <Integer, String>();
		opCodeStrings = Parser.readFromFile(opCodesPath);
		
		for (Map.Entry<Integer, String> e : opCodeStrings.entrySet())
        {
            System.out.println(e.getKey() + " - " + e.getValue());
            
        }
		
		for (Map.Entry<Integer, String> e : opCodeStrings.entrySet())
        {
	    	OpCode instance = new OpCode(e.getValue());
	    	opCodeList.add(instance);	    	
	    }
	}

	private static void readProgramCode(final String programCodePath, ArrayList<AssemblerLine> programCodeList) {
		// Read the program code from file and fill the 
		HashMap <Integer, String> programCodeStrings = new HashMap <Integer, String>();
		programCodeStrings = Parser.readFromFile(programCodePath);
		
		for (Map.Entry<Integer, String> e : programCodeStrings.entrySet())
        {
			System.out.println(e.getKey() + " - " + e.getValue());	
        }
				 
		for (Map.Entry<Integer, String> e : programCodeStrings.entrySet())
        {
	    	AssemblerLine instance = new AssemblerLine(e.getValue(), e.getKey());
	    	programCodeList.add(instance);	    	
	    }
	}
	

}
