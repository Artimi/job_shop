import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolutionTests {

  private Operation a_1;
  private Operation a_2;
  private Operation b_1;
  private Operation b_2;
  private List<Operation> operations1;
  private List<Operation> operations2;
  private Job job_a;
  private Job job_b;
  private List<Job> jobs;
  private JobShopProblem jobShopProblem;

  @BeforeEach
  void init() {
    a_1 = new Operation("a", 1, 1);
    a_2 = new Operation("a", 2, 2);
    b_1 = new Operation("b", 1, 3);
    b_2 = new Operation("b", 2, 2);
    operations1 = new ArrayList<Operation>(Arrays.asList(a_1, a_2));
    operations2 = new ArrayList<Operation>(Arrays.asList(b_1, b_2));
    job_a = new Job("a", operations1);
    job_b = new Job("b", operations2);
    jobs = new ArrayList<Job>(Arrays.asList(job_a, job_b));
    jobShopProblem = new JobShopProblem(jobs);
  }

  @Test
  void isValidTrue() {
    HashMap<Operation, Integer> schedule = new HashMap<Operation, Integer>();
    schedule.put(a_1, 0);
    schedule.put(a_2, 1);
    schedule.put(b_1, 1);
    schedule.put(b_2, 4);
    Solution solution = new Solution(jobShopProblem, schedule);

    assertTrue(solution.isValid());
  }

  @Test
  void isValidFalsePrecedence() {
    HashMap<Operation, Integer> scheduleMapping = new HashMap<Operation, Integer>();
    scheduleMapping.put(a_1, 0);
    scheduleMapping.put(a_2, 0);
    scheduleMapping.put(b_1, 1);
    scheduleMapping.put(b_2, 2);
    Solution schedule = new Solution(jobShopProblem, scheduleMapping);

    assertFalse(schedule.isValid());
  }

  @Test
  void isValidFalseCapacity() {
    HashMap<Operation, Integer> scheduleMapping = new HashMap<Operation, Integer>();
    scheduleMapping.put(a_1, 0);
    scheduleMapping.put(a_2, 1);
    scheduleMapping.put(b_1, 0);
    scheduleMapping.put(b_2, 3);
    Solution schedule = new Solution(jobShopProblem, scheduleMapping);

    assertFalse(schedule.isValid());
  }
}
