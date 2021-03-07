package dev.systemless.systemless.mixin.client;

import dev.systemless.systemless.Systemless;
import dev.systemless.systemless.bus.EventBusKt;
import dev.systemless.systemless.bus.events.core.PreSystemlessEvent;
import dev.systemless.systemless.bus.events.core.SystemlessEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "startGame", at = @At("HEAD"))
    private void startGame(CallbackInfo ci) {
        EventBusKt.getEventBus().register(Systemless.INSTANCE);
        EventBusKt.getEventBus().post(new PreSystemlessEvent());
    }

    @Inject(method = "startGame", at = @At("RETURN"))
    private void returnStartGame(CallbackInfo ci) {
        EventBusKt.getEventBus().post(new SystemlessEvent());
    }
}
