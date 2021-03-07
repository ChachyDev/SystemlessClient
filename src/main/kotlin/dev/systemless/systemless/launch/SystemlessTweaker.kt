package dev.systemless.systemless.launch

import net.minecraft.launchwrapper.ITweaker
import net.minecraft.launchwrapper.LaunchClassLoader
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.Mixins
import java.io.File

class SystemlessTweaker : ITweaker {
    private val a = ArrayList<String>()

    override fun acceptOptions(args: MutableList<String>?, gameDir: File?, assetsDir: File?, profile: String?) {
        if (args != null) {
            a.addAll(args)
        }
        addArg("gameDir", gameDir)
        addArg("assetsDir", assetsDir)
        addArg("version", profile)
    }

    override fun injectIntoClassLoader(classLoader: LaunchClassLoader?) {
        MixinBootstrap.init()
        Mixins.addConfiguration("mixins.systemless.json")
    }

    override fun getLaunchTarget() = "net.minecraft.client.main.Main"

    override fun getLaunchArguments() = a.toTypedArray()

    private fun addArg(label: String, value: Any?) {
        a.add("--$label")
        a.add(if (value is String) value else if (value is File) value.absolutePath else ".")
    }
}