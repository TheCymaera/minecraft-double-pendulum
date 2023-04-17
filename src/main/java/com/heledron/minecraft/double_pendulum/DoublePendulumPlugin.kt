package com.heledron.minecraft.double_pendulum

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class DoublePendulumPlugin : JavaPlugin() {
    private var scene: Scene = EmptyScene()
    override fun onEnable() {
        server.scheduler.scheduleSyncRepeatingTask(this, { scene.update() }, 0, 1)
        getCommand("double_pendulum")!!.setExecutor { sender: CommandSender, _: Command?, _: String?, args: Array<String?> ->
            if (args.size == 1) {
                when (args[0]) {
                    "scene1" -> {
                        scene = EmptyScene()
                        sender.sendMessage("Double Pendulum: Loaded scene 1")
                        return@setExecutor true
                    }

                    "scene2" -> {
                        scene = OnePendulumScene(this)
                        sender.sendMessage("Double Pendulum: Loaded scene 2")
                        return@setExecutor true
                    }

                    "scene3" -> {
                        scene = TwoPendulumsScene(this)
                        sender.sendMessage("Double Pendulum: Loaded scene 3")
                        return@setExecutor true
                    }
                }
            }
            false
        }
    }
}
