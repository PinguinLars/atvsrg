package me.ashypinguin.atvsrg.maps

sealed interface BeatMapStatus {
  val rank: BeatMapRank

  data class PASSED(val score: Int, override val rank: BeatMapRank) : BeatMapStatus
  data object FAILED : BeatMapStatus {
    override val rank: BeatMapRank = BeatMapRank.F
  }
}
