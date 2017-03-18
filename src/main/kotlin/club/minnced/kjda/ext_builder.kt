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

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.IMentionable
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageEmbed
import net.dv8tion.jda.core.entities.MessageEmbed.Field
import java.awt.Color
import java.awt.Color.decode
import java.time.Instant
import java.time.temporal.TemporalAccessor

fun message(builder: MessageBuilder = MessageBuilder(), init: MessageBuilder.() -> Unit): Message {
    builder.init()
    return builder.build()
}

operator fun MessageBuilder.plusAssign(other: CharSequence) {
    append(other)
}

operator fun MessageBuilder.plusAssign(other: Char) {
    append(other)
}

operator fun MessageBuilder.plusAssign(other: IMentionable) {
    append(other)
}

infix inline fun MessageBuilder.embed(init: EmbedBuilder.() -> Unit): MessageBuilder {
    val builder = EmbedBuilder()
    builder.init()
    setEmbed(builder.build())
    return this
}

fun embed(init: EmbedBuilder.() -> Unit): MessageEmbed {
    val builder = EmbedBuilder()
    builder.init()
    return builder.build()
}

fun EmbedBuilder.fields(body: EmbedBuilder.() -> Set<Field>): EmbedBuilder {
    body().forEach { addField(it) }
    return this
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

fun field(init: FieldBuilder.() -> Unit): MutableSet<Field> {
    val builder = FieldBuilder()
    builder.init()
    return mutableSetOf(builder.build())
}

infix fun MutableSet<Field>.and(init: FieldBuilder.() -> Unit): MutableSet<Field> {
    val builder = FieldBuilder()
    builder.init()
    this += builder.build()
    return this
}

infix inline fun EmbedBuilder.color(lazy: () -> String)
    = this.setColor(decode(lazy()))
infix fun EmbedBuilder.color(color: Color)
    = this.setColor(color)
infix inline fun EmbedBuilder.image(lazy: () -> String)
    = this.setImage(lazy())

infix fun EmbedBuilder.author(init: Author.() -> Unit) {
    val author = Author()
    author.init()
    this.setAuthor(author.name, author.url, author.icon)
}

inline fun EmbedBuilder.footer(icon: String? = null, lazy: () -> String)
    = this.setFooter(lazy(), icon)

inline fun EmbedBuilder.title(url: String? = null, lazy: () -> String)
    = this.setTitle(lazy(), url)

fun EmbedBuilder.timestamp(lazy: () -> TemporalAccessor = { Instant.now() })
    = this.setTimestamp(lazy())

class FieldBuilder internal constructor() {

    var name: String = EmbedBuilder.ZERO_WIDTH_SPACE
        set(value) {
            require(value.length <= MessageEmbed.TITLE_MAX_LENGTH)
            field = value
        }
    var value: String = EmbedBuilder.ZERO_WIDTH_SPACE
        set(value) {
            require(value.length <= MessageEmbed.VALUE_MAX_LENGTH)
            field = value
        }
    var inline: Boolean = true

    internal fun build() = Field(name, value, inline)
}

data class Author internal constructor(
    val name: String = EmbedBuilder.ZERO_WIDTH_SPACE,
    val url: String? = null,
    val icon: String? = null
)
