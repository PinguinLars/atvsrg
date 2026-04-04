package me.ashypinguin.atvsrg.maps

/**
 * A beatmap note
 * @property beat the beat the note is on.
 * @property position where the note should come. See [BeatMapNotePosition]
 */
data class BeatMapNote(val position: BeatMapNotePosition, val beat: UInt)
