package model.values;

import model.types.IType;
import model.types.IntegerType;

public class IntegerValue implements  IValue {
    private int value = 0;

    public IntegerValue(int v) {
        value = v;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return " " + value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntegerValue))
            return false;
        IntegerValue castObj = (IntegerValue) obj;
        return value == castObj.value;
    }

    @Override
    public IType getType() {
        return new IntegerType();
    }

    @Override
    public IValue deepCopy() {
        return new IntegerValue(value);
    }
}