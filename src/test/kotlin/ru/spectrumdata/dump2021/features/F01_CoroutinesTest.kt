package ru.spectrumdata.dump2021.features

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class F01_CoroutinesTest : StringSpec() {
    init {
        "Проверяем, что сумма сходится"{
            F01_Coroutines().parallelProcessing() shouldBe (0..9999).sum()
        }
    }
}
