package cs131.pa1.filter.concurrent;

import java.io.File;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class CdFilter extends ConcurrentFilter {
	private String dirToSet;
	
	public CdFilter(String line) throws Exception {
		super();
		dirToSet = ConcurrentREPL.currentWorkingDirectory;
		String[] args = line.trim().split(" ");
		//cmd cd need a parameter(path)
		if(args.length == 1) {
			System.out.printf(Message.REQUIRES_PARAMETER.toString(), line.trim());
			throw new Exception();
		}
		//back to one directory up in the directory hierarchy
		if(args[1].equals("..")) {
			String current = ConcurrentREPL.currentWorkingDirectory;
			current = current.substring(0, current.lastIndexOf(Filter.FILE_SEPARATOR));
			dirToSet = current;
		} else if (!args[1].equals(".")) { //general situation
			String current = ConcurrentREPL.currentWorkingDirectory;
			current = current + Filter.FILE_SEPARATOR + args[1];
			File test = new File(current);
			if (test.isDirectory()) {
				dirToSet = current;
			} else {
				System.out.printf(Message.DIRECTORY_NOT_FOUND.toString(), line);
				throw new IllegalArgumentException();
			}
		}
	}
	
	public void process() {
		processLine("");
		//set done signal
		DONE = true;
	}
	
	public String processLine(String line) {
		ConcurrentREPL.currentWorkingDirectory = dirToSet;
		return null;
	}

}
