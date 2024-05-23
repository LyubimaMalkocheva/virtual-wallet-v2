package com.virtualwallet.models.input_model_dto;

import com.virtualwallet.models.TransactionCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.checker.index.qual.Positive;

public class TransactionDto {
    @Min(value = 1, message = "Transfer amount cannot be less than 1")
    @Positive
    private double amount;

    @NotEmpty(message = "The recipient Iban of the transaction can't be empty.")
    private String iban;

    @NotNull(message = "Category can't be empty.")
    private int category;
    public TransactionDto(){
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
