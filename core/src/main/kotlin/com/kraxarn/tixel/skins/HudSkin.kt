package com.kraxarn.tixel.skins

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.kraxarn.tixel.Colors
import ktx.assets.toInternalFile

class HudSkin : Skin()
{
	init
	{
		val font = BitmapFont("font/hud.fnt".toInternalFile())
		add("default", LabelStyle(font, Colors.foreground))
	}
}