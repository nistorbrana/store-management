package com.learning.storemanagement.repository;

import com.learning.storemanagement.model.StoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<StoreUser, Integer> {

    Optional<StoreUser> findByUsername(String username);
}
