package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import me.ashypinguin.atvsrg.utils.UI_ELEMENT_BG_COLOR

private const val SCORE_WIDTH_PERCENT = .125f
private const val SCORE_HEIGHT_PERCENT = .075f
private const val SCORE_OFFSET_WALL_PERCENT = 0f
private const val SCORE_OFFSET_GROUND_PERCENT = 1f - SCORE_HEIGHT_PERCENT

//private val INT_FORMATTER = NumberFormat.getInstance(Locale)

fun ShapeRenderer.scoreCounter(worldWidth: Float, worldHeight: Float) {
  color = UI_ELEMENT_BG_COLOR
  rect(
    worldWidth * SCORE_OFFSET_WALL_PERCENT,
    worldHeight * SCORE_OFFSET_GROUND_PERCENT,
    worldWidth * SCORE_WIDTH_PERCENT,
    worldHeight * SCORE_HEIGHT_PERCENT
  )
}

fun SpriteBatch.scoreCounter(score: Int, font: BitmapFont, worldWidth: Float, worldHeight: Float) {
  font.draw(
    this,
    "$score",
    worldWidth * SCORE_OFFSET_WALL_PERCENT,
    worldHeight * (SCORE_OFFSET_GROUND_PERCENT + SCORE_HEIGHT_PERCENT * 0.75f),
    worldWidth * SCORE_WIDTH_PERCENT,
    Align.bottomLeft,
    false
  )
}
