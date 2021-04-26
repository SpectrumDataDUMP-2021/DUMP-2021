package ru.spectrumdata.dump2021.comparison

class JKC04_KotlinImmutableCollections {
    val myList: List<String> = listOf("a", "b" , /* нельзя добавить null */)
    fun add(value: String){
        // myList.add(value)  - нет такого метода в интерфейсе
    }
}
