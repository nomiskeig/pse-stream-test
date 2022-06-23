package com.example.demo;

import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List; 

class StreamSender implements Runnable, CounterObserver {
  private ResponseBodyEmitter emitter;
  private int counter;
  private ObjectMapper objectMapper = new ObjectMapper();

  public StreamSender(ResponseBodyEmitter emitter) {
    this.emitter = emitter;
    this.counter = 0;
  }

  public synchronized void update(int counter) {
    this.counter = counter;
   notifyAll();
  }

  public synchronized void run() {

    try {
      while (true) {
       this.wait(); 
       // wait();
        Event event = new Event(1, this.counter);
        //emitter.send(objectMapper.writeValueAsString(event) + "\n",MediaType.APPLICATION_JSON);

       // emitter.send(event + "\n", MediaType.APPLICATION_JSON);
        emitter.send(event);
       //k emitter.send(event, MediaType.APPLICATION_JSON);
        //emitter.send(List.of(event.getProcess(), event.getJobID()), MediaType.APPLICATION_JSON);
        emitter.send("\n", MediaType.APPLICATION_JSON);
      } 
      // emitter.complete();
    } catch (Exception ex) {
      emitter.complete();
      emitter.completeWithError(ex);

    }
  }

}
