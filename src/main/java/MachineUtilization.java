import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class MachineUtilization {
  private HashMap<Integer, List<ScheduledOperation>> utilization;

  public MachineUtilization(HashMap<Integer, List<ScheduledOperation>> utilization) {
    this.utilization = utilization;
  }

  public MachineUtilization() {
    this(new HashMap<>());
  }

  public static MachineUtilization fromSchedule(HashMap<Operation, Integer> schedule) {

    HashMap<Integer, List<ScheduledOperation>> utilization = new HashMap<>();
    for (Map.Entry<Operation, Integer> pair : schedule.entrySet()) {
      Operation operation = pair.getKey();
      int operationStart = pair.getValue();
      ScheduledOperation scheduledOperation =
          new ScheduledOperation(
              operation, operationStart, operationStart + operation.getProcessingTime());
      utilization
          .computeIfAbsent(operation.getMachine(), k -> new ArrayList<>())
          .add(scheduledOperation);
    }

    utilization.values().forEach(Collections::sort);

    return new MachineUtilization(utilization);
  }

  public HashMap<Integer, List<ScheduledOperation>> getUtilization() {
    return utilization;
  }

  public int makespan() {
    int maxMakespan = 0;
    for (List<ScheduledOperation> operations : utilization.values()) {
      if (!operations.isEmpty()) {
        ScheduledOperation lastOperation = operations.get(operations.size() - 1);
        if (lastOperation.getEnd() > maxMakespan) maxMakespan = lastOperation.getEnd();
      }
    }
    return maxMakespan;
  }

  private static boolean areOperationsOverlapping(List<ScheduledOperation> operations) {
    for (int i = 1; i < operations.size(); i++) {
      if (ScheduledOperation.overlaps(operations.get(i - 1), operations.get(i))) return true;
    }
    return false;
  }

  public boolean isValid() {
    for (List<ScheduledOperation> operations : utilization.values()) {
      if (areOperationsOverlapping(operations)) return false;
    }
    return true;
  }

  private SortedMap<Integer, Integer> computeDelays(List<ScheduledOperation> operations) {
    SortedMap<Integer, Integer> result = new TreeMap<>();
    int lastEnd = 0;
    for (ScheduledOperation operation : operations) {
      if (operation.getStart() > lastEnd) {
        result.put(lastEnd, operation.getStart() - lastEnd);
      }
      lastEnd = operation.getEnd();
    }
    return result;
  }

  public ScheduledOperation scheduleAsSoonAsPossible(Operation operation, int earliestStart) {
    int machine = operation.getMachine();
    List<ScheduledOperation> operations =
        utilization.computeIfAbsent(machine, k -> new ArrayList<>());
    SortedMap<Integer, Integer> delays = computeDelays(operations);
    ScheduledOperation scheduledOperation = null;
    for (Entry<Integer, Integer> pair : delays.entrySet()) {
      // skip delays that start before earliestStart
      if (pair.getKey() + pair.getValue() <= earliestStart) continue;
      // start at earliestStart if it has a space
      else if (pair.getKey() < earliestStart
          && pair.getKey() + pair.getValue() - earliestStart > operation.getProcessingTime()) {
        scheduledOperation =
            new ScheduledOperation(
                operation, earliestStart, earliestStart + operation.getProcessingTime());
        break;
      // if there is a delay longer than processingTime schedule it
      } else if (pair.getValue() >= operation.getProcessingTime()) {
        scheduledOperation =
            new ScheduledOperation(
                operation, pair.getKey(), pair.getKey() + operation.getProcessingTime());
        break;
      }
    }
    if (scheduledOperation == null) {
      int lastOnMachineEnd =  operations.isEmpty() ? 0 : operations.get(operations.size() - 1).getEnd();
      int lastEnd = Math.max(lastOnMachineEnd, earliestStart);
      scheduledOperation =
          new ScheduledOperation(operation, lastEnd, lastEnd + operation.getProcessingTime());
    }
    utilization.get(machine).add(scheduledOperation);
    // this could be solved by insort but this will do too
    Collections.sort(operations);
    return scheduledOperation;
  }
}
