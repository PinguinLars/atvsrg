package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.utils.*

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

fun ShapeRenderer.key(color: Color, pressed: Boolean, worldWidth: Float, worldHeight: Float, offset: Int) {
  this.color = if (pressed) color else color.dull(KEY_UNPRESSED_DARKNESS)
  rect(
    worldWidth * (NOTE_WIDTH_PRECENT * offset + NOTE_WALL_OFFSET_PERCENT),
    worldHeight * NOTE_GROUND_OFFSET_PERCENT,
    worldWidth * NOTE_WIDTH_PRECENT,
    worldHeight * NOTE_HEIGHT_PRECENT
  )
}
