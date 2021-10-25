package schedulingalgorithms;

import java.text.DecimalFormat;
import java.util.LinkedList;

public class RR {

    Process process[];
    //LinkedList<Process> processing = new LinkedList();
    LinkedList<Process> queue = new LinkedList();
    LinkedList<Process> finished = new LinkedList();
    LinkedList<Integer> time = new LinkedList();
    LinkedList<Integer> qtime = new LinkedList();
    LinkedList<Integer> qtime2 = new LinkedList();

    void perform() {
        DecimalFormat df = new DecimalFormat("#.##");
        /**
         * Write Process Here*
         */
        String name[] = {"A", "B", "C", "D", "E"};
        double AT[] = {0, 2, 4, 6, 8};
        double ST[] = {3, 6, 4, 5, 2};
        //QT value is 1

        process = new Process[name.length];
        for (int i = 0; i < name.length; i++) {
            process[i] = new Process();
            process[i].name = name[i];
            process[i].at = AT[i];
            process[i].st = ST[i];
            process[i].remaining = ST[i];
            process[i].processing = 0;
        }

        for (int i = 0; i < 100; i++) {

            Process performed = new Process();

            if (!queue.isEmpty()) {
                performed = queue.pollFirst();
                performed.executed = 1;
                performed.remaining--;
                time.add(i - 1);
                finished.add(performed);
            }

            for (int j = 0; j < name.length; j++) {
                if (process[j].processing == 0 && process[j].at <= i) {
                    queue.add(process[j]);
                    process[j].processing = 1;
                }
            }

            if (performed.executed == 1) {
                if (performed.remaining != 0) {
                    queue.add(performed);
                } else {
                    for (int j = 0; j < name.length; j++) {
                        if (process[j].name.equals(performed.name)) {
                            process[j].ct = i;
                            break;
                        }
                    }
                }
            }

        }

        System.out.println("");
        for (int i = 0; i < finished.size(); i++) {
            System.out.print("| " + finished.get(i).name + " |\t");
        }
        System.out.println("");
        for (int i = 0; i < finished.size(); i++) {
            System.out.print(time.get(i) + "  " + (time.get(i) + 1) + "\t");
        }
        System.out.println("\n\nQueue\n");
        
        for (int i = 0; i < finished.size(); i++) {
           qtime.add((int)finished.get(i).st);
           finished.get(i).st--;
           qtime2.add((int)finished.get(i).st);
        }
        
        for (int i = 0; i < finished.size(); i++) {
            System.out.print("  "+qtime2.get(i)+"  ");
        }
        System.out.println("");
        for (int i = 0; i < finished.size(); i++) {
            System.out.print(" |" + finished.get(i).name + "| ");
        }
        System.out.println("");
        for (int i = 0; i < finished.size(); i++) {
            System.out.print("  "+qtime.get(i)+"  ");
        }
        

        System.out.println("\n");
        System.out.println("Process\tAT\tST\tCT\tTAT\tWT");
        for (int i = 0; i < name.length; i++) {
            process[i].tat = process[i].ct - process[i].at;
            process[i].wt = process[i].tat - ST[i];
            System.out.print(process[i].name + "\t" + (int) process[i].at + "\t" + (int)ST[i] + "\t" + (int) process[i].ct + "\t");
            System.out.print((int) process[i].tat + "\t");
            System.out.println((int) process[i].wt);
        }

        double Atat = 0;
        double Awt = 0;
        for (int i = 0; i < name.length; i++) {
            Atat = process[i].tat + Atat;
            Awt = process[i].wt + Awt;
        }
        System.out.println("\nAverage TAT = " + (int) Atat + "/" + name.length + " = " + df.format(Atat / name.length)+" ms");
        System.out.println("Average WT = " + (int) Awt + "/" + name.length + " = " + df.format(Awt / name.length)+" ms");

    }

    public static void main(String[] args) {
        RR a = new RR();
        a.perform();
    }
}
