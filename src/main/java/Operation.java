public class Operation {

  private String jobId;
  private int machine;
  private int processingTime;

  public Operation(String jobId, int machine, int processingTime) {
    this.jobId = jobId;
    this.machine = machine;
    this.processingTime = processingTime;
  }

  public String getJobId() {
    return jobId;
  }

  public int getMachine() {
    return machine;
  }

  public int getProcessingTime() {
    return processingTime;
  }

  // Why comparison of ScheduledOperation works even without this?
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;

    if (!(o instanceof Operation)) return false;

    Operation that = (Operation) o;

    return this.machine == that.machine && this.processingTime == that.processingTime;
  }

  @Override
  public String toString() {
    return "Operation{" +
        "jobId='" + jobId + '\'' +
        ", machine=" + machine +
        ", processingTime=" + processingTime +
        '}';
  }
}
