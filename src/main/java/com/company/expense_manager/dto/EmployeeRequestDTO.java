package com.company.expense_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmployeeRequestDTO {
	@NotBlank(message = "Employee name cannot be blank")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Department cannot be blank")
    private String department;

    public EmployeeRequestDTO() {}

    public EmployeeRequestDTO(String name, String email, String department) {
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public String getName() { 
    	return name;
    }
    
    public void setName(String name) { 
    	this.name = name;
    }

    public String getEmail() { 
    	return email;
    }
    
    public void setEmail(String email) { 
    	this.email = email;
    }

    public String getDepartment() { 
    	return department;
    }
    
    public void setDepartment(String department) { 
    	this.department = department;
    }
}
