package ru.spectrumdata.dump2021.comparison;

public class JKC02_JavaImmutableVariables {
    /**
     * Не особо показательно, видна сбивка форматирования и
     * необходимость дополнительного ключевого слова
     */
    private final int iX = 3;
    private int mX = 4;

    public void someFunc(){
        final int iY = 1;
        int mY = 2;
    }
}
