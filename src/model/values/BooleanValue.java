package model.values;

import model.types.BooleanType;
import model.types.IType;

public class BooleanValue implements IValue {
    private boolean value = false;

    public BooleanValue(boolean v) {
        value = v;
    }

    public boolean getValue() {
        return value;
    }

    public String toString() {
        return " " + value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BooleanValue))
            return false;
        BooleanValue castObj = (BooleanValue) obj;
        return value == castObj.value;
    }

    @Override
    public IType getType() {
        return new BooleanType();
    }

    @Override
    public IValue deepCopy() {
        return new BooleanValue(value);
    }
}
