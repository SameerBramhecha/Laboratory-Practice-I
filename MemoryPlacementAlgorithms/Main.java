import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Best Fit");
        System.out.println("2. Worst Fit");
        System.out.println("3. First Fit");
        System.out.println("4. Next Fit");
        System.out.println("5. Exit");
        Main m = new Main();
        int ch;
        String c = "Y";
        while (c.equals("Y")) {
            System.out.println("Enter Choice: ");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    m.bestfit(sc);
                    break;
                case 2:
                    m.worstfit(sc);
                    break;
                case 3:
                    m.firstfit(sc);
                    break;
                case 4:
                    m.nextfit(sc);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Enter Valid Choice..");
                    break;

            }
            System.out.println("Do you want to continue?[Y/N]");
            c = sc.next();
        }
    }

    void firstfit(Scanner sc) {

        System.out.println("Enter No of memory blocks: ");
        int n = sc.nextInt();
        int mb[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter size of block " + (i + 1) + " :");
            mb[i] = sc.nextInt();
        }
        System.out.println("Enter no of processes : ");
        int p = sc.nextInt();
        int ps[] = new int[p];
        for (int i = 0; i < p; i++) {
            System.out.println("Enter size of process " + (i + 1) + " :");
            ps[i] = sc.nextInt();
        }
        int allocation[] = new int[p];
        Arrays.fill(allocation, -1);
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < n; j++) {
                if (ps[i] <= mb[j]) {
                    allocation[i] = j + 1;
                    mb[j] -= ps[i];
                    break;
                }
            }
        }
        print(allocation, ps);

    }

    void worstfit(Scanner sc) {
        System.out.println("Enter No of memory blocks: ");
        int n = sc.nextInt();
        int mb[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter size of block " + (i + 1) + " :");
            mb[i] = sc.nextInt();
        }
        System.out.println("Enter no of processes : ");
        int p = sc.nextInt();
        int ps[] = new int[p];
        for (int i = 0; i < p; i++) {
            System.out.println("Enter size of process " + (i + 1) + " :");
            ps[i] = sc.nextInt();
        }
        int allocation[] = new int[p];
        Arrays.fill(allocation, -1);
        for (int i = 0; i < p; i++) {
            int wstindex = -1;
            for (int j = 0; j < n; j++) {
                if (mb[j] >= ps[i]) {
                    if (wstindex == -1) {
                        wstindex = j;
                    } else if (mb[wstindex] < mb[j]) {
                        wstindex = j;
                    }
                }
            }
            if (wstindex != -1) {
                allocation[i] = wstindex + 1;
                mb[wstindex] -= ps[i];
            }
        }
        print(allocation, ps);

    }

    void nextfit(Scanner sc) {
        System.out.println("Enter No of memory blocks: ");
        int n = sc.nextInt();
        int mb[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter size of block " + (i + 1) + " :");
            mb[i] = sc.nextInt();
        }
        System.out.println("Enter no of processes : ");
        int p = sc.nextInt();
        int ps[] = new int[p];
        for (int i = 0; i < p; i++) {
            System.out.println("Enter size of process " + (i + 1) + " :");
            ps[i] = sc.nextInt();
        }
        int allocation[] = new int[p];
        int j = 0;
        Arrays.fill(allocation, -1);
        for (int i = 0; i < p; i++) {
            int count = 0;
            while (j < n && count < p) {
                count++;
                if (mb[j] >= ps[i]) {
                    allocation[i] = j + 1;
                    mb[j] -= ps[i];
                    break;
                }
                j = (j + 1) % n;
            }
        }
        print(allocation, ps);

    }

    void bestfit(Scanner sc) {
        System.out.println("Enter No of memory blocks: ");
        int n = sc.nextInt();
        int mb[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter size of block " + (i + 1) + " :");
            mb[i] = sc.nextInt();
        }
        System.out.println("Enter no of processes : ");
        int p = sc.nextInt();
        int ps[] = new int[p];
        for (int i = 0; i < p; i++) {
            System.out.println("Enter size of process " + (i + 1) + " :");
            ps[i] = sc.nextInt();
        }
        int allocation[] = new int[p];
        Arrays.fill(allocation, -1);
        for (int i = 0; i < p; i++) {
            int bstindex = -1;
            for (int j = 0; j < n; j++) {
                if (mb[j] >= ps[i]) {
                    if (bstindex == -1) {
                        bstindex = j;
                    } else if (mb[bstindex] > mb[j]) {
                        bstindex = j;
                    }
                }
            }
            if (bstindex != -1) {
                allocation[i] = bstindex + 1;
                mb[bstindex] -= ps[i];
            }
        }
        print(allocation, ps);

    }

    static void print(int allocation[], int ps[]) {
        System.out.println("Process No.\t" + "Process Size\t" + "Memory Block Alloted");
        for (int i = 0; i < ps.length; i++) {
            if (allocation[i] != -1) {
                System.out.println((i + 1) + ".\t\t" + ps[i] + "\t\t" + allocation[i]);
            } else {
                System.out.println((i + 1) + ".\t\t" + ps[i] + "\t\tNot Alloted(Insufficient Memory)");
            }

        }
    }
}
