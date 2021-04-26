package ru.spectrumdata.dump2021.features.sqldsl

data class SqlColumn(
    val name: String,
    val type: String,
    val notnull: Boolean = true,
    val comment: String? = null,
    val default: String? = null,
) : ISqlElement {

    override fun toSql(): String {
        val nullString = if (notnull) "NOT NULL" else "NULL"
        val defaultString = if (default.isNullOrBlank()) "" else "DEFAULT ${default}"
        return "${name} ${type} $nullString $defaultString".trim()
    }
}
