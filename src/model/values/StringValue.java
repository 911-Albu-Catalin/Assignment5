package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    String value;

    public StringValue(String v) {
        value = v;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof StringValue))
            return false;
        StringValue castObj = (StringValue)obj;
        return value.equals(castObj.value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(value);
    }

    public String toString() {
        return value;
    }
}
