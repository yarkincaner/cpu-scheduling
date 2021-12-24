import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Ali Yarkın Caner
 * @studentID 20180808063
 */

public class tp_20180808063 {

    public static void main(String[] args) {

        String inputPath = args[0];
        ArrayList<Job> jobs = getInput(inputPath);
        FirstComeFirstServed fcfs = new FirstComeFirstServed(jobs);
        fcfs.run();
    }

    public static ArrayList<Job> getInput(String path) {

        ArrayList<Job> jobs = new ArrayList<>();
        String currentLine;
        Job job;

        try {

            BufferedReader br = new BufferedReader(new FileReader(path));

            int line = 1;
            while ((currentLine = br.readLine()) != null) {

                String[] temp = currentLine.split(":");
                job = new Job(Integer.parseInt(temp[0]));
                String[] tuples = temp[1].split(";");

                int numOfOccurrences = 0; //Number of occurrences of -1 at IO burst
                for (String tuple: tuples) {

                    tuple = tuple.replaceAll("[()]", "");
                    String[] values = tuple.split(",");

                    int cpu_burst = Integer.parseInt(values[0]);
                    int io_burst = Integer.parseInt(values[1]);

                    if (io_burst == -1) {
                        numOfOccurrences++;
                    }

                    if (numOfOccurrences > 1) {
                        throw new Exception("Duplicate termination operation in " + line + ". line");
                    } else {
                        job.addTuple(new Tuple(cpu_burst, io_burst));
                    }
                }

                if (!job.isLastValid()) {
                    throw new Exception("Last process' io burst must be -1 for job_id: " + job.getJob_id());
                } else {
                    jobs.add(job);
                }

                line++;
            }

            br.close();

        } catch (Exception e) { e.printStackTrace(); }

        return jobs;
    }
}
