package com.virtualwallet.repositories.contracts;

import com.virtualwallet.models.TransactionCategory;
import com.virtualwallet.models.enums.CategoryType;

import java.util.List;

public interface TransactionCategoryRepository {
    List<TransactionCategory> findAll();

    TransactionCategory findById(int id);

    TransactionCategory save(TransactionCategory category);
}
