package com.neo.gymification.repositories;

import com.neo.gymification.models.GUser;
import com.neo.gymification.models.TaskType;
import com.neo.gymification.models.WeeklyTask;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyTaskRepository extends CrudRepository<WeeklyTask, Long> {

  Optional<List<WeeklyTask>> findAllByWeekId(long weekId);

  Optional<WeeklyTask> findByWeekIdAndTaskType(long weekId, TaskType taskType);
}