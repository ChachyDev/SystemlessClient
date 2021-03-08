package dev.systemless.systemless.installer.container

class Container(private val args: Array<String>) {
    operator fun get(name: String): String? {
        if (args.indexOf(name) >= 0) {

            return args.getOrNull(args.indexOf("--$name") + 1)
        }
        return null
    }

    fun exists(name: String) = args.getOrNull(args.indexOf("--$name")) != null
}