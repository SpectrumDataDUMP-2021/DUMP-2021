package ru.spectrumdata.dump2021.comparison

class JKC04_KotlinImmutableCollections {
    val myList: List<String> = listOf("a", "b")
    val myMutableList = myList.toMutableList()

    fun add(value: String) {
        // В интерфейсе коллекции myList нет метода `add`
        // myList.add("value")
        myMutableList.add("value")
    }
}
