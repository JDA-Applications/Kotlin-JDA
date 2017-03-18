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

infix fun MessageChannel.send(lazyContent: () -> String): Message {
    return sendMessage(lazyContent()).complete()
}

infix fun MessageChannel.sendEmbed(init: EmbedBuilder.() -> Unit): Message {
    val msg = message { embed(init) }
    return sendMessage(msg).complete()
}


infix fun MessageChannel.sendAsync(init: MessageBuilder.() -> Unit): Promise<Message> {
    val msg = message(init = init)
    return sendMessage(msg).promise()
}

infix fun MessageChannel.sendAsync(lazyContent: () -> String): Promise<Message> {
    return sendMessage(lazyContent()).promise()
}

infix fun MessageChannel.sendEmbedAsync(init: EmbedBuilder.() -> Unit): Promise<Message> {
    val msg = message {
        embed(init)
    }
    return sendMessage(msg).promise()
}


infix fun Message.edit(init: MessageBuilder.() -> Unit): Message {
    val msg = message(init = init)
    return editMessage(msg).complete()
}

infix fun Message.edit(lazyContent: () -> String): Message {
    return editMessage(lazyContent()).complete()
}

infix fun Message.editEmbed(init: EmbedBuilder.() -> Unit): Message {
    val msg = message { embed(init) }
    return editMessage(msg).complete()
}


infix fun Message.editAsync(init: MessageBuilder.() -> Unit): Promise<Message> {
    val msg = message(init = init)
    return editMessage(msg).promise()
}

infix fun Message.editAsync(lazyContent: () -> String): Promise<Message> {
    return editMessage(lazyContent()).promise()
}

infix fun Message.editEmbedAsync(init: EmbedBuilder.() -> Unit): Promise<Message> {
    val msg = message { embed(init) }
    return editMessage(msg).promise()
}
