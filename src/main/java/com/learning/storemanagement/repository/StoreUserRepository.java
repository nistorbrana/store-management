package com.learning.storemanagement.repository;

import com.learning.storemanagement.model.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreUserRepository extends JpaRepository<StoreUser, Integer> {
}
