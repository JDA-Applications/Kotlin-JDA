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

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel

infix fun MessageChannel.send(init: MessageBuilder.() -> Unit): Message {
    val msg = message(init = init)
    return sendMessage(msg).complete()
}

infix fun MessageChannel.sendText(lazyContent: () -> Any): Message {
    return sendMessage(lazyContent().toString()).complete()
}

infix fun MessageChannel.sendEmbed(init: EmbedBuilder.() -> Unit): Message {
    val msg = message { embed(init) }
    return sendMessage(msg).complete()
}


infix fun MessageChannel.sendAsync(init: MessageBuilder.() -> Unit): RestPromise<Message> {
    val msg = message(init = init)
    return sendMessage(msg).promise()
}

infix fun MessageChannel.sendTextAsync(lazyContent: () -> Any): RestPromise<Message> {
    return sendMessage(lazyContent().toString()).promise()
}

infix fun MessageChannel.sendEmbedAsync(init: EmbedBuilder.() -> Unit): RestPromise<Message> {
    val msg = message {
        embed(init)
    }
    return sendMessage(msg).promise()
}


infix fun Message.edit(init: MessageBuilder.() -> Unit): Message {
    val msg = message(init = init)
    return editMessage(msg).complete()
}

infix fun Message.editText(lazyContent: () -> Any): Message {
    return editMessage(lazyContent().toString()).complete()
}

infix fun Message.editEmbed(init: EmbedBuilder.() -> Unit): Message {
    val msg = message { embed(init) }
    return editMessage(msg).complete()
}


infix fun Message.editAsync(init: MessageBuilder.() -> Unit): RestPromise<Message> {
    val msg = message(init = init)
    return editMessage(msg).promise()
}

infix fun Message.editTextAsync(lazyContent: () -> Any): RestPromise<Message> {
    return editMessage(lazyContent().toString()).promise()
}

infix fun Message.editEmbedAsync(init: EmbedBuilder.() -> Unit): RestPromise<Message> {
    val msg = message { embed(init) }
    return editMessage(msg).promise()
}
