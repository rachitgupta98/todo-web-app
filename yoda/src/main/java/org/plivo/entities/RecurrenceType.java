package org.plivo.entities;

import org.apache.commons.lang3.StringUtils;

public enum RecurrenceType {
    DAILY,
    WEEKLY,
    MONTHLY,
    NEVER;

    public static RecurrenceType fromValue(String type) {
        if (StringUtils.isBlank(type) || StringUtils.contains(type, "null")) {
            return NEVER;
        }
        return RecurrenceType.valueOf(type);
    }

    public static String fromValue(RecurrenceType type) {
        if (type == null) {
            return NEVER.name();
        }
        return type.name();
    }
}
