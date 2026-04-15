package me.ashypinguin.atvsrg.utils

import me.ashypinguin.atvsrg.maps.BeatMapNote
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.*

typealias ANotes = Array<out BeatMapNote>

/**
 * Helper function to creat slam notes
 * ## Example
 * ```
 * *slamNotes(beat)
 * ```
 */
fun slamNotes(beat: Int) = arrayOf(
  BeatMapNote(LEFT_COLUMN, beat),
  BeatMapNote(LEFT_MID_COLUMN, beat),
  BeatMapNote(RIGHT_MID_COLUMN, beat),
  BeatMapNote(RIGHT_COLUMN, beat),
)

fun leftNote(beat: Int) = BeatMapNote(LEFT_COLUMN, beat)
fun leftMidNote(beat: Int) = BeatMapNote(LEFT_MID_COLUMN, beat)
fun rightMidNote(beat: Int) = BeatMapNote(RIGHT_MID_COLUMN, beat)
fun rightNote(beat: Int) = BeatMapNote(RIGHT_COLUMN, beat)

fun doubleNote(beat: Int, pos1: BeatMapNotePosition, pos2: BeatMapNotePosition): ANotes {
  if (pos1 == pos2) throw IllegalArgumentException("pos1 ($pos1) cannot be equal to pos2 ($pos2)")
  return arrayOf(BeatMapNote(pos1, beat), BeatMapNote(pos2, beat))
}
