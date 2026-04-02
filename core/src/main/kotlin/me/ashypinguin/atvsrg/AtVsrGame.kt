package me.ashypinguin.atvsrg

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.async.KtxAsync
import me.ashypinguin.atvsrg.screens.MainMenuScreen

class AtVsrGame : KtxGame<KtxScreen>() {
  val batch by lazy { SpriteBatch() }
  val font by lazy { BitmapFont() }

  override fun create() {
    KtxAsync.initiate()

    addScreen(MainMenuScreen(this))
    setScreen<MainMenuScreen>()
  }

  override fun dispose() {
    batch.dispose()
    font.dispose()
    super.dispose()
  }
}

