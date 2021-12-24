/**
 * @author Ali Yarkın Caner
 * @studentID 20180808063
 */

public class Tuple {

    private int cpu_burst;
    private int io_burst;
    private int returnTime;

    public Tuple(int cpu_burst, int io_burst) {
        this.cpu_burst = cpu_burst;
        this.io_burst = io_burst;
        returnTime = 0;
    }

    public boolean isReady(int time) {
        if (time >= returnTime) {
            return true;
        }
        return false;
    }

    public int getCpu_burst() { return cpu_burst; }

    public int getIo_burst() { return io_burst; }

    public int getReturnTime() { return returnTime; }

    public void setReturnTime(int returnTime) { this.returnTime = returnTime; }

    public int totalBurstTime() {
        if (io_burst == -1) {
            return cpu_burst;
        }
        return cpu_burst + io_burst;
    }

    @Override
    public String toString() {
        String s = "(" + cpu_burst + "," + io_burst + ")";
        return s;
    }
}
