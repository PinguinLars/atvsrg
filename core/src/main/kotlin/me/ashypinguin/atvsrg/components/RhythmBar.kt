package me.ashypinguin.atvsrg.components

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.utils.*

fun ShapeRenderer.rhythmBar(worldWidth: Float, worldHeight: Float, beat: Float) {
  color = RHYTHM_BAR_COLOR
  rect(
    worldWidth * NOTE_WALL_OFFSET_PERCENT,
    // the .5f is an offset to not make the notes feel stuck to the bar
    worldHeight * (1f - ((beat + RHYTHM_BAR_OFFSET) % BEAT_SCROLL_SPEED / BEAT_SCROLL_SPEED)),
    worldWidth * (NOTE_WIDTH_PERCENT * 4f),
    worldHeight * .01f
  )
}
