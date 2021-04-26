package ru.spectrumdata.dump2021.comparison;

public class JKC05_JavaDefaultValues {
    public int someFunction() {
        return someFunction(1);
    }

    public int someFunction(int x) {
        return someFunction(x, 2);
    }

    public int someFunction(int x, int y) {
        return someFunction(x, y, 3);
    }

    public int someFunction(int x, int y, int z) {
        return x + y + z;
    }

    public void someFunctionCall() {
        System.out.println(someFunction());
        System.out.println(someFunction(4));
        System.out.println(someFunction(4, 5));
    }
}
