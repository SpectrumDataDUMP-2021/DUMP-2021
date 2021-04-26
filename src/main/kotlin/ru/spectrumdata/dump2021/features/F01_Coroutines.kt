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
    suspend fun parallelProcessing() : Int {
        // можем прямо управлять пулами потоков
        val readPool = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        val processPool = Executors.newFixedThreadPool(8).asCoroutineDispatcher()
        val sum: AtomicInteger = AtomicInteger(0)
        // определение асинхронной группы кода, которая вся в целом должна завершиться
        coroutineScope {
            // инициализация канала, аналог  golang `make(chan string,100)`, сразу с привязанной корутиной генерации
            val input: ReceiveChannel<Int> = produce(readPool, capacity = 100) {
                // тут некое долгое чтение из базы или файла имитируется, поточное
                repeat(10_000) {
                    // имитация задержек чтения
                    delay(Random.nextLong(0, 1))
                    send(it)
                }
            }
            repeat(8) {
                // запуск рутины в нужном пуле потоков
                launch(processPool){
                    for(i in input){
                        // имитация задержек обработки
                        delay(Random.nextLong(0, 2))
                        sum.addAndGet(i)
                    }
                }
            }
        }
        return sum.get() // will be (0..9999).sum()
    }
}
