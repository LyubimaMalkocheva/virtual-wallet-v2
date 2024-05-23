package com.virtualwallet.services.contracts;

import com.virtualwallet.models.TransactionCategory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface TransactionCategoryService {
    List<TransactionCategory> getAllCategories();

    TransactionCategory getCategoryById(int id);

    TransactionCategory saveCategory(TransactionCategory category);

}
