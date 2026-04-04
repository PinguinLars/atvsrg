package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.clearScreen
import me.ashypinguin.atvsrg.*
import me.ashypinguin.atvsrg.maps.BeatMap

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
  override fun render(delta: Float) {
    clearScreen(GRAY_BG_TONE, GRAY_BG_TONE, GRAY_BG_TONE)
    game.viewport.apply()
    game.batch.projectionMatrix = game.viewport.camera.combined
    game.renderer.projectionMatrix = game.viewport.camera.combined

    game.withRenderer(ShapeRenderer.ShapeType.Filled) {
      //Make drawable screen black
      color = Color.BLACK
      rect(0f, 0f, it.viewport.worldWidth, it.viewport.worldHeight)

      color = if (input.isKeyPressed(Input.Keys.D)) Color.YELLOW else Color.YELLOW.dull()
      rect(
        it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )
      color = if (input.isKeyPressed(Input.Keys.F)) Color.ORANGE else Color.ORANGE.dull()
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT + NOTE_WALL_OFFSET_PERCENT),
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )
      color = if (input.isKeyPressed(Input.Keys.J)) Color.RED else Color.RED.dull()
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 2+ NOTE_WALL_OFFSET_PERCENT),
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )
      color = if (input.isKeyPressed(Input.Keys.K)) Color.PURPLE else Color.PURPLE.dull()
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 3+ NOTE_WALL_OFFSET_PERCENT),
        it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
      )
      if (input.isKeyPressed(Input.Keys.Q)) game.exit()
    }
  }

  override fun dispose() {
    map?.dispose()
    super.dispose()
  }
}
