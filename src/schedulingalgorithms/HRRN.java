package schedulingalgorithms;

import java.text.DecimalFormat;
import java.util.LinkedList;

public class HRRN {

    Process process[];

    void perform() {
        DecimalFormat df = new DecimalFormat("#.##");

        LinkedList<Process> processing = new LinkedList();
        LinkedList<Process> finished = new LinkedList();

        /**
         * Write Process Here*
         */
        String name[] = {"P0", "P1", "P2", "P3"};
        double AT[] = {2, 5, 10, 13};
        double ST[] = {1, 3, 1, 6};

        process = new Process[name.length];
        for (int i = 0; i < name.length; i++) {
            process[i] = new Process();
            process[i].name = name[i];
            process[i].at = AT[i];
            process[i].st = ST[i];
            process[i].processing = 0;
        }

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < name.length; j++) {
                if (process[j].processing == 0 && process[j].at <= i) {
                    processing.add(process[j]);
                    process[j].processing = 1;
                }
            }

            if (processing.size() > 1) {
                for (int j = 0; j < processing.size(); j++) {
                    processing.get(j).rrx = ((i - processing.get(j).at) + processing.get(j).st) / processing.get(j).st;
                    System.out.println(i + "   " + processing.get(j).name + "   " + df.format(processing.get(j).rrx));
                }
                System.out.println("");
                Process p = processing.get(0);
                for (int j = 1; j < processing.size(); j++) {
                    if (p.rrx < processing.get(j).rrx) {
                        p = processing.get(j);
                    }
                }

                processing.remove(p);
                p.ct = i + p.st;
                i = (int) p.ct - 1;
                finished.add(p);
            } else if (processing.size() == 1) {
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

        for (int i = 0; i < name.length; i++) {
            finished.get(i).tat = finished.get(i).ct - finished.get(i).at;
            finished.get(i).wt = finished.get(i).tat - finished.get(i).st;
        }

        for (int i = 0; i < name.length; i++) {
            for (int j = 0; j < finished.size(); j++) {
                if (process[i].name.equals(finished.get(j).name)) {
                    process[i].ct = finished.get(j).ct;
                    process[i].tat = process[i].ct - process[i].at;
                    process[i].wt = process[i].tat - process[i].st;
                    break;
                }
            }
        }

        System.out.println("\n");
        System.out.println("Process\tAT\tST\tCT\tTAT\tWT");
        for (int i = 0; i < name.length; i++) {
            System.out.print(process[i].name + "\t" + (int) process[i].at + "\t" + (int) process[i].st + "\t" + (int) process[i].ct + "\t");
            System.out.print((int) process[i].tat + "\t");
            System.out.println((int) process[i].wt);
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
        HRRN a = new HRRN();
        a.perform();
    }
}
