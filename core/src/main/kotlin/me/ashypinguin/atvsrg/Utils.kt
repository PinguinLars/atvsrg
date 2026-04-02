package me.ashypinguin.atvsrg

import com.badlogic.gdx.graphics.g2d.SpriteBatch

/**
 * Start a [SpriteBatch] render with the [game][AtVsrGame] instance.
 *
 * @receiver the main [game][AtVsrGame] instance
 * @param scope Function block that gets called between [SpriteBatch.begin] and [SpriteBatch.end]
 * @see ktx.graphics.use
 */
fun AtVsrGame.withBatch(scope: SpriteBatch.(AtVsrGame) -> Unit) {
  batch.begin()
  batch.scope(this)
  batch.end()
}
