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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    @Final
    private List<IResourcePack> defaultResourcePacks;

    @Shadow
    protected abstract ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException;

    @Inject(method = "startGame", at = @At("HEAD"))
    private void startGame(CallbackInfo ci) {
        // Register systemless.
        defaultResourcePacks.add(new ClassLoaderResourcePack("systemless"));
    }

    @Inject(method = "startGame", at = @At("RETURN"))
    private void startGameReturn(CallbackInfo ci) {
        Display.setTitle("SystemlessClient 0.0.1-master-dev");
        InputStream inputstream = this.getClass().getResourceAsStream("/assets/systemless/systemless16.png");
        InputStream inputstream1 = this.getClass().getResourceAsStream("/assets/systemless/systemless32.png");

        if (inputstream != null && inputstream1 != null) {
            try {
                Display.setIcon(new ByteBuffer[]{this.readImageToBuffer(inputstream), this.readImageToBuffer(inputstream1)});
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    inputstream1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
