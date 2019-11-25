import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityList {
  private JobShopProblem jobShopProblem;
  private List<Operation> activityList;

  public ActivityList(JobShopProblem jobShopProblem, List<Operation> activityList) {
    this.jobShopProblem = jobShopProblem;
    this.activityList = activityList;
  }

  public ActivityList(JobShopProblem jobShopProblem) {
    this(jobShopProblem, new ArrayList<>());
  }

  public void add(Operation operation) {
    activityList.add(operation);
  }

  private boolean checkPrecedence() {
    Set<Operation> processedOperations = new HashSet<>();

    for (Operation operation : activityList) {
      Job job = jobShopProblem.getJob(operation.getJobId());
      Operation maybePrecedent = job.getPrecedent(operation);
      if (maybePrecedent != null && !processedOperations.contains(maybePrecedent)) {
        return false;
      }
      processedOperations.add(operation);
    }
    return true;
  }

  public HashMap<Operation, Integer> toSchedule() {
    if (!checkPrecedence()) {
      throw new AssertionError("Activity list is not in precedence order!");
    }

    MachineUtilization machineUtilization = new MachineUtilization();
    HashMap<Operation, Integer> schedule = new HashMap<>();

    for (Operation operation : activityList) {
      Job job = jobShopProblem.getJob(operation.getJobId());
      Operation maybePrecedent = job.getPrecedent(operation);
      int earliestStart =
          maybePrecedent == null
              ? 0
              : schedule.get(maybePrecedent) + maybePrecedent.getProcessingTime();
      ScheduledOperation scheduledOperation =
          machineUtilization.scheduleAsSoonAsPossible(operation, earliestStart);
      schedule.put(operation, scheduledOperation.getStart());
    }

    return schedule;
  }

  @Override
  public String toString() {
    return "ActivityList{" +
        "activityList=" + activityList +
        '}';
  }
}
