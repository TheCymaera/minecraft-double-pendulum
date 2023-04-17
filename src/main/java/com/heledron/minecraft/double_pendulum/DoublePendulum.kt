package com.heledron.minecraft.double_pendulum

import kotlin.math.cos
import kotlin.math.sin

class DoublePendulum {
    // gravity
    var gravity = 1.0

    // mass
    var mass1 = 1.0
    var mass2 = 1.0

    // length
    var length1 = 90.0
    var length2 = 90.0

    // angle
    var angle1 = 0.0
    var angle2 = 0.0

    // angular velocity
    var velocity1 = 0.0
    var velocity2 = 0.0
    fun step() {
        val g = gravity
        val m1 = mass1
        val m2 = mass2
        val a1 = angle1
        val a2 = angle2
        val l1 = length1
        val l2 = length2
        val v1 = velocity1
        val v2 = velocity2
        val acc1T1 = -g * (2 * m1 + m2) * sin(a1)
        val acc1T2 = -m2 * g * sin(a1 - 2 * a2)
        val acc1T3 = -2 * sin(a1 - a2) * m2
        val acc1T4 = sq(v2) * l2 + v1 * v1 * l1 * cos(a1 - a2)
        val acc1B = l1 * (2 * m1 + m2 - m2 * cos(2 * a1 - 2 * a2))
        val acc1 = (acc1T1 + acc1T2 + acc1T3 * acc1T4) / acc1B
        val acc2T1 = 2 * sin(a1 - a2)
        val acc2T2 = sq(v1) * l1 * (m1 + m2)
        val acc2T3 = g * (m1 + m2) * cos(a1)
        val acc2T4 = sq(v2) * l2 * m2 * cos(a1 - a2)
        val acc2B = l2 * (2 * m1 + m2 - m2 * cos(2 * a1 - 2 * a2))
        val acc2 = acc2T1 * (acc2T2 + acc2T3 + acc2T4) / acc2B

        // velocity += acceleration
        velocity1 += acc1
        velocity2 += acc2

        // angle += velocity
        angle1 += velocity1
        angle2 += velocity2
    }

    private fun sq(x: Double): Double {
        return x * x
    }
}
