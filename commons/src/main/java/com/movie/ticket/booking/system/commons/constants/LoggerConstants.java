package com.movie.ticket.booking.system.commons.constants;

public enum LoggerConstants {
    ENTERED_CONTROLLER_MESSAGE("Entered {} Controller of {} class with value {}"),
    EXITING_CONTROLLER_MESSAGE("Exiting {} Controller of {} class"),
    ENTERED_SERVICE_MESSAGE("Entered into {} Service method of {} class with value {}"),
    EXITING_SERVICE_MESSAGE("Exiting {} Service method of {} class");
    private final String value;
    LoggerConstants(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
