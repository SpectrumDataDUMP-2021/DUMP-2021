package ru.spectrumdata.dump2021.features

interface ISomeValue {
    fun hello(): String
}

/**
 * Объект уже является singleton.
 * При этом это и класс и инстанс одновременно
 */
object F04_Singletons : ISomeValue {
    override fun hello(): String = "world" // при этом это не статика и спокойно реализует интерфейсы
}

// можно присвоить как значение
val mySomeValue: ISomeValue = F04_Singletons

// но его же можно использовать и как имя класса
val myName = F04_Singletons::class.simpleName

// и в генерик части
val myList: List<F04_Singletons> = listOf(F04_Singletons)

/**
 * Особый вид singleton - компаньоны , это "статическая" часть класса.
 * Обычно нужна для утилит, констант и фабричных паттернов
 */
class MyClass private constructor(val myValue: String) { // закрыли конструктор для каких-то целей
    private var someFlag: Int = DEFAULT_FLAG_VALUE

    /**
     * Обратите внимание, что компаньон класса может самостоятельно реализовывать интерфейсы
     * При этом он тоже не статический, а полностью обычный класс-singleton
     */
    companion object : ISomeValue {
        const val DEFAULT_FLAG_VALUE = 123 // константа

        fun create(value: String): MyClass { // фабричный метод
            // видно что компаньон "видит" приватные члены своего класса
            if (value.isBlank()) {
                throw Exception("Нельзя создавать из пустой строки")
            }

            val result = MyClass(value)

            if (value.toUpperCase() == value.toLowerCase()) {
                result.someFlag = 100
            }

            return result;
        }

        // реализация интерфейса
        override fun hello(): String {
            return "world of companions"
        }
    }
}

// теперь его можно использовать как "статическую" часть класса
val myclass = MyClass.create("xxx")
val mystring = MyClass.hello()
