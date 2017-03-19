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
@file:JvmName("KJDAMessages")
package club.minnced.kjda.entities

import club.minnced.kjda.*
import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.MessageEmbed

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
 * Sends a new [Message] constructed from the contents
 * of the provided [KMessage].
 *
 * It is recommended to [sendAsync] when the resulting [Message]
 * is not used.
 *
 * @param[message]
 *       A sendable [KMessage] instance
 *
 * @throws[IllegalArgumentException]
 *        If the provided message is empty
 *
 * @return[Message] - The message that has been processed
 */
infix fun MessageChannel.send(message: KMessage): Message {
    require(message.sendable) {
        "Cannot send empty message!"
    }

    val (content, tts, embed) = message
    return send {
        if (content !== null)
            this += content
        if (embed !== null)
            this.setEmbed(embed)
        this.setTTS(tts)
    }
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
 * Sends a new [Message] constructed from the contents
 * of the provided [KMessage].
 *
 * @param[message]
 *       A sendable [KMessage] instance
 *
 * @throws[IllegalArgumentException]
 *        If the provided message is empty
 *
 * @return[RestPromise] - RestPromise representing the send task
 */
infix fun MessageChannel.sendAsync(message: KMessage): RestPromise<Message> {
    require(message.sendable) {
        "Cannot send empty message!"
    }

    val (content, tts, embed) = message
    return sendAsync {
        if (content !== null)
            this += content
        if (embed !== null)
            this.setEmbed(embed)
        this.setTTS(tts)
    }
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

infix fun Message.edit(message: KMessage): Message {
    require(message.sendable) {
        "Cannot send empty message!"
    }

    val (content, tts, embed) = message
    return edit {
        if (content !== null)
            this += content
        if (embed !== null)
            this.setEmbed(embed)
        this.setTTS(tts)
    }
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

infix fun Message.editAsync(message: KMessage): RestPromise<Message> {
    require(message.sendable) {
        "Cannot send empty message!"
    }

    val (content, tts, embed) = message
    return editAsync {
        if (content !== null)
            this += content
        if (embed !== null)
            this.setEmbed(embed)
        this.setTTS(tts)
    }
}

infix fun Message.editTextAsync(lazyContent: () -> Any): RestPromise<Message> {
    return editMessage(lazyContent().toString()).promise()
}

infix fun Message.editEmbedAsync(init: EmbedBuilder.() -> Unit): RestPromise<Message> {
    val msg = message { embed(init) }
    return editMessage(msg).promise()
}

data class KMessage(
    val content: String? = null,
    val tts: Boolean = false,
    val embed: MessageEmbed? = null
) {
    internal val sendable: Boolean
        get() = content?.isNotEmpty() ?: false || embed !== null
}

/**
 * Splits the rawContent of this message into
 * the provided slice amount, use `<= 0` amount to slice all.
 *
 * Using `\s+` as slice regex
 */
operator fun Message.div(upTo: Int): List<String> {
    if (upTo < 1)
        return rawContent / upTo

    return rawContent / upTo
}
