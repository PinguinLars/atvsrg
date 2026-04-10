package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.GRAY_BG_TONE
import me.ashypinguin.atvsrg.maps.BeatMapStatus
import me.ashypinguin.atvsrg.withRenderer
import ktx.app.clearScreen as clear

class EndScreen(game: Atvsrg, val status: BeatMapStatus) : AbstractScreen(game) {
  override fun render(delta: Float) {
    clear(GRAY_BG_TONE, GRAY_BG_TONE, GRAY_BG_TONE)
    game.viewport.apply()
    game.batch.projectionMatrix = game.viewport.camera.combined
    game.renderer.projectionMatrix = game.viewport.camera.combined

    game.withRenderer(Filled) {
      //Make drawable screen black
      color = Color.BLACK
      rect(0f, 0f, it.viewport.worldWidth, it.viewport.worldHeight)
    }
  }
}
