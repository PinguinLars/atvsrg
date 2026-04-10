package me.ashypinguin.atvsrg.maps

enum class BeatMapRank {
  SS, S, A, B, C, D, F;

  companion object {
    fun getFromAcc(acc: Float): BeatMapRank = when {
      acc >= .99f -> SS
      acc >= .95f -> S
      acc >= .9f -> A
      acc >= .8f -> B
      acc >= .7f -> C
      else -> D
    }
  }
}
