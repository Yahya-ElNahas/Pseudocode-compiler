package osProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Program {

	LinkedList instructionList;
	LinkedList memory;
	int burstTime;
	static int numOfPrograms = 0;
	int pid;
    String name;
    static int totalTime = 0;
    static int noFiles = 0;
	
	public Program(File file) throws Exception
	{
		instructionList = new LinkedList();
		memory = new LinkedList();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		burstTime = 0;
		while((line = br.readLine()) != null) {
			instructionList.insertLast(line);
			burstTime++;
			totalTime++;
		}
		pid = ++numOfPrograms;
		name = "Program " + pid;
	}
	
	public boolean isEmpty()
	{
		return instructionList.isEmpty();
	}
	
	public void execute()
	{
		String s = (String)instructionList.removeFirst();
		System.out.println(s);
		String[] a = s.split(" ");
		switch((String)a[0]) {
		case "assign": 
			if(a.length == 3 && ((String)a[2]).equals("input"))
				assign((String)a[1]);
			else if(a.length == 4 && ((String)a[2]).equals("readFile")) {
				try {
					readFile((String)a[1], (String)a[3]);
				} catch (IOException e) {
					System.out.println("File not Found");
				}
			}
			else if(a.length == 5)
				assign((String)a[1], a[3], a[4], (String)a[2]);
			break;
		case "print":
			if(a.length == 2)
				print(a[1]);
			break;
		case "writeFile":
			try {
				writeFile(a);
			} catch(IOException e) {
				System.out.println("Error Writing File");
			}
			break;
		case "": System.out.print("");break;
		default: 
			System.out.println("Error");
			break;
		}
		burstTime--;
		totalTime--;
	}
	
	public void assign(String var, Object o1, Object o2, String op)
	{
		Object x1 = memory.search(o1);
		Object x2 = memory.search(o2);
		Link l = (Link)memory.searchLink(var);
		if(x1 == null || x2 == null) {
			System.out.println("Error");
			return;
		}
		String s = (String)x1;
		String[] a = s.split(" ");
		x1 = Integer.parseInt(a[2]);
		s = (String)x2;
		a = s.split(" ");
		x2 = Integer.parseInt(a[2]);
		if(l != null) {
			switch (op) {
			case "add": l.data = var + " = " + ((int)x1 + (int)x2);break;
			case "subtract": l.data = var + " = " + ((int)x1 - (int)x2);break;
			case "multiply": l.data = var + " = " + ((int)x1 * (int)x2);break;
			case "divide": l.data = var + " = " + ((int)x1 / (int)x2);break;
			}
		}
		switch(op) {
		case "add": memory.insertLast(var + " = " + ((int)x1 + (int)x2));break;
		case "subtract": memory.insertLast(var + " = " + ((int)x1 - (int)x2));break;
		case "multiply": memory.insertLast(var + " = " + ((int)x1 * (int)x2));break;
		case "divide": memory.insertLast(var + " = " + ((int)x1 / (int)x2));break;
		}
	}
	
	public void assign(String var)
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print(var + " = ");
		Object o = sc.nextLine();
		memory.insertLast(var + " = " + o);
	}
	
	public void print(Object o)
	{
		Object val = memory.search(o);
		if(val == null) {
			System.out.println((String)o + " Not Found");
			return;
		}
		System.out.println(val);
	}
	
	public void readFile(String var, String filePath) throws IOException
	{
		String p = (String)memory.search(filePath);
		if(p == null) {
			System.out.println("File Not Found");
			return;
		}
		String[] path = p.split(" = ");
		File file = new File(path[1]);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		memory.insertLast(var + " = " + line);
	}
	
	public void writeFile(String[] a) throws IOException
	{
		File file = new File("C:\\Users\\Karas\\Desktop\\Created Files\\newFile" + ++noFiles + ".txt");		// path of created file
		file.createNewFile();
		FileWriter fr = new FileWriter(file);
		for(int i = 1;i < a.length;i++) {
			Object x = memory.search(a[i]);
			if(x == null) {
				System.out.println("Error");
				return;
			}
			fr.write((String)x + "\n");
		}
		fr.close();
	}
	
	public String toString()
	{
		return name;
	}
}








