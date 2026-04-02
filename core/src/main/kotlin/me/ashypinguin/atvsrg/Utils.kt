@file:Suppress("unused") // these are functions that are written in advanced.

package me.ashypinguin.atvsrg

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import kotlin.system.exitProcess

/**
 * Start a [SpriteBatch] render with the [game][AtVsrGame] instance.
 *
 * @receiver the main [game][AtVsrGame] instance
 * @param scope Function block that gets called between [SpriteBatch.begin] and [SpriteBatch.end]
 * @see ktx.graphics.use
 */
fun AtVsrGame.withBatch(scope: SpriteBatch.(AtVsrGame) -> Unit) {
  batch.begin()
  batch.scope(this)
  batch.end()
}

/**
 * Safely exit the game
 * @param status the exit/status code the games exits with and a non-zero code means failure
 * @return Nothing, because it shouldn't return instead it exits the whole game
 * @see exitProcess
 */
fun AtVsrGame.exit(status: Int = 0): Nothing {
  dispose()
  exitProcess(status)
}
