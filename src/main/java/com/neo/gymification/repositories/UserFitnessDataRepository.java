package com.neo.gymification.repositories;

import com.neo.gymification.models.UserFitnessData;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import javax.swing.text.html.Option;

@Repository
public interface UserFitnessDataRepository extends CrudRepository<UserFitnessData, String> {

  Optional<UserFitnessData> findByDateAndHwAddress(long date, String userName);

}
