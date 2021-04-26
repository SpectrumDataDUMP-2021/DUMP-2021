package ru.spectrumdata.dump2021.features

import kotlin.random.Random
import kotlin.reflect.KClass

class F03_SmartCastAndInline {
    /**
     * Догадывается о типах
     */
    val i = 1 // понимает что это Int == val i: Int
    val ifun = { 1 } // понимает, что это ()->Int  == val ifun: ()-> Int

    interface Iface
    class A : Iface
    class B : Iface

    val list = listOf(A(), B()) // понимает, что это List<Iface>

    /**
     * Понимает что у переменной известно null или не null и какой тип
     */
    fun someNullTypeCast() {
        val obj: Any? = when (Random.nextInt(1, 3)) {
            1 -> null
            2 -> "hello"
            else -> 4
        } // изначально не известно, что же такое живет в obj
        if (null != obj) { // тут мы проверили значение на null
            println(obj.equals(123)) // и тут уже используем как не null
        }
        if (obj is String) { // тут мы проверили, что это строка
            println(obj.toInt()) // и теперь весь код в контексте это знает
        }
    }

    /**
     * reified хак для обхода typeErasure
     */
    // сначала классический вариант Java стайл c явным использованием класса
    fun <T : Any> printing(value: T, clazz: KClass<T>): T {
        println("У нас тут переменная класса `${clazz.simpleName}`, со значением: `$value`")
        return value
    }

    // а вот это хак, не требующий явной передачи типа в генерике и класса:
    inline fun <reified T : Any> printing(value: T) = printing(value, T::class)

    // теперь можно делать так:
    val myInt = printing(123) // == val myInt : Int = printing<Int>(123, Int::class)
}
