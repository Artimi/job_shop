import java.util.List;
import org.jetbrains.annotations.Nullable;

public class Job {
  private List<Operation> operations;
  private String id;

  public Job(String id, List<Operation> operations) {
    this.operations = operations;
    this.id = id;
  }

  public List<Operation> getOperations() {
    return operations;
  }

  public String getId() {
    return id;
  }

  public Operation getPrecedent(Operation operation) {
    int operationIndex = operations.indexOf(operation);
    if (operationIndex == 0) {
      return null;
    } else {
      return operations.get(operationIndex - 1);
    }
  }

  /**
   * Return successor of operation. Return null if there is none.
   */
  public Operation getSuccessor(@Nullable Operation operation) {
    if (operation == null) {
      return operations.get(0);
    } else {
      int operationIndex = operations.indexOf(operation);
      if (operationIndex == operations.size() - 1) {
        return null;
      } else {
        return operations.get(operationIndex + 1);
      }
    }
  }
}
