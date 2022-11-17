import java.util.*;
public class RoundRobin {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no of process");
		int n = sc.nextInt();
        System.out.println("Enter Time Quantam");
        int quantum = sc.nextInt();
        int[] jobs= new int[n];
		int[] bursttime= new int[n];
		for(int i=0;i<n;i++){
			System.out.println("Enter Process number and Burst Time: ");
			jobs[i] = sc.nextInt();
			bursttime[i] = sc.nextInt();
		}
        int wt[] = new int[n];
        int[] rem_bt = new int[n];
        for(int i=0;i<n;i++){
            rem_bt[i] = bursttime[i];
        }
        int t = 0;
        while(true){
            boolean done = true;
            for(int i=0;i<n;i++){
                if(rem_bt[i]>0){
                    done = false;
                    if(rem_bt[i]>quantum){
                        t+=quantum;
                        rem_bt[i] -= quantum;
                    }
                    else{
                        t+=rem_bt[i];
                        wt[i] = t-bursttime[i];
                        rem_bt[i] =0;
                    }
                }
            }
            if(done==true){
                break;
            }
        }
		
        int[] tat = new int[n];
        for(int i=0;i<n;i++){
			tat[i] = wt[i]+ bursttime[i];
		}
        double avgwt=0;
		for(int i:wt){
			avgwt +=i;
		}
		avgwt/=n;
		double avgtat=0;
		for(int i:tat){
			avgtat +=i;
		}
		avgtat/=n;
		System.out.println("Process\t   BurstTime\t WaitingTime\t TurnAroundTime");
		for(int i=0;i<n;i++){
			System.out.println(jobs[i]+"\t\t"+bursttime[i]+"\t\t"+wt[i]+"\t\t"+tat[i]);
		}
		System.out.println("Average Waiting Time: "+avgwt);
		System.out.println("Average Turn Around Time: "+avgtat);
		sc.close();
    }
}

