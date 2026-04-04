package me.ashypinguin.atvsrg.screens

import ktx.app.KtxScreen
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.logger

private val log = logger<AbstractScreen>()

/**
 * Super class for all screens.
 * It gives access to shared recourses in [Atvsrg]
 *
 * @property game the main game instance that can be used to access shared recourses
 */
abstract class AbstractScreen(protected val game: Atvsrg) : KtxScreen {
  /**
   * Overridden version of [KtxScreen.resize] to make sure the viewport gets update.
   *
   * This is here to make sure it gets applied to every class.
   */
  override fun resize(width: Int, height: Int) {
    log.debug { "Resize triggered on screen ${javaClass.simpleName}" }
    game.viewport.update(width, height, true)
  }

  /**
   * Easily log dispose events
   */
  override fun dispose() = log.debug { "Disposing ${javaClass.simpleName}" }

  override fun show() = log.debug { "Showing ${javaClass.simpleName}" }
}
