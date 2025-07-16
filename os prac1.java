import java.util.Scanner;

public class SimpleFCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        
        int[] arrival = new int[n];
        int[] burstTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] completionTime = new int[n];

        // Input arrival and burst times
        for (int i = 0; i < n; i++) {
            System.out.println("Enter Arrival Time and Burst Time for Process " + (i + 1) + ":");
            arrival[i] = sc.nextInt();
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
        //completion time
         for (int i = 0; i < n; i++) {
            completionTime[i] = waitingTime[i] +  burstTime[i];
        }
        
        

        // Display results
        System.out.println("\nProcess\tBurst\tCompletion\tWaiting\tTurnaround");
        int totalWT = 0, totalTAT = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("P" + (i + 1) + "\t" + burstTime[i] + "\t" + completionTime[i] +"\t"+ "\t"     + waitingTime[i] + "\t"    + turnAroundTime[i]);
            totalWT += waitingTime[i];
            totalTAT += turnAroundTime[i];
        }

        System.out.println("\nAverage Waiting Time: " + (float) totalWT / n);
        System.out.println("Average Turnaround Time: " + (float) totalTAT / n);

        sc.close();
    }
}
