package schedulingalgorithms;

import java.text.DecimalFormat;
import java.util.LinkedList;

public class FCFS {

    Process process[];

    void AscendingSort(Process arr[]) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].at > arr[j + 1].at) {
                    // swap arr[j+1] and arr[j] 
                    Process temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    void perform() {
        DecimalFormat df = new DecimalFormat("#.##");

        LinkedList<Process> processing = new LinkedList();
        LinkedList<Process> finished = new LinkedList();

        /**
         * Write Process Here*
         */
        String name[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        double AT[] = {0, 2, 4, 6, 8, 0, 2, 4, 6, 8};
        double ST[] = {3, 6, 4, 5, 2, 3, 6, 4, 5, 2};

        process = new Process[name.length];
        for (int i = 0; i < name.length; i++) {
            process[i] = new Process();
            process[i].name = name[i];
            process[i].at = AT[i];
            process[i].st = ST[i];
            process[i].processing = 0;
        }

        AscendingSort(process);

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < name.length; j++) {
                if (process[j].processing == 0 && process[j].at <= i) {
                    processing.add(process[j]);
                    process[j].processing = 1;
                }
            }

            if (processing.size() > 0) {
                Process p = processing.pollFirst();
                p.ct = i + p.st;
                i = (int) p.ct - 1;
                finished.add(p);
            }
        }

        for (int i = 0; i < name.length; i++) {
            System.out.print("| " + finished.get(i).name + " |\t");
        }
        System.out.println("");
        for (int i = 0; i < name.length; i++) {
            System.out.print((int) (finished.get(i).ct - finished.get(i).st) + "  " + (int) finished.get(i).ct + "\t");
        }
        System.out.println("\n");
        System.out.println("Process\tAT\tST\tCT\tTAT\tWT");
        for (int i = 0; i < name.length; i++) {
            finished.get(i).tat = finished.get(i).ct - finished.get(i).at;
            finished.get(i).wt = finished.get(i).tat - finished.get(i).st;
            System.out.print(finished.get(i).name + "\t" + (int) finished.get(i).at + "\t" + (int) finished.get(i).st + "\t" + (int) finished.get(i).ct + "\t");
            System.out.print((int) finished.get(i).tat + "\t");
            System.out.println((int) finished.get(i).wt);
        }

        double Atat = 0;
        double Awt = 0;
        for (int i = 0; i < name.length; i++) {
            Atat = finished.get(i).tat + Atat;
            Awt = finished.get(i).wt + Awt;
        }
        System.out.println("\nAverage TAT = " + (int) Atat + "/" + name.length + " = " + df.format(Atat / name.length) + " ms");
        System.out.println("Average WT = " + (int) Awt + "/" + name.length + " = " + df.format(Awt / name.length) + " ms");

    }

    public static void main(String[] args) {
        FCFS a = new FCFS();
        a.perform();
    }
}
