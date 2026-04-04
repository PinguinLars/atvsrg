package me.ashypinguin.atvsrg

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import me.ashypinguin.atvsrg.screens.AbstractScreen
import me.ashypinguin.atvsrg.screens.MainMenuScreen

private val log = logger<Atvsrg>()

/**
 * The main game instance.
 *
 * # DO NOT CALL MANUALLY
 */
class Atvsrg(val logLevel: Int) : KtxGame<AbstractScreen>() {

  /** Share sprite batch */
  val batch by lazy { SpriteBatch() }

  /** Default arial font used by LIBgdx */
  val font by lazy {
    log.info { "Loading font: Roboto-Regular.ttf" }
    val generator = FreeTypeFontGenerator("Roboto-Regular.ttf".toInternalFile())
    val parameter = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
      size = 24
      borderWidth = 1f
      color = Color.WHITE
    }
    val font = generator.generateFont(parameter)
    generator.dispose()
    log.debug { "Font loaded" }
    // Roboto-Regular.ttf should exist
    font!!
  }

  /** Viewport of the game */
  val viewport by lazy { FitViewport(800f, 480f) }

  /**
   * Main entrypoint of the game.
   */
  override fun create() {
    Gdx.app.logLevel = logLevel
    log.info { "Starting @vsrg" }
    log.info { "The log level is ${logLevel.toLogLevel()}" }

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
    log.info { "Exiting" }
  }
}

