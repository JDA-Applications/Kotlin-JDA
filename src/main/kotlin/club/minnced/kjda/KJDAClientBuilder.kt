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

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import net.dv8tion.jda.core.AccountType
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
fun client(accountType: AccountType, init: JDABuilder.() -> Unit) = async(CommonPool) {
    val builder = JDABuilder(accountType)
    builder.init()
    return@async builder.buildBlocking()
}

/** Lazy infix overload for [JDABuilder.setToken] */
infix inline fun JDABuilder.token(lazyToken: () -> String)     = this.setToken(lazyToken())
/** Lazy infix overload for [JDABuilder.setGame] */
infix inline fun JDABuilder.game(lazy: () -> String)           = this.setGame(Game.of(lazy()))
/** Lazy infix overload for [JDABuilder.setStatus] */
infix inline fun JDABuilder.status(lazy: () -> OnlineStatus)   = this.setStatus(lazy())
/** Lazy infix overload for [JDABuilder.setEventManager] */
infix inline fun JDABuilder.manager(lazy: () -> IEventManager) = this.setEventManager(lazy())
/** Lazy infix overload for [JDABuilder.addListener] */
infix inline fun JDABuilder.listener(lazy: () -> Any)          = this.addListener(lazy())
/** Lazy infix overload for [JDABuilder.setProxy] */
infix inline fun JDABuilder.proxy(lazy: () -> HttpHost)        = this.setProxy(lazy())
/** Lazy infix overload for [JDABuilder.setAudioSendFactory] */
infix inline fun JDABuilder.audioSendFactory(lazy: () -> IAudioSendFactory)
    = this.setAudioSendFactory(lazy())

/** Infix overload for [JDABuilder.setIdle] */
infix fun JDABuilder.idle(lazy: Boolean)          = this.setIdle(lazy)
/** Infix overload for [JDABuilder.setEnableShutdownHook] */
infix fun JDABuilder.shutdownHook(lazy: Boolean)  = this.setEnableShutdownHook(lazy)
/** Infix overload for [JDABuilder.setAudioEnabled] */
infix fun JDABuilder.audio(lazy: Boolean)         = this.setAudioEnabled(lazy)
/** Infix overload for [JDABuilder.setWebSocketTimeout] */
infix fun JDABuilder.connectTimeout(lazy: Int)    = this.setWebSocketTimeout(lazy)
/** Infix overload for [JDABuilder.setAutoReconnect] */
infix fun JDABuilder.autoReconnect(lazy: Boolean) = this.setAutoReconnect(lazy)

/** Overload for [JDABuilder.addListener] */
fun JDABuilder.listener(vararg listener: Any)       = this.addListener(*listener)
/** Overload for [JDABuilder.removeListener] */
fun JDABuilder.removeLisetner(vararg listener: Any) = this.removeListener(*listener)

/** Operator overload for [JDABuilder.addListener] */
operator fun JDABuilder.plusAssign(other: Any) { listener(other) }
