package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport

private const val FPS_WIDTH_PERCENT = .15f
private const val FPS_HEIGHT_PERCENT = .1f
private const val FPS_OFFSET_WALL_PERCENT = 0f
private const val FPS_OFFSET_GROUND_PERCENT = 0f

fun ShapeRenderer.fpsCounter(viewport: FitViewport) {
  color = Color.DARK_GRAY
  rect(
    viewport.worldWidth * FPS_OFFSET_WALL_PERCENT,
    viewport.worldHeight * FPS_OFFSET_GROUND_PERCENT,
    viewport.worldWidth * FPS_WIDTH_PERCENT,
    viewport.worldHeight * FPS_HEIGHT_PERCENT
  )
}

fun SpriteBatch.fpsCounter(fps: Int, viewport: FitViewport, font: BitmapFont) {
  font.draw(
    this,
    "fps: $fps",
    viewport.worldWidth * FPS_OFFSET_WALL_PERCENT,
    viewport.worldHeight * (FPS_OFFSET_GROUND_PERCENT + FPS_HEIGHT_PERCENT * 0.75f),
    viewport.worldWidth * FPS_WIDTH_PERCENT,
    Align.bottomLeft,
    true
  )
}
