package com.heledron.minecraft.double_pendulum

import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin

interface Scene {
    fun update()
}

class EmptyScene : Scene {
    override fun update() {
        // do nothing...
    }
}

class OnePendulumScene(plugin: JavaPlugin) : Scene {
    private val dp = DoublePendulum()
    private val origin: Location

    init {
        origin = Location(plugin.server.getWorld("world"), -47.5, -12.0, 14.5, Math.PI.toFloat() / 4, 0f)
        dp.angle1 = 0.5
        dp.angle2 = 0.0
    }

    override fun update() {
        dp.step()
        DoublePendulumRenderer.draw(dp, origin, 7)
    }
}

class TwoPendulumsScene(plugin: JavaPlugin) : Scene {
    private val dp1 = DoublePendulum()
    private val dp2 = DoublePendulum()
    private val origin1: Location
    private val origin2: Location

    init {
        origin1 = Location(plugin.server.getWorld("world"), -47.5, -12.0, 14.5 - 2, Math.PI.toFloat() / 4, 0f)
        origin2 = Location(plugin.server.getWorld("world"), -47.5, -12.0, 14.5 + 2, Math.PI.toFloat() / 4, 0f)
        dp1.angle1 = 0.4 + Math.PI / 3
        dp2.angle1 = dp1.angle1 - 0.05
        dp1.angle2 = 0.8
        dp2.angle2 = dp1.angle2
    }

    override fun update() {
        dp1.step()
        dp2.step()
        DoublePendulumRenderer.draw(dp1, origin1, 7)
        DoublePendulumRenderer.draw(dp2, origin2, 7)
    }
}
