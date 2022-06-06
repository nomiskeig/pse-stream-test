package com.example.demo;

public class Event {
  private int process;
  private int jobID;
  public Event(int process, int jobID) {
    this.process = process;
    this.jobID = jobID;
  }
  public int getProcess() {
    return this.process;
  }
  public void setProcess(int process) {
    this.process = process;
  }
  public int getJobID() {
    return this.jobID;
  }
  public void setJobID(int jobID) {
    this.jobID = jobID;
  }
}
