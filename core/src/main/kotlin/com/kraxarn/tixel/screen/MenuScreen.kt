package com.kraxarn.tixel.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.kraxarn.tixel.Colors
import com.kraxarn.tixel.enums.Direction
import com.kraxarn.tixel.extensions.draw
import com.kraxarn.tixel.skins.MenuSkin
import ktx.actors.onClick
import ktx.actors.plusAssign
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.graphics.use
import kotlin.math.abs
import kotlin.random.Random

class MenuScreen : KtxScreen
{
	private val stage = Stage(ExtendViewport(1280F, 720F))
	private val skin = MenuSkin()
	private val layout = Table(skin)

	private val arrow = Image(skin, MenuSkin.arrow)
	private val startGame = TextButton("Start game", skin)
	private val exitGame = TextButton("Exit game", skin)
	private val debug = Label("...", skin, MenuSkin.debug)

	private val playerAtlas = TextureAtlas("atlas/player.atlas".toInternalFile())
	private val player = Animation(0.15F, playerAtlas.findRegions("running"))
	private var playerTime = 0F
	private var playerPos = Vector2()
	private val playerSize = Vector2(72f, 72f)

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

		debug.x = 16f
		debug.y = stage.height - 32f
		debug.setAlignment(Align.topLeft)
		stage += debug

		Gdx.input.inputProcessor = stage

		resetPlayerPosition()
	}

	override fun render(delta: Float)
	{
		ScreenUtils.clear(Colors.background)

		debug.setText(getDebugText())

		updateArrow()
		updatePlayer(delta)

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

	private fun updatePlayer(delta: Float)
	{
		playerTime += delta
		playerPos.x -= 1.75f

		if (playerPos.x < -playerSize.x)
		{
			resetPlayerPosition()
		}

		stage.batch.use {
			it.draw(player.getKeyFrame(playerTime, true), playerPos, playerSize)
		}
	}

	private fun getDebugText(): String
	{
		return String.format(
			"Debug Mode\nFPS: %d\nDelta: %.2f",
			Gdx.graphics.framesPerSecond,
			Gdx.graphics.deltaTime,
		)
	}

	private fun resetPlayerPosition()
	{
		playerPos.set(
			stage.width,
			Random.Default.nextInt(
				(playerSize.y).toInt(),
				(stage.height - playerSize.y).toInt(),
			).toFloat(),
		)
	}

	override fun resize(width: Int, height: Int)
	{
		stage.viewport.update(width, height)
	}

	override fun dispose()
	{
		skin.disposeSafely()
		stage.disposeSafely()
	}
}