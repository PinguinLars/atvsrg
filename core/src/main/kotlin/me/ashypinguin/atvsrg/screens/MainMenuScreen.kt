package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Align
import me.ashypinguin.atvsrg.AtVsrGame
import me.ashypinguin.atvsrg.withBatch

class MainMenuScreen(game: AtVsrGame) : AbstractScreen(game) {
  override fun render(delta: Float) {
    game.viewport.apply()
    game.batch.projectionMatrix = game.viewport.camera.combined

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
      game.addScreen(GameScreen(game))
      game.setScreen<GameScreen>()
      game.removeScreen<MainMenuScreen>()
      dispose()
    }
  }
}
