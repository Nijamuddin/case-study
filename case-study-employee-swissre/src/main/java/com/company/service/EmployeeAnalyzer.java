package com.company.service;

import com.company.dto.Employee;

import java.io.*;
import java.util.*;

public class EmployeeAnalyzer {
    private Map<String, Employee> employees = new HashMap<>();
    private Map<String, List<Employee>> managerToEmployees = new HashMap<>();

    public void readEmployeeData(String filePath) throws IOException {

        // Load the resource using the class loader
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            // Skip the header row
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                String firstName = data[1].trim();
                String lastName = data[2].trim();
                double salary = Double.parseDouble(data[3].trim());
                String managerId = null;
                if (data.length > 4) {
                    managerId = data[4].trim().isEmpty() ? null : data[4].trim();
                }

                Employee employee = new Employee(id, firstName, lastName, managerId, salary);
                employees.put(id, employee);

                if (managerId != null) {
                    managerToEmployees.computeIfAbsent(managerId, k -> new ArrayList<>()).add(employee);
                }
            }
        }
    }

    public void analyzeSalaries() {
        for (String managerId : managerToEmployees.keySet()) {
            Employee manager = employees.get(managerId);
            List<Employee> subordinates = managerToEmployees.get(managerId);

            double averageSalary = subordinates.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
            double managerSalary = manager.getSalary();
            double lowerBound = averageSalary * 1.2;
            double upperBound = averageSalary * 1.5;

            if (managerSalary < lowerBound) {
                System.out.printf("Manager %s earns %.2f less than they should.%n", manager.getFullName(), lowerBound - managerSalary);
            } else if (managerSalary > upperBound) {
                System.out.printf("Manager %s earns %.2f more than they should.%n", manager.getFullName(), managerSalary - upperBound);
            }
        }
    }

    public void analyzeReportingLines() {
        for (Employee employee : employees.values()) {
            int depth = getReportingDepth(employee);
            if (depth > 4) {
                System.out.printf("Employee %s has a reporting line that is %d levels too long.%n", employee.getFullName(), depth - 4);
            }
        }
    }

    private int getReportingDepth(Employee employee) {
        int depth = 0;
        String managerId = employee.getManagerId();
        while (managerId != null) {
            depth++;
            managerId = employees.get(managerId).getManagerId();
        }
        return depth;
    }
}
