package com.kraxarn.tixel.entities

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kraxarn.tixel.enums.Tile
import com.kraxarn.tixel.skins.HudSkin
import ktx.assets.toInternalFile

class Hud : Table(HudSkin())
{
	private val atlas = TextureAtlas("atlas/items.atlas".toInternalFile())
	private val gemImage = Image(atlas.findRegion(Tile.GEM.id.toString()))
	private val coinImage = Image(atlas.findRegion(Tile.COIN.id.toString()))

	private val gemCount = Label("0/0", skin)
	private val coinCount = Label("0", skin)

	private val padding = 12f
	private val spacing = 6f
	private val scaling = 2f

	init
	{
		setFillParent(true)
		align(Align.topRight)

		padTop(padding)
		padRight(padding)

		add(gemCount)
		add(gemImage)
		row().padTop(spacing)

		add(coinCount)
		add(coinImage)
	}

	private fun add(label: Label): Cell<Label>
	{
		return super.add(label)
			.align(Align.right)
			.padRight(spacing)
	}

	private fun add(image: Image): Cell<Image>
	{
		return super.add(image)
			.width(image.width * scaling)
			.height(image.height * scaling)
			.align(Align.right)
	}
}