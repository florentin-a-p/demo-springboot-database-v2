package com.countries.database.demo.repository;

import com.countries.database.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
  User findByUserName(String username);
}
