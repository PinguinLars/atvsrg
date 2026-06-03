package me.ashypinguin.atvsrg.maps

sealed interface BeatMapStatus {
  val rank: BeatMapRank
  val score: Int
  val highestCombo: Int
  val judgementAmount: MutableMap<NoteJudgement, Int>

  data class Passed(
    override val score: Int,
    override val rank: BeatMapRank,
    override val highestCombo: Int,
    override val judgementAmount: MutableMap<NoteJudgement, Int>
  ) : BeatMapStatus

  data class Failed(
    override val score: Int,
    override val highestCombo: Int,
    override val judgementAmount: MutableMap<NoteJudgement, Int>
  ) : BeatMapStatus {
    override val rank: BeatMapRank = BeatMapRank.F
  }
}
