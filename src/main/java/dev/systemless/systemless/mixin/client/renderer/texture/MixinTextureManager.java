package dev.systemless.systemless.mixin.client.renderer.texture;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TextureManager.class)
public class MixinTextureManager {
    @NotNull
    @Contract(pure = true)
    @ModifyVariable(method = "bindTexture", print = true, ordinal = 0, at = @At(value = "HEAD"))
    private ResourceLocation getSystemlessTextFour(ResourceLocation location) {
        if (location.getResourcePath().equals("textures/font/ascii.png")) return location;
        return new ResourceLocation("systemless", "systemless.png");
    }
}
