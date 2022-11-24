package com.github.lucaspnhr.commom.customexception;

import java.util.UUID;

public class NotFoundHeroException extends RuntimeException {

    public NotFoundHeroException(UUID id) {
        super("Hero with id %s was not found".formatted(id));
    }
}
