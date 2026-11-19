package com.kiet.employeemanagement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>();

    // (b) Add a new employee
    @PostMapping
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
        for (Employee emp : employees) {
            if (emp.getId() == employee.getId()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Employee with ID " + employee.getId() + " already exists.");
            }
        }
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    // (c1) Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employees);
    }

    // (c2) Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable int id) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                return ResponseEntity.ok(emp);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee with ID " + id + " not found.");
    }

    // (d) Update employee details
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                emp.setName(updatedEmployee.getName());
                emp.setDepartment(updatedEmployee.getDepartment());
                emp.setSalary(updatedEmployee.getSalary());
                return ResponseEntity.ok(emp);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee with ID " + id + " not found.");
    }

    // (e) Delete employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                employees.remove(i);
                return ResponseEntity.ok("Employee with ID " + id + " deleted successfully.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee with ID " + id + " not found.");
    }
}