package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

data class KeyStates(val left: Boolean, val leftMid: Boolean, val rightMid: Boolean, val right: Boolean) :
  Iterable<Boolean> {
  override fun iterator(): Iterator<Boolean> = object : Iterator<Boolean> {
    private var index = 0
    override fun hasNext(): Boolean = index < 4
    override fun next(): Boolean = when (index++) {
      0 -> left
      1 -> leftMid
      2 -> rightMid
      3 -> right
      else -> throw NoSuchElementException()
    }
  }
}

fun ShapeRenderer.key(
  color: Color,
  offset: Int,
  wallOffset: Float,
  groundOffset: Float,
  noteWidth: Float,
  noteHeight: Float
) {
  this.color = color
  rect(noteWidth * offset + wallOffset, groundOffset, noteWidth, noteHeight)
}
