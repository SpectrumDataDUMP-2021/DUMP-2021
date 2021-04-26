package ru.spectrumdata.dump2021.comparison

class JKC05_KotlinDefaultValues {
    fun someFunction(x: Int = 1, y: Int = 2, z: Int = 3) = x + y + z

    fun someFunctionCall() {
        println(someFunction())
        println(someFunction(4))
        println(someFunction(4, 5))
        println(someFunction(z = 10))
    }
}
