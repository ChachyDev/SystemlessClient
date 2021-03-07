package dev.systemless.systemless.mixin.client.gui;

import net.minecraft.client.gui.GuiMainMenu;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu {
    @NotNull
    @Contract(pure = true)
    @ModifyVariable(method = "drawScreen(IIF)V", ordinal = 1, at = @At(value = "STORE"))
    private String getSystemlessTextTwo(String s) {
        return "Systemless is LGPL";
    }

    @NotNull
    @Contract(pure = true)
    @ModifyVariable(method = "drawScreen(IIF)V", ordinal = 0, at = @At("STORE"))
    private String getSystemlessText(String s) {
        return "Systemless";
    }
}
