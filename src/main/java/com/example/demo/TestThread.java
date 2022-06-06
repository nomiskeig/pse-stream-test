package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TestThread extends Thread {
  private int counter;
  private List<CounterObserver> observers;
  private Random random = new Random();

  public TestThread() {
    this.counter = 0;
    this.observers = new ArrayList<>();
  }

  public void addSubject(CounterObserver observer) {
      this.observers.add(observer);

  }

  @Override
  public void run() {
    while (true) {
      this.counter = this.counter + 1;
      if (this.counter == Integer.MAX_VALUE) {
        this.counter = 0;
      }
      for (CounterObserver observer : observers) {
        observer.update(this.counter);
      }
      try {
        //Thread.sleep(random.nextInt(2000));
        Thread.sleep(0, 1);
      } catch (Exception e) {
      }
      ;
    }
  }

  public int getCounter() {
    return this.counter;
  }

}
