package com.github.lucaspnhr.model.powerstats;

import com.github.lucaspnhr.model.share.ValueObject;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

public class Dexterity implements ValueObject<Dexterity> {

    private int value;

    public Dexterity(int value) {
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
        Dexterity dexterity = (Dexterity) o;
        return sameValueAs(dexterity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean sameValueAs(Dexterity other) {
        return this.value == other.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}