package com.kraxarn.tixel.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.kraxarn.tixel.skins.MenuSkin

class DebugInfo(skin: MenuSkin) : Label("Debug Mode", skin, MenuSkin.debug)
{
	init
	{
		x = 16f
		setAlignment(Align.topLeft)
	}

	override fun act(delta: Float)
	{
		val fps = Gdx.graphics.framesPerSecond
		setText(String.format("Debug Mode\nFPS: %d\nDelta: %.2f", fps, delta))

		super.act(delta)
	}
}