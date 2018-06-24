package com.neo.gymification.repositories;

import com.neo.gymification.models.UserImage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserImageRepository extends CrudRepository<UserImage, String> {

  Optional<UserImage> findByUserName(String userName);
}
