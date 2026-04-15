package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.utils.BEAT_SCROLL_SPEED
import me.ashypinguin.atvsrg.utils.NOTE_HEIGHT_PRECENT
import me.ashypinguin.atvsrg.utils.NOTE_WALL_OFFSET_PERCENT
import me.ashypinguin.atvsrg.utils.NOTE_WIDTH_PRECENT

fun ShapeRenderer.note(color: Color, worldWidth: Float, worldHeight: Float, offset: Int, noteBeat: Int, beat: Float) {
  this.color = color
  rect(
    worldWidth * (NOTE_WALL_OFFSET_PERCENT + NOTE_WIDTH_PRECENT * offset),
    worldHeight * ((noteBeat - beat) / BEAT_SCROLL_SPEED),
    worldWidth * NOTE_WIDTH_PRECENT,
    worldWidth * NOTE_HEIGHT_PRECENT
  )
}
