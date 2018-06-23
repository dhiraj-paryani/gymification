package com.neo.gymification.repositories;

import com.neo.gymification.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

  User findByUserName(String userName);
}
