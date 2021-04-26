package ru.spectrumdata.dump2021.comparison;

public class JKC06_JavaStringCompare {
    String value = "hello";

    public boolean matches(String otherString) {
        return (null == value && null == otherString)
                || (value != null && value.equals(otherString));
    }
}
