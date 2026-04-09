package me.ashypinguin.atvsrg.maps

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Disposable
import kotlin.time.Duration

/** Handy alias to not use [MutableList]*/
private typealias Notes = List<BeatMapNote>

/**
 * A beatmap usable for [GameScreen][me.ashypinguin.atvsrg.screens.GameScreen]
 * @property song The music (mp3) file for the beatmap (can be passed as a [FileHandle] or a [Music])
 * @property length The length of the map. NOTE: this causes a [IllegalStateException] if longer then [song]
 * @property bpm The beats per minute of the map. This is used to calculate when notes should drop. (Which get dropped on a specific beat.)
 * @property notes A list of [BeatMapNote], which get dropped on [the beat specified][BeatMapNote.beat].
 */
data class BeatMap(val bpm: Int) : Disposable {
  override fun dispose() = Unit
}
