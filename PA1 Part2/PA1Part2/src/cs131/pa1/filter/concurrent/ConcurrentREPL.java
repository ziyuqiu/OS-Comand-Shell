package cs131.pa1.filter.concurrent;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class ConcurrentREPL {

	static String currentWorkingDirectory;
	public static List<Thread> threadList = new LinkedList<Thread>();
	//Hashtable jobs saves all threads working in the background
	static Hashtable<Integer, String> jobs = new Hashtable<Integer,String>();
	//counter for jobs
	public static int counter =0;
	
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		//open scanner
		Scanner s = new Scanner(System.in);
		//welcome message
		System.out.print(Message.WELCOME);
		//command stores the whole line of command
		String command;
		while(true) {
			//obtaining the command from the user
			System.out.print(Message.NEWCOMMAND);
			command = s.nextLine();
			//exit if prompted
			if(command.equals("exit")) {
				break;
			} 
			//print background 
			else if(command.equals("repl_jobs")){
				int num = 1;
				//print jobs working in the background in the required format
				for (String job : jobs.values()){
					System.out.println("\t"+num + ". "+job);
					num++;
				}
			}
			else {
				//if the command end with "&"
				if(command.endsWith("&")){
					//make the thread run in the background and save to jobs
					Thread mainThreads = new Thread(new Repl_Runnable(command.substring(0, command.length()-1).trim(), counter));
					threadList.add(mainThreads);				
					jobs.put(counter, command);
					counter++;
					mainThreads.start();
				} else {
					//create a new thread for each repl
					Thread mainThreads = new Thread(new Repl_Runnable(command, counter));									
					mainThreads.start();
					try {
						mainThreads.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}				
			}
		}
		//join all threads
		for (Thread mainThread : threadList){
			try {				
				mainThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//close console
		s.close();
		//goodbye message
		System.out.print(Message.GOODBYE);
	}

		
}
