package com.github.lucaspnhr.model.powerstats;

import com.github.lucaspnhr.model.share.ValueObject;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

public class Agility implements ValueObject<Agility> {

    private int value;

    public Agility(int value) {
        Validate.inclusiveBetween(0, 10, value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Agility updateValue(int value) {
        return new Agility(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agility agility = (Agility) o;
        return sameValueAs(agility);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean sameValueAs(Agility other) {
        return this.value == other.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
