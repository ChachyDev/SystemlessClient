package dev.systemless.systemless.installer

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dev.systemless.systemless.installer.container.Container
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.net.URL
import java.time.Instant
import kotlin.system.exitProcess


object Installer {
    @JvmStatic
    fun main(a: Array<String>) {
        val args = Container(a)

        val accept = args.exists("accept")

        if (!accept) {
            println("You did not accept to install. No")
            exitProcess(0)
        }

        val minecraftDir = args["minecraftDir"]?.let { File(it) } ?: findPlatformDirectory()

        if (!minecraftDir.exists()) {
            println("You don't have minecraft installed...")
            exitProcess(0)
        }

        val version = File(minecraftDir, "versions/Systemless")
        version.mkdirs()

        File(version, "Systemless.json").writeBytes(
            this::class.java.getResourceAsStream("/version.json").use { it.readBytes() })

        val url = URL("https://github.com/ChachyDev/SystemlessClient/releases/download/0.0.1-master-dev/SystemlessClient-0.0.1-master-dev.jar")
        val conn = url.openConnection()
        val size = conn.contentLength

        val libFolder = File(minecraftDir, "libraries/dev/systemless/Systemless/1.2.3")
        libFolder.mkdirs()

        try {
            BufferedInputStream(url.openStream()).use { `in` ->
                File(libFolder, "Systemless-1.2.3.jar").outputStream().use { fileOutputStream ->
                    val dataBuffer = ByteArray(1024)
                    var bytesRead: Int
                    var sumCount = 0.0
                    while (`in`.read(dataBuffer, 0, 1024).also { bytesRead = it } != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead)

                        sumCount += bytesRead
                        if (size > 0) {
                            println("Downloaded ${sumCount / size * 100.0}%")
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val profile = File(minecraftDir, "launcher_profiles.json")

        val json = JsonParser().parse(profile.readText()).asJsonObject
        val profiles = json["profiles"].asJsonObject
        val profileObj = JsonObject()
        val instant = Instant.ofEpochMilli(System.currentTimeMillis())
        profileObj.addProperty("name", "Systemless")
        profileObj.addProperty("lastVersionId", "Systemless")
        profileObj.addProperty("type", "custom")
        profileObj.addProperty("created", instant.toString())
        profileObj.addProperty("lastUsed", instant.toString())
        profileObj.addProperty("icon", "Furnace")
        profiles.add("Systemless", profileObj)
        json.addProperty("selectedProfile", "Systemless")
        profile.writeText(Gson().toJson(json))

        println("Done!")
    }

    private fun findPlatformDirectory() = with(System.getProperty("os.name")) {
        when {
            startsWith("Windows", true) -> File(System.getenv("APPDATA"), ".minecraft")
            startsWith("Mac", true) || startsWith("Darwin", true) -> File(
                System.getProperty("user.home"),
                "Application Support/minecraft"
            )
            // Linux/default
            else -> File(System.getProperty("user.home"), ".minecraft")
        }
    }
}