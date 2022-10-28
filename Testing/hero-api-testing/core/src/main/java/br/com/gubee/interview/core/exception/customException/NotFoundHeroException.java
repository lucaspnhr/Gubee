package br.com.gubee.interview.core.exception.customException;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class NotFoundHeroException extends RuntimeException {

    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    public NotFoundHeroException(UUID id) {
        super(String.format("Not found hero with id = %s", id.toString()));
    }
}
