//Different Arrival Times
import java.util.*;

class Process5 {
    int pid;
    int at;
    int bt;
    int rem_bt;
    int wt;
    int tat;
    int ft;

    Process5(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.rem_bt = bt;
        this.wt = 0;
        this.tat =0;
        this.ft =0 ;
    }

}

public class RoundRobin2 {
	 static Process5 proc[];
	    static int quantum;
	    static int p;
	    static double avgwt = 0;
	    static double avgtat = 0;
	    
		static Queue<Process5> wait = new LinkedList<Process5>();
		static Queue<Process5> ready = new LinkedList<Process5>();
		static Queue<Process5> finish = new LinkedList<Process5>();
	    
	 public static void input(Scanner sc) {

	        System.out.println("Enter no of process");
	        p = sc.nextInt();
	        proc = new Process5[p];
	        System.out.println("Enter Time Quantam");
	        quantum = sc.nextInt();
	        for (int i = 0; i < p; i++) {
	            System.out.println("Enter Process number, ArrivalTime and Burst Time: ");
	            int pid = sc.nextInt();
	            int at = sc.nextInt();
	            int bt = sc.nextInt();
	            proc[i] = new Process5(pid,at,bt);
	            wait.add(proc[i]);
	        }
	    }
	    public static void print() {
	        System.out.println("Process\tBurstTime\tWaitingTime\tTurnAroundTime");
	        Iterator<Process5> it = finish.iterator();
			while(it.hasNext()) {
				Process5 i =  it.next();
				System.out.println(i.pid+"\t\t"+i.at+"\t\t"+i.bt+"\t\t"+i.wt+"\t\t"+i.tat);
			}
	        System.out.println("Average Waiting Time: " + avgwt);
	        System.out.println("Average Turn Around Time: " + avgtat);
	    }

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        input(sc);
	        roundrobin();
	        calcwaitingtime();
	        System.out.println("\nOutput of RoundRobin(Premptive) Scheduling: ");
	        print();
	        sc.close();
	    }
	    
	    public static void calcwaitingtime() {
	    	Iterator<Process5> it = finish.iterator();
			while(it.hasNext()) {
				Process5 i =  it.next();
				avgwt += i.wt;
				avgtat += i.tat;
			}
			avgwt /= p;
			avgtat/=p;
	    }
		public static void roundrobin() {
			Process5 p,q=null;
			int elapsed = 0;
			while(!wait.isEmpty() || !ready.isEmpty()) {
				while(!wait.isEmpty()) {
					p = wait.peek();
					if(p.at <= elapsed) {
						p = wait.remove();
						ready.add(p);
						
						//System.out.println(p.pid);
					}
					else {
						break;
					}
				}
				if(q!=null) {
					ready.add(q);
					//System.out.println(q.pid);
					q=null;
					
				}
				if(ready.isEmpty()) {
					elapsed++;
				}
				else {
					p = ready.remove();
					if(p.rem_bt>quantum) {
						p.rem_bt -= quantum;
						elapsed += quantum;
						q = p;
					}
					else {
						elapsed += p.rem_bt;
						p.rem_bt = 0;
						p.ft = elapsed;
						p.tat = p.ft- p.at;
						p.wt = p.tat- p.bt;
						finish.add(p);
					}
					
					
					
				}
			}
			
		}
}
