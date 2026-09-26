package com.company.expense_manager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.company.expense_manager.entity.ExpenseStatus;

public class ExpenseResponseDTO {
	private Long id;
    private BigDecimal amount;
    private String category;
    private String description;
    private ExpenseStatus status;
    private LocalDateTime createdAt;
    private Long employeeId;
    private String employeeName;

    public ExpenseResponseDTO(Long id, BigDecimal amount, String category, String description,
                              ExpenseStatus status, LocalDateTime createdAt, Long employeeId, String employeeName) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    // Standard Getters
    public Long getId() { 
    	return id;
    }
    public BigDecimal getAmount() { 
    	return amount;
    }
    public String getCategory() { 
    	return category;
    }
    public String getDescription() { 
    	return description;
    }
    public ExpenseStatus getStatus() { 
    	return status;
    }
    public LocalDateTime getCreatedAt() { 
    	return createdAt;
    }
    public Long getEmployeeId() { 
    	return employeeId;
    }
    public String getEmployeeName() { 
    	return employeeName;
    }
}
