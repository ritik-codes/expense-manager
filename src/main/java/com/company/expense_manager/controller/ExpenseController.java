package com.company.expense_manager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.company.expense_manager.dto.ExpenseRequestDTO;
import com.company.expense_manager.dto.ExpenseResponseDTO;
import com.company.expense_manager.entity.ExpenseStatus;
import com.company.expense_manager.service.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expense Controller", description = "Endpoints for submitting and approving expense reports")
public class ExpenseController {
	
	private final ExpenseService expenseService;
	
	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
	@PostMapping
	@Operation(summary = "Submit a new expense")
	public ResponseEntity<ExpenseResponseDTO> submitExpense(@Valid @RequestBody ExpenseRequestDTO dto) {
	    System.out.println(">>> RECEIVED DTO: " + dto);
	    ExpenseResponseDTO created = expenseService.createExpense(dto);
	    return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping
	@Operation(summary = "Get all submitted expenses")
	public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses(){
		return ResponseEntity.ok(expenseService.getAllExpenses());
	}
	
	@GetMapping("/status/{status}")
	@Operation(summary = "Filter expenses by status (PENDING, APPROVED, REJECTED)")
	public ResponseEntity<List<ExpenseResponseDTO>> getByStatus(@PathVariable ExpenseStatus status){
		return ResponseEntity.ok(expenseService.getExpensesByStatus(status));
	}
	
	@PatchMapping("/{id}/status")
    @Operation(summary = "Manager action: Approve or reject an expense")
    public ResponseEntity<ExpenseResponseDTO> updateExpenseStatus(@PathVariable Long id, @RequestParam ExpenseStatus status) {
        return ResponseEntity.ok(expenseService.updateStatus(id, status));
    }
	
	@PutMapping("/{id}")
	@Operation(summary = "Edit an existing expense report (allowed only if PENDING)")
	public ResponseEntity<ExpenseResponseDTO> updateExpense(
	        @PathVariable Long id, 
	        @Valid @RequestBody ExpenseRequestDTO dto) {
	    return ResponseEntity.ok(expenseService.updateExpense(id, dto));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an expense report (allowed only if PENDING)")
	public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
	    expenseService.deleteExpense(id);
	    return ResponseEntity.noContent().build();
	}
	
}
