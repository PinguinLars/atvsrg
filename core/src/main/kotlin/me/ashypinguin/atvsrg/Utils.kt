package me.ashypinguin.atvsrg

import com.badlogic.gdx.graphics.g2d.SpriteBatch

fun AtVsrGame.withBatch(scope: SpriteBatch.(AtVsrGame) -> Unit) {
  batch.begin()
  batch.scope(this)
  batch.end()
}
