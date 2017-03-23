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

package club.minnced.kjda.entities

import net.dv8tion.jda.core.Permission.MESSAGE_WRITE
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.TextChannel
import net.dv8tion.jda.core.requests.RestAction


fun Guild.ban(id: Long, days: Int = 0)   = controller.ban(id.toString(), days)
fun Guild.ban(id: String, days: Int = 0) = controller.ban(id, days)
infix fun Guild.kick(member: Member) = controller.kick(member)

infix fun Member.mute(channel: TextChannel): RestAction<*> {
    val over = channel.getPermissionOverride(this)
    if (over !== null) return over.manager.deny(MESSAGE_WRITE)
    return channel.createPermissionOverride(this).setDeny(MESSAGE_WRITE)
}
