package com.company;

import com.company.service.EmployeeAnalyzer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeAnalyzerTest {

    private EmployeeAnalyzer analyzer;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() throws IOException {
        analyzer = new EmployeeAnalyzer();
        // Load the sample data from the CSV located in the resources directory
        analyzer.readEmployeeData("data/employees.csv");

        // Capture the console output
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @After
    public void tearDown() {
        // Reset the System.out after the test
        System.setOut(originalOut);
    }

    @Test
    public void testManagerEarningLessThanTheyShould() {
        // Expected: No output for managers earning less than they should based on the provided data.
        analyzer.analyzeSalaries();

        // Capture output
        String output = outputStreamCaptor.toString();
        assertFalse(output.contains("earns less than they should"));
    }

    @Test
    public void testManagerEarningMoreThanTheyShould() {
        // Run the salary analysis
        analyzer.analyzeSalaries();

        // Capture output
        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("more than they should"));
    }

    @Test
    public void testEmployeeWithReportingLineTooLong() {
        // Expected: Employees with reporting lines that are too long
        analyzer.analyzeReportingLines();

        // Capture output
        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("levels too long"));
    }
}
