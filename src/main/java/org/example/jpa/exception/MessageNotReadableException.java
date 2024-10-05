package org.example.jpa.exception;

public class MessageNotReadableException extends RuntimeException {

    public MessageNotReadableException() {
        super("Unable to read request data.");
    }
}