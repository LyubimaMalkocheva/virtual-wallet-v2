package com.virtualwallet.models;

import com.virtualwallet.models.enums.CategoryType;
import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name = "categories")
public class TransactionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;
    @Column(name = "category_name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    public TransactionCategory() {
    }

    public TransactionCategory(int id, CategoryType categoryType) {
        this.id = id;
        this.categoryType = categoryType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionCategory that)) return false;
        return getId() == that.getId() && getCategoryType() == that.getCategoryType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCategoryType());
    }
}
