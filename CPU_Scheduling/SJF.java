import java.util.Scanner;

public class SJF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter no of process");
        int n = sc.nextInt();
        int pid[] = new int[n];// process id
        int at[] = new int[n];// arrival time
        int bt[] = new int[n];// burst time
        int ct[] = new int[n];// complete time
        int ta[] = new int[n];// turnaround time
        int wt[] = new int[n];// waiting time
        int f[] = new int[n];// flag to check if process is completed or not
        int k[] = new int[n];// it also stores burst time

        int curtime = 0, tot = 0;
        float avgwt = 0, avgta = 0;

        for (int i = 0; i < n; i++) {
            pid[i] = i + 1;
            System.out.println("Enter Process " + (i + 1) + " arrival time: ");
            at[i] = sc.nextInt();
            System.out.println("Enter Process " + (i + 1) + " burst time: ");
            bt[i] = sc.nextInt();
            k[i] = bt[i];
            f[i] = 0;
        }
        while (true) {
            int min = Integer.MAX_VALUE;
            int c = n;
            if (tot == n) {
                break;
            }
            for (int i = 0; i < n; i++) {
                if ((at[i] <= curtime) && (f[i] == 0) && (bt[i] < min)) {
                    min = bt[i];
                    c = i;
                }
            }
            if (c == n) {
                curtime++;
            } else {
                bt[c]--;
                curtime++;
                if (bt[c] == 0) {
                    ct[c] = curtime;
                    f[c] = 1;
                    tot++;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            ta[i] = ct[i] - at[i];
            wt[i] = ta[i] - k[i];
            avgwt += wt[i];
            avgta += ta[i];
        }

        System.out.println("pid  arrival  burst  complete turn waiting");
        for (int i = 0; i < n; i++) {
            System.out.println(pid[i] + "\t" + at[i] + "\t" + k[i] + "\t" + ct[i] + "\t" + ta[i] + "\t" + wt[i]);
        }

        System.out.println("\naverage tat is " + (float) (avgta / n));
        System.out.println("average wt is " + (float) (avgwt / n));
        sc.close();
    }
}