package org.plivo.repository;

import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.plivo.config.DotEnvContainer;
import org.plivo.entities.RecurrenceType;
import org.plivo.entities.Task;
import org.plivo.entities.TaskPriority;
import org.plivo.utils.GeneralUtils;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TaskRepository {
    private static final String JDBC_URL = DotEnvContainer.get("MYSQL_URL");
    private static final String JDBC_USER = DotEnvContainer.get("MYSQL_USER");
    private static final String JDBC_PASSWORD = DotEnvContainer.get("MYSQL_PASSWORD");

    public List<Task> getAllTasks() throws SQLException {
        var query = "SELECT * FROM TODO_TASKS";
        var tasks = new ArrayList<Task>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
         var stmt = conn.createStatement();
         var rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                var task = Task.builder()
                    .id(rs.getInt("task_id"))
                    .title(rs.getString("task_title"))
                    .description(rs.getString("description"))
                    .dueDate(String.valueOf(rs.getDate("due_date")))
                    .priority(TaskPriority.valueOf(rs.getString("priority")))
                    .isActive(rs.getBoolean("is_active"))
                    .isRecurring(rs.getBoolean("is_recurring"))
                    .recurrenceType(RecurrenceType.fromValue(rs.getString("recurrence_type")))
                    .build();
                tasks.add(task);
            }
        }
        return tasks;
    }

    public boolean createTask(Task task) throws SQLException {
        var query = "INSERT INTO TODO_TASKS (task_title, description, due_date, priority, is_active, is_recurring, recurrence_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 var stmt = conn.prepareStatement(query)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            if (GeneralUtils.isValidDateFormat(task.getDueDate())) {
                stmt.setDate(3, Date.valueOf(task.getDueDate()));
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }
            stmt.setString(4, String.valueOf(task.getPriority()));
            stmt.setBoolean(5, task.isActive());
            stmt.setBoolean(6, task.isRecurring());
            stmt.setString(7, RecurrenceType.fromValue(task.getRecurrenceType()));
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateTask(Task task) throws SQLException {
        String query = "UPDATE TODO_TASKS SET task_title = ?, description = ?, due_date = ?, priority = ?, is_active = ?, is_recurring = ?, recurrence_type = ? WHERE task_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var stmt = conn.prepareStatement(query)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            if (GeneralUtils.isValidDateFormat(task.getDueDate())) {
                stmt.setDate(3, Date.valueOf(task.getDueDate()));
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }
            stmt.setString(4, String.valueOf(task.getPriority()));
            stmt.setBoolean(5, task.isActive());
            stmt.setBoolean(6, task.isRecurring());
            stmt.setString(7, RecurrenceType.fromValue(task.getRecurrenceType()));
            stmt.setInt(8, task.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteTask(int id) throws SQLException {
        String query = "DELETE FROM TODO_TASKS WHERE task_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public void generateRecurringTasks() throws SQLException {
        // SQL query to select active recurring tasks
        String query = "SELECT * FROM TODO_TASKS WHERE is_recurring = 1 AND is_active = 1 AND due_date < CURDATE()";

        try (var conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                var task = Task.builder()
                    .id(rs.getInt("task_id"))
                    .title(rs.getString("task_title"))
                    .description(rs.getString("description"))
                    .dueDate(String.valueOf(rs.getDate("due_date")))
                    .priority(TaskPriority.valueOf(rs.getString("priority")))
                    .isActive(rs.getBoolean("is_active"))
                    .isRecurring(rs.getBoolean("is_recurring"))
                    .recurrenceType(RecurrenceType.fromValue(rs.getString("recurrence_type")))
                    .build();

                var newDueDate = calculateNextOccurrence(Date.valueOf(task.getDueDate()), task.getRecurrenceType());
                if (newDueDate != null && GeneralUtils.isValidDateFormat(newDueDate) ) {
                    var updatedTask = task.toBuilder().dueDate(String.valueOf(newDueDate));
                    updateTask(updatedTask.build());
                }
            }
        }
    }

    private LocalDate calculateNextOccurrence(Date currentDueDate, RecurrenceType recurrenceType) {
        return switch (recurrenceType) {
            case DAILY -> currentDueDate.toLocalDate().plusDays(1);
            case WEEKLY -> currentDueDate.toLocalDate().plusWeeks(1);
            case MONTHLY -> currentDueDate.toLocalDate().plusMonths(1);
            default -> null;
        };
    }
}
