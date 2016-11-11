package asmLine;

public class ResultForwardingItem {
	
	// TODO create reasonable names for asm lines
	// first and second are :(
	private ExecAsmLine firstAsmLine = null;
	private ExecAsmLine secondAsmLine = null;
	
	private int totalAluDelay = 0;
	private int totalPipeLineDelay = 0;
	
	public ResultForwardingItem() {
		// TODO Auto-generated constructor stub
	}
	
	public ResultForwardingItem(ExecAsmLine initFirstAsmLine, ExecAsmLine initSecondAsmLine){
		firstAsmLine = initFirstAsmLine;
		secondAsmLine = initSecondAsmLine;
	}

}
