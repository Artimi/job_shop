import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomSolver implements JobShopSolver {

  private ActivityList getRandomActivityList(JobShopProblem jobShopProblem) {
    ActivityList activityList = new ActivityList(jobShopProblem);
    HashMap<String, Operation> lastScheduledOperations = new HashMap<>();
    List<String> jobsToSchedule = new ArrayList<>(jobShopProblem.getJobs().keySet());
    Random random = new Random();
    while (true) {
      if (jobsToSchedule.isEmpty()) {
        break;
      }
      String jobId = jobsToSchedule.get(random.nextInt(jobsToSchedule.size()));
      Job job = jobShopProblem.getJob(jobId);
      Operation lastScheduledOperation = lastScheduledOperations.get(jobId);
      Operation operation = job.getSuccessor(lastScheduledOperation);
      if (operation == null) {
        // we scheduled entire job
        jobsToSchedule.remove(jobId);
        continue;
      }
      activityList.add(operation);
      lastScheduledOperations.put(jobId, operation);
    }
    return activityList;
  }

  @Override
  public Solution solve(JobShopProblem jobShopProblem) {
    ActivityList activityList = getRandomActivityList(jobShopProblem);
    HashMap<Operation, Integer> schedule = activityList.toSchedule();
    return new Solution(jobShopProblem, schedule);
  }
}
