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
@file:JvmName("KJDAClientBuilder")

package club.minnced.kjda

import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.audio.factory.IAudioSendFactory
import net.dv8tion.jda.core.entities.Game
import net.dv8tion.jda.core.hooks.IEventManager
import org.apache.http.HttpHost

/**
 * Constructs a new [JDABuilder] and applies the specified
 * init function `() -> Unit` to that receiver.
 * This uses [JDABuilder.buildAsync]
 *
 * The token is not required here, however needs to be configured in the given function!
 *
 * @param[accountType]
 *       The [AccountType] for the account being issued for creation
 * @param[init]
 *       The function which uses the constructed JDABuilder as receiver to setup
 *       the JDA information before building it
 *
 * @sample client
 *
 * @see    JDABuilder
 */
fun client(accountType: AccountType, init: JDABuilder.() -> Unit): JDA {
    val builder = JDABuilder(accountType)
    builder.init()
    return builder.buildAsync()
}

/** Lazy infix overload for [JDABuilder.setToken] */
infix inline fun <reified T: JDABuilder> T.token(lazyToken: () -> String): T
    = this.setToken(lazyToken()) as T
/** Lazy infix overload for [JDABuilder.setGame] */
infix inline fun <reified T: JDABuilder> T.game(lazy: () -> String): T
    = this.setGame(Game.of(lazy())) as T
/** Lazy infix overload for [JDABuilder.setStatus] */
infix inline fun <reified T: JDABuilder> T.status(lazy: () -> OnlineStatus): T
    = this.setStatus(lazy()) as T
/** Lazy infix overload for [JDABuilder.setEventManager] */
infix inline fun <reified T: JDABuilder> T.manager(lazy: () -> IEventManager): T
    = this.setEventManager(lazy()) as T
/** Lazy infix overload for [JDABuilder.addListener] */
infix inline fun <reified T: JDABuilder> T.listener(lazy: () -> Any): T
    = this.addListener(lazy()) as T
/** Lazy infix overload for [JDABuilder.setProxy] */
infix inline fun <reified T: JDABuilder> T.proxy(lazy: () -> HttpHost): T
    = this.setProxy(lazy()) as T
/** Lazy infix overload for [JDABuilder.setAudioSendFactory] */
infix inline fun <reified T: JDABuilder> T.audioSendFactory(lazy: () -> IAudioSendFactory): T
    = this.setAudioSendFactory(lazy()) as T

/** Infix overload for [JDABuilder.setIdle] */
infix inline fun <reified T: JDABuilder> T.idle(lazy: Boolean): T
    = this.setIdle(lazy) as T
/** Infix overload for [JDABuilder.setEnableShutdownHook] */
infix inline fun <reified T: JDABuilder> T.shutdownHook(lazy: Boolean): T
    = this.setEnableShutdownHook(lazy) as T
/** Infix overload for [JDABuilder.setAudioEnabled] */
infix inline fun <reified T: JDABuilder> T.audio(lazy: Boolean): T
    = this.setAudioEnabled(lazy) as T
/** Infix overload for [JDABuilder.setWebSocketTimeout] */
infix inline fun <reified T: JDABuilder> T.connectTimeout(lazy: Int): T
    = this.setWebSocketTimeout(lazy) as T
/** Infix overload for [JDABuilder.setAutoReconnect] */
infix inline fun <reified T: JDABuilder> T.autoReconnect(lazy: Boolean): T
    = this.setAutoReconnect(lazy) as T

/** Overload for [JDABuilder.addListener] */
inline fun <reified T: JDABuilder> T.listener(vararg listener: Any): T
    = this.addListener(*listener) as T
/** Overload for [JDABuilder.removeListener] */
inline fun <reified T: JDABuilder> T.removeLisetner(vararg listener: Any): T
    = this.removeListener(*listener) as T

/** Operator overload for [JDABuilder.addListener] */
inline operator fun <reified T: JDABuilder> T.plusAssign(other: Any) { listener(other) }
