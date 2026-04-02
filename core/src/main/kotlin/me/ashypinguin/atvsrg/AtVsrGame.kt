package me.ashypinguin.atvsrg

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxGame
import ktx.async.KtxAsync
import me.ashypinguin.atvsrg.screens.AbstractScreen
import me.ashypinguin.atvsrg.screens.MainMenuScreen

/**
 * The main game instance.
 *
 * DO NOT CALL MANUALLY
 */
class AtVsrGame : KtxGame<AbstractScreen>() {

  /** Share sprite batch */
  val batch by lazy { SpriteBatch() }

  /** Default arial font used by LIBgdx */
  val font by lazy { BitmapFont() }

  /**
   * Main entrypoint of the game.
   */
  override fun create() {
    KtxAsync.initiate()

    addScreen(MainMenuScreen(this))
    setScreen<MainMenuScreen>()
  }

  /**
   * Disposes all shared resources and calls [KtxGame.dispose]
   */
  override fun dispose() {
    batch.dispose()
    font.dispose()
    super.dispose()
  }
}

