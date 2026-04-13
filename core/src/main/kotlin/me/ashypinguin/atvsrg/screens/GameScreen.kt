package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.GRAY_BG_TONE
import me.ashypinguin.atvsrg.components.*
import me.ashypinguin.atvsrg.logger
import me.ashypinguin.atvsrg.maps.*
import me.ashypinguin.atvsrg.utils.*
import ktx.app.clearScreen as clear

private val log = logger<GameScreen>()

class GameScreen(game: Atvsrg, val map: BeatMap) : AbstractScreen(game) {
  /** The time since that the fps last got updated in seconds */
  private var timeSinceLastFpsUpdate = 1f
  private var fps = 0
  private var lastValidIndex = 0

  @Suppress("unused") //TODO: implement this
  private var acc = 1f
  private var score = 0

  /**
   * Handy internal variable to get beats
   */
  private val beat get() = timeSinceStart * (map.bpm / 60)
  private var timeSinceStart = -2.5f

  private var shownNotes = mutableListOf<BeatMapNote>()

  override fun show() {
    //init music
    if (System.getenv("ATVSRG_MUTE")?.lowercase() != "true") {
      map.song.volume = MUSIC_VOLUME
    } else {
      map.song.volume = 0f
      log.warning { "Sound has been disabled because 'ATVSRG_MUTE' is true" }
    }
    map.song.isLooping = false
    map.song.position = 0f
    map.song.play()

    //make sure notes are sorted
    map.sortNotes()
  }

  override fun render(delta: Float) {
    clear(GRAY_BG_TONE, GRAY_BG_TONE, GRAY_BG_TONE)
    game.viewport.apply()
    game.batch.projectionMatrix = game.viewport.camera.combined
    game.renderer.projectionMatrix = game.viewport.camera.combined

    timeSinceLastFpsUpdate += delta
    if (timeSinceLastFpsUpdate >= .25f) {
      timeSinceLastFpsUpdate = 0f
      fps = (1 / delta).toInt()
      // delta also can be found with fps^-1 because fps is the inverse of delta and the inverse of an inverse is the original
      log.debug { "FPS: $fps, Delta: $delta" }
    }
    timeSinceStart += delta

    //Keys
    val lKey = input.isKeyPressed(Keys.D)
    val lmKey = input.isKeyPressed(Keys.F)
    val rmKey = input.isKeyPressed(Keys.J)
    val rKey = input.isKeyPressed(Keys.K)

    val keyStates = KeyStates(lKey, lmKey, rmKey, rKey)

    // Get all notes
    shownNotes.clear()
    for (i in lastValidIndex..<map.notes.size) {
      val note = map.notes[i]
      if (note.beat > beat + BEAT_SCROLL_SPEED) break
      else if (note.beat >= beat) shownNotes.add(note)
      else lastValidIndex = i + 1
    }

    //Handle note hit events TODO
    /*
        if (lKey) {}
        if (lmKey) {}
        if (rmKey) {}
        if (rKey) {}
    */

    val worldWidth = game.viewport.worldWidth
    val worldHeight = game.viewport.worldHeight

    game.withRenderer(ShapeRenderer.ShapeType.Filled) {
      drawableArea(worldWidth, worldHeight)
      columns(worldWidth * NOTE_WIDTH_PRECENT, worldHeight, worldWidth)
      rhythmBar(worldWidth, worldHeight, beat)

      //Inactive keys
      keyStates.forEachIndexed { i, pressed ->
        if (pressed) return@forEachIndexed
        key(
          when (i) {
            0 -> LEFT_COLOR
            1 -> LEFT_MID_COLOR
            2 -> RIGHT_MID_COLOR
            3 -> RIGHT_COLOR
            else -> throw IllegalArgumentException("Unexpected key index $i")
          }, false, worldWidth, worldHeight, i
        )
      }

      //Notes
      shownNotes.forEach { note ->
        when (note.pos) {
          BeatMapNotePosition.LEFT_COLUMN -> note(LEFT_COLOR, worldWidth, worldHeight, 0, note.beat, beat)
          BeatMapNotePosition.LEFT_MID_COLUMN -> note(LEFT_MID_COLOR, worldWidth, worldHeight, 1, note.beat, beat)
          BeatMapNotePosition.RIGHT_MID_COLUMN -> note(RIGHT_MID_COLOR, worldWidth, worldHeight, 2, note.beat, beat)
          BeatMapNotePosition.RIGHT_COLUMN -> note(RIGHT_COLOR, worldWidth, worldHeight, 3, note.beat, beat)
        }
      }

      keyStates.forEachIndexed { i, pressed ->
        if (!pressed) return@forEachIndexed
        key(
          when (i) {
            0 -> LEFT_COLOR
            1 -> LEFT_MID_COLOR
            2 -> RIGHT_MID_COLOR
            3 -> RIGHT_COLOR
            else -> throw IllegalArgumentException("Unexpected key index $i")
          }, true, worldWidth, worldHeight, i
        )
      }

      fpsCounter(worldWidth, worldHeight)
      scoreCounter(worldWidth, worldHeight)
    }

    game.withBatch {
      it.smallFont.draw(this, "beats: ${beat.toInt()}", 0f, worldHeight * .9f)
      fpsCounter(fps, it.smallFont, worldWidth, worldHeight)
      scoreCounter(score, it.smallFont, worldWidth, worldHeight)
    }

    if (timeSinceStart > map.length || map.song.position > map.length || !map.song.isPlaying || input.isKeyPressed(Keys.Q)) {
      game.addScreen(EndScreen(game, BeatMapStatus.PASSED(score, BeatMapRank.S)))
      game.setScreen<EndScreen>()
      game.removeScreen<GameScreen>()
    }
  }

  override fun dispose() {
    map.dispose()
    super.dispose()
  }
}
