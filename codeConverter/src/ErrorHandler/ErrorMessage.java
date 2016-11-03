package ErrorHandler;

import java.util.Formatter;

public class ErrorMessage {
	private static int errorCount = 0;
	private Formatter formatter = new Formatter(System.out);
	private ErrorType errorType = ErrorType.illegalError;
	
	private int errorId = -1;
	
	public int getErrorId()
	{
		return errorId;
	}
	private String errorString = null;
	
	public String getErrorString()
	{
		return errorString;
	}
	
	private int fileLineId = -1;
	
	private String filePath = null;
	
	public void print()
	{		
		formatter.format("Error %d of %d (%5s) occured ", errorId, errorCount, errorType);
		
		if (filePath != null)
			formatter.format(":\tline %d of file %s", fileLineId, filePath);
		
		formatter.format("\n");
		
		if (errorString != null)
			formatter.format("\tDetails: %s", errorString);
		
		formatter.format("\n");
	}
	

	
	public ErrorMessage()
	{
		print();
	}
	
	public ErrorMessage(ErrorType initErrorType)
	{
		errorType = initErrorType;
		errorCount++;
		errorId = errorCount;
	}
		
	public ErrorMessage(ErrorType initErrorType, int initFileLineId)
	{
		this(initErrorType);		
		fileLineId = initFileLineId;
	}
	
	public ErrorMessage(ErrorType initErrorType, int initFileLineId, String initFilePath)
	{
		this(initErrorType,initFileLineId);
		filePath = initFilePath;
	}
	
	public ErrorMessage(ErrorType initErrorType, int initFileLineId, String initFilePath, String initErrorString)
	{
		this(initErrorType,initFileLineId, initFilePath);
		errorString = initErrorString;
	}
	

	public static void main(String[] args)
	{
		System.out.println("Error message log:");
		ErrorMessage err1 = new ErrorMessage(ErrorType.inputError, 23, "/nata.txt","It's error");
		err1.print();
		ErrorMessage err2 = new ErrorMessage(ErrorType.outputError, 57, "/slava.txt");
		err2.print();
	}
}
