package schedulingalgorithms;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class Priroty {

    Process process[];
    LinkedList<Process> perform = new LinkedList<>();
    int index;

    void ArrivalSort(Process arr[]) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if ((arr[j].priroty == arr[j + 1].priroty) && (arr[j].at > arr[j + 1].at)) {
                    // swap arr[j+1] and arr[j] 
                    Process temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    void AscendingSort(Process arr[]) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].priroty > arr[j + 1].priroty) {
                    // swap arr[j+1] and arr[j] 
                    Process temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        ArrivalSort(process);
    }

    void DescendingSort(Process arr[]) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].priroty < arr[j + 1].priroty) {
                    // swap arr[j+1] and arr[j] 
                    Process temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        ArrivalSort(process);
    }

    void FinalSort(Process arr[]) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].id > arr[j + 1].id) {
                    // swap arr[j+1] and arr[j] 
                    Process temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    void perform(int p) {
        //case

        DecimalFormat df = new DecimalFormat("#.##");

        LinkedList<Process> processing = new LinkedList();
        LinkedList<Process> finished = new LinkedList();
        LinkedList<Integer> time = new LinkedList();

        /**
         * Write Process Here*
         */
        String name[] = {"A", "B", "C", "D", "E"};
        double AT[] = {0, 2, 4, 6, 8};
        double ST[] = {3, 6, 4, 5, 2};
        int PT[] = {3, 2, 1, 5, 7};

        process = new Process[name.length];
        for (int i = 0; i < name.length; i++) {
            process[i] = new Process();
            process[i].name = name[i];
            process[i].at = AT[i];
            process[i].st = ST[i];
            process[i].remaining = ST[i];
            process[i].priroty = PT[i];
            process[i].processing = 0;
            process[i].id = i;
        }

        if (p == 1) {
            AscendingSort(process);
        } else {
            DescendingSort(process);
        }

        for (int i = 0; i < name.length; i++) {
            perform.add(process[i]);
        }

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < name.length; j++) {
                if (process[j].processing == 0 && process[j].at <= i) {
                    processing.add(process[j]);
                    process[j].processing = 1;
                }
            }

            for (int j = 0; j < perform.size(); j++) {
                if (perform.get(j).remaining <= 0) {
                    perform.remove(j);
                }
            }

            if (processing.contains(perform.peek())) {
                perform.peek().ct = (int) i + perform.peek().remaining;
                perform.peek().remaining = 0;
                finished.add(perform.peek());
                time.add(i);
                i = (int) (perform.peek().ct - 1);
                time.add(i + 1);
            } else {
                index = -1;
                for (int j = 0; j < perform.size(); j++) {
                    if (processing.contains(perform.get(j))) {
                        index = j;
                        break;
                    }
                }
                if (index != -1) {
                    perform.get(index).remaining--;
                    perform.get(index).ct = i + 1;
                    finished.add(perform.get(index));
                    time.add(i);
                    time.add(i + 1);
                }
            }
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

        for (int i = 0; i < finished.size(); i++) {
            System.out.print("| " + finished.get(i).name + " |\t");
        }
        System.out.println("");
        for (int i = 0; i < time.size(); i = i + 2) {
            System.out.print(time.get(i) + "  " + time.get(i + 1) + "\t");
        }

        FinalSort(process);

        System.out.println("\n");
        System.out.println("Process\tAT\tST\tPT\tCT\tTAT\tWT");
        for (int i = 0; i < name.length; i++) {
            process[i].tat = process[i].ct - process[i].at;
            process[i].wt = process[i].tat - process[i].st;
            System.out.print(process[i].name + "\t" + (int) process[i].at + "\t" + (int) process[i].st + "\t" + (int) process[i].priroty + "\t" + (int) process[i].ct + "\t");
            System.out.print((int) process[i].tat + "\t");
            System.out.println((int) process[i].wt);
        }

        double Atat = 0;
        double Awt = 0;
        for (int i = 0; i < name.length; i++) {
            Atat = process[i].tat + Atat;
            Awt = process[i].wt + Awt;
        }

        System.out.println("\nAverage TAT = " + (int) Atat + "/" + name.length + " = " + df.format(Atat / name.length) + " ms");
        System.out.println("Average WT = " + (int) Awt + "/" + name.length + " = " + df.format(Awt / name.length) + " ms");

    }

    public static void main(String[] args) {

        System.out.println("1. Lower the number Higher the priroty");
        System.out.println("1. Higher the number Lower the priroty");
        System.out.println("2. Lower the number Lower the priroty");
        System.out.println("2. Higher the number Higher the priroty\n");
        System.out.print("Enter the Priroty :");
        Scanner in = new Scanner(System.in);
        int p = in.nextInt();
        if (p == 1 || p == 2) {
            Priroty a = new Priroty();
            a.perform(p);
        } else {
            System.out.println("Wrong Input");
        }
    }
}
