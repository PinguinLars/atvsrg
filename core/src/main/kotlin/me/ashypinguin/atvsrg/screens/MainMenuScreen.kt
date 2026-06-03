package me.ashypinguin.atvsrg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.badlogic.gdx.utils.Align
import me.ashypinguin.atvsrg.Atvsrg
import me.ashypinguin.atvsrg.components.clear
import me.ashypinguin.atvsrg.components.drawableArea
import me.ashypinguin.atvsrg.maps.BeatMap
import me.ashypinguin.atvsrg.maps.BeatMapNotePosition.*
import me.ashypinguin.atvsrg.utils.*

private val log = logger<GameScreen>()

class MainMenuScreen(game: Atvsrg) : AbstractScreen(game) {
  override fun render(delta: Float) {
    clear()
    game.viewport.apply()
    game.renderer.projectionMatrix = game.viewport.camera.combined
    game.batch.projectionMatrix = game.viewport.camera.combined

    val worldWidth = game.viewport.worldWidth
    val worldHeight = game.viewport.worldHeight

    val x = Gdx.input.x
    val y = Gdx.input.y

    game.withRenderer(Filled) {
      drawableArea(it.viewport.worldWidth, it.viewport.worldHeight)

      color = UI_ELEMENT_BG_COLOR
      rect(worldWidth * .35f, worldHeight * .2f, worldWidth * .3f, worldHeight * .1f)
      rect(worldWidth * .35f, worldHeight * .4f, worldWidth * .3f, worldHeight * .1f)
      rect(worldWidth * .35f, worldHeight * .6f, worldWidth * .3f, worldHeight * .1f)
    }

    game.withBatch {
      it.bigFont.draw(
        this, it.i18n["welcome"], 0f, worldHeight * .9f, worldWidth, Align.center, false
      )

      it.bigFont.draw(
        this, it.i18n["play"], 0f, worldHeight * .675f, worldWidth, Align.center, false
      )

      it.bigFont.draw(
        this, it.i18n["tutorial"], 0f, worldHeight * .475f, worldWidth, Align.center, false
      )

      it.bigFont.draw(
        this, it.i18n["settings"], 0f, worldHeight * .275f, worldWidth, Align.center, false
      )
    }

    if (Gdx.input.isTouched) {
      val x = Gdx.input.x
      val y = Gdx.input.y
      log.debug { "Mouse: x=$x, y=$y; World: height=$worldHeight, width=$worldWidth" }

      val notes = listOf(
        leftNote(1),
        leftMidNote(2),
        rightMidNote(3),
        rightNote(4),
        rightNote(5),
        *doubleNote(6, LEFT_COLUMN, LEFT_MID_COLUMN),
        *slamNotes(7),
        *slamNotes(8),
        *slamNotes(9),
        leftNote(10),
        leftMidNote(11),
        rightMidNote(12),
        rightNote(13),
        rightMidNote(14),
        leftMidNote(15),
        leftNote(16),
        *doubleNote(17, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(18, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(19, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(20, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(21, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(22, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(23, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(24, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(25, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(26, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(27, LEFT_COLUMN, RIGHT_COLUMN),

        *tripleNote(29, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, LEFT_COLUMN),

        *tripleNote(31, LEFT_COLUMN, LEFT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(33, LEFT_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(35, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(37, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, LEFT_COLUMN),

        *tripleNote(39, LEFT_COLUMN, LEFT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(41, LEFT_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(43, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *rippleNotes(45),
        *rippleNotes(51),
        *rippleNotes(57),
        leftNote(63),
        *doubleNote(64, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(65, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(66, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(67, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(68, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(69, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(70, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(71, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(72, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(73, LEFT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(74, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(75, LEFT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(76, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(77, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *slamNotes(78),
        *doubleNote(79, LEFT_COLUMN, RIGHT_COLUMN),
        *slamNotes(80),
        *doubleNote(81, LEFT_COLUMN, LEFT_MID_COLUMN),
        *slamNotes(82),
        *doubleNote(83, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *slamNotes(84),
        *doubleNote(85, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(86, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *slamNotes(87),
        rightMidNote(88),
        rightMidNote(89),
        rightMidNote(90),
        rightMidNote(91),
        rightMidNote(92),
        rightMidNote(93),
        rightMidNote(94),
        leftNote(94),
        rightMidNote(95),
        rightMidNote(96),
        rightMidNote(97),
        rightMidNote(98),
        rightMidNote(99),
        rightMidNote(100),
        rightMidNote(101),
        leftMidNote(101),
        rightMidNote(102),
        rightMidNote(103),
        rightMidNote(104),
        rightMidNote(105),
        rightMidNote(106),
        rightMidNote(107),
        rightMidNote(108),

        rightMidNote(111),

        *doubleNote(115, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(116, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(117, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(118, LEFT_MID_COLUMN, LEFT_COLUMN),
        *doubleNote(119, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(120, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(121, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(122, LEFT_MID_COLUMN, LEFT_COLUMN),
        *doubleNote(123, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(124, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(125, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(126, LEFT_MID_COLUMN, LEFT_COLUMN),
        *doubleNote(127, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(128, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(129, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(130, LEFT_MID_COLUMN, LEFT_COLUMN),
        *rippleNotes(131),
        *rippleNotes(137),

        leftNote(150),
        rightNote(151),
        *slamNotes(155),

        leftNote(161),
        leftMidNote(162),
        rightMidNote(163),
        rightNote(164),
        rightNote(165),
        *doubleNote(166, LEFT_COLUMN, LEFT_MID_COLUMN),
        *slamNotes(167),
        *slamNotes(168),
        *slamNotes(169),
        leftNote(170),
        leftMidNote(171),
        rightMidNote(172),
        rightNote(173),
        rightMidNote(174),
        leftMidNote(175),
        leftNote(176),
        *doubleNote(177, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(178, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(179, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(180, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(181, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(182, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(183, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(184, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(185, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(186, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(187, LEFT_COLUMN, RIGHT_COLUMN),

        *tripleNote(189, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, LEFT_COLUMN),

        *tripleNote(191, LEFT_COLUMN, LEFT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(193, LEFT_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(195, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(197, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, LEFT_COLUMN),

        *tripleNote(199, LEFT_COLUMN, LEFT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(201, LEFT_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(203, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *rippleNotes(205),
        *rippleNotes(211),
        *rippleNotes(217),
        leftNote(223),
        *doubleNote(224, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(225, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(226, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(227, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(228, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(229, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(230, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(231, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(232, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(233, LEFT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(234, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(235, LEFT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(236, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(237, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *slamNotes(238),
        *doubleNote(239, LEFT_COLUMN, RIGHT_COLUMN),
        *slamNotes(240),
        *doubleNote(241, LEFT_COLUMN, LEFT_MID_COLUMN),
        *slamNotes(242),
        *doubleNote(243, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *slamNotes(244),
        *doubleNote(245, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(246, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *slamNotes(247),
        rightMidNote(248),
        rightMidNote(249),
        rightMidNote(250),
        rightMidNote(251),
        rightMidNote(252),
        rightMidNote(253),
        rightMidNote(254),
        leftNote(254),
        rightMidNote(255),
        rightMidNote(256),
        rightMidNote(257),
        rightMidNote(258),
        rightMidNote(259),
        rightMidNote(260),
        rightMidNote(261),
        leftMidNote(261),
        rightMidNote(262),
        rightMidNote(263),
        rightMidNote(264),
        rightMidNote(265),
        rightMidNote(266),
        rightMidNote(267),
        rightMidNote(268),

        rightMidNote(271),

        *doubleNote(275, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(276, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(277, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(278, LEFT_MID_COLUMN, LEFT_COLUMN),
        *doubleNote(279, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(280, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(281, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(282, LEFT_MID_COLUMN, LEFT_COLUMN),
        *doubleNote(283, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(284, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(285, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(286, LEFT_MID_COLUMN, LEFT_COLUMN),
        *doubleNote(287, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(288, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(289, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(290, LEFT_MID_COLUMN, LEFT_COLUMN),
        *rippleNotes(291),
        *rippleNotes(297),

        leftNote(310),
        rightNote(311),
        *slamNotes(315),

        leftNote(321),
        leftMidNote(322),
        rightMidNote(323),
        rightNote(324),
        rightNote(325),
        *doubleNote(326, LEFT_COLUMN, LEFT_MID_COLUMN),
        *slamNotes(327),
        *slamNotes(328),
        *slamNotes(329),
        leftNote(330),
        leftMidNote(331),
        rightMidNote(332),
        rightNote(333),
        rightMidNote(334),
        leftMidNote(335),
        leftNote(336),
        *doubleNote(337, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(338, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(339, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(340, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(341, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(342, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(343, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(344, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(345, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(346, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(347, LEFT_COLUMN, RIGHT_COLUMN),

        *tripleNote(349, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, LEFT_COLUMN),

        *tripleNote(351, LEFT_COLUMN, LEFT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(353, LEFT_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(355, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(357, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, LEFT_COLUMN),

        *tripleNote(359, LEFT_COLUMN, LEFT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(361, LEFT_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *tripleNote(363, LEFT_MID_COLUMN, RIGHT_MID_COLUMN, RIGHT_COLUMN),

        *rippleNotes(365),
        *rippleNotes(371),
        *rippleNotes(377),
        leftNote(383),
        *doubleNote(384, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(385, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(386, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(387, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(388, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(389, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(390, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(391, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(392, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(393, LEFT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(394, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(395, LEFT_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(396, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(397, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *slamNotes(398),
        *doubleNote(399, LEFT_COLUMN, RIGHT_COLUMN),
        *slamNotes(400),
        *doubleNote(401, LEFT_COLUMN, LEFT_MID_COLUMN),
        *slamNotes(402),
        *doubleNote(403, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *slamNotes(404),
        *doubleNote(405, LEFT_COLUMN, LEFT_MID_COLUMN),
        *doubleNote(406, RIGHT_COLUMN, RIGHT_MID_COLUMN),
        *slamNotes(407),
        rightMidNote(408),
        rightMidNote(409),
        rightMidNote(410),
        rightMidNote(411),
        rightMidNote(412),
        rightMidNote(413),
        rightMidNote(414),
        leftNote(414),
        rightMidNote(415),
        rightMidNote(416),
        rightMidNote(417),
        rightMidNote(418),
        rightMidNote(419),
        rightMidNote(420),
        rightMidNote(421),
        leftMidNote(421),
        rightMidNote(422),
        rightMidNote(423),
        rightMidNote(424),
        rightMidNote(425),
        rightMidNote(426),
        rightMidNote(427),
        rightMidNote(428),

        rightMidNote(431),

        *doubleNote(435, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(436, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(437, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(438, LEFT_MID_COLUMN, LEFT_COLUMN),
        *doubleNote(439, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(440, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(441, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(442, LEFT_MID_COLUMN, LEFT_COLUMN),
        *doubleNote(443, LEFT_COLUMN, RIGHT_COLUMN),
        *doubleNote(444, LEFT_MID_COLUMN, RIGHT_MID_COLUMN),
        *doubleNote(445, RIGHT_MID_COLUMN, RIGHT_COLUMN),
        *doubleNote(446, LEFT_MID_COLUMN, LEFT_COLUMN),

        leftNote(468),
      )

      log.debug { "Amount of notes is: ${notes.size}" }
      val map = BeatMap(180, 100_000_000, notes, "no-time-to-explain-by-goodkid.wav".toMusic())
      game.addScreen(GameScreen(game, map))
      game.setScreen<GameScreen>()
      game.removeScreen<MainMenuScreen>()
      dispose()
    }
  }
}
