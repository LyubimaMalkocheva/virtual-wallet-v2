package com.virtualwallet.services;

import com.virtualwallet.models.TransactionCategory;
import com.virtualwallet.repositories.contracts.TransactionCategoryRepository;
import com.virtualwallet.services.contracts.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCategoryServiceImpl implements TransactionCategoryService {
    @Autowired
    private TransactionCategoryRepository repository;

    @Override
    public List<TransactionCategory> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public TransactionCategory getCategoryById(int id) {
        return repository.findById(id);
    }

    @Override
    public TransactionCategory saveCategory(TransactionCategory category) {
        return repository.save(category);
    }
}
