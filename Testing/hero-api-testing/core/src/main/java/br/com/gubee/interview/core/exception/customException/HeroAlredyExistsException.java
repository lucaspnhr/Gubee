package br.com.gubee.interview.core.exception.customException;

public class HeroAlredyExistsException extends RuntimeException {
    public HeroAlredyExistsException(String name) {
        super(String.format("Hero with name %s already exists in our sytem", name));
    }
}
