package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import me.ashypinguin.atvsrg.utils.UI_ELEMENT_BG_COLOR

private const val COMBO_WIDTH_PERCENT = .125f
private const val COMBO_HEIGHT_PERCENT = .075f
private const val COMBO_OFFSET_WALL_PERCENT = 0f
private const val COMBO_OFFSET_GROUND_PERCENT = 1f - COMBO_HEIGHT_PERCENT - SCORE_HEIGHT_PERCENT

fun ShapeRenderer.comboCounter(worldWidth: Float, worldHeight: Float) {
  color = UI_ELEMENT_BG_COLOR
  rect(
    worldWidth * COMBO_OFFSET_WALL_PERCENT,
    worldHeight * COMBO_OFFSET_GROUND_PERCENT,
    worldWidth * COMBO_WIDTH_PERCENT,
    worldHeight * COMBO_HEIGHT_PERCENT
  )
}

fun SpriteBatch.comboCounter(combo: Int, font: BitmapFont, worldWidth: Float, worldHeight: Float) {
  font.draw(
    this,
    "combo: $combo",
    worldWidth * COMBO_OFFSET_WALL_PERCENT,
    worldHeight * (COMBO_OFFSET_GROUND_PERCENT + COMBO_HEIGHT_PERCENT * 0.75f),
    worldWidth * COMBO_WIDTH_PERCENT,
    Align.bottomLeft,
    false
  )
}
