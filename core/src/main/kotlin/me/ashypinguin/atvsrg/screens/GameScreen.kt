package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import me.ashypinguin.atvsrg.*
import me.ashypinguin.atvsrg.components.fpsCounter
import me.ashypinguin.atvsrg.maps.BeatMap
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition
import me.ashypinguin.atvsrg.maps.BeatMapRank
import me.ashypinguin.atvsrg.maps.BeatMapStatus
import ktx.app.clearScreen as clear

private val log = logger<GameScreen>()
private const val NOTE_WIDTH_PRECENT = .15f
private const val NOTE_HEIGHT_PRECENT = .05f

/**
 * The space between the walls and the first and last note.
 *
 * It is half of the remaining percent of 4 times [NOTE_WIDTH_PRECENT]
 */
private const val NOTE_WALL_OFFSET_PERCENT = (1f - NOTE_WIDTH_PRECENT * 4) * .5f
private const val NOTE_GROUND_OFFSET_PERCENT = NOTE_HEIGHT_PRECENT * .5f
private const val KEY_UNPRESSED_DARKNESS = .5f
private const val COLUMN_DARKNESS = .7f
private const val NOTE_DARKNESS = .3f

private const val BEAT_SCROLL_SPEED = 5

private val LEFT_COLOR = Color.YELLOW
private val LEFT_MID_COLOR = Color.ORANGE
private val RIGHT_MID_COLOR = Color.RED
private val RIGHT_COLOR = Color.PURPLE

private const val MUSIC_VOLUME = .4f

class GameScreen(game: Atvsrg, val map: BeatMap) : AbstractScreen(game) {
  /** The time since that the fps last got updated in seconds */
  private var timeSinceLastFpsUpdate = 1f
  private var fps = 0
  private var lastValidIndex = 0

  /**
   * Handy internal variable to get beats
   */
  private val beat get() = timeSinceStart * (map.bpm / 60)
  private var timeSinceStart = -2.5f

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

