import java.util.*;

class Process{
    int pid;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int waitingTime;
    int turnaroundTime;
    int completionTime;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class SimpleRoundRobinWithCT {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for process " + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            processes[i] = new Process(i + 1, at, bt);
        }

        System.out.print("Enter time quantum: ");
        int quantum = sc.nextInt();

        roundRobin(processes, quantum);
    }

    public static void roundRobin(Process[] processes, int quantum) {
        int time = 0;
        Queue<Process> queue = new LinkedList<>();
        int completed = 0;
        int n = processes.length;
        boolean[] inQueue = new boolean[n];

        while (completed < n) {
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= time && processes[i].remainingTime > 0 && !inQueue[i]) {
                    queue.add(processes[i]);
                    inQueue[i] = true;
                }
            }

            if (queue.isEmpty()) {
                time++;
                continue;
            }

            Process current = queue.poll();

            int execTime = Math.min(current.remainingTime, quantum);
            current.remainingTime -= execTime;
            time += execTime;

            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= time && processes[i].remainingTime > 0 && !inQueue[i]) {
                    queue.add(processes[i]);
                    inQueue[i] = true;
                }
            }

            if (current.remainingTime > 0) {
                queue.add(current);
            } else {
                current.completionTime = time;
                current.turnaroundTime = current.completionTime - current.arrivalTime;
                current.waitingTime = current.turnaroundTime - current.burstTime;
                completed++;
            }
        }

        printResults(processes);
    }

    public static void printResults(Process[] processes) {
        double totalWT = 0, totalTAT = 0;

        System.out.println("\nPID\tAT\tBT\tCT\tWT\tTAT");
        for (Process p : processes) {
            System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\n",
                    p.pid, p.arrivalTime, p.burstTime, p.completionTime, p.waitingTime, p.turnaroundTime);
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
        }

        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWT / processes.length);
        System.out.printf("Average Turnaround Time: %.2f\n", totalTAT / processes.length);
    }
}

