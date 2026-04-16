package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

fun ShapeRenderer.note(color: Color, offset: Int, noteOffset: Float, y: Float, noteWidth: Float, noteHeight: Float) {
  this.color = color
  rect(noteOffset + noteWidth * offset, y, noteWidth, noteHeight)
}
