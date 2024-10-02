package com.company.dto;

public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private String managerId;
    private double salary;

    public Employee(String id, String firstName, String lastName, String managerId, double salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerId = managerId;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getManagerId() {
        return managerId;
    }

    public double getSalary() {
        return salary;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
