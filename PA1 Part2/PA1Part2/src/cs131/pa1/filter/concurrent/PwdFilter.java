package cs131.pa1.filter.concurrent;

public class PwdFilter extends ConcurrentFilter {
	public PwdFilter() {
		super();
	}
	
	public void process() {		
		try {
			//changed add() to put()
			output.put(processLine(""));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DONE =true;
	}
	
	public String processLine(String line) {
		return ConcurrentREPL.currentWorkingDirectory;
	}
}
