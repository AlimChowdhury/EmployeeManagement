package com.alim.EmployeeManagement.repository;

import java.util.Optional;

import com.alim.EmployeeManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
}
//public interface UserRepository extends CrudRepository<User, Long> {

//  List<User> findByName(String name);

//}