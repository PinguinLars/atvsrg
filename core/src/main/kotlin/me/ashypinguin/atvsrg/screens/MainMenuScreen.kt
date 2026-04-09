package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import ktx.app.clearScreen
import me.ashypinguin.atvsrg.*
import me.ashypinguin.atvsrg.maps.BeatMap
import me.ashypinguin.atvsrg.maps.BeatMapNote
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.LEFT_COLUMN
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.LEFT_MID_COLUMN
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.RIGHT_COLUMN
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.RIGHT_MID_COLUMN

class MainMenuScreen(game: Atvsrg) : AbstractScreen(game) {
  override fun render(delta: Float) {
    clearScreen(GRAY_BG_TONE, GRAY_BG_TONE, GRAY_BG_TONE)
    game.viewport.apply()
    game.renderer.projectionMatrix = game.viewport.camera.combined
    game.batch.projectionMatrix = game.viewport.camera.combined

    game.withRenderer(ShapeRenderer.ShapeType.Filled) {
      color = Color.BLACK
      rect(0f, 0f, it.viewport.worldWidth, it.viewport.worldHeight)
    }

    game.withBatch {
      it.font.draw(
        this,
        "Welcome by @vsrg!!\nPress anywhere to start",
        0f,
        it.viewport.worldHeight * .9f,
        it.viewport.worldWidth,
        Align.center,
        false
      )
    }

    if (Gdx.input.isTouched) {
      val notes = listOf(
        BeatMapNote(LEFT_COLUMN, 1),
        BeatMapNote(LEFT_MID_COLUMN, 2),
        BeatMapNote(RIGHT_MID_COLUMN, 3),
        BeatMapNote(RIGHT_COLUMN, 4),
        BeatMapNote(RIGHT_COLUMN, 5),
        BeatMapNote(LEFT_COLUMN, 6),
        BeatMapNote(LEFT_MID_COLUMN, 6),
      )
      val map = BeatMap(240, 100_000_000, notes, "no-time-to-explain-by-goodkid.wav".toMusic())
      game.addScreen(GameScreen(game, map))
      game.setScreen<GameScreen>()
      game.removeScreen<MainMenuScreen>()
      dispose()
    }
  }
}
