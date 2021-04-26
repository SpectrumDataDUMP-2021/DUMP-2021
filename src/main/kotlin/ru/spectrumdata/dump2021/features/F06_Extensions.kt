package ru.spectrumdata.dump2021.features

import java.io.File
import java.io.Reader


// можно расширять системные классы как функциями
// и этим решать какие-то неудобности и проблемы в API

fun Int.cube() = this * this * this
// так и свойствами
val Int.asCube get() = this * this * this

val c3f = 3.cube() // 9
val c3p = 3.asCube // 9


// можно расширять собственные интерфейсы, компаньоны

interface IMyInterface {
    fun read(reader: Reader): String
}

/**
 * Вынесено в расширение, так как если размещать в интерфейсе, даже как default - это
 * будет не final, другого способоа сделать инварианту на уровне интерфейса нет
 */
fun IMyInterface.read(file: File) = file.reader().use{ this.read(it) }

/**
 * Так например можно добавить свои фабричные методы в зависимых модулях
 */
interface IDbConnection {
    fun execute(query: String)
    companion object // заготовка
}

/**
 * И где-то для удобства использования в зависимом модуле
 */
fun IDbConnection.Companion.postgres(connectionString: String) : IDbConnection {
    TODO("Реализации тут мы не прописываем")
}

// и теперь можно все делать через интерфейс
val myPgConnection = IDbConnection.postgres("тут какая-то строка подключения")

