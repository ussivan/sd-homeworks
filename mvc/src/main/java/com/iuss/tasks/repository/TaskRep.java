package com.iuss.tasks.repository;

import com.iuss.tasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRep extends JpaRepository<Task, Long> {
}
