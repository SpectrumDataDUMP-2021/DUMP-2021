package ru.spectrumdata.dump2021.features

import java.io.File
import java.time.Duration
import java.time.Instant
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class F02_PropertyProviders {
    // Встроенный провайдер Lazy для инициализации значения только на запрос
    val isEnabled by lazy { System.getenv("I_AM_ENABLED").toBoolean() }

    /**
     * Пример простого самодельного делегата для свойств с кэшированнием значения (на время).
     * В данном случае перечитывание файла каждые 10 минут, причем тоже ленивое
     */
    val someFileContent: String by cached(Duration.ofMinutes(10)) {
        File("./somefile.txt").readText()
    }
}

/**
 * Упрощенный акцессор
 */
fun <T> cached(cacheTime: Duration = Duration.ofMinutes(1), getter: () -> T) = CachedValue(cacheTime, getter)

/**
 * Клас для свойств с логированием создания и изменения
 */
class CachedValue<T>(
    /**
     * Время кэширования
     */
    private val cacheTime: Duration,

    /**
     * Метод получения нового значения свойства
     */
    val getter: () -> T,
) :
    ReadOnlyProperty<Any, T> {
    private val sync = Any()
    private var value: T? = null
    private var lastRead: Instant = Instant.MIN
    private val requireRefresh get() = null == value || (lastRead + cacheTime <= Instant.now())

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        if (requireRefresh) {
            synchronized(sync) {
                if (requireRefresh) {
                    value = getter()
                }
            }
        }
        return value!!
    }
}

