package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.*
import me.ashypinguin.atvsrg.components.fpsCounter
import me.ashypinguin.atvsrg.maps.BeatMap
import ktx.app.clearScreen as clear

private val log = logger<GameScreen>()
private const val NOTE_WIDTH_PRECENT = .15f
private const val NOTE_HEIGHT_PRECENT = .05f

/**
 * The space between the walls and the first and last note.
 *
 * It is half of the remaining percent of 4 times [NOTE_WIDTH_PRECENT]
 */
private const val NOTE_WALL_OFFSET_PERCENT = (1f - NOTE_WIDTH_PRECENT * 4) * .5f
private const val NOTE_GROUND_OFFSET_PERCENT = NOTE_HEIGHT_PRECENT * .5f
private const val KEY_UNPRESSED_DARKNESS = .5f
private const val COLUMN_DARKNESS = .7f
private const val NOTE_DARKNESS = .3f

private const val BEAT_SCROLL_SPEED = 8

private val LEFT_COLOR = Color.YELLOW
private val LEFT_MID_COLOR = Color.ORANGE
private val RIGHT_MID_COLOR = Color.RED
private val RIGHT_COLOR = Color.PURPLE

class GameScreen(game: Atvsrg, val map: BeatMap) : AbstractScreen(game) {
  /** The time since that the fps last got updated in seconds */
  private var timeSinceLastFpsUpdate = 1f
  private var fps = 0

  /**
   * Handy internal variable to get beats
   */
  private val beats
    get() = timeSinceStart * (map.bpm / 60)
  private var timeSinceStart = 0f

  override fun show() {
    map.song.isLooping = false
    map.song.position = 0f
    map.song.play()
  }

  override fun render(delta: Float) {
    clear(GRAY_BG_TONE, GRAY_BG_TONE, GRAY_BG_TONE)
    game.viewport.apply()
    game.batch.projectionMatrix = game.viewport.camera.combined
    game.renderer.projectionMatrix = game.viewport.camera.combined

    timeSinceLastFpsUpdate += delta
    if (timeSinceLastFpsUpdate >= .25f) {
      timeSinceLastFpsUpdate = 0f
      fps = (1 / delta).toInt()
      // delta also can be found with fps^-1 because fps is the inverse of delta and the inverse of an inverse is the original
      log.debug { "FPS: $fps, Delta: $delta" }
    }
    timeSinceStart += delta

    game.withRenderer(ShapeRenderer.ShapeType.Filled) {
      //Make drawable screen black
      color = Color.BLACK
      rect(0f, 0f, it.viewport.worldWidth, it.viewport.worldHeight)

      //Columns
      color = LEFT_COLOR.dull(COLUMN_DARKNESS)
      rect(
        it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
        0f,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight
      )
      color = LEFT_MID_COLOR.dull(COLUMN_DARKNESS)
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT + NOTE_WALL_OFFSET_PERCENT),
        0f,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight
      )
      color = RIGHT_MID_COLOR.dull(COLUMN_DARKNESS)
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 2 + NOTE_WALL_OFFSET_PERCENT),
        0f,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight
      )
      color = RIGHT_COLOR.dull(COLUMN_DARKNESS)
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 3 + NOTE_WALL_OFFSET_PERCENT),
        0f,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight
      )

      //Keys
      color = if (input.isKeyPressed(Keys.D)) LEFT_COLOR else LEFT_COLOR.dull(KEY_UNPRESSED_DARKNESS)
      rect(
        it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )
      color = if (input.isKeyPressed(Keys.F)) LEFT_MID_COLOR else LEFT_MID_COLOR.dull(KEY_UNPRESSED_DARKNESS)
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT + NOTE_WALL_OFFSET_PERCENT),
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )
      color = if (input.isKeyPressed(Keys.J)) RIGHT_MID_COLOR else RIGHT_MID_COLOR.dull(KEY_UNPRESSED_DARKNESS)
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 2 + NOTE_WALL_OFFSET_PERCENT),
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )
      color = if (input.isKeyPressed(Keys.K)) RIGHT_COLOR else RIGHT_COLOR.dull(KEY_UNPRESSED_DARKNESS)
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 3 + NOTE_WALL_OFFSET_PERCENT),
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )

      //rhythm bar
      color = Color.LIGHT_GRAY
      rect(
        it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
        it.viewport.worldHeight * (1f - (beats % BEAT_SCROLL_SPEED / BEAT_SCROLL_SPEED)),
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 4f),
        it.viewport.worldHeight * .01f
      )

      //Test note
      color = LEFT_COLOR.dull(NOTE_DARKNESS)
      rect(
        it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
        it.viewport.worldHeight * .6f,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )

      fpsCounter(it.viewport)
    }

    game.withBatch {
      it.fpsFont.draw(this, "beats: ${beats.toInt()}", 0f, it.viewport.worldHeight * .9f)
      fpsCounter(fps, it.viewport, it.fpsFont)
    }

    if (timeSinceStart > map.length ||
      map.song.position > map.length ||
      !map.song.isPlaying ||
      input.isKeyPressed(Keys.Q)
    ) TODO("Make a end screen")
  }

  override fun dispose() {
    map.dispose()
    super.dispose()
  }
}
