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

import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.audio.factory.IAudioSendFactory
import net.dv8tion.jda.core.entities.Game
import net.dv8tion.jda.core.hooks.IEventManager
import org.apache.http.HttpHost

fun client(accountType: AccountType, init: JDABuilder.() -> Unit) {
    val builder = JDABuilder(accountType)
    builder.init()
    builder.buildAsync()
}

infix inline fun JDABuilder.token(lazyToken: () -> String)     = this.setToken(lazyToken())
infix inline fun JDABuilder.game(lazy: () -> String)           = this.setGame(Game.of(lazy()))
infix inline fun JDABuilder.status(lazy: () -> OnlineStatus)   = this.setStatus(lazy())
infix inline fun JDABuilder.manager(lazy: () -> IEventManager) = this.setEventManager(lazy())
infix inline fun JDABuilder.listener(lazy: () -> Any)          = this.addListener(lazy())
infix inline fun JDABuilder.proxy(lazy: () -> HttpHost)        = this.setProxy(lazy())
infix inline fun JDABuilder.audioSendFactory(lazy: () -> IAudioSendFactory)
    = this.setAudioSendFactory(lazy())

infix fun JDABuilder.idle(lazy: Boolean)          = this.setIdle(lazy)
infix fun JDABuilder.shutdownHook(lazy: Boolean)  = this.setEnableShutdownHook(lazy)
infix fun JDABuilder.audio(lazy: Boolean)         = this.setAudioEnabled(lazy)
infix fun JDABuilder.connectTimeout(lazy: Int)    = this.setWebSocketTimeout(lazy)
infix fun JDABuilder.autoReconnect(lazy: Boolean) = this.setAutoReconnect(lazy)

fun JDABuilder.listener(vararg listener: Any)       = this.addListener(*listener)
fun JDABuilder.removeLisetner(vararg listener: Any) = this.removeListener(*listener)

operator fun JDABuilder.plusAssign(other: Any) { listener(other) }
