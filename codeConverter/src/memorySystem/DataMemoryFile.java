package memorySystem;

import asmLine.DqAsmLine;
import asmLine.ProgramCode;
import commonTypes.PrecisionType;

public class DataMemoryFile extends MemoryFile{
	
	public DataMemoryFile() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fillMemoryList(ProgramCode pcode, PrecisionType prType) {
		
		for (DqAsmLine dq: pcode.getDeclarationCode()){
			String line = dq.getmOperandOut().substring(2);
			linesList.add(line);
		}
	}

	@Override
	public void setPrecisionType(PrecisionType precType) {
		int precision = 0;
		switch (precType)
		{
		case singlePrecision:
			precision = 8;
			break;
		case doublePrecision:
			precision = 16;
			break;
		default:
			break;
		}
		precisionTypeString = String.format("%08X", precision);	
		
	}

}
