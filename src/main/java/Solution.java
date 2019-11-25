import java.util.HashMap;

public class Solution {
  private JobShopProblem jobShopProblem;
  private HashMap<Operation, Integer> schedule;
  private MachineUtilization machineUtilization;

  public Solution(JobShopProblem jobShopProblem, HashMap<Operation, Integer> schedule) {
    this.jobShopProblem = jobShopProblem;
    this.schedule = schedule;
  }

  public HashMap<Operation, Integer> getSchedule() {
    return schedule;
  }

  private Boolean checkPrecedence() {
    int lastOperationEndTime;
    int start;
    for (Job job : jobShopProblem.getJobs().values()) {
      lastOperationEndTime = 0;
      for (Operation operation : job.getOperations()) {
        start = schedule.get(operation);
        if (start < lastOperationEndTime) {
          return false;
        }
        lastOperationEndTime = start + operation.getProcessingTime();
      }
    }
    return true;
  }

  private Boolean checkCapacity() {
    return getMachineUtilization().isValid();
  }

  public Boolean isValid() {
    return checkPrecedence() && checkCapacity();
  }

  public MachineUtilization getMachineUtilization() {
    if (machineUtilization == null) {
      machineUtilization = MachineUtilization.fromSchedule(schedule);
    }
    return machineUtilization;
  }
}
