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

import club.minnced.kjda.*
import club.minnced.kjda.entities.sendAsync
import net.dv8tion.jda.core.AccountType.BOT
import net.dv8tion.jda.core.OnlineStatus.DO_NOT_DISTURB
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.EventListener
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.io.File

fun main(args: Array<String>) {
    client(BOT) {
        token { File(".token").readText() }
        game {
            "Powered by Kotlin-JDA"
        }

        status { DO_NOT_DISTURB }

        this += Runner()
        this += EventListener {
            if (it is ReadyEvent)
                println("Dab!!!")
        }
    }
}

class Runner : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent?) {
        event!!

        if (event.author.isBot || event.author == event.jda.selfUser)
            return
        val start = System.currentTimeMillis()
        event.channel.sendTyping().onlyIf(event.message.rawContent == ".ping") {
            event.channel.sendAsync {
                this += "Ping"
                embed {
                    field {
                        name = "Time"
                        value = (System.currentTimeMillis() - start).toString()
                    }
                }
            } then {
                println("Sent Ping response! [${it?.embeds?.firstOrNull()?.fields?.first()?.value}]")
            } catch { it?.printStackTrace() }
        }
    }
}
