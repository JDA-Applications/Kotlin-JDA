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

package club.minnced.kjda

import net.dv8tion.jda.core.requests.RestAction

fun<V> RestAction<V>.promise() = Promise(this)

class Promise<V>(action: RestAction<V>) {

    private val success = Callback<V>()
    private val failure = Callback<Throwable>()

    infix fun then(lazyCallback: (V) -> Unit): Promise<V> {
        success.backing = lazyCallback
        return this
    }

    infix fun catch(lazyHandler: (Throwable) -> Unit): Promise<V> {
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
        get() = finishedValue !== null
    var backing: (T) -> Unit = { }
        set(value) {
            if (finished)
                value(finishedValue!!)
            field = value
        }

    fun call(value: T): Unit = synchronized( backing ) {
        finishedValue = value
        backing(value)
    }

}