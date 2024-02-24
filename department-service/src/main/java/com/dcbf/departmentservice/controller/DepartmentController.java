package com.dcbf.departmentservice.controller;


import com.dcbf.departmentservice.exception.DepartmentNotFoundException;
import com.dcbf.departmentservice.model.Department;
import com.dcbf.departmentservice.client.EmployeeClient;
import com.dcbf.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private EmployeeClient employeeClient;


    @PostMapping
    public Department add(@RequestBody Department department) {
        LOGGER.info("Department add: {}", department);
        return repository.addDepartment(department);
    }

    @GetMapping
    public List<Department> findAll(@RequestHeader("loggedInUser") String user) {
        System.out.println(user);
        LOGGER.info("Department find");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable Long id) {
        throw new DepartmentNotFoundException("department not found");
//        LOGGER.info("Department find: id={}", id);
//        return repository.findById(id);
    }

    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees(@RequestHeader("loggedInUser") String userId) {
        LOGGER.info("=============== userId: "+ userId);
        LOGGER.info("Department find");
        List<Department> departments = repository.findAll();
        departments.forEach(department -> department.setEmployees(employeeClient.findByDepartment(department.getId())));
        return  departments;
    }

}
