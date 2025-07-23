import java.util.*;

class Process {
    int pid, at, bt, pr, ct, wt, tat;
    Process(int pid, int at, int bt, int pr) {
        this.pid = pid; this.at = at; this.bt = bt; this.pr = pr;
    }
}

public class PriorityScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        Process[] p = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time, burst time and priority for process " + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            int pr = sc.nextInt();
            p[i] = new Process(i+1, at, bt, pr);
        }

        int time = 0, done = 0;
        boolean[] finished = new boolean[n];

        while (done < n) {
            int idx = -1, highest = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!finished[i] && p[i].at <= time && p[i].pr < highest) {
                    highest = p[i].pr;
                    idx = i;
                }
            }
            if (idx == -1) {
                time++;
                continue;
            }
            Process curr = p[idx];
            time += curr.bt;
            curr.ct = time;
            curr.tat = curr.ct - curr.at;
            curr.wt = curr.tat - curr.bt;
            finished[idx] = true;
            done++;
        }

        System.out.println("PID AT BT PR CT WT TAT");
        for (Process proc : p) {
            System.out.printf("%d %d %d %d %d %d %d\n", 
                proc.pid, proc.at, proc.bt, proc.pr, proc.ct, proc.wt, proc.tat);
        }
    }
}

