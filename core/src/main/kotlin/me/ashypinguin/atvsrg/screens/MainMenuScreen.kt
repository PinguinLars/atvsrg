package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Align
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import me.ashypinguin.atvsrg.AtVsrGame
import me.ashypinguin.atvsrg.exit
import me.ashypinguin.atvsrg.withBatch
import kotlin.system.exitProcess

class MainMenuScreen(game: AtVsrGame) : AbstractScreen(game) {
  private val image = Texture("logo.png".toInternalFile(), true).apply {
    setFilter(
      Texture.TextureFilter.Linear,
      Texture.TextureFilter.Linear
    )
  }

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
      game.exit()
    }
  }

  override fun resize(width: Int, height: Int) {
    game.viewport.update(width, height, true)
  }

  override fun dispose() {
    image.disposeSafely()
    super.dispose()
  }
}
