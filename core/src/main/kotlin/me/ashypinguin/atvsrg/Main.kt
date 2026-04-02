package me.ashypinguin.atvsrg

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.async.KtxAsync

class Main : KtxGame<KtxScreen>() {
  val batch by lazy { SpriteBatch() }
  val font by lazy { BitmapFont() }

  override fun create() {
    KtxAsync.initiate()

    addScreen(FirstScreen())
    setScreen<FirstScreen>()
  }

  override fun dispose() {
    batch.dispose()
    font.dispose()
    super.dispose()
  }
}

