import java.util.*;

public class FCFS {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Number of Processes");
		int n = sc.nextInt();
		int pn[] = new int[n];
		int bt[] = new int[n];
		for (int i = 0; i < n; i++) {
			System.out.println("Enter process number: ");
			pn[i] = sc.nextInt();
			System.out.println("Enter Burst Time: ");
			bt[i] = sc.nextInt();
		}

		int wt[] = new int[n];
		int tat[] = new int[n];
		wt[0] = 0;
		for (int i = 1; i < n; i++) {
			wt[i] = bt[i - 1] + wt[i - 1];
		}
		for (int i = 0; i < n; i++) {
			tat[i] = bt[i] + wt[i];
		}
		double avgwt = 0;
		for (int i : wt) {
			avgwt += i;
		}
		avgwt /= n;
		double avgtat = 0;
		for (int i : tat) {
			avgtat += i;
		}
		avgtat /= n;

		System.out.println("Process No.\tBurst Time\tWaiting Time\tTurnAroundTime");
		for (int i = 0; i < n; i++) {
			System.out.println(pn[i] + "\t\t" + bt[i] + "\t\t" + wt[i] + "\t\t" + tat[i]);
		}
		System.out.println("Average Waiting Time: ");
		System.out.println(avgwt);
		System.out.println("Average TurnAround Time: ");
		System.out.println(avgtat);
	}
}
