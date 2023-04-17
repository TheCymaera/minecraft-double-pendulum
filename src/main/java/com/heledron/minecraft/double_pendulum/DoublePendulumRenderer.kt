package com.heledron.minecraft.double_pendulum

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

object DoublePendulumRenderer {
    fun draw(pendulum: DoublePendulum, origin: Location, viewLength: Int) {
        val actualLength = pendulum.length1 + pendulum.length2
        val viewLength1 = viewLength * (pendulum.length1 / actualLength)
        val viewLength2 = viewLength * (pendulum.length2 / actualLength)
        val line1 = vectorFromAngle(pendulum.angle1 - Math.PI / 2, viewLength1, origin.yaw.toDouble())
        val line2 = vectorFromAngle(pendulum.angle2 - Math.PI / 2, viewLength2, origin.yaw.toDouble())
        val p2 = origin.clone().add(line1)
        val p3 = p2.clone().add(line2)
        drawRod(origin, line1, Color.fromRGB(0, 0, 255))
        drawRod(p2, line2, Color.fromRGB(255, 0, 0))
        drawBulb(p2, Color.fromRGB(0, 0, 0))
        drawBulb(p3, Color.fromRGB(0, 0, 0))
        drawTrail(p3)
    }

    private fun vectorFromAngle(angle: Double, length: Double, rotateY: Double): Vector {
        return Vector(cos(angle) * length, sin(angle) * length, 0.0).rotateAroundY(rotateY)
    }

    private fun drawBulb(location: Location, color: Color) {
        // origin.getWorld().spawnParticle(Particle.REDSTONE, origin, 1, 0, 0, 0, new DustOptions(color, 2));
    }

    private fun drawRod(origin: Location, line: Vector, color: Color) {
        val segments = (line.length() * 10).toInt()
        for (loc in getPointsOnLine(origin, line, segments)) {
            loc!!.world!!.spawnParticle(Particle.WATER_BUBBLE, loc, 1, 0.0, 0.0, 0.0, 0.0)
        }
    }

    private fun drawTrail(location: Location) {
        location.world!!.spawnParticle(Particle.WAX_OFF, location, 2, 0.0, 0.0, 0.0, 0.0)
    }

    private fun getPointsOnLine(location: Location, line: Vector, segments: Int): Array<Location?> {
        val out = arrayOfNulls<Location>(segments)
        val segment = line.clone().multiply(1 / segments.toDouble())
        val currentPoint = location.clone()
        for (i in 0 until segments) {
            out[i] = currentPoint.clone()
            currentPoint.add(segment)
        }
        return out
    }
}
