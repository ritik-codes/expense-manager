package com.company.expense_manager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.company.expense_manager.dto.ExpenseRequestDTO;
import com.company.expense_manager.dto.ExpenseResponseDTO;
import com.company.expense_manager.entity.Employee;
import com.company.expense_manager.entity.Expense;
import com.company.expense_manager.entity.ExpenseStatus;
import com.company.expense_manager.exception.InvalidExpenseOperationException;
import com.company.expense_manager.exception.ResourceNotFoundException;
import com.company.expense_manager.repository.EmployeeRepository;
import com.company.expense_manager.repository.ExpenseRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ExpenseService {
	private final ExpenseRepository expenseRepository;
	private final EmployeeRepository employeeRepository;
	
	public ExpenseService(ExpenseRepository expenseRepository, EmployeeRepository employeeRepository) {
		this.expenseRepository = expenseRepository;
		this.employeeRepository = employeeRepository;
	};
	
	@Transactional
	public ExpenseResponseDTO createExpense(ExpenseRequestDTO dto) {
		Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
		
		Expense expense = new Expense(dto.getAmount(), dto.getCategory(), dto.getDescription(), employee);
		Expense saved = expenseRepository.save(expense);
		return mapToDTO(saved);
	}
	
	public List<ExpenseResponseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ExpenseResponseDTO> getExpensesByStatus(ExpenseStatus status) {
        return expenseRepository.findByStatus(status).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public ExpenseResponseDTO updateStatus(Long expenseId, ExpenseStatus status) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));

        expense.setStatus(status);
        Expense updated = expenseRepository.save(expense);
        return mapToDTO(updated);
    }

    private ExpenseResponseDTO mapToDTO(Expense expense) {
        return new ExpenseResponseDTO(
                expense.getId(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getStatus(),
                expense.getCreatedAt(),
                expense.getEmployee().getId(),
                expense.getEmployee().getName()
        );
    }

    @Transactional
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO dto) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        // Guard Clause: Only PENDING expenses can be edited
        if (expense.getStatus() != ExpenseStatus.PENDING) {
            throw new InvalidExpenseOperationException(
                    "Cannot edit an expense that has already been " + expense.getStatus());
        }

        // Update allowable fields
        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());
        expense.setDescription(dto.getDescription());

        // Optional: If you allow changing the associated employee
        if (dto.getEmployeeId() != null && !dto.getEmployeeId().equals(expense.getEmployee().getId())) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
            expense.setEmployee(employee);
        }

        Expense updatedExpense = expenseRepository.save(expense);
        return mapToDTO(updatedExpense);
    }

    @Transactional
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        // Guard Clause: Only PENDING expenses can be deleted
        if (expense.getStatus() != ExpenseStatus.PENDING) {
            throw new InvalidExpenseOperationException(
                    "Cannot delete an expense that has already been " + expense.getStatus());
        }

        expenseRepository.delete(expense);
    }
	
}
