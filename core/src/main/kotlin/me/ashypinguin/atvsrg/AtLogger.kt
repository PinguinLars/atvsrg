package me.ashypinguin.atvsrg

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import ktx.log.Logger
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Logger with date formatting.
 * @property name class name.
 */
class AtLogger(name: String) : Logger(name) {
  companion object {
    private val DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMMYY HH:mm:ss.SSS")
  }

  override fun buildMessage(message: String): String = "[${LocalDateTime.now().format(DATE_FORMAT)}] $name: $message"

  @OptIn(ExperimentalContracts::class)
  fun warning(message: () -> String) {
    contract { callsInPlace(message, InvocationKind.AT_MOST_ONCE) }
    if (Gdx.app.logLevel >= Application.LOG_INFO) Gdx.app.log("WARNING", buildMessage(message()))
  }
}

/**
 * Generate logger with date formatting.
 */
inline fun <reified T : Any> logger(): AtLogger = AtLogger(T::class.java.name)
