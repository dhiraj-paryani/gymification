package com.neo.gymification.repositories;

import com.neo.gymification.models.Activity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, UUID> {

  List<Activity> findByUserHwAddress(String userHwAddress);
  List<Activity> findByUserHwAddressAndDateGreaterThanOrderByDate(String userHwAddress, Long date);
  Optional<Activity> findByTaskIdAndUser_HwAddress(UUID taskId, String hwAddress);
}
