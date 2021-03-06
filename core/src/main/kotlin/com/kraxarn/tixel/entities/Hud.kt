package com.kraxarn.tixel.entities

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kraxarn.tixel.enums.PlayerSpeedModifier
import com.kraxarn.tixel.enums.Tile
import com.kraxarn.tixel.objects.Level
import com.kraxarn.tixel.skins.HudSkin
import ktx.assets.toInternalFile

class Hud : Table(HudSkin())
{
	private val state = State()

	private val atlas = TextureAtlas("atlas/items.atlas".toInternalFile())
	private val gemImage = Image(atlas.findRegion(Tile.GEM.id.toString()))
	private val coinImage = Image(atlas.findRegion(Tile.COIN.id.toString()))

	private val gemText = Label("0/0", skin)
	private val coinText = Label("0", skin)

	private val padding = 12f
	private val spacing = 6f
	private val scaling = 2f

	val gemCount get() = state.gems
	val coinCount get() = state.coins
	val isDead get() = state.isDead

	var playerSpeedModifier = PlayerSpeedModifier.DEFAULT

	init
	{
		setFillParent(true)
		align(Align.topRight)

		padTop(padding)
		padRight(padding)

		add(coinText)
		add(coinImage)
		row().space(spacing)

		add(gemText)
		add(gemImage)
	}

	fun update(level: Level)
	{
		gemText.isVisible = level.gemCount > 0
		gemImage.isVisible = gemText.isVisible

		gemText.setText("$gemCount/${level.gemCount ?: 0}")
		coinText.setText(coinCount.toString())
	}

	fun addCoin()
	{
		// TODO: Play coin sound
		state.coins++
	}

	fun addGem()
	{
		// TODO: Play gem sound
		state.gems++
	}

	fun kill()
	{
		TODO("Player cannot die")
	}

	fun respawn()
	{
		state.isDead = false
	}

	fun reload()
	{
		state.gems = 0
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