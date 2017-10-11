package cs131.pa1.filter.concurrent;
import java.io.File;

public class LsFilter extends ConcurrentFilter{
	int counter;
	File folder;
	File[] flist;
	
	public LsFilter() {
		super();
		counter = 0;
		folder = new File(ConcurrentREPL.currentWorkingDirectory);
		flist = folder.listFiles();
	}
	
	@Override
	public void process() {
		while(counter < flist.length) {
			//changed add() to put()
			try {
				output.put(processLine(""));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//send ending signal
		DONE = true;
	}
	
	@Override
	public String processLine(String line) {
		return flist[counter++].getName();
	}
}
