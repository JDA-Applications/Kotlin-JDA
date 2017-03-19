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

package club.minnced.kjda.events

import net.dv8tion.jda.core.events.Event
import net.dv8tion.jda.core.hooks.EventListener
import net.dv8tion.jda.core.hooks.IEventManager
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias static = JvmStatic

class AsyncEventManager(private val executor: ExecutorService = AsyncEventManager.POOL) : IEventManager {

    companion object {
        @static val POOL: ExecutorService by lazy({
            Executors.newCachedThreadPool {
                val t = Thread(it, "EventThread")
                t.isDaemon = true
                t
            }
        })
    }

    private val listeners = CopyOnWriteArraySet<EventListener>()

    override fun handle(event: Event?) = executor.execute { listeners.forEach {
        try {
            it.onEvent(event)
        }
        catch (ex: Throwable) {
            ex.printStackTrace()
        }}
    }

    override fun register(listener: Any?) {
        require(listener is EventListener) {
            "Listener must implement EventListener!"
        }
        listeners += listener as EventListener
    }

    override fun getRegisteredListeners(): MutableList<Any> = mutableListOf(listeners)

    override fun unregister(listener: Any?) {
        if (listener is EventListener)
            listeners -= listener
    }
}