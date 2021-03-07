package dev.systemless.systemless.bus

import me.kbrewster.eventbus.eventbus
import me.kbrewster.eventbus.invokers.LMFInvoker

val eventBus = eventbus {
    invoker { LMFInvoker() }
}