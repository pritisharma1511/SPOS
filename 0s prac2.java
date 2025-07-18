import java.util.*;

class Process {
    int id, at, bt, rt, ct, tat, wt;
    Process(int id, int at, int bt) {
        this.id = id;
        this.at = at;
        this.bt = bt;
        this.rt = bt;
    }
}

public class SJF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("No. of processes: ");
        int n = sc.nextInt();
        Process[] p = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("AT & BT for P" + (i+1) + ": ");
            p[i] = new Process(i+1, sc.nextInt(), sc.nextInt());
        }

        int t = 0, completed = 0;
        double totalWT = 0, totalTAT = 0;

        while (completed < n) {
            int idx = -1, minRT = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (p[i].at <= t && p[i].rt > 0 && p[i].rt < minRT) {
                    minRT = p[i].rt;
                    idx = i;
                }
            }

            if (idx == -1) { t++; continue; }

            p[idx].rt--; t++;

            if (p[idx].rt == 0) {
                completed++;
                p[idx].ct = t;
                p[idx].tat = p[idx].ct - p[idx].at;
                p[idx].wt = p[idx].tat - p[idx].bt;
                totalWT += p[idx].wt;
                totalTAT += p[idx].tat;
            }
        }

        System.out.println("\nP\tAT\tBT\tCT\tTAT\tWT");
        for (Process proc : p)
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\n", proc.id, proc.at, proc.bt, proc.ct, proc.tat, proc.wt);

        System.out.printf("\nAvg TAT: %.2f\nAvg WT: %.2f\n", totalTAT/n, totalWT/n);
    }
}
