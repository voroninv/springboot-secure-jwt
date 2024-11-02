package com.base.jwt.repositories;

import com.base.jwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select r from Role r left join fetch r.permissions")
    List<Role> findAll();

    @Query("select r from Role r left join fetch r.permissions where r.id = ?1")
    Optional<Role> findById_ (Integer id);

    @Query("select r from Role r left join fetch r.permissions where r.name = ?1")
    Optional<Role> findByName(String name);
}
