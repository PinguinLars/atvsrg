package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align

fun ShapeRenderer.fpsCounter(x: Float, y: Float, width: Float, height: Float) {
  color = Color.DARK_GRAY
  rect(x, y, width, height)
}

fun SpriteBatch.fpsCounter(fps: Int, font: BitmapFont, x: Float, y: Float, width: Float) {
  font.draw(
    this,
    "fps: $fps",
    x,
    y,
    width,
    Align.bottomLeft,
    false
  )
}
