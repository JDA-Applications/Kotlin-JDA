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
@file:JvmName("KJDA")
package club.minnced.kjda

import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.entities.Game
import net.dv8tion.jda.core.entities.impl.JDAImpl
import net.dv8tion.jda.core.events.Event

typealias Status = OnlineStatus

/** Sets the game for the current session using [Presence.setGame][net.dv8tion.jda.core.managers.Presence.setGame] */
infix inline fun JDA.game(lazy: () -> Game): JDA {
    presence.game = lazy()
    return this
}
/** Sets the online status for the current session using [Presence.setStatus][net.dv8tion.jda.core.managers.Presence.setStatus] */
infix inline fun JDA.status(lazy: () -> Status): JDA {
    presence.status = lazy()
    return this
}
/** Sets the online status for the current session using [Presence.setIdle][net.dv8tion.jda.core.managers.Presence.setIdle] */
infix inline fun JDA.idle(lazy: () -> Boolean): JDA {
    presence.isIdle = lazy()
    return this
}

/**
 * Fires the specified Event on the receiving JDA instance
 */
infix fun JDA.emit(event: Event) {
    check(this is JDAImpl)
    (this as JDAImpl).eventManager.handle(event)
}
