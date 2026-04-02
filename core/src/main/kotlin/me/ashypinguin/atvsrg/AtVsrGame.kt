package me.ashypinguin.atvsrg

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import me.ashypinguin.atvsrg.screens.AbstractScreen
import me.ashypinguin.atvsrg.screens.MainMenuScreen

/**
 * The main game instance.
 *
 * # DO NOT CALL MANUALLY
 */
class AtVsrGame : KtxGame<AbstractScreen>() {

  /** Share sprite batch */
  val batch by lazy { SpriteBatch() }

  /** Default arial font used by LIBgdx */
  val font by lazy {
    val generator = FreeTypeFontGenerator("Roboto-Regular.ttf".toInternalFile())
    val parameter = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
      size = 24
      borderWidth = 1f
      color = Color.WHITE
    }
    val font = generator.generateFont(parameter)
    generator.dispose()
    // Roboto-Regular.ttf should exist
    font!!
  }

  /** Viewport of the game */
  val viewport by lazy { FitViewport(800f, 480f) }

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

