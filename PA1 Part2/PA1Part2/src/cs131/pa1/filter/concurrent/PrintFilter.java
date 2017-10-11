package cs131.pa1.filter.concurrent;

public class PrintFilter extends ConcurrentFilter {
	public PrintFilter() {
		super();
	}
	
	public void process(){
		//if the previous filter is done or there is any nonempty input, start excecuting
		while(!prev.isDone()||!input.isEmpty()){
			//while there is nonempty input, keep processing each line
			while (!input.isEmpty()){
				String line;
				try {
					//change poll() to take() for possible waiting if necessary until an element becomes available
					line = input.take();								
					processLine(line);
				} catch(InterruptedException e){
					e.printStackTrace();
				}	
			}
		}
		//set done signal
		DONE = true;
	}

	public String processLine(String line) {
		System.out.println(line);
		return null;
	}
}
