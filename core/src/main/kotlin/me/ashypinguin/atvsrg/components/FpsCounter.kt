package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import me.ashypinguin.atvsrg.utils.FPS_HEIGHT_PERCENT
import me.ashypinguin.atvsrg.utils.FPS_OFFSET_GROUND_PERCENT
import me.ashypinguin.atvsrg.utils.FPS_OFFSET_WALL_PERCENT
import me.ashypinguin.atvsrg.utils.FPS_WIDTH_PERCENT

fun ShapeRenderer.fpsCounter(worldWidth: Float, worldHeight: Float) {
  color = Color.DARK_GRAY
  rect(
    worldWidth * FPS_OFFSET_WALL_PERCENT,
    worldHeight * FPS_OFFSET_GROUND_PERCENT,
    worldWidth * FPS_WIDTH_PERCENT,
    worldHeight * FPS_HEIGHT_PERCENT
  )
}

fun SpriteBatch.fpsCounter(fps: Int, font: BitmapFont, worldWidth: Float, worldHeight: Float) {
  font.draw(
    this,
    "fps: $fps",
    worldWidth * FPS_OFFSET_WALL_PERCENT,
    worldHeight * (FPS_OFFSET_GROUND_PERCENT + FPS_HEIGHT_PERCENT * 0.75f),
    worldWidth * FPS_WIDTH_PERCENT,
    Align.bottomLeft,
    false
  )
}
