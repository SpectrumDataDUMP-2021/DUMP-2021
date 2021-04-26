package ru.spectrumdata.dump2021.comparison

class JKC06_KotlinStringCompare {
    var value: String? = "hello"
    fun matches(otherString: String?): Boolean {
        return value == otherString
    }
}
