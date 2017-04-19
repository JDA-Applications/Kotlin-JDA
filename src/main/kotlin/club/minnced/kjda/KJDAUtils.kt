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

operator fun CharSequence.times(amount: Int): String {
    val out = StringBuilder(this)
    repeat(amount) {
        out += this
    }
    return out.toString()
}

val SPACES = Regex("\\s+")

operator fun CharSequence.div(amount: Int): List<String> {
    if (amount < 1)
        return this.split(SPACES)
    return this.split(SPACES, amount)
}

operator fun String.rem(col: Collection<Any?>) = rem(col.toTypedArray<Any?>())
operator fun String.rem(arr: Array<Any?>) = String.format(this, *arr)
