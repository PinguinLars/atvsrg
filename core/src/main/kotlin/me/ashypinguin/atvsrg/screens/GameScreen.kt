package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import ktx.assets.toInternalFile
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.components.*
import me.ashypinguin.atvsrg.maps.*
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.*
import me.ashypinguin.atvsrg.maps.NoteJudgement.*
import me.ashypinguin.atvsrg.maps.NoteJudgement.Companion.toJudgement
import me.ashypinguin.atvsrg.utils.*
import kotlin.math.abs
import ktx.app.clearScreen as clear

private val log = logger<GameScreen>()

class GameScreen(game: Atvsrg, val map: BeatMap) : AbstractScreen(game) {
  private lateinit var judgmentFont: BitmapFont

  private val bpm = map.bpm.toFloat()

  @Suppress("unused") //TODO: implement this
  private val noteAmount = map.notes.size

  /** The time since that the fps last got updated in seconds */
  private var timeSinceLastFpsUpdate = 1f
  private var fps = 0
  private var lastValidIndex = 0

  @Suppress("unused") //TODO: implement this
  private var acc = 1f
  private var score = 0

  /** Handy internal variable to get beats */
  private val beat get() = timeSinceStart * (bpm / 60f)
  private var timeSinceStart = -2.5f

  private var shownNotes = mutableListOf<BeatMapNote>()

  private var worldWidth = game.viewport.worldWidth
  private var worldHeight = game.viewport.worldHeight

  private var noteWallOffset = worldWidth * NOTE_WALL_OFFSET_PERCENT
  private var keyGroundOffset = worldHeight * NOTE_GROUND_OFFSET_PERCENT
  private var rhythmBarHeight = worldHeight * .01f
  private var noteWidth = worldWidth * NOTE_WIDTH_PERCENT
  private var noteHeight = worldHeight * NOTE_HEIGHT_PERCENT
  private var fpsX = worldWidth * FPS_OFFSET_WALL_PERCENT
  private var fpsY = worldHeight * FPS_OFFSET_GROUND_PERCENT
  private var fpsWidth = worldWidth * FPS_WIDTH_PERCENT
  private var fpsHeight = worldHeight * FPS_HEIGHT_PERCENT
  private var lastJudgement: NoteJudgement? = null
  private var lastJudgementTime = 0f
  private var highestCombo = 0
  private var judgmentAmount = mutableMapOf(
    Pair(PERFECT, 0),
    Pair(GREAT, 0),
    Pair(GOOD, 0),
    Pair(MEH, 0),
    Pair(OK, 0),
    Pair(MISS, 0)
  )

  private var combo = 0
    set(value) { //this makes it easier to update the combo and not forgot to up the highestCombo
      field = value
      if (field >= highestCombo) highestCombo = field
    }

  @Suppress("BooleanLiteralArgument")//because apprently this is an issue
  private var hitRegistered = KeyStatesTime(
    0f, 0f, 0f, 0f,
    false, false, false, false
  )

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

