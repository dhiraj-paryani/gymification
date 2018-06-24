package com.neo.gymification.repositories;

import com.neo.gymification.models.BadgeName;
import com.neo.gymification.models.UserBadge;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserBadgeRepository extends CrudRepository<UserBadge, UUID> {

  Optional<UserBadge> findByBadgeName(BadgeName badgeName);
}
