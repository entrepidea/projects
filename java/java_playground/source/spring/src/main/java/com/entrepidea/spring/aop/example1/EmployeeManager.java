package com.entrepidea.spring.aop.example1;

import java.util.List;

public interface EmployeeManager {
    public EmployeeDTO getEmployeeById(Integer employeeId);

    public List<EmployeeDTO> getAllEmployees();

    public void createEmployee(EmployeeDTO employee);

    public void deleteEmployee(Integer employeeId);

    public void updateEmployee(EmployeeDTO employee);
}
