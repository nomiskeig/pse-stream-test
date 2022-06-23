package com.example.demo;


public class SpecificCommand extends Command<Employee> {
    @Override
public Employee executeCommand() {
   return new Employee("testname", "asdf");
}
}
