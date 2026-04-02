package me.ashypinguin.atvsrg.maps

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.files.FileHandle

data class BeatMap(val song: Music) {
  constructor(songFile: FileHandle) : this(song = Gdx.audio.newMusic(songFile))
}
