import java.util.*;

public class FCFS {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter no of process");
		int n = sc.nextInt();
		ArrayList<Integer> jobs = new ArrayList<>();
		ArrayList<Integer> bursttime = new ArrayList<>();
		ArrayList<Integer> at = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			System.out.println("Enter Process number,Arrival Time and Burst Time: ");
			jobs.add(sc.nextInt());
			at.add(sc.nextInt());
			bursttime.add(sc.nextInt());
		}
		ArrayList<Integer> wt = new ArrayList<>();
		ArrayList<Integer> st = new ArrayList<>();
		st.add(0, 0);
		wt.add(0, 0);
		for (int i = 1; i < n; i++) {
			int wasted = 0;
			int a = st.get(i - 1) + bursttime.get(i - 1);
			st.add(i, a);
			int x = st.get(i) - at.get(i);
			wt.add(x);
			if (wt.get(i) < 0) {
				wasted = Math.abs(wt.get(i));
				wt.set(i, 0);
			}
			st.add(i, (st.get(i) + wasted));
		}

		ArrayList<Integer> tat = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int y = wt.get(i) + bursttime.get(i);
			tat.add(y);
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
		System.out.println("Process\t BurstTime\tArrival Time\t WaitingTime\t TurnAroundTime");
		for (int i = 0; i < n; i++) {
			System.out.println(
					jobs.get(i) + "\t\t" + bursttime.get(i) + "\t\t" + at.get(i) + "\t\t" + wt.get(i) + "\t\t"
							+ tat.get(i));
		}
		System.out.println("Average Waiting Time: " + avgwt);
		System.out.println("Average Turn Around Time: " + avgtat);
		sc.close();
	}
}
