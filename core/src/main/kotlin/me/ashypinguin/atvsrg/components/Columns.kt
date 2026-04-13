package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.utils.*

fun ShapeRenderer.columns(noteWidth: Float, worldHeight: Float, worldWidth: Float) {
  color = LEFT_COLOR.dull(COLUMN_DARKNESS)
  rect(worldWidth * NOTE_WALL_OFFSET_PERCENT, 0f, noteWidth, worldHeight)
  color = LEFT_MID_COLOR.dull(COLUMN_DARKNESS)
  rect(worldWidth * (NOTE_WIDTH_PRECENT + NOTE_WALL_OFFSET_PERCENT), 0f, noteWidth, worldHeight)
  color = RIGHT_MID_COLOR.dull(COLUMN_DARKNESS)
  rect(worldWidth * (NOTE_WIDTH_PRECENT * 2 + NOTE_WALL_OFFSET_PERCENT), 0f, noteWidth, worldHeight)
  color = RIGHT_COLOR.dull(COLUMN_DARKNESS)
  rect(worldWidth * (NOTE_WIDTH_PRECENT * 3 + NOTE_WALL_OFFSET_PERCENT), 0f, noteWidth, worldHeight)
}
