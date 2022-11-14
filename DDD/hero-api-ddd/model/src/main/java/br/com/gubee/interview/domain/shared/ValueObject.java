package br.com.gubee.interview.domain.shared;

public interface ValueObject<T> {
    boolean sameValueAs(T other);
}
