package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.utils.*

fun ShapeRenderer.columns(noteWidth: Float, worldHeight: Float, offset: Float) {
  color = LEFT_COLUMN_COLOR
  rect(offset, 0f, noteWidth, worldHeight)
  color = LEFT_MID_COLUMN_COLOR
  rect(offset + noteWidth, 0f, noteWidth, worldHeight)
  color = RIGHT_MID_COLUMN_COLOR
  rect(offset + noteWidth * 2, 0f, noteWidth, worldHeight)
  color = RIGHT_COLUMN_COLOR
  rect(offset + noteWidth * 3, 0f, noteWidth, worldHeight)
}
