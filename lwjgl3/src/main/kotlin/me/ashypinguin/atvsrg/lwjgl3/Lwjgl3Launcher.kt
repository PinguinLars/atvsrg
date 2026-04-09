@file:JvmName("Lwjgl3Launcher")
@file:Suppress("SpellCheckingInspection")

package me.ashypinguin.atvsrg.lwjgl3

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.utils.GdxRuntimeException
import me.ashypinguin.atvsrg.Atvsrg
import java.lang.System.err
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

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

  try {
    Lwjgl3Application(Atvsrg(logLevel), config)
  } catch (e: Throwable) {
    if (logLevel > Application.LOG_NONE) {
      val format = DateTimeFormatter.ofPattern("ddMMMYY HH:mm:ss.SSS")!!
      val getDate = { LocalDateTime.now().format(format)!! }
      val location = "me.ashypinguin.atvsrg.Lwjgl3Launcher"
      if (e.cause != null && e is GdxRuntimeException) {
        err.println("[FATAL] [${getDate()}] $location: A fatal error of type ${e.cause!!::class.java.name} with the reason \"${e.cause!!.message}\" has been thrown.")
        if (logLevel >= Application.LOG_DEBUG) e.printStackTrace(err)
        else println("[INFO] [${getDate()}] $location: Stacktraces are limited to the debug loglevel. To enable run with \"ATVSRG_LOG=debug\".")
        err.println("[FATAL] [${getDate()}] $location: Exiting...")
      } else {
        // This shouldn't be reachable
        err.println("[FATAL] [${getDate()}] $location: Something has seriously gone wrong.")
        throw e
      }
    }
    exitProcess(1)
  }
}
