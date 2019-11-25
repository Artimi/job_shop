import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ScheduledOperationTests {

  @Test
  void overlaps() {
    Operation operation = new Operation("a", 0, 2);
    ScheduledOperation scheduledOperation1 = new ScheduledOperation(operation, 0, 2);
    ScheduledOperation scheduledOperation2 = new ScheduledOperation(operation, 1, 3);
    ScheduledOperation scheduledOperation3 = new ScheduledOperation(operation, 2, 4);

    assertTrue(ScheduledOperation.overlaps(scheduledOperation1, scheduledOperation2));
    assertTrue(ScheduledOperation.overlaps(scheduledOperation2, scheduledOperation1));

    assertFalse(ScheduledOperation.overlaps(scheduledOperation1, scheduledOperation3));
    assertFalse(ScheduledOperation.overlaps(scheduledOperation3, scheduledOperation1));
  }
}
