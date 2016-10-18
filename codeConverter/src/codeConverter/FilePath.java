package codeConverter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//TODO: make it singleton

public class FilePath {

	private static ArrayList<String> inputFilePathList = new ArrayList<String>();
	private static String dataMemoryFilePath = "";
	private static String progMemoryFilePath = "";
	private static String firstFileName = "";
	
	public static ArrayList<String> getInputFilePathList() {
		return inputFilePathList;
	}

	public static String getDataMemoryFilePath() {
		return dataMemoryFilePath;
	}

	public static String getProgMemoryFilePath() {
		return progMemoryFilePath;
	}
		
	public static void initializeFilePathes()
	{
		fillFilePathList();
		createOutputFiles(firstFileName);		
	}
	
	private static ArrayList<String> fillFilePathList()
	{		
		int numberOfFiles = getNumberOfFiles();
		boolean isFirst = true;
		
		for (int i = 0; i < numberOfFiles; i++)
		{	
			Device.print(i + ": ");
			String filePath = getFilePath(isFirst);
			if (!filePath.isEmpty())
			{
				inputFilePathList.add(filePath);
			}
			isFirst = false;
		}
			
		return inputFilePathList;
	}
	
	private static int getNumberOfFiles()
	{
		Scanner sc = new Scanner(System.in);
		int count = 0;
		Device.println("Enter the number of files: ");
		
		if (sc.hasNextInt())
			count = sc.nextInt();
		else
		{	
			Device.println("Warning: need to enter an integer");
		}
		return count;
	}
	
	private static String getFilePath(boolean isFirst)
	{
		Scanner sc = new Scanner(System.in);
		String filePath = "";
		Device.println("Enter the name of an ASM-file you want to work with (without .txt): ");
		String fileName = sc.nextLine();
		filePath = getRelativePath(fileName);
		if (isFirst)
			firstFileName = fileName;

		return filePath;
	}
	

	private static void createOutputFiles(String fileName) 
	{
		String dataMemoryFileName = fileName.concat("-data");
		String progMemoryFileName = fileName.concat("-prog");
		
		dataMemoryFilePath = getRelativePath(dataMemoryFileName);
		progMemoryFilePath = getRelativePath(progMemoryFileName);
		
	}

	private static String getRelativePath(String fileName)
	{
		String directoryPath = "D:\\prog\\";
		// Add .txt
		String filePath = fileName.concat(".txt");	
		String relativePath = directoryPath.concat(filePath);
		return relativePath;
	}

	
	private static boolean isFilePathCorrect(String filePath) 
	{
		boolean isCorrect = true;
		
		
		return isCorrect;
	}
	

	public static void main(String[] args)
	{
		Device.println("FilePath class begins\n");
		
		
		initializeFilePathes();
		for (int i = 0; i < inputFilePathList.size(); i++)
		{
			Device.println("File " + i + ": " + inputFilePathList.get(i));
		}
		Device.println("Data memory file path: " + dataMemoryFilePath );
		Device.println("Program memory file path: " + progMemoryFilePath);
		
		Device.println("\nFilePath class ends");		
	}

}
