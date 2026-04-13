@file:Suppress("unused") // these are functions that are written in advanced.

package me.ashypinguin.atvsrg.utils

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.assets.toInternalFile
import ktx.log.error
import me.ashypinguin.atvsrg.Atvsrg
import kotlin.system.exitProcess

/**
 * Start a [SpriteBatch] render with the [game][me.ashypinguin.atvsrg.Atvsrg] instance.
 *
 * @receiver The main [game][me.ashypinguin.atvsrg.Atvsrg] instance
 * @param scope Function block that gets called between [SpriteBatch.begin] and [SpriteBatch.end]
 * @see ktx.graphics.use
 * @see withRenderer
 */
inline fun Atvsrg.withBatch(scope: SpriteBatch.(Atvsrg) -> Unit) {
  batch.begin()
  batch.scope(this)
  batch.end()
}

/**
 * Start a [ShapeRenderer]
 *
 * @receiver The main [game][Atvsrg] instance
 * @param scope Function block that gets called between [ShapeRenderer.begin] and [ShapeRenderer.end]
 * @param type The [type][ShapeRenderer.ShapeType] the [ShapeRenderer] uses. (Optional defaults to [Line][ShapeRenderer.ShapeType.Line])
 * @see ktx.graphics.use
 * @see withBatch
 */
inline fun Atvsrg.withRenderer(
  type: ShapeRenderer.ShapeType = ShapeRenderer.ShapeType.Line,
  scope: ShapeRenderer.(Atvsrg) -> Unit
) {
  renderer.begin(type)
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
  if (status != 0) error { "Manual exit triggered with status: $status." }
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
fun Color.dull(t: Float): Color = this.cpy().lerp(Color.DARK_GRAY, t)

/**
 * Translates a string to a music object.
 * @receiver The location of the music file.
 */
fun String.toMusic(): Music = Gdx.audio.newMusic(this.toInternalFile())


fun <T> T.inlineIf(condition: Boolean, t: T.() -> Unit): T {
  if (condition) t()
  return this
}
