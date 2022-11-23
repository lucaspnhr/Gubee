package com.github.lucaspnhr.model.powerstats;

import com.github.lucaspnhr.model.share.ValueObject;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

public class Strength implements ValueObject<Strength> {

    private int value;

    public Strength(int value) {
        Validate.inclusiveBetween(0, 10, value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Strength updateValue(int value) {
        return new Strength(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Strength strength = (Strength) o;
        return sameValueAs(strength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean sameValueAs(Strength other) {
        return this.value == other.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}