package com.github.lucaspnhr.model.powerstats;

import com.github.lucaspnhr.model.share.ValueObject;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

public class Intelligence implements ValueObject<Intelligence> {

    private int value;

    public Intelligence(int value) {
        Validate.inclusiveBetween(0, 10, value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Dexterity updateValue(int value) {
        return new Dexterity(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intelligence that = (Intelligence) o;
        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean sameValueAs(Intelligence other) {
        return this.value == other.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}