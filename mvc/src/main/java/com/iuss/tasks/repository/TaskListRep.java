package com.iuss.tasks.repository;

import com.iuss.tasks.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRep extends JpaRepository<TaskList, Long> {
}
