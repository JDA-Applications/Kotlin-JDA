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
@file:JvmName("KJDABuilders")
package club.minnced.kjda

import club.minnced.kjda.builders.KEmbedBuilder
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.IMentionable
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageEmbed

/**
 * Constructs a [Message] from the specified [init] function
 * which has a [MessageBuilder] as its receiver.
 *
 * To set an embed use [embed]!
 *
 * @param[builder]
 *       An optional [MessageBuilder] to use as receiver,
 *       this creates a new instance by default.
 * @param[init]
 *       A function which constructs a new [Message] by using
 *       the receiving [MessageBuilder]
 *
 * @return[Message] - a sendable finished Message instance
 */
fun message(builder: MessageBuilder = MessageBuilder(), init: MessageBuilder.() -> Unit): Message {
    builder.init()
    return builder.build()
}

operator fun Appendable.plusAssign(other: CharSequence) {
    append(other)
}

operator fun Appendable.plusAssign(other: Char) {
    append(other)
}

operator fun Appendable.plusAssign(other: IMentionable) {
    append(other.asMention)
}

/**
 * Constructs a [MessageEmbed] for the receiving [MessageBuilder]
 * and sets that constructed Embed using [MessageBuilder.setEmbed]!
 *
 * @param[init]
 *       A function which constructs a [MessageEmbed] from the receiving
 *       [EmbedBuilder]
 *
 * @receiver[MessageBuilder]
 *
 * @return[MessageBuilder] - current MessageBuilder
 */
infix inline fun MessageBuilder.embed(crossinline init: KEmbedBuilder.() -> Unit): MessageBuilder
    = setEmbed(club.minnced.kjda.builders.embed { init() })
