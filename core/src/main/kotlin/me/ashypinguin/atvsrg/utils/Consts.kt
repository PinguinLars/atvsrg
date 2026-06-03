package me.ashypinguin.atvsrg.utils

const val RHYTHM_BAR_OFFSET = .5f

const val NOTE_WIDTH_PERCENT = .15f
const val NOTE_HEIGHT_PERCENT = .05f

/**
 * The space between the walls and the first and last note.
 *
 * It is half of the remaining percent of 4 times [NOTE_WIDTH_PERCENT]
 */
const val NOTE_WALL_OFFSET_PERCENT = (1f - NOTE_WIDTH_PERCENT * 4) * .5f
const val NOTE_GROUND_OFFSET_PERCENT = NOTE_HEIGHT_PERCENT * .5f
const val KEY_UNPRESSED_DARKNESS = .5f
const val COLUMN_DARKNESS = .7f
const val NOTE_DARKNESS = .3f

const val BEAT_SCROLL_SPEED = 5

//something e-3f is milliseconds :3
//If the hit was earlier than 188ms then it doesn't count (later it is of course a miss)
const val MISS_MAX_OFFSET_SEC = 188e-3f
const val MEH_MAX_OFFSET_SEC = 151e-3f
const val OK_MAX_OFFSET_SEC = 127e-3f
const val GOOD_MAX_OFFSET_SEC = 97e-3f
const val GREAT_MAX_OFFSET_SEC = 64e-3f
const val PERFECT_MAX_OFFSET_SEC = 16e-3f

const val MUSIC_VOLUME = .3f
const val FPS_WIDTH_PERCENT = .125f
const val FPS_HEIGHT_PERCENT = .075f
const val FPS_OFFSET_WALL_PERCENT = 1f - FPS_WIDTH_PERCENT
const val FPS_OFFSET_GROUND_PERCENT = 0f

const val ROUNDING_SEGMENTS = 32
const val CORNER_DEGREES = 90f

const val RANK_SS_LEFT_BORDER_PERCENT = .7f
const val RANK_S_LEFT_BORDER_PERCENT = .7f
const val RANK_S_PADDING = (.45f - .125f) * .5f

const val GRAY_BG_TONE = .3f

const val JUDGEMENT_SHOW_TIME = .4f
const val DEBOUNCE_TIME = .1f
