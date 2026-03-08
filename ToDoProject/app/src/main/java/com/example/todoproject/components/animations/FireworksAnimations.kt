package com.example.todoproject.components.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Composable function to display a fireworks animation. The animation is created using a Canvas and an infinite transition.
 * The animation is created by drawing circles that expand from the center of the Canvas.
 * @param modifier the modifier to apply to the Canvas. Default value is an empty Modifier
 * @param duration the duration of the animation in milliseconds. Default value is 3000ms
 * @param targetValue the target value of the animation. The animation will expand from 0f to the target value. Default value is 1f
 */
@Composable
fun FireworksAnimation(modifier: Modifier = Modifier, duration: Int = 3000, targetValue: Float) {

    val transition = rememberInfiniteTransition(label = "")

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Canvas(modifier = modifier) {

        val center = Offset(size.width / 2, size.height / 2)

        val particleCount = 20
        val radius = progress * 300

        for (i in 0 until particleCount) {

            val angle = (2 * Math.PI / particleCount) * i

            val x = center.x + radius * cos(angle).toFloat()
            val y = center.y + radius * sin(angle).toFloat()

            drawCircle(
                color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat()
                ),
                radius = 15f,
                center = Offset(x, y)
            )
        }
    }
}