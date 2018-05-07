package com.cakes.repository;

import com.cakes.domain.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Cakes data access
 */
@Repository
public interface CakeRepository extends MongoRepository<Cake, String> {

    List<Cake> findAll();
}
