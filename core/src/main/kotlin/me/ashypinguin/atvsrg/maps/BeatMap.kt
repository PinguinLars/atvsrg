package me.ashypinguin.atvsrg.maps

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Disposable

/** Handy alias to not use [List]*/
typealias Notes = List<BeatMapNote>

/**
 * A beatmap usable for [GameScreen][me.ashypinguin.atvsrg.screens.GameScreen]
 * @property song The music (mp3) file for the beatmap (can be passed as a [FileHandle] or a [Music])
 * @property length The length of the map in seconds. NOTE: this causes a [IllegalStateException] if longer then [song]
 * @property bpm The beats per minute of the map. This is used to calculate when notes should drop. (Which get dropped on a specific beat.)
 * @property notes A list of [BeatMapNote], which get dropped on [the beat specified][BeatMapNote.beat].
 */
class BeatMap(val bpm: Int, val length: Int, initialNotes: Notes, val song: Music) : Disposable {
  //  val notes: Notes = initialNotes.sortedBy(BeatMapNote::beat)
  val notes: Notes
    field = initialNotes.toMutableList()

  private var notesSorted = false

  /**
   * Sorts the notes such that you don't have to loop over them during rendering.
   * It makes sure that when it is sorted, and you call it again it just returns early.
   */
  fun sortNotes() {
    if (notesSorted) return
    notes.sortBy(BeatMapNote::beat)
    notesSorted = true
  }

  override fun dispose() = song.dispose()
}
