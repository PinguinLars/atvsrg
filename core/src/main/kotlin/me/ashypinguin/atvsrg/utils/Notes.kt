package me.ashypinguin.atvsrg.utils

import me.ashypinguin.atvsrg.maps.BeatMapNote
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.*

typealias Notes = Array<out BeatMapNote>
typealias Note = BeatMapNote
private typealias Pos = BeatMapNotePosition

/**
 * Helper function to creat slam notes
 * ## Example
 * ```
 * *slamNotes(beat)
 * ```
 */
fun slamNotes(beat: Int) = arrayOf(
  Note(LEFT_COLUMN, beat),
  Note(LEFT_MID_COLUMN, beat),
  Note(RIGHT_MID_COLUMN, beat),
  Note(RIGHT_COLUMN, beat),
)

fun leftNote(beat: Int) = Note(LEFT_COLUMN, beat)
fun leftMidNote(beat: Int) = Note(LEFT_MID_COLUMN, beat)
fun rightMidNote(beat: Int) = Note(RIGHT_MID_COLUMN, beat)
fun rightNote(beat: Int) = Note(RIGHT_COLUMN, beat)

fun doubleNote(beat: Int, pos1: Pos, pos2: Pos): Notes {
  if (pos1 == pos2) throw IllegalArgumentException("pos1 ($pos1) cannot be equal to pos2 ($pos2)")
  return arrayOf(Note(pos1, beat), Note(pos2, beat))
}

fun tripleNote(beat: Int, pos1: Pos, pos2: Pos, pos3: Pos): Notes {
  if (!(pos1 != pos2 && pos2 != pos3 && pos1 != pos3)) throw IllegalArgumentException("pos1 ($pos1), pos2 ($pos2) and pos3($pos3) cannot be equal to another")
  return arrayOf(Note(pos1, beat), Note(pos2, beat), Note(pos3, beat))
}
