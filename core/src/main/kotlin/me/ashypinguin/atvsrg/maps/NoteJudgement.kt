package me.ashypinguin.atvsrg.maps

import com.badlogic.gdx.graphics.Color
import me.ashypinguin.atvsrg.utils.*

enum class NoteJudgement(val maxOffsetSec: Float) {
  PERFECT(PERFECT_MAX_OFFSET_SEC),
  GREAT(GREAT_MAX_OFFSET_SEC),
  GOOD(GOOD_MAX_OFFSET_SEC),
  OK(OK_MAX_OFFSET_SEC),
  MEH(MEH_MAX_OFFSET_SEC),
  MISS(MISS_MAX_OFFSET_SEC);

  fun toColor(): Color = when (this) {
    PERFECT -> Color.BLUE
    GREAT -> RANK_S_COLOR
    GOOD -> RANK_A_COLOR
    OK -> RANK_B_COLOR
    MEH -> RANK_C_COLOR
    MISS -> RANK_D_COLOR
  }

  companion object {
    /**
     * Get the judgement from the offset (in seconds)
     * @return Returns null when the offset is outside [the max][MISS_MAX_OFFSET_SEC]
     */
    @Suppress("GrazieInspection", "GrazieStyle") // We use judgement and not judgment because osu! does.
    fun Float.toJudgement(): NoteJudgement? = when {
      this < 0f -> throw IllegalStateException("Receiver most be a positive value. Got $this")
      this <= PERFECT.maxOffsetSec -> PERFECT
      this <= GREAT.maxOffsetSec -> GREAT
      this <= GOOD.maxOffsetSec -> GOOD
      this <= OK.maxOffsetSec -> OK
      this <= MEH.maxOffsetSec -> MEH
      this <= MISS.maxOffsetSec -> MISS
      else -> null
    }
  }
}
