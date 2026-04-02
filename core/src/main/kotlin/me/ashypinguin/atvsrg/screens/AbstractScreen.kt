package me.ashypinguin.atvsrg.screens

import ktx.app.KtxScreen
import me.ashypinguin.atvsrg.AtVsrGame

/**
 * Super class for all screens.
 * It gives access to shared recourses in [AtVsrGame]
 *
 * @param game the main game instance that can be used to access shared recourses
 */
abstract class AbstractScreen(protected val game: AtVsrGame) : KtxScreen
