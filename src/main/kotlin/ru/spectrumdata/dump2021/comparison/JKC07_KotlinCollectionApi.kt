package ru.spectrumdata.dump2021.comparison

class JKC07_KotlinCollectionApi {
    val myList = listOf("a", "1", "2", "3", "b", "3", "cc")
    val ints: List<Int> = myList.map { it.toIntOrNull() }.filterNotNull()
    val sumDistinct: Int = ints.distinct().sum()
}
