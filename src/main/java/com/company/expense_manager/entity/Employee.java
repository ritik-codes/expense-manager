package com.company.expense_manager.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String department;
	
	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Expense> expenses = new ArrayList<>();
	
	public Employee(){
		
	}
	
	public Employee(String name, String email, String department){
		this.name = name;
		this.email = email;
		this.department = department;
	}
	
	// Standard Getters and Setters
    public Long getId(){ 
    	return id; 
    }
    
    public void setId(Long id){ 
    	this.id = id;
    }
    
    public String getName(){ 
    	return name;
    }
    
    public void setName(String name){ 
    	this.name = name;
    }
    
    public String getEmail(){ 
    	return email;
    }
    
    public void setEmail(String email){ 
    	this.email = email;
    }
    
    public String getDepartment(){
    	return department; 
    }
    
    public void setDepartment(String department){
    	this.department = department; 
    }
    
    public List<Expense> getExpenses(){
    	return expenses; 
    }
    
    public void setExpenses(List<Expense> expenses){
    	this.expenses = expenses;
    }
	
}
