package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType {
    private IType inner;

    public RefType(IType i) {
        inner = i;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public IType deepCopy() {
        return new RefType(inner);
    }
}
