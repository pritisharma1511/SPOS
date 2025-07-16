import java.util.Scanner;

public class SimpleFCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] burstTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];

        // Input burst times
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Burst Time for Process " + (i + 1) + ": ");
            burstTime[i] = sc.nextInt();
        }

        // Calculate waiting time
        waitingTime[0] = 0;
        for (int i = 1; i < n; i++) {
            waitingTime[i] = waitingTime[i - 1] + burstTime[i - 1];
        }

        // Calculate turnaround time
        for (int i = 0; i < n; i++) {
            turnAroundTime[i] = waitingTime[i] + burstTime[i];
        }

        // Display results
        System.out.println("\nProcess\tBurst\tWaiting\tTurnaround");
        int totalWT = 0, totalTAT = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("P" + (i + 1) + "\t" + burstTime[i] + "\t" + waitingTime[i] + "\t" + turnAroundTime[i]);
            totalWT += waitingTime[i];
            totalTAT += turnAroundTime[i];
        }

        System.out.println("\nAverage Waiting Time: " + (float) totalWT / n);
        System.out.println("Average Turnaround Time: " + (float) totalTAT / n);

        sc.close();
    }
}
