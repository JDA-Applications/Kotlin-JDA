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
@file:JvmName("KJDAMessageSending")
package club.minnced.kjda

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel

/**
 * Sends a new [Message] to the receiving [MessageChannel]
 * and returns the resulting [Message] object.
 *
 * It is recommended to use [sendAsync] if the returned
 * [Message] is not used.
 *
 * @param[init]
 *       A function which holds a [MessageBuilder] as its receiver
 *
 * @receiver[MessageChannel]
 *
 * @return [Message] of successful send task.
 */
infix fun MessageChannel.send(init: MessageBuilder.() -> Unit): Message {
    val msg = message(init = init)
    return sendMessage(msg).complete()
}

/**
 * Sends a new [Message] to the receiving [MessageChannel]
 * and returns the resulting [Message] object.
 *
 * It is recommended to use [sendTextAsync] if the returned
 * [Message] is not used.
 *
 * @param[lazyContent]
 *       A function which provides anything and will be converted to a [String]
 *       to be provided as `Message Content`.
 *
 * @receiver[MessageChannel]
 *
 * @return [Message] of successful send task.
 */
infix fun MessageChannel.sendText(lazyContent: () -> Any): Message {
    return sendMessage(lazyContent().toString()).complete()
}

/**
 * Sends a new [MessageEmbed][net.dv8tion.jda.core.entities.MessageEmbed] to the receiving [MessageChannel]
 * and returns the resulting [Message] object.
 *
 * It is recommended to use [sendEmbedAsync] if the returned
 * [Message] is not used.
 *
 * @param[init]
 *       A function which holds an [EmbedBuilder] as its receiver
 *
 * @receiver[MessageChannel]
 *
 * @return [Message] of successful send task.
 */
infix fun MessageChannel.sendEmbed(init: EmbedBuilder.() -> Unit): Message {
    val msg = message { embed(init) }
    return sendMessage(msg).complete()
}

/**
 * Sends a [Message] to the receiving [MessageChannel]
 * and returns a [RestPromise] to represent the async task.
 *
 * You can use [RestPromise.then] and [RestPromise.catch] to
 * handle success and failure.
 *
 * @param[init]
 *       A function which constructs a [Message] instance using
 *       a [MessageBuilder] as its receiver.
 *
 * @receiver[MessageChannel]
 *
 * @return A [RestPromise] representing the send task
 */
infix fun MessageChannel.sendAsync(init: MessageBuilder.() -> Unit): RestPromise<Message> {
    val msg = message(init = init)
    return sendMessage(msg).promise()
}

/**
 * Sends a [Message] to the receiving [MessageChannel]
 * and returns a [RestPromise] to represent the async task.
 *
 * You can use [RestPromise.then] and [RestPromise.catch] to
 * handle success and failure.
 *
 * @param[lazyContent]
 *       A function which constructs a [Message] instance using
 *       the provided function lazy load its content using a [Any.toString] call
 *
 * @receiver[MessageChannel]
 *
 * @return A [RestPromise] representing the send task
 */
infix fun MessageChannel.sendTextAsync(lazyContent: () -> Any): RestPromise<Message> {
    return sendMessage(lazyContent().toString()).promise()
}

/**
 * Sends a [MessageEmbed][net.dv8tion.jda.core.entities.MessageEmbed] to the receiving [MessageChannel]
 * and returns a [RestPromise] to represent the async task.
 *
 * You can use [RestPromise.then] and [RestPromise.catch] to
 * handle success and failure.
 *
 * @param[init]
 *       A function which constructs a [Message] instance using
 *       a [EmbedBuilder] as its receiver.
 *
 * @receiver[MessageChannel]
 *
 * @return A [RestPromise] representing the send task
 */
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
