package com.kraxarn.tixel.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.kraxarn.tixel.Colors
import com.kraxarn.tixel.enums.Direction
import com.kraxarn.tixel.skin.MenuSkin
import ktx.actors.onClick
import ktx.actors.plusAssign
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import kotlin.math.abs

class MenuScreen : KtxScreen
{
	private val batch = SpriteBatch()

	private val stage = Stage(ExtendViewport(1280F, 720F))
	private val skin = MenuSkin()
	private val layout = Table(skin)

	private val arrow = Image(skin, MenuSkin.arrow)
	private val startGame = TextButton("Start game", skin)
	private val exitGame = TextButton("Exit game", skin)

	private var current = 0
	private var arrowDirection = Direction.RIGHT

	init
	{
		startGame.onClick {
			TODO()
		}

		exitGame.onClick { Gdx.app.exit() }

		layout.debug = true
		layout.setFillParent(true)

		layout.add(startGame).align(Align.left)

		layout.row().space(16F)
		layout.add(exitGame).align(Align.left)

		stage += layout.padLeft(128F).align(Align.left)

		if (!Gdx.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen))
		{
			arrow.x = 76F
			stage += arrow
		}

		Gdx.input.inputProcessor = stage
	}

	override fun render(delta: Float)
	{
		ScreenUtils.clear(Colors.background)

		updateArrow()

		stage.act(delta)
		stage.draw()
	}

	private fun updateArrow()
	{
		if (Gdx.input.isKeyJustPressed(Keys.UP))
		{
			current = 0
		}
		else if (Gdx.input.isKeyJustPressed(Keys.DOWN))
		{
			current = 1
		}

		arrow.y = when (current)
		{
			0 -> startGame.y
			1 -> exitGame.y
			else -> 0F
		} + startGame.height / 2 - arrow.height / 2

		val offset = abs(arrow.x - 82F)
		if (arrowDirection == Direction.LEFT)
		{
			arrow.moveBy(-0.5F - (offset / 10F), 0F)
		}
		else
		{
			arrow.moveBy(0.5F + (offset / 10F), 0F)
		}

		if (arrow.x <= 64)
		{
			arrowDirection = Direction.RIGHT
		}
		else if (arrow.x >= 82)
		{
			arrowDirection = Direction.LEFT
		}
	}

	override fun resize(width: Int, height: Int)
	{
		stage.viewport.update(width, height)
	}

	override fun dispose()
	{
		skin.disposeSafely()
		batch.disposeSafely()
	}
}