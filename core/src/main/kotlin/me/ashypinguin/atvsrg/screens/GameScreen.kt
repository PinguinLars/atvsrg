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

class GameScreen(game: Atvsrg, /* Temporary */ val map: BeatMap? = null) : AbstractScreen(game) {
  /** The time since that the fps last got updated in seconds */
  private var timeSinceLastFpsUpdate = 1f
  private var fps = 0

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
      log.debug() { "FPS: $fps, Delta: $delta" }
    }

    game.withRenderer(ShapeRenderer.ShapeType.Filled) {
      //Make drawable screen black
      color = Color.BLACK
      rect(0f, 0f, it.viewport.worldWidth, it.viewport.worldHeight)

      //Keys
      color = if (input.isKeyPressed(Keys.D)) Color.YELLOW else Color.YELLOW.dull()
      rect(
        it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )
      color = if (input.isKeyPressed(Keys.F)) Color.ORANGE else Color.ORANGE.dull()
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT + NOTE_WALL_OFFSET_PERCENT),
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )
      color = if (input.isKeyPressed(Keys.J)) Color.RED else Color.RED.dull()
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 2 + NOTE_WALL_OFFSET_PERCENT),
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )
      color = if (input.isKeyPressed(Keys.K)) Color.PURPLE else Color.PURPLE.dull()
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 3 + NOTE_WALL_OFFSET_PERCENT),
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )

      fpsCounter(it.viewport)
    }

    game.withBatch { fpsCounter(fps, it.viewport, it.font) }

    if (input.isKeyPressed(Keys.Q)) game.exit()
  }

  override fun dispose() {
    map?.dispose()
    super.dispose()
  }
}
