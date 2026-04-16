package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.clearScreen
import me.ashypinguin.atvsrg.utils.DRAWABLE_BG_COLOR
import me.ashypinguin.atvsrg.utils.GRAY_BG_TONE

/**
 * Makes the drawable area black.
 */
fun ShapeRenderer.drawableArea(worldWidth: Float, worldHeight: Float) {
  color = DRAWABLE_BG_COLOR
  rect(0f, 0f, worldWidth, worldHeight)
}

fun clear() = clearScreen(GRAY_BG_TONE, GRAY_BG_TONE, GRAY_BG_TONE)
