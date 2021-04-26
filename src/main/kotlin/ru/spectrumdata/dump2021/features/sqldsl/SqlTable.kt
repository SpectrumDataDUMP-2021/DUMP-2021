package ru.spectrumdata.dump2021.features.sqldsl

data class SqlTable(
    val schema: String,
    val name: String,
    val columns: List<SqlColumn>,
    val constraints: List<SqlConstraint>,
    val comment: String? = null
) : ISqlElement {
    override fun toSql(): String {
        val sb = StringBuilder()
        sb.appendLine("""CREATE TABLE IF NOT EXISTS $schema.$name (""")
        val elements = columns + constraints
        sb.appendLine(elements.map { it.toSql() }.joinToString(",\n"))
        sb.appendLine(");")
        if(!comment.isNullOrBlank()) {
            sb.appendLine("COMMENT ON TABLE ${schema}.${name} IS '${comment}';")
        }
        columns.filter { !it.comment.isNullOrBlank() }.forEach { column ->
            sb.appendLine("COMMENT ON COLUMN ${schema}.${name}.${column.name} IS '${column.comment}';")
        }
        return sb.toString()
    }

}
