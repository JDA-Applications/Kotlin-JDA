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
import net.dv8tion.jda.core.entities.MessageEmbed
import net.dv8tion.jda.core.entities.MessageEmbed.Field
import java.awt.Color
import java.time.temporal.TemporalAccessor

class KEmbedBuilder internal constructor() {

    var description: StringBuilder = StringBuilder()

    var fields: MutableList<Field> = mutableListOf()

    var title: String? = null
    var url: String? = null
    var time: TemporalAccessor? = null
    var color: Int? = null

    var author   : KEmbedAuthor?    = null
    var thumbnail: String? = null
    var footer   : KEmbedFooter?    = null
    var image    : String?          = null

    internal fun build(): MessageEmbed = with (EmbedBuilder()) {
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
            setFooter(footer.text, footer.url)
        if (author !== null)
            setAuthor(author.name, author.url, author.icon)

        build()
    }

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

}

data class KEmbedAuthor(var name: String = EmbedBuilder.ZERO_WIDTH_SPACE, var url: String? = null, var icon: String? = null)
data class KEmbedFooter(var text: String = EmbedBuilder.ZERO_WIDTH_SPACE, var url: String? = null)

class FieldBuilder {

    var name: String = EmbedBuilder.ZERO_WIDTH_SPACE
    var value: String = EmbedBuilder.ZERO_WIDTH_SPACE
    var inline: Boolean = true

    internal fun build() = Field(name, value, inline)

}

/////////////////
/// Extensions
/////////////////

infix inline fun KEmbedBuilder.description(lazy: () -> String): KEmbedBuilder {
    description = StringBuilder(lazy())
    return this
}
infix inline fun KEmbedBuilder.image(lazy: () -> String): KEmbedBuilder {
    image = lazy()
    return this
}
infix inline fun KEmbedBuilder.url(lazy: () -> String): KEmbedBuilder {
    url = lazy()
    return this
}
infix inline fun KEmbedBuilder.title(lazy: () -> String): KEmbedBuilder {
    title = lazy()
    return this
}
infix inline fun KEmbedBuilder.thumbnail(lazy: () -> String): KEmbedBuilder {
    thumbnail = lazy()
    return this
}
infix inline fun KEmbedBuilder.time(lazy: () -> TemporalAccessor): KEmbedBuilder {
    time = lazy()
    return this
}
infix inline fun KEmbedBuilder.color(lazy: () -> Int): KEmbedBuilder {
    color = lazy()
    return this
}

inline fun KEmbedBuilder.color(rgb: Int, lazy: Color.() -> Unit): KEmbedBuilder {
    val data = Color(rgb)
    data.lazy()
    color = data.rgb
    return this
}

infix inline fun KEmbedBuilder.author(lazy: KEmbedAuthor.() -> Unit): KEmbedBuilder {
    val data = KEmbedAuthor()
    data.lazy()
    author = data
    return this
}
infix inline fun KEmbedBuilder.footer(lazy: KEmbedFooter.() -> Unit): KEmbedBuilder {
    val data = KEmbedFooter()
    data.lazy()
    footer = data
    return this
}

infix fun KEmbedBuilder.field(lazy: FieldBuilder.() -> Unit): KEmbedBuilder {
    val builder = FieldBuilder()
    builder.lazy()
    fields.add(builder.build())
    return this
}

fun embed(init: KEmbedBuilder.() -> Unit): MessageEmbed = with (KEmbedBuilder()) {
    init()
    build()
}
