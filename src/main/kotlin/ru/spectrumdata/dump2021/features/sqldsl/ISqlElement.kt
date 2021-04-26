package ru.spectrumdata.dump2021.features.sqldsl

/**
 * Допустим есть некое API описания объектов таблиц в SQL
 */
interface ISqlElement {
    fun toSql(): String
}
