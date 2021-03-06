package com.kraxarn.tixel.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.kraxarn.tixel.Colors
import ktx.app.KtxScreen
import ktx.assets.disposeSafely

abstract class Screen : KtxScreen
{
	protected val stage = Stage(ExtendViewport(1280F, 720F))

	override fun render(delta: Float)
	{
		ScreenUtils.clear(Colors.background)
	}

	override fun resize(width: Int, height: Int)
	{
		stage.viewport.update(width, height)
	}

	override fun dispose()
	{
		stage.disposeSafely()
	}
}