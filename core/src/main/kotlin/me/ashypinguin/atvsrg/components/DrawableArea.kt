package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
 * Makes the drawable area black.
 */
fun ShapeRenderer.drawableArea(worldWidth: Float, worldHeight: Float) {
  color = Color.BLACK
  rect(0f, 0f, worldWidth, worldHeight)
}
