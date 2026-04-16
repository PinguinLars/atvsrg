package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.utils.RHYTHM_BAR_COLOR

fun ShapeRenderer.rhythmBar(offset: Float, height: Float, width: Float, y: Float) {
  color = RHYTHM_BAR_COLOR
  rect(offset, y, width, height)
}
