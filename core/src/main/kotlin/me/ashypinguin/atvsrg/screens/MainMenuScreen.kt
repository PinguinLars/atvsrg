package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.badlogic.gdx.utils.Align
import ktx.actors.onClick
import ktx.scene2d.label
import ktx.scene2d.scene2d
import ktx.scene2d.textButton
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.components.clear
import me.ashypinguin.atvsrg.components.drawableArea
import me.ashypinguin.atvsrg.maps.BeatMap
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.*
import me.ashypinguin.atvsrg.utils.*

private val log = logger<GameScreen>()

class MainMenuScreen(game: Atvsrg) : AbstractScreen(game) {
  override fun render(delta: Float) {
    clear()
    game.viewport.apply()
    game.renderer.projectionMatrix = game.viewport.camera.combined
    game.batch.projectionMatrix = game.viewport.camera.combined

    val worldWidth = game.viewport.worldWidth
    val worldHeight = game.viewport.worldHeight

    val x = Gdx.input.x
    val y = Gdx.input.y

    game.withRenderer(Filled) {
      drawableArea(it.viewport.worldWidth, it.viewport.worldHeight)

      color = UI_ELEMENT_BG_COLOR
      rect(worldWidth * .35f, worldHeight * .2f, worldWidth * .3f, worldHeight * .1f)
      rect(worldWidth * .35f, worldHeight * .4f, worldWidth * .3f, worldHeight * .1f)
      rect(worldWidth * .35f, worldHeight * .6f, worldWidth * .3f, worldHeight * .1f)
    }

    game.withBatch {
      it.bigFont.draw(
        this, it.i18n["welcome"], 0f, worldHeight * .9f, worldWidth, Align.center, false
      )

      it.bigFont.draw(
        this, it.i18n["play"], 0f, worldHeight * .675f, worldWidth, Align.center, false
      )

      it.bigFont.draw(
        this, it.i18n["tutorial"], 0f, worldHeight * .475f, worldWidth, Align.center, false
      )

      it.bigFont.draw(
        this, it.i18n["settings"], 0f, worldHeight * .275f, worldWidth, Align.center, false
      )
    }

    if (Gdx.input.isTouched) {
      val x = Gdx.input.x
      val y = Gdx.input.y
      log.debug { "Mouse: x=$x, y=$y; World: height=$worldHeight, width=$worldWidth" }

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
        leftNote(10),
        leftMidNote(11),
        rightMidNote(12),
        rightNote(13),
        rightMidNote(14),
        leftMidNote(15),
        leftNote(16),
        *doubleNote(17, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(18, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(19, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(20, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(21, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(22, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(23, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(24, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(25, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(26, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(27, LEFT_COLUMN, RIGHT_COLUMN),
        *tripleNote(28, LEFT_MID_COLUMN, RIGHT_MID_COLUMN,LEFT_COLUMN),
        *tripleNote(29, LEFT_COLUMN, LEFT_MID_COLUMN,RIGHT_COLUMN),
        *tripleNote(30, LEFT_COLUMN, RIGHT_MID_COLUMN,RIGHT_COLUMN),
        *tripleNote(31, LEFT_MID_COLUMN, RIGHT_MID_COLUMN,RIGHT_COLUMN),
        *tripleNote(32, LEFT_MID_COLUMN, RIGHT_MID_COLUMN,LEFT_COLUMN),
        *tripleNote(33, LEFT_COLUMN, LEFT_MID_COLUMN,RIGHT_COLUMN),
        *tripleNote(34, LEFT_COLUMN, RIGHT_MID_COLUMN,RIGHT_COLUMN),
        *tripleNote(35, LEFT_MID_COLUMN, RIGHT_MID_COLUMN,RIGHT_COLUMN),
      )
      val map = BeatMap(180, 100_000_000, notes, "no-time-to-explain-by-goodkid.wav".toMusic())
      game.addScreen(GameScreen(game, map))
      game.setScreen<GameScreen>()
      game.removeScreen<MainMenuScreen>()
      dispose()
    }
  }
}
