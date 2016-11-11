package memorySystem;

import java.util.ArrayList;

import asmLine.ProgramCode;
import commonTypes.PrecisionType;
import optimiser.Optimiser;

// TODO correct visibility levels

public class ProgramMemoryFile extends MemoryFile{

	ArrayList<String> programMemoryList = new ArrayList<String>();	
	Optimiser optimiser = new Optimiser();
	
	public ProgramMemoryFile() {
		// TODO Auto-generated constructor stub
	}
		
	@Override
	public void fillMemoryFile(String filePath) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPrecisionType(PrecisionType precType) {
		// currently only 1 precision type is available
		int numberOfDigits = 8;
		precisionTypeString = String.format("%08X", numberOfDigits);
	}

	@Override
	public void fillMemoryList(ProgramCode pcode, PrecisionType prType) {
		// TODO Auto-generated method stub
		
	}
	
	

}
