package cs131.pa1.filter.concurrent;
import java.util.concurrent.*;

import cs131.pa1.filter.Filter;


public abstract class ConcurrentFilter extends Filter implements Runnable {
	
	//changed from Queue to LinkedBlockingQueue for possible waiting
	protected LinkedBlockingQueue<String> input;
	protected LinkedBlockingQueue<String> output;
	//done signal
	public boolean DONE=false;
		
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof ConcurrentFilter){
			//changed variable name from "sequentialNext" to "concurrentNext" for better understanding
			ConcurrentFilter concurrentNext = (ConcurrentFilter) nextFilter;
			this.next = concurrentNext;
			concurrentNext.prev = this;
			if (this.output == null){
				//change type
				this.output = new LinkedBlockingQueue<String>();
			}
			concurrentNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}
	
	public void process(){
		while(!prev.isDone() || !input.isEmpty()){
			//keep processing line while input is not empty 
			while (!input.isEmpty()){				
				String line;
				try {
					//change poll() to take() for possible waiting if necessary until an element becomes available
					line = input.take();
					String processedLine = processLine(line);
					if(processedLine !=null){
						output.put(processedLine);
					}
				} catch(InterruptedException e){
						e.printStackTrace();
				}				
			}
		}
		//set done signal
		DONE = true;
	}
	
	@Override
	public boolean isDone() {
		return DONE;
	}
	
	protected abstract String processLine(String line);
	
	//this method is crucial to implement RUNNABLE, its easier to add it here rather than add it into each filters
	public void run(){
		//call process()
		process();
		//System.out.println(this.getClass().getName().toString());
	}
	
}
