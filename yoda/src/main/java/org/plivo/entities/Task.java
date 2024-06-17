package org.plivo.entities;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class Task {
    private int id;
    private String title;
    private String description;
    private String dueDate;
    private TaskPriority priority;
    private boolean isActive;
    private boolean isRecurring;
    @Builder.Default
    private RecurrenceType recurrenceType = RecurrenceType.NEVER;

    public void setId(int id) {
        this.id = id;
    }
}
