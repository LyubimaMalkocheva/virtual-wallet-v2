package com.virtualwallet.repositories;

import com.virtualwallet.models.CheckNumber;
import com.virtualwallet.models.TransactionCategory;
import com.virtualwallet.models.Wallet;
import com.virtualwallet.repositories.contracts.TransactionCategoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public class TransactionCategoryRepositoryImpl implements TransactionCategoryRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TransactionCategoryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<TransactionCategory> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from TransactionCategory", TransactionCategory.class).list();
        }
    }

    @Override
    public TransactionCategory findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(TransactionCategory.class, id);
        }
    }

    @Override
    public TransactionCategory save(TransactionCategory category) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(category);
            session.getTransaction().commit();
            return category;
        }
    }
}
