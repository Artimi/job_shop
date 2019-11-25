import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MachineUtilizationTests {

  HashMap<Operation, Integer> schedule = new HashMap<>();
  private Operation a_1;
  private Operation a_2;
  private Operation b_1;
  private Operation b_2;

  @BeforeEach
  void init() {
    a_1 = new Operation("a", 1, 1);
    a_2 = new Operation("a", 2, 2);
    b_1 = new Operation("b", 1, 3);
    b_2 = new Operation("b", 2, 2);

    schedule.put(a_1, 0);
    schedule.put(a_2, 1);
    schedule.put(b_1, 1);
    schedule.put(b_2, 4);
  }

  @Test
  void fromSchedule() {
    MachineUtilization machineUtilization = MachineUtilization.fromSchedule(schedule);
    HashMap<Integer, List<ScheduledOperation>> expected = new HashMap<>();
    expected.put(
        1, Arrays.asList(new ScheduledOperation(a_1, 0, 1), new ScheduledOperation(b_1, 1, 4)));
    expected.put(
        2, Arrays.asList(new ScheduledOperation(a_2, 1, 3), new ScheduledOperation(b_2, 4, 6)));

    HashMap<Integer, List<ScheduledOperation>> actual = machineUtilization.getUtilization();

    assertEquals(expected, actual);
  }

  @Test
  void makespan() {
    MachineUtilization machineUtilization = MachineUtilization.fromSchedule(schedule);
    assertEquals(machineUtilization.makespan(), 6);
  }

  @Test
  void isValid() {
    MachineUtilization machineUtilization = MachineUtilization.fromSchedule(schedule);
    assertTrue(machineUtilization.isValid());
  }

  @Test
  void isValidFalse() {
    // b_1 overlaps with a_1
    schedule.put(b_1, 0);
    MachineUtilization machineUtilization = MachineUtilization.fromSchedule(schedule);
    assertFalse(machineUtilization.isValid());
  }

  @Test
  void scheduleAsSoonAsPossible() {
    Operation a_3 = new Operation("a", 2, 1);
    MachineUtilization machineUtilization = MachineUtilization.fromSchedule(schedule);

    ScheduledOperation scheduledOperation = machineUtilization.scheduleAsSoonAsPossible(a_3, 1);
    assertEquals(3, scheduledOperation.getStart());
    assertEquals(4, scheduledOperation.getEnd());
  }

  @Test
  void scheduleAsSoonAsPossibleDontFit() {
    Operation a_3 = new Operation("a", 2, 2);
    MachineUtilization machineUtilization = MachineUtilization.fromSchedule(schedule);

    ScheduledOperation scheduledOperation = machineUtilization.scheduleAsSoonAsPossible(a_3, 1);
    assertEquals(6, scheduledOperation.getStart());
    assertEquals(8, scheduledOperation.getEnd());
  }
}
