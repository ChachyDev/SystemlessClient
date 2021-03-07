package dev.systemless.systemless

import dev.systemless.systemless.bus.events.core.SystemlessEvent
import me.kbrewster.eventbus.Subscribe
import org.lwjgl.opengl.Display

object Systemless {
    @Subscribe
    fun onSystemless(event: SystemlessEvent) {
        println("Systemless!")
        Display.setTitle("SystemlessClient 0.0.1-master-dev")
    }
}