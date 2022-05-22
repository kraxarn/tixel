package com.kraxarn.tixel.skins

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
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

		val debugFont = BitmapFont("font/debug.fnt".toInternalFile())
		add(debug, LabelStyle(debugFont, Colors.foreground))

		add(arrow, Texture("image/arrow.png".toInternalFile()))
		add(player, TextureAtlas("atlas/player.atlas".toInternalFile()))
	}

	companion object
	{
		const val arrow = "arrow"
		const val player = "player"
		const val debug = "debug"
	}
}