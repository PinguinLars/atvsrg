package me.ashypinguin.atvsrg

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.I18NBundle
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import me.ashypinguin.atvsrg.screens.AbstractScreen
import me.ashypinguin.atvsrg.screens.MainMenuScreen
import me.ashypinguin.atvsrg.utils.logger
import me.ashypinguin.atvsrg.utils.toLogLevel
import java.util.*

private val log = logger<Atvsrg>()

/**
 * The main game instance.
 *
 * # DO NOT CALL MANUALLY
 */
class Atvsrg(val logLevel: Int) : KtxGame<AbstractScreen>() {

  /** Translations */
  val i18n by lazy { I18NBundle.createBundle("i18n/nls".toInternalFile(), Locale.of("nl", "NL")) }

  /** Shared sprite batch */
  val batch by lazy { SpriteBatch() }

  /** Shared shape render */
  val renderer by lazy { ShapeRenderer() }

  /** Default arial font used by LIBgdx */
  val bigFont by lazy {
    log.info { "Loading font: Roboto-Regular.ttf with size 24" }
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
  val smallFont by lazy {
    log.info { "Loading font: Roboto-Regular.ttf with size 16" }
    val generator = FreeTypeFontGenerator("Roboto-Regular.ttf".toInternalFile())
    val parameter = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
      size = 16
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
  val viewport by lazy { FitViewport(640f, 360f) }

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
    renderer.dispose()
    bigFont.dispose()
    smallFont.dispose()
    super.dispose()
    log.info { "Exiting" }
  }
}

