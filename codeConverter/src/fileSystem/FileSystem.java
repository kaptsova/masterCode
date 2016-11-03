package fileSystem;

import java.util.ArrayList;

import commonTypes.ApplicationType;
import commonTypes.PrecisionType;

public abstract class FileSystem {

	private static FileSystem uniqueFileSystem;
	
	// fields 
	// 	- pathes to files
	protected ArrayList<String> inputFilePathList = new ArrayList<String>();
	public static FileSystem getUniqueFileSystem() {
		return uniqueFileSystem;
	}
	public  ArrayList<String> getInputFilePathList() {
		return inputFilePathList;
	}
	public String getDataMemoryFilePath() {
		return dataMemoryFilePath;
	}
	public String getProgMemoryFilePath() {
		return progMemoryFilePath;
	}
	public String getOpCodeFilePath() {
		return opCodeFilePath;
	}
	public String getInfoFilePath() {
		return infoFilePath;
	}
	public static String getFirstFileName() {
		return firstFileName;
	}
	public int getNumberOfFiles() {
		return numberOfFiles;
	}


	protected String dataMemoryFilePath = "";
	protected String progMemoryFilePath = "";
	protected String opCodeFilePath = "";
	protected String infoFilePath = "";
	
	
	protected static String firstFileName = "";
	// 	- number f input files to be converted
	protected int numberOfFiles = 0;
	
	String opCodeSglFilePath = "opcodes_sgl";
	String opCodeDblFilePath = "opcodes_dbl";
	
	// abstract methods
	protected abstract int setNumberOfFiles();
	protected abstract ArrayList<String> setInputFilePathList();
	
	// create singleton filesystem
	protected FileSystem(){;}
		
	public static FileSystem getInstance(ApplicationType appType){
		if (uniqueFileSystem == null)
		{
			switch (appType)
			{
			case consoleApplication:
				uniqueFileSystem = new FileSystem_Console();
				break;
			case guiApplication:
				uniqueFileSystem = new FileSystem_Gui();
				break;
			case fileBasedApplication:
				uniqueFileSystem = new FileSystem_Gui();
				break;
			default:
				uniqueFileSystem = new FileSystem_Console();
				break;
			}
		}
		return uniqueFileSystem;
	}
	
	public void initializeFileSystem(){
		setInputFilePathList();
		createOutputFiles(firstFileName);
		setOpCodeFilePath(PrecisionType.doublePrecision);
	}
	
	private void createOutputFiles(String fileName) 
	{
		String dataMemoryFileName = fileName.concat("-data");
		String progMemoryFileName = fileName.concat("-prog");
		String infoFileName = fileName.concat("-info");
		
		dataMemoryFilePath = FilePath.getRelativePath(dataMemoryFileName);
		progMemoryFilePath = FilePath.getRelativePath(progMemoryFileName);	
		infoFilePath = FilePath.getRelativePath(infoFileName);	
	}
	
	public String toString(){
		
		for (int i = 0; i < inputFilePathList.size(); i++)
		{
			System.out.println("File " + i + ": " + inputFilePathList.get(i));
		}
		System.out.println("Data memory file path: " + dataMemoryFilePath );
		System.out.println("Program memory file path: " + progMemoryFilePath);
		System.out.println("Common info file path: " + infoFilePath);
		
		System.out.println("OpCode file path: " + opCodeFilePath);
		System.out.println("\nFilePath class ends");	
		return "";
		
	}
	
	private String setOpCodeFilePath(PrecisionType prType){
		
		switch (prType)
		{
		case singlePrecision:
			opCodeFilePath = opCodeSglFilePath;
			break;
		case doublePrecision:
			opCodeFilePath = opCodeDblFilePath;
			break;
		default:
			break;
		}
		opCodeFilePath = FilePath.getRelativePath(opCodeFilePath);
		return "";
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FileSystem fs = FileSystem.getInstance(ApplicationType.consoleApplication);
		fs.initializeFileSystem();
		fs.toString();
				
	}

}
