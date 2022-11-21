//Consideriing Different Arrival Times

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

class Process4{
	int pid;
	int at;
	int bt;
	int priority;
	int wt;
	int tat;
	int ft;
	
	Process4(int pid,int at,int bt,int priority){
		this.pid = pid;
		this.at = at;
		this.bt = bt;
		this.priority = priority;
		this.ft = 0;
		this.wt = 0;
	}
	
	public int prior() {
		return priority;
	}
}
class Comp implements Comparator<Process4>{
	public int compare(Process4 a, Process4 b) {
		return a.prior() - b.prior();
	}
}
public class Priority2{
	
	static Process4 proc[];
	static int p;
	static double avgwt=0;
	static double avgtat=0;
	static Queue<Process4> wait = new LinkedList<Process4>();
	static Queue<Process4> ready = new PriorityQueue<Process4>(new Comp());
	static Queue<Process4> finish = new LinkedList<Process4>();
	
	public static void input(Scanner sc) {
		System.out.println("Enter no. of processes:");
		p = sc.nextInt();
		
		proc = new Process4[p];
		
		for(int i=0;i<p;i++) {
			
			System.out.println("Enter Process No.: ");
			int pid = sc.nextInt();
			System.out.println("Enter Arrival Time: ");
			int at = sc.nextInt();
			System.out.println("Enter Burst Time: ");
			int bt = sc.nextInt();
			System.out.println("Enter Priority: ");
			int priority = sc.nextInt();
			proc[i] = new Process4(pid,at,bt,priority);
			wait.add(proc[i]);
		}
	}
	
	public static void print() {
		System.out.println("ProcessNo.\tArrivalTime\tBurstTime\tPriority\tWaitingTime\tTurnAroundTime");
		Iterator<Process4> it = finish.iterator();
		while(it.hasNext()) {
			Process4 i =  it.next();
			System.out.println(i.pid+"\t\t"+i.at+"\t\t"+i.bt+"\t\t"+i.priority+"\t\t"+i.wt+"\t\t"+i.tat);
		}
		
		
		System.out.println("Average Waiting Time: " + avgwt);
		System.out.println("Average Turn Around Time: " + avgtat);
	}
	public static void priorityScheduling() {
		Process4 p;
		int elapsed = 0;
		while(!ready.isEmpty() || !wait.isEmpty()) {
			while(!wait.isEmpty()) {
				p = wait.peek();
				if(p.at <= elapsed) {
					p = wait.remove();
					ready.add(p);
				}
				else {
					break;
				}
			}
			if(ready.isEmpty()) {
				elapsed++;
			}
			else {
				p = ready.remove();
				p.ft = elapsed + p.bt;
				elapsed = p.ft;
				p.tat = p.ft- p.at;
				p.wt = p.tat- p.bt;
				finish.add(p);
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		input(sc);
		priorityScheduling();
		System.out.println("Input Sorted According to Priority: ");
		System.out.println("ProcessNo.\tBurstTime\tPriority");
		for(int i=0;i<p;i++) {
			System.out.println(proc[i].pid+"\t\t"+proc[i].bt+ "\t\t"+proc[i].priority);
		}
		
		System.out.println("\nOutput of Priority Scheduling: ");
		print();
		
	}
}