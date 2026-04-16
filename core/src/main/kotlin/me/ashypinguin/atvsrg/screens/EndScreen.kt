package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.LIME
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.components.clear
import me.ashypinguin.atvsrg.components.drawableArea
import me.ashypinguin.atvsrg.maps.BeatMapRank.*
import me.ashypinguin.atvsrg.maps.BeatMapStatus
import me.ashypinguin.atvsrg.utils.*

private val log = logger<EndScreen>()

class EndScreen(game: Atvsrg, val status: BeatMapStatus) : AbstractScreen(game) {
  override fun render(delta: Float) {
    clear()
    game.viewport.apply()
    game.batch.projectionMatrix = game.viewport.camera.combined
    game.renderer.projectionMatrix = game.viewport.camera.combined

    val worldWidth = game.viewport.worldWidth
    val worldHeight = game.viewport.worldHeight

    game.withRenderer(Filled) {
      drawableArea(worldWidth, worldHeight)

      color = UI_ELEMENT_BG_COLOR
      //song bar
      rect(0f, worldHeight * .9f, worldWidth, worldHeight * .1f)

      //score
      rect(worldWidth * .01f, worldHeight * .75f, worldWidth * .4f, worldHeight * .1f)

      //combo, acc, etc
      rect(worldWidth * .01f, worldHeight * .2f, worldWidth * .4f, worldHeight * .525f)

      //rank letter
      rect(worldWidth * .5f, worldHeight * .2f, worldWidth * .45f, worldHeight * .65f)

      //Gold SS
      when (status.rank) {
        SS -> {
          color = RANK_SS_COLOR

          val leftBorder = worldWidth * RANK_SS_LEFT_BORDER_PERCENT
          val rightBorder = worldWidth * .825f
          val actualLeftBorder = worldWidth * (RANK_SS_LEFT_BORDER_PERCENT - .125f)
          val shadowLeftBorder = worldWidth * (RANK_SS_LEFT_BORDER_PERCENT - .025f)

          //Drawing this first so I can override it and so the ss looks stacked
          val lowerSWidth = worldWidth * .125f - worldHeight * .05f
          rect(actualLeftBorder, worldHeight * .25f, lowerSWidth, worldHeight * .1f)
          rect(actualLeftBorder, worldHeight * .475f, lowerSWidth, worldHeight * .1f)
          rect(actualLeftBorder, worldHeight * .7f, lowerSWidth, worldHeight * .1f)
          arc(actualLeftBorder, worldHeight * .575f, worldHeight * .1f, 180f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          rect(actualLeftBorder - worldHeight * .1f, worldHeight * .575f, worldHeight * .1f, worldHeight * .125f)
          arc(actualLeftBorder, worldHeight * .7f, worldHeight * .1f, 90f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          arc(actualLeftBorder, worldHeight * .3f, worldHeight * .05f, 90f, 180f, ROUNDING_SEGMENTS)

          //Draw the shadow
          color = UI_ELEMENT_BG_COLOR
          arc(shadowLeftBorder, worldHeight * .575f, worldHeight * .1f, 180f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          rect(shadowLeftBorder - worldHeight * .1f, worldHeight * .575f, worldHeight * .1f, worldHeight * .125f)
          arc(shadowLeftBorder, worldHeight * .7f, worldHeight * .1f, 90f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          arc(shadowLeftBorder, worldHeight * .3f, worldHeight * .05f, 90f, 180f, ROUNDING_SEGMENTS)

          color = RANK_SS_COLOR
          rect(leftBorder, worldHeight * .25f, worldWidth * (.825f - RANK_SS_LEFT_BORDER_PERCENT), worldHeight * .1f)
          arc(rightBorder, worldHeight * .35f, worldHeight * .1f, 270f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          rect(rightBorder, worldHeight * .35f, worldHeight * .1f, worldHeight * .125f)
          arc(rightBorder, worldHeight * .475f, worldHeight * .1f, 0f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          rect(leftBorder, worldHeight * .475f, worldWidth * (.825f - RANK_SS_LEFT_BORDER_PERCENT), worldHeight * .1f)
          arc(leftBorder, worldHeight * .575f, worldHeight * .1f, 180f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          rect(leftBorder - worldHeight * .1f, worldHeight * .575f, worldHeight * .1f, worldHeight * .125f)
          arc(leftBorder, worldHeight * .7f, worldHeight * .1f, 90f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          rect(leftBorder, worldHeight * .7f, worldWidth * (.825f - RANK_SS_LEFT_BORDER_PERCENT), worldHeight * .1f)
          arc(rightBorder, worldHeight * .75f, worldHeight * .05f, 270f, 180f, ROUNDING_SEGMENTS)
          arc(leftBorder, worldHeight * .3f, worldHeight * .05f, 90f, 180f, ROUNDING_SEGMENTS)

          end()
          begin(Line) //fuck you LibGDX

          run { //I don't want to the vars to escape out of this scope :)
            val r = worldHeight * (.125f / 2f)
            val cx = leftBorder + r
            val cy = worldHeight * (.7f - .125f / 2f)
            val offset = r * 1.3333f
            curve(
              cx, cy + r,
              cx - offset, cy + offset,
              cx - offset, cy - offset,
              cx, cy - r,
              ROUNDING_SEGMENTS
            )
          }
          run { //I don't want to the vars to escape out of this scope :)
            val r = worldHeight * (.125f / 2f)
            val cx = rightBorder - r
            val cy = worldHeight * (.475f - .125f / 2f)
            val offset = r * 1.3333f
            curve(
              cx, cy + r,
              cx + offset, cy + offset,
              cx + offset, cy - offset,
              cx, cy - r,
              ROUNDING_SEGMENTS
            )
          }
          run { //I don't want to the vars to escape out of this scope :)
            val r = worldHeight * (.125f / 2f)
            val cx = actualLeftBorder + r
            val cy = worldHeight * (.7f - .125f / 2f)
            val offset = r * 1.3333f
            curve(
              cx, cy + r,
              cx - offset, cy + offset,
              cx - offset, cy - offset,
              cx, cy - r,
              ROUNDING_SEGMENTS
            )
          }
        }

        S -> {
          val leftBorder = worldWidth * (.5f + RANK_S_PADDING)
          val rightBorder = worldWidth * (.95f - RANK_S_PADDING)

          color = RANK_S_COLOR
          rect(leftBorder, worldHeight * .25f, worldWidth * (.825f - RANK_SS_LEFT_BORDER_PERCENT), worldHeight * .1f)
          arc(rightBorder, worldHeight * .35f, worldHeight * .1f, 270f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          rect(rightBorder, worldHeight * .35f, worldHeight * .1f, worldHeight * .125f)
          arc(rightBorder, worldHeight * .475f, worldHeight * .1f, 0f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          rect(leftBorder, worldHeight * .475f, worldWidth * (.825f - RANK_SS_LEFT_BORDER_PERCENT), worldHeight * .1f)
          arc(leftBorder, worldHeight * .575f, worldHeight * .1f, 180f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          rect(leftBorder - worldHeight * .1f, worldHeight * .575f, worldHeight * .1f, worldHeight * .125f)
          arc(leftBorder, worldHeight * .7f, worldHeight * .1f, 90f, CORNER_DEGREES, ROUNDING_SEGMENTS)
          rect(leftBorder, worldHeight * .7f, worldWidth * (.825f - RANK_SS_LEFT_BORDER_PERCENT), worldHeight * .1f)
          arc(rightBorder, worldHeight * .75f, worldHeight * .05f, 270f, 180f, ROUNDING_SEGMENTS)
          arc(leftBorder, worldHeight * .3f, worldHeight * .05f, 90f, 180f, ROUNDING_SEGMENTS)

          end()
          begin(Line) //fuck you LibGDX

          run { //I don't want to the vars to escape out of this scope :)
            val r = worldHeight * (.125f / 2f)
            val cx = leftBorder + r
            val cy = worldHeight * (.7f - .125f / 2f)
            val offset = r * 1.3333f
            curve(
              cx, cy + r,
              cx - offset, cy + offset,
              cx - offset, cy - offset,
              cx, cy - r,
              ROUNDING_SEGMENTS
            )
          }
          run { //I don't want to the vars to escape out of this scope :)
            val r = worldHeight * (.125f / 2f)
            val cx = rightBorder - r
            val cy = worldHeight * (.475f - .125f / 2f)
            val offset = r * 1.3333f
            curve(
              cx, cy + r,
              cx + offset, cy + offset,
              cx + offset, cy - offset,
              cx, cy - r,
              ROUNDING_SEGMENTS
            )
          }
        }

        A -> {
          val leftBorder = worldWidth * .6f
          val rightBorder = worldWidth * .85f
          val bottomBorder = worldHeight * .3f
          val topBorder = worldHeight * .75f

          color = RANK_A_COLOR

          triangle(
            leftBorder,bottomBorder,
            worldWidth * .65f, bottomBorder,
            worldWidth * .75f, topBorder,
          )
          triangle(
            leftBorder,bottomBorder,
            worldWidth * .75f, topBorder,
            worldWidth * .7f, topBorder,
          )
          triangle(
            rightBorder, bottomBorder,
            worldWidth * .8f, bottomBorder,
            worldWidth * .7f, topBorder,
          )
          triangle(
            rightBorder, bottomBorder,
            worldWidth * .7f, topBorder,
            worldWidth * .75f, topBorder,
          )
          rect(worldWidth * .675f, worldHeight * .45f, worldWidth * .1f, worldHeight * .075f)
        }

        B -> {}
        C -> {}
        D -> {}
        F -> {}
      }
    }

    //debug
    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      rank = when (rank) {
        SS -> S
        S -> A
        A -> B
        B -> C
        C -> D
        D -> F
        F -> SS
      }
      log.debug { "rank = $rank" }
    }
  }
}
