@file:JvmName("Lwjgl3Launcher")
@file:Suppress("SpellCheckingInspection")

package me.ashypinguin.atvsrg.lwjgl3

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration.getDisplayMode
import me.ashypinguin.atvsrg.Atvsrg

private const val LOG_ENV = "ATVSRG_LOG"

/** Launches the desktop (LWJGL3) application. */
fun main() {
  // This handles macOS support and helps on Windows.
  if (StartupHelper.startNewJvmIfRequired()) return

  /** Get log level to pass to [Atvsrg] */
  val logLevel = when (System.getenv(LOG_ENV)?.lowercase()) {
    "debug" -> Application.LOG_DEBUG
    "info" -> Application.LOG_INFO
    "none" -> Application.LOG_NONE
    "error" -> Application.LOG_ERROR
    null -> Application.LOG_ERROR
    else -> {
      println("[WARNING] Env. var. '$LOG_ENV' has an illegal state: \"${System.getenv(LOG_ENV)}\"")
      Application.LOG_ERROR
    }
  }

  val config = Lwjgl3ApplicationConfiguration().apply {
    setTitle("atvsrg")
    useVsync(false)
    setWindowedMode(640, 360)
    setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
  }

  Lwjgl3Application(Atvsrg(logLevel), config)
}
