package model.types;

import model.values.BooleanValue;
import model.values.IValue;

public class BooleanType implements IType {
    @Override
    public boolean equals(Object another) {
        return another instanceof BooleanType;
    }

    @Override
    public IValue defaultValue() {
        return new BooleanValue(false);
    }

    @Override
    public IType deepCopy() {
        return new BooleanType();
    }

    public String toString() {
        return "bool";
    }
}
