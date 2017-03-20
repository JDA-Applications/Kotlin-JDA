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

package club.minnced.kjda.entities

import club.minnced.kjda.get
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.MessageHistory
import java.util.LinkedList
import java.util.Queue
import java.util.concurrent.TimeUnit.SECONDS

suspend fun MessageHistory.paginated() = produce<Message>(CommonPool) {
    val messages: Queue<Message> = LinkedList(retrievePast(100).get())
    do {
        while (messages.isNotEmpty())
            send(messages.poll())
        delay(1, SECONDS)
        messages += retrievePast(100).get()
    }
    while (messages.isNotEmpty())
}

val channel: MessageChannel? = null

fun main(args: Array<String>) = runBlocking {
    val history = MessageHistory(channel)
    val it = history.paginated().iterator()
    while (it.hasNext())
        println(it.next())
}
