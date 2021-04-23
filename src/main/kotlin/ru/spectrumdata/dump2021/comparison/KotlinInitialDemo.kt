package ru.spectrumdata.dump2021.comparison

class KotlinInitialDemo(
    var myValue: String = "",
    ints: Iterable<Int> = emptyList(),
) {
    val ints: List<Int> = ints.toList()
}