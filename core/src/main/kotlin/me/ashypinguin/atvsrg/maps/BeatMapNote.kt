package me.ashypinguin.atvsrg.maps

/**
 * A beatmap note
 * @property beat the beat the note is on.
 * @property pos where the note should come. See [BeatMapNotePosition]
 */
data class BeatMapNote(val pos: BeatMapNotePosition, val beat: Int)
