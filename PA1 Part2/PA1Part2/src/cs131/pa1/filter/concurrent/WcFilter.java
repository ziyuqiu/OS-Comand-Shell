package cs131.pa1.filter.concurrent;

public class WcFilter extends ConcurrentFilter {
	private int linecount=0;
	private int wordcount=0;
	private int charcount=0;
	
	public WcFilter() {		
		super();
	}
	public void process() {
		//if previous filter is done, or there is any nonempty input, start processing
		while(!prev.isDone()||!input.isEmpty()){
			//while input is not empty, keep processing line
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
		try {
			//add wordcount results to output
			output.put(linecount + " " + wordcount + " " + charcount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//set done signal
		DONE = true;
	}	
	
	public String processLine(String line) {
		// count the lines
		linecount++;
		// count the words
		if(line != null && line.length() > 0){
			String[] words = line.trim().split("\\s+");
			for (String word :words){
				//ignore space or empty string
				if(!word.equals(" ")&&!word.equals("")){
					wordcount ++;
				}
			}
		}
		// count the characters
		charcount += line.length();
		return null;
	}
}
