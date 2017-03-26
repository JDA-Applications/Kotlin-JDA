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
@file:JvmName("KJDAUser")
package club.minnced.kjda.entities

import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.entities.*

typealias Status = OnlineStatus

val User.game: Game?
    get() = mutualGuilds.first().getMember(this).game

val User.status: Status
    get() = mutualGuilds.first().getMember(this).onlineStatus

val User.isSelf: Boolean
    get() = this is SelfUser

val Member.connectedChannel: VoiceChannel?
    get() = voiceState.channel

val Member.isConnected: Boolean
    get() = connectedChannel !== null