    game.withRenderer(ShapeRenderer.ShapeType.Filled) {
      //Make drawable screen black
      color = Color.BLACK
      rect(0f, 0f, it.viewport.worldWidth, it.viewport.worldHeight)

      //Columns
      color = LEFT_COLOR.dull(COLUMN_DARKNESS)
      rect(
        it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
        0f,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight
      )
      color = LEFT_MID_COLOR.dull(COLUMN_DARKNESS)
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT + NOTE_WALL_OFFSET_PERCENT),
        0f,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight
      )
      color = RIGHT_MID_COLOR.dull(COLUMN_DARKNESS)
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 2 + NOTE_WALL_OFFSET_PERCENT),
        0f,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight
      )
      color = RIGHT_COLOR.dull(COLUMN_DARKNESS)
      rect(
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 3 + NOTE_WALL_OFFSET_PERCENT),
        0f,
        it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
        it.viewport.worldHeight
      )

      //Keys
      val lKey = input.isKeyPressed(Keys.D)
      val lmKey = input.isKeyPressed(Keys.F)
      val rmKey = input.isKeyPressed(Keys.J)
      val rKey = input.isKeyPressed(Keys.K)

      //Inactive keys
      if (!lKey) {
        color = LEFT_COLOR.dull(KEY_UNPRESSED_DARKNESS)
        rect(
          it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
          it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
          it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
          it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
        )
      }
      if (!lmKey) {
        color = LEFT_MID_COLOR.dull(KEY_UNPRESSED_DARKNESS)
        rect(
          it.viewport.worldWidth * (NOTE_WIDTH_PRECENT + NOTE_WALL_OFFSET_PERCENT),
          it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
          it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
          it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
        )
      }
      if (!rmKey) {
        color = RIGHT_MID_COLOR.dull(KEY_UNPRESSED_DARKNESS)
        rect(
          it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 2 + NOTE_WALL_OFFSET_PERCENT),
          it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
          it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
          it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
        )
      }
      if (!rKey) {
        color = RIGHT_COLOR.dull(KEY_UNPRESSED_DARKNESS)
        rect(
          it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 3 + NOTE_WALL_OFFSET_PERCENT),
          it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
          it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
          it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
        )
      }

      //rhythm bar
      color = Color.LIGHT_GRAY
      rect(
        it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
        // the .5f is an offset to not make the notes feel stuck to the bar
        it.viewport.worldHeight * (1f - ((beat + .5f) % BEAT_SCROLL_SPEED / BEAT_SCROLL_SPEED)),
        it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 4f),
        it.viewport.worldHeight * .01f
      )

      //Notes
      with(map) {
        for (i in lastValidIndex..<notes.size) {
          if (notes[i].beat > beat + BEAT_SCROLL_SPEED) break
          if (notes[i].beat >= beat) when (notes[i].pos) {
            BeatMapNotePosition.LEFT_COLUMN -> {
              color = LEFT_COLOR.dull(NOTE_DARKNESS)
              rect(
                it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
                it.viewport.worldHeight * ((notes[i].beat - beat) / BEAT_SCROLL_SPEED),
                it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
                it.viewport.worldWidth * NOTE_HEIGHT_PRECENT
              )
            }

            BeatMapNotePosition.LEFT_MID_COLUMN -> {
              color = LEFT_MID_COLOR.dull(NOTE_DARKNESS)
              rect(
                it.viewport.worldWidth * (NOTE_WALL_OFFSET_PERCENT + NOTE_WIDTH_PRECENT),
                it.viewport.worldHeight * ((notes[i].beat - beat) / BEAT_SCROLL_SPEED),
                it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
                it.viewport.worldWidth * NOTE_HEIGHT_PRECENT
              )
            }

            BeatMapNotePosition.RIGHT_MID_COLUMN -> {
              color = RIGHT_MID_COLOR.dull(NOTE_DARKNESS)
              rect(
                it.viewport.worldWidth * (NOTE_WALL_OFFSET_PERCENT + NOTE_WIDTH_PRECENT * 2),
                it.viewport.worldHeight * ((notes[i].beat - beat) / BEAT_SCROLL_SPEED),
                it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
                it.viewport.worldWidth * NOTE_HEIGHT_PRECENT
              )
            }

            BeatMapNotePosition.RIGHT_COLUMN -> {
              color = RIGHT_COLOR.dull(NOTE_DARKNESS)
              rect(
                it.viewport.worldWidth * (NOTE_WALL_OFFSET_PERCENT + NOTE_WIDTH_PRECENT * 3),
                it.viewport.worldHeight * ((notes[i].beat - beat) / BEAT_SCROLL_SPEED),
                it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
                it.viewport.worldWidth * NOTE_HEIGHT_PRECENT
              )
            }
          } else lastValidIndex = i + 1
        }
      }

      if (lKey) {
        color = LEFT_COLOR
        rect(
          it.viewport.worldWidth * NOTE_WALL_OFFSET_PERCENT,
          it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
          it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
          it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
        )
      }
      if (lmKey) {
        color = LEFT_MID_COLOR
        rect(
          it.viewport.worldWidth * (NOTE_WIDTH_PRECENT + NOTE_WALL_OFFSET_PERCENT),
          it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
          it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
          it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
        )
      }
      if (rmKey) {
        color = RIGHT_MID_COLOR
        rect(
          it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 2 + NOTE_WALL_OFFSET_PERCENT),
          it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
          it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
          it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
        )
      }
      if (rKey) {
        color = RIGHT_COLOR
        rect(
          it.viewport.worldWidth * (NOTE_WIDTH_PRECENT * 3 + NOTE_WALL_OFFSET_PERCENT),
          it.viewport.worldHeight * NOTE_GROUND_OFFSET_PERCENT,
          it.viewport.worldWidth * NOTE_WIDTH_PRECENT,
          it.viewport.worldHeight * NOTE_HEIGHT_PRECENT
        )
      }

      fpsCounter(it.viewport)
    }

    game.withBatch {
      it.fpsFont.draw(this, "beats: ${beat.toInt()}", 0f, it.viewport.worldHeight * .9f)
      fpsCounter(fps, it.viewport, it.fpsFont)
    }

    if (timeSinceStart > map.length ||
      map.song.position > map.length ||
      !map.song.isPlaying ||
      input.isKeyPressed(Keys.Q)
    ) {
      game.addScreen(EndScreen(game, BeatMapStatus.PASSED(727_272, BeatMapRank.S)))
      game.setScreen<EndScreen>()
      game.removeScreen<GameScreen>()
    }
  }

  override fun dispose() {
    map.dispose()
    super.dispose()
  }
}
