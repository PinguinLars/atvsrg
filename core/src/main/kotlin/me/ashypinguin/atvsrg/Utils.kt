@file:Suppress("unused") // these are functions that are written in advanced.

package me.ashypinguin.atvsrg

import com.badlogic.gdx.Application
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import kotlin.system.exitProcess

/**
 * Start a [SpriteBatch] render with the [game][Atvsrg] instance.
 *
 * @receiver the main [game][Atvsrg] instance
 * @param scope Function block that gets called between [SpriteBatch.begin] and [SpriteBatch.end]
 * @see ktx.graphics.use
 */
fun Atvsrg.withBatch(scope: SpriteBatch.(Atvsrg) -> Unit) {
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
fun Atvsrg.exit(status: Int = 0): Nothing {
  dispose()
  exitProcess(status)
}

/**
 * Print the log level as a human-readable string
 * @receiver The log level as an integer
 * @throws IllegalStateException when the log level isn't in the valid range. (0..3)
 * @return The human-readable log level
 */
fun Int.toLogLevel(): String = when (this) {
  Application.LOG_INFO -> "info"
  Application.LOG_ERROR -> "error"
  Application.LOG_DEBUG -> "debug"
  Application.LOG_NONE -> "none"
  else -> throw IllegalStateException("LogLevel has an illegal value of $this. This should have been thrown by libGDX")
}
