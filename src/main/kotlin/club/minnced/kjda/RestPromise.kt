/*
 *     Copyright 2016 - 2017 Florian Spieß
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("UNUSED")
package club.minnced.kjda

import kotlinx.coroutines.experimental.*
import net.dv8tion.jda.core.requests.RestAction
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.coroutines.experimental.CoroutineContext

/** Constructs a new [RestPromise] for this [RestAction] instance */
fun<V> RestAction<V?>.promise() = RestPromise(this)

/** Shortcut for [RestPromise.then] */
infix fun<V> RestAction<V?>.then(apply: V?.() -> Unit) = this.promise().then {
    it.apply()
}

/** Shortcut for [RestPromise.catch] */
infix fun<V> RestAction<V?>.catch(apply: Throwable?.() -> Unit) = this.promise().catch {
    it.apply()
}

// Conditional
fun<V> RestAction<V?>.onlyIf(condition: Boolean, block: RestPromise<V>.() -> Unit = { }) {
    if (condition)
        this.promise().block()
}

fun<V> RestAction<V?>.unless(condition: Boolean, block: RestPromise<V>.() -> Unit = { }) {
    if (!condition)
        this.promise().block()
}

// Coroutines
fun<V> RestAction<V?>.prepare(context: CoroutineContext = CommonPool) = async(context, start = false) {
    this@prepare.promise()
}

fun<V> RestAction<V?>.start(context: CoroutineContext = CommonPool) = launch(context) {
    this@start.queue()
}

suspend fun<V> RestAction<V?>.get(context: CoroutineContext = CommonPool) = run(context) {
    this@get.complete()
}

suspend fun<V> RestAction<V?>.after(time: Long, unit: TimeUnit = MILLISECONDS, context: CoroutineContext = CommonPool)
= run(context) {
    delay(time, unit)
    this@after.get()
}

/**
 * This class allows the end-user to specify callback behaviour after issuing
 * a request.
 *
 * ```kotlin
 * channel.sendMessage("Hello").promise() then {
 *     println("Sent Message $it")
 * } catch {
 *     println("Failed to send Message")
 *     it.printStackTrace()
 * }
 * ```
 *
 * @constructor Creates a new RestPromise for the specified [RestAction] and
 *              calls the [RestAction.queue]
 */
class RestPromise<V>(action: RestAction<V?>) {

    private val success = Callback<V>()
    private val failure = Callback<Throwable>()

    /**
     * Overrides the internal Success-Callback.
     *
     * The provided function is called immediately if this already finished
     * Successfully.
     *
     * @param[lazyCallback] The function to replace the current Success-Callback
     *
     * @return The current RestPromise
     */
    infix fun then(lazyCallback: (V?) -> Unit): RestPromise<V> {
        success.backing = lazyCallback
        return this
    }

    /**
     * Overrides the internal Failure-Callback.
     *
     * The provided function is called immediately if this already failed.
     *
     * @param[lazyHandler] The function to replace the current Failure-Callback
     *
     * @return The current RestPromise
     */
    infix fun catch(lazyHandler: (Throwable?) -> Unit): RestPromise<V> {
        failure.backing = lazyHandler
        return this
    }

    init {
        action.queue({ success.call(it) }, { failure.call(it) })
    }

}

@FunctionalInterface
internal class Callback<T> {

    var finishedValue: T? = null
    var finished: Boolean = false

    var backing: (T?) -> Unit = { }
    set(value) {
            if (finished)
                value(finishedValue)
            field = value
    }

    fun call(value: T?): Unit = synchronized( backing ) {
        finished = true
        finishedValue = value
        backing(value)
    }

}
