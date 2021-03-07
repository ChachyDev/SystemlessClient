package dev.systemless.systemless.mixin.client.gui;

import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {
    @NotNull
    @Contract(pure = true)
    @ModifyVariable(method = "renderString", ordinal = 0, at = @At(value = "HEAD"))
    private String getSystemlessTextTwo(String s) {
        return "Systemless";
    }

    @NotNull
    @Contract(pure = true)
    @ModifyVariable(method = "getStringWidth", ordinal = 0, at = @At(value = "HEAD"))
    private String getSystemlessTextThree(String s) {
        return "Systemless";
    }
}
