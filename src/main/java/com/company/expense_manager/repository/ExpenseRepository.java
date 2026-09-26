package com.company.expense_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.expense_manager.entity.Expense;
import com.company.expense_manager.entity.ExpenseStatus;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByStatus(ExpenseStatus status);
	List<Expense> findByEmployeeId(Long employeeId);
}
