package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue {
    private int address;
    private IType locationType;

    public RefValue(int a, IType t) {
        address = a;
        locationType = t;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RefValue))
            return false;
        RefValue castObj = (RefValue) obj;
        return address == castObj.address && locationType == castObj.locationType;
    }

    @Override
    public String toString() {
        return address + ", " + locationType.toString();
    }

    public int getAddress() {
        return address;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType);
    }
}
