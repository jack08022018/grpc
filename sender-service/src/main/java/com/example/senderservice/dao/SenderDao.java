package com.example.senderservice.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class SenderDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createNativeQuery(String query) {
        entityManager.createNativeQuery(query)
                .executeUpdate();
    }

}
