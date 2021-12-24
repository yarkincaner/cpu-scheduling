import java.util.ArrayList;

/**
 * @author Ali Yarkın Caner
 * @studentID 20180808063
 */

public class Job {

    private int job_id;
    private ArrayList<Tuple> tuples;
    private int pivot;
    private int size;
    private boolean isTerminated;

    public Job(int job_id) {
        this.job_id = job_id;
        tuples = new ArrayList<>();
        pivot = 0;
        size = -1;
        isTerminated = false;
    }

    public int getJob_id() { return job_id; }

    public ArrayList<Tuple> getTuples() { return tuples; }

    public int getPivot() { return pivot; }

    public Tuple getTupleByPivot() { return tuples.get(pivot); }

    public void incrementPivot() { pivot++; }

    public int getAllBurstTime() {
        int sum = 0;
        for (Tuple tuple: tuples) {
            sum += tuple.totalBurstTime();
        }
        return sum;
    }

    public void addTuple(Tuple tuple) {
        tuples.add(tuple);
        size++;
    }

    public Tuple getTuple(int index) {
        return tuples.get(index);
    }

    public boolean isTerminated() { return isTerminated; }

    public void setTerminated(boolean terminated) { isTerminated = terminated; }

    /**
     * Checks whether the last io burst -1 or not
     */
    public boolean isLastValid() { return tuples.get(size).getIo_burst() == -1; }

    @Override
    public String toString() {
        String output = job_id + ":";
        for (Tuple tuple: tuples) {

            output += tuple;
            if (tuple.getIo_burst() != -1) {
                output += ";";
            }
        }

        return output;
    }
}
