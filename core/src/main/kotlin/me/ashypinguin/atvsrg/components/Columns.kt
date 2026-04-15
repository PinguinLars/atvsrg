package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.utils.*

fun ShapeRenderer.columns(noteWidth: Float, worldHeight: Float, worldWidth: Float) {
  color = LEFT_COLUMN_COLOR
  rect(worldWidth * NOTE_WALL_OFFSET_PERCENT, 0f, noteWidth, worldHeight)
  color = LEFT_MID_COLUMN_COLOR
  rect(worldWidth * (NOTE_WIDTH_PERCENT + NOTE_WALL_OFFSET_PERCENT), 0f, noteWidth, worldHeight)
  color = RIGHT_MID_COLUMN_COLOR
  rect(worldWidth * (NOTE_WIDTH_PERCENT * 2 + NOTE_WALL_OFFSET_PERCENT), 0f, noteWidth, worldHeight)
  color = RIGHT_COLUMN_COLOR
  rect(worldWidth * (NOTE_WIDTH_PERCENT * 3 + NOTE_WALL_OFFSET_PERCENT), 0f, noteWidth, worldHeight)
}
