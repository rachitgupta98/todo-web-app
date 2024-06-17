package org.plivo.servlets;

import com.google.common.net.MediaType;
import com.google.gson.Gson;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.plivo.entities.Task;
import org.plivo.services.TaskService;
import org.plivo.utils.GeneralUtils;

@Slf4j
public class TodoTaskServlet extends HttpServlet {
    private static final String TASK_ID = "id";

    @Inject
    private static TaskService taskService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.JSON_UTF_8.toString());
        var task = taskService.getAllTasks();
        try {
            if (task != null) {
                var json = GeneralUtils.gson.toJson(task);
                response.getWriter().write(json);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.JSON_UTF_8.toString());
        try {
            var reader = request.getReader();
            var newTask = GeneralUtils.gson.fromJson(reader, Task.class);

            var createdTask = taskService.createTask(newTask);
            if (createdTask) {
                response.getWriter().write(new Gson().toJson(newTask));
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.JSON_UTF_8.toString());
        var taskId = request.getParameter(TASK_ID);
        try {
            if (taskId != null) {
                var reader = request.getReader();
                var updatedTask = GeneralUtils.gson.fromJson(reader, Task.class);
                updatedTask.setId(Integer.parseInt(taskId));

                boolean success = taskService.updateTask(updatedTask);

                if (success) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error in updating the todo task , {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        var taskId = request.getParameter(TASK_ID);
        try {
            if (taskId != null) {
                boolean success = taskService.deleteTask(Integer.parseInt(taskId));
                if (success) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error in delete the todo task , {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
