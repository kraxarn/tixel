package com.kraxarn.tixel.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Json
import com.kraxarn.tixel.entities.AnimatedSprite
import com.kraxarn.tixel.entities.MenuArrow
import com.kraxarn.tixel.entities.draw
import com.kraxarn.tixel.enums.Direction
import com.kraxarn.tixel.extensions.*
import com.kraxarn.tixel.objects.Level
import com.kraxarn.tixel.skins.MenuSkin
import ktx.actors.onClick
import ktx.actors.plusAssign
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.graphics.use
import ktx.json.fromJson
import ktx.log.logger
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class MenuScreen : Screen()
{
	private val log = logger<MenuScreen>()
	private val skin = MenuSkin()
	private val layout = Table(skin)

	private val arrow = MenuArrow(skin)
	private val startGame = TextButton("Start game", skin)
	private val exitGame = TextButton("Exit game", skin)
	private val debug = Label("...", skin, MenuSkin.debug)
	private val player = AnimatedSprite(0.15f, "atlas/player.atlas", "running")

	private var current = 0

	init
	{
		startGame.onClick { startGame() }
		exitGame.onClick { exitGame() }

		layout.debug = true
		layout.setFillParent(true)

		layout.add(startGame).align(Align.left)

		layout.row().space(16F)
		layout.add(exitGame).align(Align.left)

		stage += layout.padLeft(128F).align(Align.left)

		if (!Gdx.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen))
		{
			stage += arrow
		}

		debug.x = 16f
		debug.y = stage.height - 32f
		debug.setAlignment(Align.topLeft)
		stage += debug

		Gdx.input.inputProcessor = stage

		player.resize(72f)
		resetPlayerPosition()
	}

	override fun render(delta: Float)
	{
		super.render(delta)

		debug.setText(getDebugText())

		updateArrow()
		updatePlayer(delta)

		stage.act(delta)
		stage.draw()
	}

	private fun updateArrow()
	{
		val previous = current

		if (Gdx.input.isKeyJustPressed(Keys.UP))
		{
			current = 0
		}
		else if (Gdx.input.isKeyJustPressed(Keys.DOWN))
		{
			current = 1
		}
		else if (Gdx.input.isKeyJustPressed(Keys.ENTER))
		{
			when (current)
			{
				0 -> startGame()
				1 -> exitGame()
			}
			return
		}

		if (current != previous)
		{
			arrow.clearActions()
			arrow += moveTo(arrow.targetX, getArrowY(), MenuArrow.MOVE_DURATION, Interpolation.circle)
		}

		if (arrow.y <= 0)
		{
			if (startGame.y > 0)
			{
				arrow.y = getArrowY()
			}
		}
		else if (!arrow.hasActions())
		{
			arrow.direction = if (arrow.direction == Direction.RIGHT) Direction.LEFT else Direction.RIGHT
			arrow += moveTo(arrow.targetX, arrow.y, MenuArrow.ANIMATION_DURATION, arrow.targetInterpolation)
		}
	}

	private fun getArrowY(): Float
	{
		return when (current)
		{
			0 -> startGame
			1 -> exitGame
			else -> return 0f
		}.y + startGame.height / 2 - arrow.height / 2
	}

	private fun updatePlayer(delta: Float)
	{
		player.move(-87.5f * delta, 0f)

		if (player.x < -player.width)
		{
			resetPlayerPosition()
		}

		stage.batch.use {
			it.draw(player, delta)
		}
	}

	private fun startGame()
	{
		log.info {
			val level: Level
			val ms = measureTimeMillis {
				level = Level(Json().fromJson("level/1a.json".toInternalFile()))
			}
			"Loaded level \"${level.name}\" (${level.gemCount} gems) in $ms ms"
		}
	}

	private fun exitGame() = Gdx.app.exit()

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
		val y = Random.Default.nextInt(
			player.height.toInt(),
			(stage.height - player.height).toInt(),
		).toFloat()

		player moveTo Vector2(stage.width, y)
	}

	override fun dispose()
	{
		skin.disposeSafely()
		super.dispose()
	}
}