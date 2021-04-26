package ru.spectrumdata.dump2021.comparison;

public class JKC03_JavaNullSafety {
    private String myNotNull = "";
    private String myNullable = null;

    public String getMyNotNull() {
        if (null == myNotNull) {
            throw new NullPointerException();
        }
        return myNotNull;
    }

    public void setMyNotNull(String value) {
        if (null == value) {
            throw new NullPointerException();
        }
        this.myNotNull = value;
    }

    public String getMyNullable() {
        return myNullable;
    }

    public void setMyNullable(String myNullable) {
        this.myNullable = myNullable;
    }
}
