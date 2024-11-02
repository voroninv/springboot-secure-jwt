package com.base.jwt.repositories;

import com.base.jwt.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select u from User u left join fetch u.roles r left join fetch r.permissions")
    List<User> findAll();

    @Query("select u from User u left join fetch u.roles r left join fetch r.permissions where u.email = ?1")
    Optional<User> findByEmail(String email);
}
