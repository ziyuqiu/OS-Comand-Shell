package cs131.pa1.filter.concurrent;
import java.util.*;

public class Repl_Runnable implements Runnable {
	
	String command;
	int counter;
	
	public Repl_Runnable(String command, int counter){
		//command as value
		this.command = command;
		//counter as key
		this.counter = counter;
	}
	
	@Override
	public void run() {
		if(!command.trim().equals("")) {			
			//build the filters list from the command
			List<ConcurrentFilter> filterlist = ConcurrentCommandBuilder.createFiltersFromCommand(command);								
				
			//build threads based on filter list
			if(filterlist != null) {
				filterToThreads(filterlist);
			}
		}
		//remove the thread from job list after the thread finishes
		ConcurrentREPL.jobs.remove(counter);
	}
	
	private void filterToThreads(List<ConcurrentFilter> filterList){
		List<Thread> threads = new LinkedList<Thread>();
		// create threads for each filter
		for (ConcurrentFilter filter : filterList){
			threads.add(new Thread(filter));
		}
		// start threads
		for (Thread thread : threads){
			thread.start();
		}
		// join threads
		for (Thread thread : threads){
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
		}
	}

}