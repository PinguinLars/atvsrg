package me.ashypinguin.atvsrg.maps

sealed interface BeatMapStatus {
  val rank: BeatMapRank

  data class Passed(val score: Int, override val rank: BeatMapRank) : BeatMapStatus
  data object Failed : BeatMapStatus {
    override val rank: BeatMapRank = BeatMapRank.F
  }
}
