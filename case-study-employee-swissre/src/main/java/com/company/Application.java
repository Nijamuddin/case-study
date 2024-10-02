package com.company;

import com.company.service.EmployeeAnalyzer;

import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
        try {
            analyzer.readEmployeeData("data/employees.csv");
            analyzer.analyzeSalaries();
            analyzer.analyzeReportingLines();
        } catch (IOException e) {
            System.out.println("Received IOError: " + e.getMessage());
            System.out.println("StackTrace: ");
            e.printStackTrace();
        }
    }
}
