package me.ashypinguin.atvsrg.utils

import com.badlogic.gdx.graphics.Color

const val RHYTHM_BAR_OFFSET = .5f

const val NOTE_WIDTH_PRECENT = .15f
const val NOTE_HEIGHT_PRECENT = .05f

/**
 * The space between the walls and the first and last note.
 *
 * It is half of the remaining percent of 4 times [NOTE_WIDTH_PRECENT]
 */
const val NOTE_WALL_OFFSET_PERCENT = (1f - NOTE_WIDTH_PRECENT * 4) * .5f
const val NOTE_GROUND_OFFSET_PERCENT = NOTE_HEIGHT_PRECENT * .5f
const val KEY_UNPRESSED_DARKNESS = .5f
const val COLUMN_DARKNESS = .7f
const val NOTE_DARKNESS = .3f

const val BEAT_SCROLL_SPEED = 5

val LEFT_COLOR = Color.YELLOW!!
val LEFT_MID_COLOR = Color.ORANGE!!
val RIGHT_MID_COLOR = Color.RED!!
val RIGHT_COLOR = Color.PURPLE!!

const val MUSIC_VOLUME = .4f
const val FPS_WIDTH_PERCENT = .125f
const val FPS_HEIGHT_PERCENT = .075f
const val FPS_OFFSET_WALL_PERCENT = 1f - FPS_WIDTH_PERCENT
const val FPS_OFFSET_GROUND_PERCENT = 0f

