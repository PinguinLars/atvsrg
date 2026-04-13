package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import java.text.NumberFormat

private const val SCORE_WIDTH_PERCENT = .125f
private const val SCORE_HEIGHT_PERCENT = .075f
private const val SCORE_OFFSET_WALL_PERCENT = 0f
private const val SCORE_OFFSET_GROUND_PERCENT = 1f - SCORE_HEIGHT_PERCENT

//private val INT_FORMATTER = NumberFormat.getInstance(Locale)

fun ShapeRenderer.scoreCounter(viewport: FitViewport) {
  color = Color.DARK_GRAY
  rect(
    viewport.worldWidth * SCORE_OFFSET_WALL_PERCENT,
    viewport.worldHeight * SCORE_OFFSET_GROUND_PERCENT,
    viewport.worldWidth * SCORE_WIDTH_PERCENT,
    viewport.worldHeight * SCORE_HEIGHT_PERCENT
  )
}

fun SpriteBatch.scoreCounter(score: Int, viewport: FitViewport, font: BitmapFont) {
  font.draw(
    this,
    "$score",
    viewport.worldWidth * SCORE_OFFSET_WALL_PERCENT,
    viewport.worldHeight * (SCORE_OFFSET_GROUND_PERCENT + SCORE_HEIGHT_PERCENT * 0.75f),
    viewport.worldWidth * SCORE_WIDTH_PERCENT,
    Align.bottomLeft,
    false
  )
}
