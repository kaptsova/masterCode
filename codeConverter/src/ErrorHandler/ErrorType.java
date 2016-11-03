package ErrorHandler;

public enum ErrorType {
	illegalError,
	inputError,
	outputError,
	
	fileNotFoundError,
	opCodeFileNotFoundError,
	
	wrongMnemonicError, 
	
	wrongOp1TypeError,
	wrongOp2TypeError, 
	wrongOp3TypeError,
	
	//wbNones
	delayError,
	wrongPrecisionError,
	
	multiplyDeclaredcVariableError, 	
	undeclaredVariableError,	
}
