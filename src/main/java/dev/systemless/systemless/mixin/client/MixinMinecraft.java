package dev.systemless.systemless.mixin.client;

import dev.systemless.systemless.resources.ClassLoaderResourcePack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow
    @Final
    private List<IResourcePack> defaultResourcePacks;

    @Inject(method = "startGame", at = @At("HEAD"))
    private void startGame(CallbackInfo ci) {
        // Register systemless.
        defaultResourcePacks.add(new ClassLoaderResourcePack("systemless"));
    }

    @Inject(method = "startGame", at = @At("RETURN"))
    private void startGameReturn(CallbackInfo ci) {
        Display.setTitle("SystemlessClient 0.0.1-master-dev");
    }
}
