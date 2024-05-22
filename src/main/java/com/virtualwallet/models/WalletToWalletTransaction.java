package com.virtualwallet.models;

import com.virtualwallet.models.enums.CategoryType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "wallet_transactions")
public class WalletToWalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_transaction_id")
    private int transactionId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "transaction_type_id")
    private int transactionTypeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    @Column(name = "recipient_wallet_id")
    private int recipientWalletId;

    @Column(name = "wallet_id")
    private int walletId;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToOne
    @JoinColumn(name = "category_id")
    private TransactionCategory category;


    public WalletToWalletTransaction(int transactionId, double amount, LocalDateTime time,
                                     int transactionTypeId, User sender, int recipientWalletId,
                                     int walletId, Status status, TransactionCategory category) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.time = time;
        this.sender = sender;
        this.transactionTypeId = transactionTypeId;
        this.recipientWalletId = recipientWalletId;
        this.walletId = walletId;
        this.status = status;
        this.category = category;
    }

    public WalletToWalletTransaction() {
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
    public int getRecipientWalletId() {
        return recipientWalletId;
    }

    public void setRecipientWalletId(int recipientWalletId) {
        this.recipientWalletId = recipientWalletId;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletToWalletTransaction that = (WalletToWalletTransaction) o;
        return transactionId == that.transactionId
                && Double.compare(amount, that.amount) == 0
                && transactionTypeId == that.transactionTypeId
                && sender == that.sender
                && recipientWalletId == that.recipientWalletId
                && walletId == that.walletId
                && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, amount, time, transactionTypeId, sender, recipientWalletId, walletId);
    }
}