    //init font
    log.info { "Loading font: Roboto-Regular.ttf with size 24" }
    val generator = FreeTypeFontGenerator("Roboto-Regular.ttf".toInternalFile())
    val parameter = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
      size = 24
      borderWidth = 1f
      color = Color.WHITE
    }
    val font = generator.generateFont(parameter)
    generator.dispose()
    log.debug { "Font loaded" }
    // Roboto-Regular.ttf should exist
    judgmentFont = font!!
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

    val keyStates = KeyStates(
      input.isKeyJustPressed(Keys.D),
      input.isKeyJustPressed(Keys.F),
      input.isKeyJustPressed(Keys.J),
      input.isKeyJustPressed(Keys.K)
    )

    val cosmeticKeyStates = KeyStates(
      input.isKeyPressed(Keys.D),
      input.isKeyPressed(Keys.F),
      input.isKeyPressed(Keys.J),
      input.isKeyPressed(Keys.K)
    )

    lastJudgementTime -= delta
    hitRegistered.leftTime -= delta
    hitRegistered.leftMidTime -= delta
    hitRegistered.rightMidTime -= delta
    hitRegistered.rightTime -= delta
    if (lastJudgement != null && lastJudgementTime < 0f) lastJudgement = null
    if (hitRegistered.left && hitRegistered.leftTime < 0f) hitRegistered.left = false
    if (hitRegistered.leftMid && hitRegistered.leftMidTime < 0f) hitRegistered.leftMid = false
    if (hitRegistered.rightMid && hitRegistered.rightMidTime < 0f) hitRegistered.rightMid = false
    if (hitRegistered.right && hitRegistered.rightTime < 0f) hitRegistered.right = false

    // Get all notes
    shownNotes.clear()
    for (i in lastValidIndex..<map.notes.size) {
      val note = map.notes[i]
      if (note.hit) continue
      if (beat <= note.beat + MISS_MAX_OFFSET_SEC * bpm / 60 && beat >= note.beat - MISS_MAX_OFFSET_SEC * bpm / 60) {
        if ((note.pos == LEFT_COLUMN && keyStates.left && !hitRegistered.left) ||
          (note.pos == LEFT_MID_COLUMN && keyStates.leftMid && !hitRegistered.leftMid) ||
          (note.pos == RIGHT_MID_COLUMN && keyStates.rightMid && !hitRegistered.rightMid) ||
          (note.pos == RIGHT_COLUMN && keyStates.right && !hitRegistered.right)
        ) {
          log.debug { "note hit window for $note, delta beat: ${note.beat - beat}" }
          //this takes the delta
          val judgement = (abs(note.beat - beat) / bpm * 60).toJudgement()
          lastJudgementTime = JUDGEMENT_SHOW_TIME
          lastJudgement = judgement
          if (judgement != null && judgement != MISS) combo++
          else if (judgement == MISS) combo = 0

          //SAFETY: all the values have been initialized
          if (judgement != null) judgmentAmount[judgement] = judgmentAmount[judgement]!! + 1

          note.hit = true

          @Suppress("KotlinConstantConditions", "RedundantSuppression")
          when (note.pos) {
            LEFT_COLUMN if keyStates.left -> {
              hitRegistered.left = true
              hitRegistered.leftTime = DEBOUNCE_TIME
            }

            LEFT_MID_COLUMN if keyStates.leftMid -> {
              hitRegistered.leftMid = true
              hitRegistered.leftMidTime = DEBOUNCE_TIME
            }

            RIGHT_MID_COLUMN if keyStates.rightMid -> {
              hitRegistered.rightMid = true
              hitRegistered.rightMidTime = DEBOUNCE_TIME
            }

            RIGHT_COLUMN if keyStates.right -> {
              hitRegistered.right = true
              hitRegistered.rightTime = DEBOUNCE_TIME
            }

            else -> throw IllegalStateException()
          }
        }
      }
      if (note.beat > beat + BEAT_SCROLL_SPEED) break
      else if (note.beat >= beat - 1) {
        if (!note.hit) shownNotes.add(note)
      } else {
        lastValidIndex = i + 1
        if (!note.hit) {
          lastJudgementTime = JUDGEMENT_SHOW_TIME
          lastJudgement = MISS
        }
      }
    }

    game.withRenderer(ShapeRenderer.ShapeType.Filled) {
      drawableArea(worldWidth, worldHeight)
      columns(noteWidth, worldHeight, noteWallOffset)

      //Inactive keys
      cosmeticKeyStates.forEachIndexed { i, pressed ->
        if (pressed) return@forEachIndexed
        key(i.offsetToUnpressedColor(), i, noteWallOffset, noteWidth, noteHeight)
      }

      //Notes
      shownNotes.forEach { note ->
        when (note.pos) {
          LEFT_COLUMN -> note(
            LEFT_NOTE_COLOR,
            0,
            noteWallOffset,
            worldHeight * ((note.beat - beat) / BEAT_SCROLL_SPEED),
            noteWidth,
            noteHeight,
          )

          LEFT_MID_COLUMN -> note(
            LEFT_MID_NOTE_COLOR,
            1,
            noteWallOffset,
            worldHeight * ((note.beat - beat) / BEAT_SCROLL_SPEED),
            noteWidth,
            noteHeight,
          )

          RIGHT_MID_COLUMN -> note(
            RIGHT_MID_NOTE_COLOR,
            2,
            noteWallOffset,
            worldHeight * ((note.beat - beat) / BEAT_SCROLL_SPEED),
            noteWidth,
            noteHeight,
          )

          RIGHT_COLUMN -> note(
            RIGHT_NOTE_COLOR,
            3,
            noteWallOffset,
            worldHeight * ((note.beat - beat) / BEAT_SCROLL_SPEED),
            noteWidth,
            noteHeight,
          )
        }
      }

      rhythmBar(
        noteWallOffset,
        rhythmBarHeight,
        noteWidth * 4f,
        worldHeight * (1f - ((beat + RHYTHM_BAR_OFFSET) % BEAT_SCROLL_SPEED / BEAT_SCROLL_SPEED))
      )

      cosmeticKeyStates.forEachIndexed { i, pressed ->
        if (!pressed) return@forEachIndexed
        key(i.offsetToColor(), i, noteWallOffset, noteWidth, noteHeight)
      }

      fpsCounter(fpsX, fpsY, fpsWidth, fpsHeight)
      scoreCounter(worldWidth, worldHeight)
    }

    game.withBatch {
      fpsCounter(fps, it.smallFont, fpsX, fpsY + fpsHeight * .75f, fpsWidth)
      it.smallFont.draw(this, "combo: $combo", 0f, worldHeight * .9f)
      scoreCounter(score, it.smallFont, worldWidth, worldHeight)

      if (lastJudgement != null) {
        judgmentFont.color = lastJudgement!!.toColor()
        judgmentFont.draw(
          this,
          lastJudgement!!.toString(),
          worldWidth * (NOTE_WALL_OFFSET_PERCENT + NOTE_WIDTH_PERCENT * .5f),
          worldHeight * .75f,
          worldWidth * (NOTE_WIDTH_PERCENT * 3),
          Align.center,
          false
        )
      }
    }

    if (timeSinceStart > map.length || map.song.position > map.length || !map.song.isPlaying || input.isKeyPressed(
        Keys.Q
      )
    ) {
      map.song.stop()
      print(judgmentAmount)
      game.addScreen(EndScreen(game, BeatMapStatus.Passed(score, BeatMapRank.SS, highestCombo, judgmentAmount)))
      game.setScreen<EndScreen>()
      game.removeScreen<GameScreen>()
    }
  }

  override fun dispose() {
    map.dispose()
    super.dispose()
  }

  //recalculate pseudo consts
  override fun resize(width: Int, height: Int) {
    worldWidth = game.viewport.worldWidth
    worldHeight = game.viewport.worldHeight
    noteWallOffset = worldWidth * NOTE_WALL_OFFSET_PERCENT
    rhythmBarHeight = worldHeight * .01f
    noteWidth = worldWidth * NOTE_WIDTH_PERCENT
    noteHeight = worldHeight * NOTE_HEIGHT_PERCENT
    keyGroundOffset = worldHeight * NOTE_GROUND_OFFSET_PERCENT
    fpsX = worldWidth * FPS_OFFSET_WALL_PERCENT
    fpsY = worldHeight * FPS_OFFSET_GROUND_PERCENT
    fpsWidth = worldWidth * FPS_WIDTH_PERCENT
    fpsHeight = worldHeight * FPS_HEIGHT_PERCENT

    super.resize(width, height)
  }
}
