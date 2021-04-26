package ru.spectrumdata.dump2021.features.sqldsl

sealed class SqlConstraint(val name: String) : ISqlElement {
    data class PrimaryKey(val fields: List<SqlColumn>) :
        SqlConstraint("${fields.map { it.name }.joinToString("_")}_pkey") {
        override fun toSql(): String {
            return "CONSTRAINT ${name} PRIMARY KEY (${fields.map { it.name }.joinToString(",")})"
        }
    }

    data class Check(val prefix: String, val sql: String) : SqlConstraint("${prefix}_chk") {
        override fun toSql(): String {
            return "CONSTRAINT ${name} CHECK ($sql)"
        }
    }
}
