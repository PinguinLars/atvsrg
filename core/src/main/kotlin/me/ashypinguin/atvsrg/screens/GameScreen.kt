package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.Align
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.exit
import me.ashypinguin.atvsrg.logger
import me.ashypinguin.atvsrg.maps.BeatMap
import me.ashypinguin.atvsrg.withBatch

private val log = logger<GameScreen>()

class GameScreen(game: Atvsrg, /* Temporary */ val map: BeatMap? = null) : AbstractScreen(game) {
  var lastPressedKey = ' '

  override fun render(delta: Float) {
    game.viewport.apply()
    game.batch.projectionMatrix = game.viewport.camera.combined

    game.withBatch {
      it.font.draw(
        this,
        "Last key: $lastPressedKey",
        0f,
        it.viewport.worldHeight * .9f,
        it.viewport.worldWidth,
        Align.center,
        false
      )
    }

    when {
      Gdx.input.isKeyPressed(Input.Keys.D) -> lastPressedKey = 'd'
      Gdx.input.isKeyPressed(Input.Keys.F) -> lastPressedKey = 'f'
      Gdx.input.isKeyPressed(Input.Keys.J) -> lastPressedKey = 'j'
      Gdx.input.isKeyPressed(Input.Keys.K) -> lastPressedKey = 'k'
      Gdx.input.isKeyPressed(Input.Keys.Q) -> game.exit()
    }
  }

  override fun dispose() {
    super.dispose()
    map?.dispose()
  }
}
