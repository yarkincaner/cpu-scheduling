import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * @author Ali Yarkın Caner
 * @studentID 20180808063
 */

public class FirstComeFirstServed {

    private ArrayList<Job> jobs;
    private Queue<Tuple> processQueue;
    private int time;
    private HashMap<Integer, Integer> tts; // turnaround times
    private double att; //Average Turnaround Time: (Waiting time + Burst time) / n
    private double awt; //Average Waiting Time: (Turnaround time - Burst time) / n

    public FirstComeFirstServed(ArrayList<Job> jobs) {
        this.jobs = jobs;
        time = 0;
        processQueue = addTupleToQueue(time);
        tts = new HashMap<>();
        att = awt = 0;
    }

    public boolean areAllTerminated() {
        for (Job job: jobs) {
            if (!job.isTerminated()) {
                return false;
            }
        }
        return true;
    }

    private Queue<Tuple> addTupleToQueue(int time) {

        Queue<Tuple> queue = new LinkedList<>();
        int i = 0;
        while (i < jobs.size()) {

            if (i > 0) {
                for (int j = 0; j < i; j++) {
                    if (jobs.get(j).getTupleByPivot().isReady(time) && !jobs.get(j).isTerminated()) {
                        i = j;
                        break;
                    }
                }
            }

            if (jobs.get(i).getTupleByPivot().isReady(time) && !jobs.get(i).isTerminated()) {
                queue.add(jobs.get(i).getTupleByPivot());

//                System.out.print("Time: " + this.time);
                this.time += jobs.get(i).getTupleByPivot().getCpu_burst();

                if (jobs.get(i).getTupleByPivot().getIo_burst() == -1) {
                    jobs.get(i).getTupleByPivot().setReturnTime(this.time);
                    jobs.get(i).setTerminated(true);
                } else {
                    jobs.get(i).getTupleByPivot().setReturnTime(this.time + jobs.get(i).getTupleByPivot().getIo_burst());
                }

//                System.out.print(" PID: " + jobs.get(i).getJob_id() + " Tuple: " + jobs.get(i).getTupleByPivot() +
//                        " Return: " + jobs.get(i).getTupleByPivot().getReturnTime() + "\n");

                if (jobs.get(i).getPivot() < jobs.get(i).getTuples().size() - 1) {
                    jobs.get(i).incrementPivot();
                    jobs.get(i).getTupleByPivot().setReturnTime(jobs.get(i).getTuple(jobs.get(i).getPivot() - 1).getReturnTime());
                } else {
                    tts.put(jobs.get(i).getJob_id(), jobs.get(i).getTupleByPivot().getReturnTime());
                }
            }
            i++;
        }

        if (time >= this.time) {
            this.time++;
        }

        return queue;
    }

    public void run() {
        while (!areAllTerminated()) {

            while (!processQueue.isEmpty()) {
                processQueue.poll();
            }
            processQueue = addTupleToQueue(time);
        }

        printResults();
    }

    private void printResults() {
        for (Job job: jobs) {
            att += tts.get(job.getJob_id());
            awt += tts.get(job.getJob_id()) - job.getAllBurstTime();
        }

        att = att / jobs.size();
        awt = awt / jobs.size();

        System.out.println("Average turnaround time: " + att + "\nAverage waiting time: " + awt);
    }
}


