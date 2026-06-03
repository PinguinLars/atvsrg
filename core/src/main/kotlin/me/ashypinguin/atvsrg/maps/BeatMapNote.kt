package me.ashypinguin.atvsrg.maps

import me.ashypinguin.atvsrg.utils.logger

private val log = logger<BeatMapNote>()

/**
 * A beatmap note
 * @property beat the beat the note is on.
 * @property pos where the note should come. See [BeatMapNotePosition]
 */
data class BeatMapNote(val pos: BeatMapNotePosition, val beat: Int) {
  @Suppress("KotlinConstantConditions") //The whole reason it is there
  var hit = false
    set(value) = if (value) field = true else log.error { "hit must never be set to false. Got $value" }

}
