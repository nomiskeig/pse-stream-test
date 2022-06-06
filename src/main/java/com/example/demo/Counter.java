package com.example.demo;

class Counter {
  private int count;
  public Counter() {
    count = 0;
  }

  public void run() {
    while(true) {
      this.count = this.count+1;
      System.out.print(count);
      if (this.count == 100000) {
        this.count = 0;
      }
      try {
        Thread.sleep(5);
      }
      catch (Exception e) {

      }
    }
  }
  public int getCount() {
    return this.count;
  }

} 


