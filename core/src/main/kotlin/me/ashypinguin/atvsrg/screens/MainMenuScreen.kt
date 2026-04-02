package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.graphics.Texture
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.graphics.use
import me.ashypinguin.atvsrg.AtVsrGame

class MainMenuScreen(game: AtVsrGame) : AbstractScreen(game) {
  private val image = Texture("logo.png".toInternalFile(), true).apply {
    setFilter(
      Texture.TextureFilter.Linear,
      Texture.TextureFilter.Linear
    )
  }

  override fun render(delta: Float) {
    clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
    game.batch.use {
      it.draw(image, 100f, 160f)
    }
  }

  override fun dispose() {
    image.disposeSafely()
  }
}
