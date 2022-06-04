package com.kraxarn.tixel

import com.kraxarn.tixel.screens.LevelScreen
import com.kraxarn.tixel.screens.MenuScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen

class Game : KtxGame<KtxScreen>()
{
	override fun create()
	{
		addScreen(MenuScreen(this))
		addScreen(LevelScreen())

		setScreen<MenuScreen>()
	}
}
