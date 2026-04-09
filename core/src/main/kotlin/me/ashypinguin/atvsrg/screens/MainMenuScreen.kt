package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import ktx.app.clearScreen
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.GRAY_BG_TONE
import me.ashypinguin.atvsrg.maps.BeatMap
import me.ashypinguin.atvsrg.withBatch
import me.ashypinguin.atvsrg.withRenderer

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
      val map = BeatMap(180)
      game.addScreen(GameScreen(game, map))
      game.setScreen<GameScreen>()
      game.removeScreen<MainMenuScreen>()
      dispose()
    }
  }
}
