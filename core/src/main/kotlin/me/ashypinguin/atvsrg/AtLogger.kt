package me.ashypinguin.atvsrg

import ktx.log.Logger
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Logger with date formatting.
 * @property name class name.
 */
class AtLogger(name: String) : Logger(name) {
  companion object {
    private val DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMMYY HH:mm:ss.SSS")
  }

  override fun buildMessage(message: String): String = "[${LocalDateTime.now().format(DATE_FORMAT)}] $name: $message"
}

/**
 * Generate logger with date formatting.
 */
inline fun <reified T : Any> logger(): Logger = AtLogger(T::class.java.name)
