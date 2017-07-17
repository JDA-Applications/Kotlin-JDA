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

@file:JvmName("KJDAEmbedBuilder")
package club.minnced.kjda.builders

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.IMentionable
import net.dv8tion.jda.core.entities.MessageEmbed
import net.dv8tion.jda.core.entities.MessageEmbed.Field
import java.awt.Color
import java.time.temporal.TemporalAccessor

class KEmbedBuilder internal constructor() : Appendable {

    val fields: MutableList<Field> = mutableListOf()
    var description: StringBuilder = StringBuilder()

    var title: String? = null
    var url  : String? = null
    var color: Int?    = null
        set(value) { field = value?.and(0xFFFFFF) }

    var thumbnail: String? = null
    var image    : String? = null

    var time : TemporalAccessor? = null

    var author: KEmbedEntity? = null
    var footer: KEmbedEntity? = null


    internal fun build() = with (EmbedBuilder()) {
        val (description, fields, title, url, time, color, author, thumbnail, footer, image) = this@KEmbedBuilder
        fields.forEach { addField(it) }

        if (!description.isNullOrBlank())
            setDescription(description)
        if (!title.isNullOrBlank())
            setTitle(title, url)

        if (image !== null)
            setImage(image)
        if (time !== null)
            setTimestamp(time)
        if (thumbnail !== null)
            setThumbnail(thumbnail)

        if (color !== null && 0 <= color)
            setColor(Color(color))

        if (footer !== null)
            setFooter(footer.value, footer.icon)
        if (author !== null)
            setAuthor(author.value, author.url, author.icon)

        build()
    }

    ///////////////////////////////
    /// Components
    ///////////////////////////////

    operator fun component1()  = description
    operator fun component2()  = fields
    operator fun component3()  = title
    operator fun component4()  = url
    operator fun component5()  = time
    operator fun component6()  = color
    operator fun component7()  = author
    operator fun component8()  = thumbnail
    operator fun component9()  = footer
    operator fun component10() = image

    ///////////////////////////////
    /// Appendable implementation
    ///////////////////////////////

    override fun append(csq: CharSequence?): KEmbedBuilder {
        description.append(csq)
        return this
    }
    override fun append(csq: CharSequence?, start: Int, end: Int): KEmbedBuilder {
        description.append(csq, start, end)
        return this
    }
    override fun append(c: Char): KEmbedBuilder {
        description.append(c)
        return this
    }

    infix fun append(any: Any?) = append(any.normalize())
    infix fun appendln(any: Any?) = append(any).appendln()
    fun appendln() = append(System.lineSeparator())

    operator fun plusAssign(any: Any?) { append(any) }

    ///////////////////////////////
    /// Lazy Setters
    ///////////////////////////////

    infix inline fun description(lazy: () -> String): KEmbedBuilder {
        description = StringBuilder(lazy())
        return this
    }
    infix inline fun image(lazy: () -> String): KEmbedBuilder {
        image = lazy()
        return this
    }
    infix inline fun url(lazy: () -> String): KEmbedBuilder {
        url = lazy()
        return this
    }
    infix inline fun title(lazy: () -> String): KEmbedBuilder {
        title = lazy()
        return this
    }
    infix inline fun thumbnail(lazy: () -> String): KEmbedBuilder {
        thumbnail = lazy()
        return this
    }
    infix inline fun time(lazy: () -> TemporalAccessor): KEmbedBuilder {
        time = lazy()
        return this
    }
    infix inline fun color(lazy: () -> Int): KEmbedBuilder {
        color = lazy()
        return this
    }

    infix inline fun author(lazy: KEmbedEntity.() -> Unit): KEmbedBuilder {
        val data = KEmbedEntity()
        data.lazy()
        author = data
        return this
    }
    infix inline fun footer(lazy: KEmbedEntity.() -> Unit): KEmbedBuilder {
        val data = KEmbedEntity()
        data.lazy()
        footer = data
        return this
    }

    infix fun field(lazy: FieldBuilder.() -> Unit): KEmbedBuilder {
        val builder = FieldBuilder()
        builder.lazy()
        fields.add(builder.build())
        return this
    }

}

data class KEmbedEntity(
    var value: String = EmbedBuilder.ZERO_WIDTH_SPACE,
    var url: String? = null,
    var icon: String? = null)

class FieldBuilder : Appendable {

    var name: String = EmbedBuilder.ZERO_WIDTH_SPACE
    var value: String = EmbedBuilder.ZERO_WIDTH_SPACE
    var inline: Boolean = true


    operator fun plusAssign(any: Any?) { append(any) }


    override fun append(csq: CharSequence?): FieldBuilder {
        this += csq
        return this
    }

    override fun append(csq: CharSequence?, start: Int, end: Int)
        = append(csq?.subSequence(start..end))
    override fun append(c: Char)
        = append(c.toString())


    infix fun appendln(any: Any?) = append(any).appendln()
    fun       appendln()          = append(System.lineSeparator())
    fun       append(any: Any?)   = append(any.normalize())

    internal fun build() = Field(name, value, inline)

}

var KEmbedBuilder.colorAwt: Color?
    get() = Color(color ?: 0)
    set(value) { color = value?.rgb }

internal fun Any?.normalize() = if (this is IMentionable) asMention else toString()

fun embed(init: KEmbedBuilder.() -> Unit): MessageEmbed = with (KEmbedBuilder()) {
    init()
    build()
}
