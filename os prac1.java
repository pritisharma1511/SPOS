import java.util.Scanner;

public class FCFS{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] waitingTime = new int[n];

        
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            arrivalTime[i] = sc.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burstTime[i] = sc.nextInt();
        }

        
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arrivalTime[j] < arrivalTime[minIndex]) {
                    minIndex = j;
                }
            }
            
            int temp = arrivalTime[i];
            arrivalTime[i] = arrivalTime[minIndex];
            arrivalTime[minIndex] = temp;
          
            temp = burstTime[i];
            burstTime[i] = burstTime[minIndex];
            burstTime[minIndex] = temp;
        }

        int currentTime = 0;

       
        for (int i = 0; i < n; i++) {
            if (currentTime < arrivalTime[i]) {
                currentTime = arrivalTime[i];  
            }
            currentTime += burstTime[i];
            completionTime[i] = currentTime;
            turnAroundTime[i] = completionTime[i] - arrivalTime[i];  // TAT = CT - AT
            waitingTime[i] = turnAroundTime[i] - burstTime[i];      // WT = TAT - BT0
        }

        // Print results
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        int totalTAT = 0, totalWT = 0;
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t" + arrivalTime[i] + "\t" + burstTime[i] + "\t" +
                               completionTime[i] + "\t" + turnAroundTime[i] + "\t" + waitingTime[i]);
            totalTAT += turnAroundTime[i];
            totalWT += waitingTime[i];
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", (double) totalTAT / n);
        System.out.printf("Average Waiting Time: %.2f\n", (double) totalWT / n);

        sc.close();
    }
}
