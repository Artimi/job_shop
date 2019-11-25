import java.util.HashMap;
import java.util.List;

public class JobShopProblem {
  private HashMap<String, Job> jobs;

  public JobShopProblem(List<Job> jobs) {
    this.jobs = new HashMap<>();
    for(Job job: jobs) {
      this.jobs.put(job.getId(), job);
    }
  }

  public HashMap<String, Job> getJobs() {
    return jobs;
  }

  public Job getJob(String jobId) {
    return jobs.get(jobId);
  }
}
