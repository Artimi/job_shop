import org.jetbrains.annotations.NotNull;

public class ScheduledOperation implements Comparable<ScheduledOperation> {
  private Operation operation;
  private int start;
  private int end;

  public ScheduledOperation(Operation operation, int start, int end) {
    this.operation = operation;
    this.start = start;
    this.end = end;
  }

  public Operation getOperation() {
    return operation;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  @Override
  public int compareTo(@NotNull ScheduledOperation scheduledOperation) {
    return start - scheduledOperation.start;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;

    if (!(o instanceof ScheduledOperation))
      return false;

    ScheduledOperation that = (ScheduledOperation) o;

    return this.operation == that.operation && this.start == that.start && this.end == that.end;
  }

  @Override
  public String toString() {
    return "ScheduledOperation{" +
        "operation=" + operation +
        ", start=" + start +
        ", end=" + end +
        '}';
  }

  static public boolean overlaps(ScheduledOperation scheduledOperation1, ScheduledOperation scheduledOperation2) {
    return scheduledOperation1.start < scheduledOperation2.end && scheduledOperation2.start < scheduledOperation1.end;
  }
}
