package osProject;

import java.io.File;

public abstract class Run {
	
	static LinkedList readyQueue = new LinkedList();
	static int ClockCycle = 0;
	static LinkedList executedProcesses = new LinkedList();
	static String ganttCycle = "";
	
	public static void run(String f) throws Exception
	{
		File file = new File(f);
		readyQueue.insertLast(new Program(file));
	}
	
	public static void showProgram(Program p)
	{
		Link current = p.instructionList.head;
		while(current != null) {
			System.out.println(current);
			current = current.next;
		}
	}
	
	public static void useRoundRobin(int quantum)
	{
		while(!readyQueue.isEmpty()) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Ready Queue: " + readyQueue + "\n");
			
			Program p = (Program)readyQueue.removeFirst();
			System.out.println(p.name + " on CPU: \n");
			
			for(int i = 0;i < quantum && !p.isEmpty();i++) {
				
				System.out.println("Clock cycle " + ++ClockCycle);
				System.out.println("..............................");// basically for the number of rounds it will excecute or the clock cycle
				p.execute();
				System.out.println("\n" +  p.name + "  is excecuting...");
				ganttCycle += p.pid;
				System.out.println("Memeory: ");
				System.out.println(p.name + " Memory: " + p.memory + "\n");
			}
			if(!p.isEmpty()) {
				readyQueue.insertLast(p);
				
			} else 
				executedProcesses.insertLast(p);
			System.out.println("\n" + p.name + " is Done");
			System.out.println("\n" + "Ready Queue after execution of " + p.name + ": " + readyQueue + "\n");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
	}
	
	public static void useShortestJobFirst()
	{ 
		while(!readyQueue.isEmpty()) {
			System.out.println("Ready Queue: " + readyQueue + "\n");
			Program p = checkShortestJob();
			System.out.println(p.name + " on CPU: \n");
			while(!p.isEmpty()) {
				System.out.println("Excecution " + ++ClockCycle); 
				p.execute();
				ganttCycle += p.pid;
				System.out.println("Memeory: ");
				System.out.println(p.name + " Memory: " + p.memory + "\n");
			}
			System.out.println("\n" +  p.name + "  is excecuting...");
			readyQueue.remove(p);
			executedProcesses.insertLast(p);
			System.out.println(p.name + " is Done");
			System.out.println("\n" + "Ready Queue after execution of " + p.name + ": " + readyQueue + "\n");
			System.out.println();
		}
	}
	
	public static Program checkShortestJob()
	{
		Program program = (Program)readyQueue.getFirst();
		int burst = program.burstTime;
		for(int i = 0;i < readyQueue.getSize();i++) {
			Program p = (Program)readyQueue.removeFirst();
			int b = p.burstTime;
			if(b < burst) {
				burst = b;
				program = p;
			}
			readyQueue.insertLast(p);
		}
		return program;
	}
	
	static void printGanttChart()
	{
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("Gantt Chart: \n");
		System.out.println("Clock Cycle  |  Process ID");
		for(int i = 0;i < ClockCycle;i++) {
			System.out.print("     " + (i+1));
			if(i < 9)
				System.out.print("             P" + ganttCycle.charAt(i));
			else 
				System.out.print("            P" + ganttCycle.charAt(i));
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		// File paths
		run("C:\\Users\\Karas\\Desktop\\Semester files\\Operating Systems\\Project\\Program_1.txt");
		run("C:\\Users\\Karas\\Desktop\\Semester files\\Operating Systems\\Project\\Program_2.txt");
		run("C:\\Users\\Karas\\Desktop\\Semester files\\Operating Systems\\Project\\Program_3.txt");
		
		useRoundRobin(2);	// Round Ribon
		//useShortestJobFirst();	// SJF
		
		printGanttChart();
	}
}