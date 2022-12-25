package com.example.receiverservice.repository;

import com.example.receiverservice.entity.FlagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlagRepository extends JpaRepository<FlagEntity, Integer> {
    @Query(value = "select top 1.* from FLAG", nativeQuery = true)
    FlagEntity findFlag();
}
