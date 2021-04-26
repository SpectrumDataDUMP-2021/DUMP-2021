package ru.spectrumdata.dump2021.features

/**
 *  Элементы функционального стиля
 */

/**
 * Определения ламбд и функций
 */
val my2_2: () -> Int = { 2 * 2 }
val my_sum: (Int) -> Int = { it + it }


fun myPseudoBlock(body: () -> Unit) { // последний параметр - функция
    println("Start")
    body()
    println("End")
}

fun myPseudoBlockUsage() {
    myPseudoBlock {   // теперь мы можем использовать myPseudoBlock в RUBY-стиле
        println("hello world")
    }

    // а можем и по-обычному
    myPseudoBlock({ println("hello world") })
}


/**
 * Хвостовая рекурсия (компилируется именно в цикл, а не в рекурсию)
 */
tailrec fun fib(size: Int, acc: Int = 0, base: Int = 1): Int {
    return when (size) {
        0 -> acc
        1 -> base
        // последний оператор должен быть return c вызовом этой же функции
        else -> fib(size - 1, base, acc + base)
    }
}

