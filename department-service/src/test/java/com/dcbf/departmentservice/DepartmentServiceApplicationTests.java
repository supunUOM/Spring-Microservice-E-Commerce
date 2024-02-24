package com.dcbf.departmentservice;

import com.dcbf.departmentservice.client.EmployeeClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DepartmentServiceApplicationTests {

    @Autowired
    private EmployeeClient employeeClient;

    @Test
    void restCallTest() {
        var response = employeeClient.findByDepartment(1L);
        System.out.println(response);
    }


}
