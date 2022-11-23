package com.github.lucaspnhr.model.share;

public interface ValueObject<T> {
    boolean sameValueAs(T other);
}