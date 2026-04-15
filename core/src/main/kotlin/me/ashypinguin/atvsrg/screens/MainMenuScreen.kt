package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import ktx.app.clearScreen
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.GRAY_BG_TONE
import me.ashypinguin.atvsrg.maps.BeatMap
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.LEFT_COLUMN
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.LEFT_MID_COLUMN
import me.ashypinguin.atvsrg.utils.*

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
      it.bigFont.draw(
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
        leftNote(1),
        leftMidNote(2),
        rightMidNote(3),
        rightNote(4),
        rightNote(5),
        *doubleNote(6, LEFT_COLUMN, LEFT_MID_COLUMN),
        *slamNotes(7),
        *slamNotes(8),
        *slamNotes(9),
      )
      val map = BeatMap(240, 100_000_000, notes, "no-time-to-explain-by-goodkid.wav".toMusic())
      game.addScreen(GameScreen(game, map))
      game.setScreen<GameScreen>()
      game.removeScreen<MainMenuScreen>()
      dispose()
    }
  }
}
