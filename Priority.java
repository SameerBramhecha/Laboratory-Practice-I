import java.util.*;

public class Priority{
    
    public static void main(String[] args){
        //Creating Object of Scanner Class to read input from user
        Scanner sc = new Scanner(System.in);
        //Taking input for the number of processes.
        System.out.println("Enter Number of process: ");
        int n = sc.nextInt();
        String pro[] = new String[n];
        int[] at = new int[n];
        int []bt = new int[n];
        int prior[] = new int[n];
        //Accepting ProcessID, Arrival time, Burst time and Priority from the user
        for(int i=0;i<n;i++){
           System.out.println("Enter Process Id, arrival time, burst time and priority");
            pro[i] = sc.next();
            at[i] = sc.nextInt();
            bt[i] = sc.nextInt();
            prior[i] = sc.nextInt();
        }
        /*Sorting the processes based on arrival time
         * if the arrival time is same then
         * sorting is done on the basis of priority
         */
        for(int i=0;i<n;i++){
            for(int j=0;j<(n-i-1);j++){
                if(at[j]>at[j+1]){
                    //swapping arrival time
                    int temp1 = at[j];
                    at[j] = at[j+1];
                    at[j+1] = temp1;
                    //swapping burst time
                    int temp2 = bt[j];
                    bt[j] = bt[j+1];
                    bt[j+1] = temp2;
                    //swapping process ID
                    String temp3 = pro[j];
                    pro[j] = pro[j+1];
                    pro[j+1] = temp3;
                    //Swapping priority
                    int temp4 = prior[j];
                    prior[j] = prior[j+1];
                    prior[j+1] = temp4;
                }
                else if(at[j]==at[j+1]){
                    if(prior[j]>prior[j+1]){
                        int temp1 = at[j];
                        at[j] = at[j+1];
                        at[j+1] = temp1;

                        int temp2 = bt[j];
                        bt[j] = bt[j+1];
                        bt[j+1] = temp2;

                        String temp3 = pro[j];
                        pro[j] = pro[j+1];
                        pro[j+1] = temp3;

                        int temp4 = prior[j];
                        prior[j] = prior[j+1];
                        prior[j+1] = temp4;
                    }
                }
            }
        }
        int []ft = new int[n];
        //Finding Finish Time for each process
        ft[0] = bt[0] + at[0];
        for(int i=1;i<n;i++){
            ft[i] = bt[i] + ft[i-1];
        }
        //Finding TurnAroundTime for each process
        int tat[] = new int[n];
        for(int i=0;i<n;i++){
            tat[i] = ft[i] - at[i];
        }
        //Finding Waiting time for each process
        int wt[] = new int[n];
        wt[0] = tat[0] - bt[0];
        for(int i=1;i<n;i++){
            wt[i] = tat[i] - bt[i];
        }
        double avgwt=0;
        //Finding average waiting time
        for(int i:wt){
            avgwt+=i;
        }
        avgwt/=n;
        //Finding average Turn Around Time
        double avgtat=0;
        for(int i:tat){
            avgtat+=i;
        }
        avgtat/=n;
        System.out.println("Process\t BurstTime\tArrival Time\tPriority\t WaitingTime\tFinish Time\t TurnAroundTime");
		for (int i = 0; i < n; i++) {
			System.out.println(
					pro[i] + "\t\t" + bt[i] + "\t\t" + at[i] +"\t\t"+ prior[i]+ "\t\t" + wt[i] +"\t\t" +ft[i]+  "\t\t\t"
							+ tat[i]);
		}
		System.out.println("Average Waiting Time: " + avgwt);
		System.out.println("Average Turn Around Time: " + avgtat);
		sc.close();
    }
}