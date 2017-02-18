package memorySystem;

import java.util.ArrayList;

import asmLine.DeclarationLine;
import asmLine.ExecutableLine;
import asmLine.ExecutableLine.Operator;
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
	public void setPrecisionType(PrecisionType precType) {
		// currently only 1 precision type is available
		int numberOfDigits = 8;
		precisionTypeString = String.format("%08X", numberOfDigits);
	}

	@Override
	public void fillMemoryList(ProgramCode pcode, PrecisionType prType) {
		if (pcode.isToOptimise()){
			fillMemoryList_optimised(pcode, prType);
		} 
		else {
			fillMemoryList_notOptimised(pcode, prType);
		}	
	}
	
	
	private void fillMemoryList_optimised(ProgramCode pcode, PrecisionType prType){
		
	}
	
	private void fillMemoryList_notOptimised(ProgramCode pcode, PrecisionType prType){
		
		// Add nops
		for (int i = 0; i < pcode.getSetupTime(); i++){
			linesList.add("00380000");
		}
		
		for (ExecutableLine ex: pcode.getExecutableCode()){
			Operator operator = ex.getOperator();
			// Calculate
			String calcLine = operator.getCalculationOperation();
			linesList.add(calcLine);
			// Add nops
			for (int i = 0; i < operator.getAluDelay() - 1; i++){
				linesList.add("00380000");
			}
			String wbLine = operator.getWriteBackOperation();
			linesList.add(wbLine);
			
			// Add nops
			for (int i = 0; i < operator.getPipeDelay(); i++){
				linesList.add("00380000");
			}
		}	
	}
	

}
