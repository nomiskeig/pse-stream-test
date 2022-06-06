package com.example.demo;

import java.util.List;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;
import java.time.LocalTime;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import java.util.Date;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

@RestController
class EmployeeController {
  private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
  private final EmployeeRepository repository;

  private Counter counter2;
  private TestThread testThread;
  private Thread thread;
  EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
    this.counter2 = new Counter();
   // this.counter2.run();
    this.testThread = new TestThread();
    this.testThread.start();
  }

  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/employees")
  List<Employee> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/employees")
  Employee newEmployee(@RequestBody Employee newEmployee) {
    return repository.save(newEmployee);
  }

  // Single item

  @GetMapping("/employees/{id}")
  Employee one(@PathVariable Long id) {

    return repository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  @PutMapping("/employees/{id}")
  Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

    return repository.findById(id)
        .map(employee -> {
          employee.setName(newEmployee.getName());
          employee.setRole(newEmployee.getRole());
          return repository.save(employee);
        })
        .orElseGet(() -> {
          newEmployee.setId(id);
          return repository.save(newEmployee);
        });
  }

  @DeleteMapping("/employees/{id}")
  void deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);
  }
  /*
  // return Flux.interval(Duration.ofSeconds(1));
  // this seems to work
  @CrossOrigin
  @GetMapping(value = "/stream", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StreamingResponseBody> download() {
    StreamingResponseBody stream = out -> {
      for (int i = 0; i < 1000; i++) {
        String msg = "/srb" + " @ " + new Date() + "\n";
        String message = String.valueOf(this.testThread.getCounter()) + "\n";
        try {
          Thread.sleep(20);
        } catch (Exception e) {
            System.out.println("Thread killed");
        }
        out.write(message.getBytes());
        out.flush();
        
      }
      ;
    };
    //return new ResponseEntity(stream, HttpStatus.OK);
    return ResponseEntity.ok()
    //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test_data.txt")
    //.contentType(MediaType.APPLICATION_OCTET_STREAM)
    .body(stream);
  }
  */
  private ExecutorService executor = Executors.newCachedThreadPool();

  @GetMapping("/send/{id}")
  void send(@PathVariable Long id) {
    try {

      // emitter.send("send from id " + id);
    } catch (Exception e) {

    }
  }
  // this seems to be the best way since we can catch errors, the rbe name is just because I took it from the tutorial
  @CrossOrigin
  @GetMapping(value = "/rbe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseBodyEmitter> handleRbe(@PathVariable Long id) {

    ResponseBodyEmitter emitter = new ResponseBodyEmitter(-1L);
    StreamSender sender = new StreamSender(emitter);
    this.testThread.addSubject(sender);
    executor.execute(sender);
      
    return new ResponseEntity(emitter, HttpStatus.OK);
  }

  Long counter = 0L;
  /*
  @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
  @GetMapping("/stream-sse-mvc")
  public SseEmitter streamSseMvc() {
    SseEmitter emitter = new SseEmitter();
    ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
    sseMvcExecutor.execute(() -> {
      try {
        for (int i = 0; true; i++) {
          SseEventBuilder event = SseEmitter.event()
              .data("SSE MVC - " + LocalTime.now().toString())
              .id(String.valueOf(i))
              .name("sse event - mvc");
          emitter.send(event);
          Thread.sleep(1000);
        }
      } catch (Exception ex) {
        emitter.completeWithError(ex);
      }
    });
    return emitter;
  }
  /*
  /*
  @RequestMapping("/stream2")
  public StreamingResponseBody handleRequst() {
    return new StreamingResponseBody() {
      @Override
      public void writeTo(OutputStream out) throws IOException {
        for (int i = 0; i < 10000; i++) {
          out.write((Integer.toString(i) + " - ")
              .getBytes());
          out.flush();
          try {
            Thread.sleep(5);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };

  }
  */
  /*
  @RequestMapping("/stream3")
  public StreamingResponseBody handleRequst2() {
    return new StreamingResponseBody() {
      @Override
      public void writeTo(OutputStream out) throws IOException {
        while (true) {
          int counter = counter2.getCount();
          out.write((Integer.toString(counter) + " - ").getBytes());
          out.flush();
          try {
            Thread.sleep(5);

          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };
  }
  */
}
