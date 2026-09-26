package com.company.expense_manager.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "expenses")
public class Expense {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false)
	private String category;  //meal, travel, hardware etc
	
	@Column(nullable = false)
	private String description;
	
	@Enumerated(EnumType.STRING)
	private ExpenseStatus status = ExpenseStatus.PENDING;
	
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	public Expense() {
		
	}
	
	public Expense(BigDecimal amount, String category, String description, Employee employee) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.employee = employee;
        this.status = ExpenseStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }
	
	// Getters and Setters
    public Long getId() { 
    	return id;
    }
    
    public void setId(Long id) { 
    	this.id = id;
    }
    
    public BigDecimal getAmount() { 
    	return amount; 
    }
    
    public void setAmount(BigDecimal amount) { 
    	this.amount = amount;
    }
    
    public String getCategory() { 
    	return category;
    }
    
    public void setCategory(String category) { 
    	this.category = category;
    }
    
    public String getDescription() { 
    	return description;
    }
    
    public void setDescription(String description) { 
    	this.description = description;
    }
    
    public ExpenseStatus getStatus() { 
    	return status;
    }
    
    public void setStatus(ExpenseStatus status) { 
    	this.status = status;
    }
    
    public LocalDateTime getCreatedAt() { 
    	return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
    	this.createdAt = createdAt;
    }
    
    public Employee getEmployee() { 
    	return employee;
    }
    
    public void setEmployee(Employee employee) { 
    	this.employee = employee;
    }
	
}
