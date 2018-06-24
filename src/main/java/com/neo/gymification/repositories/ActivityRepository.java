package com.neo.gymification.repositories;

import com.neo.gymification.models.Activity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, String> {

  List<Activity> findByUserHwAddress(String userHwAddress);
}
