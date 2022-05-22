package com.kraxarn.tixel.skin

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.kraxarn.tixel.Colors
import ktx.assets.toInternalFile

class MenuSkin : Skin()
{
	init
	{
		val menuFont = BitmapFont("font/menu.fnt".toInternalFile())
		add("default", LabelStyle(menuFont, Colors.foreground))

		add("default", TextButtonStyle(null, null, null, menuFont))

		add(arrow, Texture("image/arrow.png".toInternalFile()))
	}

	companion object
	{
		const val arrow = "arrow"
	}
}