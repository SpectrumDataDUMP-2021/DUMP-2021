package ru.spectrumdata.dump2021.features

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class F01_Coroutines {
    suspend fun parallelProcessing(): Int {
        // можем прямо управлять пулами потоков
        val readPool = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        val processPool = Executors.newFixedThreadPool(8).asCoroutineDispatcher()
        val sum: AtomicInteger = AtomicInteger(0)

        // определение асинхронной группы кода, которая вся в целом должна завершиться
        coroutineScope {
            /**
             * Инициализация канала сразу с привязкой корутинной генерации
             * По сути является аналогом channel в Golang := make(chan string, 100)
             */
            val input: ReceiveChannel<Int> = produce(readPool, capacity = 100) {
                // Здесь мы имитируем некое поточное долгое чтение из базы или файла
                repeat(10_000) {
                    delay(Random.nextLong(0, 1)) // имитация задержек чтения
                    send(it)
                }
            }
            repeat(8) {
                // Запускаем несколько корутин в нужном пуле потоков
                launch(processPool) {
                    for (i in input) {
                        delay(Random.nextLong(0, 2)) // имитация задержек обработки
                        sum.addAndGet(i)
                    }
                }
            }
        }

        return sum.get() // will be (0..9999).sum()
    }
}
