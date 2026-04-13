package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.utils.BEAT_SCROLL_SPEED
import me.ashypinguin.atvsrg.utils.NOTE_WALL_OFFSET_PERCENT
import me.ashypinguin.atvsrg.utils.NOTE_WIDTH_PRECENT
import me.ashypinguin.atvsrg.utils.RHYTHM_BAR_OFFSET

fun ShapeRenderer.rhythmBar(worldWidth: Float, worldHeight: Float, beat: Float) {
  color = Color.LIGHT_GRAY
  rect(
    worldWidth * NOTE_WALL_OFFSET_PERCENT,
    // the .5f is an offset to not make the notes feel stuck to the bar
    worldHeight * (1f - ((beat + RHYTHM_BAR_OFFSET) % BEAT_SCROLL_SPEED / BEAT_SCROLL_SPEED)),
    worldWidth * (NOTE_WIDTH_PRECENT * 4f),
    worldHeight * .01f
  )
}
