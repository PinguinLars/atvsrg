package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.badlogic.gdx.utils.Align
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.components.clear
import me.ashypinguin.atvsrg.components.drawableArea
import me.ashypinguin.atvsrg.maps.BeatMap
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.LEFT_COLUMN
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.LEFT_MID_COLUMN
import me.ashypinguin.atvsrg.utils.*

class MainMenuScreen(game: Atvsrg) : AbstractScreen(game) {
  override fun render(delta: Float) {
    clear()
    game.viewport.apply()
    game.renderer.projectionMatrix = game.viewport.camera.combined
    game.batch.projectionMatrix = game.viewport.camera.combined

    val worldWidth = game.viewport.worldWidth
    val worldHeight = game.viewport.worldHeight

    game.withRenderer(Filled) {
      drawableArea(it.viewport.worldWidth, it.viewport.worldHeight)

      color = UI_ELEMENT_BG_COLOR
      rect(worldWidth * .35f, worldHeight * .2f, worldWidth * .3f, worldHeight * .1f)
      rect(worldWidth * .35f, worldHeight * .4f, worldWidth * .3f, worldHeight * .1f)
      rect(worldWidth * .35f, worldHeight * .6f, worldWidth * .3f, worldHeight * .1f)
    }

    game.withBatch {
      it.bigFont.draw(
        this,
        it.i18n["welcome"],
        0f,
        worldHeight * .9f,
        worldWidth,
        Align.center,
        false
      )

      it.bigFont.draw(
        this,
        it.i18n["play"],
        0f,
        worldHeight * .675f,
        worldWidth,
        Align.center,
        false
      )

      it.bigFont.draw(
        this,
        it.i18n["tutorial"],
        0f,
        worldHeight * .475f,
        worldWidth,
        Align.center,
        false
      )

      it.bigFont.draw(
        this,
        it.i18n["settings"],
        0f,
        worldHeight * .275f,
        worldWidth,
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
