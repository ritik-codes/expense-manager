package com.company.expense_manager.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseRequestDTO {

    @JsonProperty("employeeId")
    @NotNull(message = "Employee Id is mandatory")
    public Long employeeId;

    @JsonProperty("amount")
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.0", message = "Expense must be at least 1.0")
    public BigDecimal amount;

    @JsonProperty("category")
    @NotBlank(message = "Category cannot be blank")
    public String category;

    @JsonProperty("description")
    @NotBlank(message = "Description cannot be blank")
    public String description;

    public ExpenseRequestDTO() {
    }

    public ExpenseRequestDTO(Long employeeId, BigDecimal amount, String category, String description) {
        this.employeeId = employeeId;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
}