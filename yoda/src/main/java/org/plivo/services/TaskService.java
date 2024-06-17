package org.plivo.services;

import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plivo.entities.Task;
import org.plivo.repository.TaskRepository;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TaskService {
    private final TaskRepository taskDAO;

    public TaskService() {
        this.taskDAO = new TaskRepository();
    }

    public List<Task> getAllTasks() {
        try {
            return taskDAO.getAllTasks();
        } catch (SQLException e) {
            log.error("Error in fetching all the task {}", e.getMessage());
            return null;
        }
    }

    public boolean createTask(Task task) {
        try {
            return taskDAO.createTask(task);
        } catch (SQLException e) {
            log.error("Error in creating the task {}", e.getMessage());
            return false;
        }
    }

    public boolean updateTask(Task task) {
        try {
            return taskDAO.updateTask(task);
        } catch (SQLException e) {
            log.error("Error in updating all the task {}", e.getMessage());
            return false;
        }
    }

    public boolean deleteTask(int id) {
        try {
            return taskDAO.deleteTask(id);
        } catch (SQLException e) {
            log.error("Error in deleting all the task {}", e.getMessage());
            return false;
        }
    }

    public void checkForRecurringTasks() {
        try {
            taskDAO.generateRecurringTasks();
        } catch (SQLException e) {
            log.error("Error in updating the instances based on recurrence {}", e.getMessage());
        }
    }
}
