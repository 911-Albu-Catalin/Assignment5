package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType {

    public StringType() {}

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    public String toString() {
        return "String";
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringType;
    }
}
