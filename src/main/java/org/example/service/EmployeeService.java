package org.example.service;

import org.example.dto.CreateEmployeeRequest;
import org.example.dto.EmployeeResponse;
import org.example.mySql.model.Employee;
import org.example.mySql.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponse createUser(CreateEmployeeRequest request) {
        Employee emp = new Employee();
        emp.setName(request.getName());
        emp.setEmail(request.getEmail());
        emp.setRole(request.getRole());
        emp.setActive(true);
        emp.setCreatedAt(LocalDateTime.now());

        Employee savedEmployee = employeeRepository.save(emp);
        return toResponse(savedEmployee);
    }



    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll().stream()
                .map(employee -> toResponse(employee))
                .toList();

    }

    public EmployeeResponse findById(Long id) {
        Employee emp = employeeRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("User not found"));

        return toResponse(emp);
    }

    private EmployeeResponse toResponse(Employee emp) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(emp.getId());
        response.setName(emp.getName());
        response.setEmail(emp.getEmail());
        response.setRole(emp.getRole());
        response.setActive(emp.isActive());
        return response;
    }
}