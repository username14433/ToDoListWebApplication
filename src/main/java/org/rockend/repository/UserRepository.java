package org.rockend.repository;

import org.rockend.entity.User;
import org.rockend.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByRoleInOrderById(Collection<UserRole> roles);

    Optional<User> findByEmailIgnoreCase(String email);

    @Modifying
    @Query("UPDATE User u SET u.role = :newRole WHERE u.id = :id")
    void updateRole(@Param("id") int id, @Param("newRole") UserRole newRole);
}
