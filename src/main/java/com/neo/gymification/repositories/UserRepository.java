package com.neo.gymification.repositories;

import com.neo.gymification.models.GUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.security.Guard;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<GUser, String> {

  Optional<GUser> findByUserName(String userName);

  Optional<GUser> findByHwAddress(String hwAddress);

  Page<GUser> findAll(Pageable pageable);

  Optional<List<GUser>> findByActiveTrue();
}
