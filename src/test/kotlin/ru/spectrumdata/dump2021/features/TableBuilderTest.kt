package ru.spectrumdata.dump2021.features

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.spectrumdata.dump2021.features.sqldsl.SqlColumn
import ru.spectrumdata.dump2021.features.sqldsl.SqlConstraint
import ru.spectrumdata.dump2021.features.sqldsl.SqlTable


internal class TableBuilderTest : StringSpec() {
    init {
        "Проверяем корректное создание и генерацию SQL" {
            val myschema__table1 by table { // теперь можем так создавать SqlTable
                // schema = myschema, name = table1
                COMMENT IS "Пример таблицы"
                val id by BIGINT NOT NULL COMMENT "Это идентификатор!"
                val part by SMALLINT NOT NULL DEFAULT 0 COMMENT "Это партиция!"
                val extra by JSONB
                val id_part by CHECK("id > 0 AND part > 0 AND part < 1000")
                PRIMARY KEY listOf(id, part)
            }
            // если создавать без DSL
            val idColumn = SqlColumn(
                "id",
                "bigint",
                notnull = true,
                comment = "Это идентификатор!"
            )
            val partColumn = SqlColumn(
                "part",
                "smallint",
                notnull = true,
                default = "0",
                comment = "Это партиция!"
            )
            val noDslCreatedTable = SqlTable(
                schema = "myschema",
                name = "table1",
                comment = "Пример таблицы",
                columns = listOf(
                    idColumn,
                    partColumn,
                    SqlColumn(
                        "extra",
                        "jsonb"
                    )
                ),
                constraints = listOf(
                    SqlConstraint.Check("id_part", "id > 0 AND part > 0 AND part < 1000"),
                    SqlConstraint.PrimaryKey(listOf(idColumn, partColumn))
                )
            )

            myschema__table1 shouldBe noDslCreatedTable

            myschema__table1.toSql() shouldBe """
                CREATE TABLE IF NOT EXISTS myschema.table1 (
                id bigint NOT NULL,
                part smallint NOT NULL DEFAULT 0,
                extra jsonb NOT NULL,
                CONSTRAINT id_part_chk CHECK (id > 0 AND part > 0 AND part < 1000),
                CONSTRAINT id_part_pkey PRIMARY KEY (id,part)
                );
                COMMENT ON TABLE myschema.table1 IS 'Пример таблицы';
                COMMENT ON COLUMN myschema.table1.id IS 'Это идентификатор!';
                COMMENT ON COLUMN myschema.table1.part IS 'Это партиция!';
                
                """.trimIndent()
        }
    }
}
