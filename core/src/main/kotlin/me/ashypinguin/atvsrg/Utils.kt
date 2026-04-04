@file:Suppress("unused") // these are functions that are written in advanced.

package me.ashypinguin.atvsrg

import com.badlogic.gdx.Application
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import kotlin.system.exitProcess

/**
 * Start a [SpriteBatch] render with the [game][Atvsrg] instance.
 *
 * @receiver The main [game][Atvsrg] instance
 * @param scope Function block that gets called between [SpriteBatch.begin] and [SpriteBatch.end]
 * @see ktx.graphics.use
 * @see withRenderer
 */
fun Atvsrg.withBatch(scope: SpriteBatch.(Atvsrg) -> Unit) {
  batch.begin()
  batch.scope(this)
  batch.end()
}

/**
 * Start a [ShapeRenderer]
 *
 * @receiver The main [game][Atvsrg] instance
 * @param scope Function block that gets called between [ShapeRenderer.begin] and [ShapeRenderer.end]
 * @param type The [type][ShapeRenderer.ShapeType] the [ShapeRenderer] uses. (Optional defaults to whatever upstreams defaults to.)
 * @see ktx.graphics.use
 * @see withBatch
 */
fun Atvsrg.withRenderer(type: ShapeRenderer.ShapeType? = null, scope: ShapeRenderer.(Atvsrg) -> Unit) {
  if (type != null) renderer.begin(type) else renderer.end()
  renderer.scope(this)
  renderer.end()
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

/**
 * Dulls a color.
 * @receiver The color to dull. (Does **not** get modified)
 * @return A new duller color.
 */
fun Color.dull() : Color = this.cpy().lerp(Color.DARK_GRAY, 0.5f)
