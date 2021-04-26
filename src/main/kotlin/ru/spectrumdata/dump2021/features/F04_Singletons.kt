package ru.spectrumdata.dump2021.features


interface ISomeValue {
    fun hello(): String
}
/**
 * Это собственно уже синглетон и это и класс и инстанс одновременно
 */
object F04_Singletons : ISomeValue {
    override fun hello(): String = "world" // это не статика и спокойно реализует интерфейсы
}
// можно присвоить как значение
val mysomeValue : ISomeValue = F04_Singletons

// но его же можно испльзовать и как имя класса
val myName = F04_Singletons::class.simpleName

// и в генерик части
val myList : List<F04_Singletons> = listOf(F04_Singletons)

// особый вид синглетонов - компаньоны , это "статическая" часть класса и обычно
// нужна для утилит, констант и фабричных паттернов
class MyClass private constructor(val myValue: String){ // закрыли конструктор для каких-то целей
    private var someFlag: Int = DEFAULT_FLAG_VALUE
    companion object : ISomeValue { // и он тоже не статический, а обычный класс-синглетон
        const val DEFAULT_FLAG_VALUE = 123 // константа
        fun create(value: String): MyClass { // фабричный метод
            // видно что компаньон "видит" приватные члены своего класса
            if(value.isBlank()) throw Exception("Нельзя создавать из пустой строки")
            val result = MyClass(value)
            if(value.toUpperCase() == value.toLowerCase()) {
                result.someFlag = 100
            }
            return result;
        }

        override fun hello(): String {
            return "world of companions"
        }
    }
}

// теперь его можно использовать как "статическую" часть класса
val myclass = MyClass.create("xxx")
val mystring = MyClass.hello()


