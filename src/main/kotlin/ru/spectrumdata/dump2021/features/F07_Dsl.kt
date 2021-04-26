package ru.spectrumdata.dump2021.features

import ru.spectrumdata.dump2021.features.sqldsl.SqlColumn
import ru.spectrumdata.dump2021.features.sqldsl.SqlConstraint
import ru.spectrumdata.dump2021.features.sqldsl.SqlTable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class TableBuilder(val schema: String, val name: String) {
    fun build(): SqlTable {
        return SqlTable(
            schema = schema,
            name = name,
            columns = columns.map { it.build() },
            constraints = constraints.toList(),
            comment = comment
        )
    }

    // изменяемый вариант колонки
    inner class SqlColumnBuilder(
        var name: String? = null,
        var type: String? = null,
        var notnull: Boolean = true,
        var comment: String? = null,
        var default: String? = null,
    ) {

        fun build(): SqlColumn {
            return SqlColumn(
                name = name ?: throw Exception("name not defined"),
                type = type ?: throw Exception("type not defined"),
                notnull = notnull,
                comment = comment,
                default = default
            )
        }

        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyProperty<Any?, SqlColumn> {
            this.name = this.name ?: property.name
            this@TableBuilder.columns.add(this)
            return ReadOnlyProperty { _, _ -> this.build() }
        }

        infix fun NOT(__null: __NULL): SqlColumnBuilder {
            this.notnull = true
            return this
        }

        infix fun DEFAULT(sql: Any): SqlColumnBuilder {
            this.default = sql.toString()
            return this
        }

        infix fun COMMENT(comment: String): SqlColumnBuilder {
            this.comment = comment
            return this
        }
    }

    private val columns: MutableList<SqlColumnBuilder> = mutableListOf()

    // для синтаксиса val myfield by INT
    val INT get() = SqlColumnBuilder(type = "int")

    // для синтаксиса val myfield by BIGINT
    val BIGINT get() = SqlColumnBuilder(type = "bigint")

    // для синтаксиса val myfield by SMALLINT
    val SMALLINT get() = SqlColumnBuilder(type = "smallint")

    // для синтаксиса val myfield by JSONB
    val JSONB get() = SqlColumnBuilder(type = "jsonb")

    // хак для псевдозначения NULL
    object __NULL

    val NULL = __NULL

    interface ICheckBuilder {
        operator fun provideDelegate(
            thisRef: Any?,
            property: KProperty<*>,
        ): ReadOnlyProperty<Any?, SqlConstraint.Check>
    }

    inner private class CheckBuilder(val prefix: String? = null, val sql: String) : ICheckBuilder {
        override operator fun provideDelegate(
            thisRef: Any?,
            property: KProperty<*>,
        ): ReadOnlyProperty<Any?, SqlConstraint.Check> {
            val prefix = this.prefix ?: property.name
            val result = SqlConstraint.Check(prefix, sql)
            this@TableBuilder.constraints.add(result)
            return ReadOnlyProperty { _, _ -> result }
        }
    }

    fun CHECK(sql: String): ICheckBuilder = CheckBuilder(null, sql)

    val constraints: MutableList<SqlConstraint> = mutableListOf()

    // обеспечим возможность настройки комментария таблицы в синтаксисе COMMENT IS "Мой комментарий"
    var comment: String? = null

    interface ICommentProvider {
        infix fun IS(comment: String)
    }

    val COMMENT: ICommentProvider = _COMMENT()

    private inner class _COMMENT : ICommentProvider {
        override infix fun IS(comment: String) {
            this@TableBuilder.comment = comment
        }
    }

    interface IPrimaryKeyBuilder {
        infix fun KEY(field: SqlColumn): IPrimaryKeyBuilder
        infix fun KEY(fields: List<SqlColumn>): IPrimaryKeyBuilder
        operator fun provideDelegate(
            thisRef: Any?,
            property: KProperty<*>,
        ): ReadOnlyProperty<Any?, SqlConstraint.PrimaryKey>
    }

    // Хак для первичного ключа
    inner private class PrimaryKeyBuilder : IPrimaryKeyBuilder {
        private lateinit var pk: SqlConstraint.PrimaryKey
        override fun KEY(field: SqlColumn): IPrimaryKeyBuilder = KEY(listOf(field))

        override fun KEY(fields: List<SqlColumn>): IPrimaryKeyBuilder {
            pk = SqlConstraint.PrimaryKey(fields)
            this@TableBuilder.constraints.add(pk)
            return this
        }

        override fun provideDelegate(
            thisRef: Any?,
            property: KProperty<*>,
        ): ReadOnlyProperty<Any?, SqlConstraint.PrimaryKey> {
            if (!::pk.isInitialized) {
                throw Exception("Ключ недоинициализирован")
            }
            return ReadOnlyProperty { _, _ -> pk }
        }
    }

    val PRIMARY: IPrimaryKeyBuilder = PrimaryKeyBuilder()
}

class TablePropertyProvider<T>(val builderFunc: TableBuilder.() -> Unit) {
    operator fun <T> provideDelegate(thisRef: T, property: KProperty<*>): ReadOnlyProperty<T, SqlTable> {
        // просто для примера извлекаем схему и имя таблицы из имени свойства
        val (schema, name) = property.name.split("__")
        val builder = TableBuilder(schema, name)
        builder.builderFunc() // вызываем нашего билдера
        val result = builder.build() // собственно кэшируем результат

        return ReadOnlyProperty { _, _ -> result } // ну и заворачиваем ответ в ReadOnlyProperty<*, SqlTable>
    }
}

// генератор свойств для глобальной области видимости
fun table(body: TableBuilder.() -> Unit) = TablePropertyProvider<Nothing?>(body)

// все, можем использовать
val myschema__table1 by table { // теперь можем так создавать SqlTable
    // schema = myschema, name = table1
    COMMENT IS "Пример таблицы"
    val id by BIGINT NOT NULL
    val part by SMALLINT NOT NULL DEFAULT 0
    val extra by JSONB
    val id_part by CHECK("id > 0 AND part > 0 AND part < 1000")
    PRIMARY KEY listOf(id, part)
}
