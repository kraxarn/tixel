package com.kraxarn.tixel.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import com.kraxarn.tixel.Game
import com.kraxarn.tixel.entities.AnimatedSprite
import com.kraxarn.tixel.entities.DebugInfo
import com.kraxarn.tixel.entities.MenuArrow
import com.kraxarn.tixel.entities.draw
import com.kraxarn.tixel.enums.AtlasAnimation
import com.kraxarn.tixel.enums.Direction
import com.kraxarn.tixel.enums.Key
import com.kraxarn.tixel.extensions.*
import com.kraxarn.tixel.skins.MenuSkin
import ktx.actors.onClick
import ktx.actors.plusAssign
import ktx.assets.disposeSafely
import ktx.graphics.use
import ktx.log.logger
import kotlin.random.Random

class MenuScreen(private val game: Game) : Screen()
{
	private val log = logger<MenuScreen>()
	private val skin = MenuSkin()
	private val layout = Table(skin)

	private val arrow = MenuArrow(skin)
	private val startGame = TextButton("Start game", skin)
	private val exitGame = TextButton("Exit game", skin)
	private val debug = DebugInfo(skin)
	private val player = AnimatedSprite(AtlasAnimation.PLAYER_RUNNING)

	private var current = 0

	init
	{
		startGame.onClick { startGame() }
		exitGame.onClick { exitGame() }

		layout.setFillParent(true)

		layout.add(startGame).align(Align.left)

		layout.row().space(32f)
		layout.add(exitGame).align(Align.left)

		stage += layout.padLeft(128F).align(Align.left)

		if (!Gdx.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen))
		{
			stage += arrow
		}

		debug.y = stage.height - 32f
		stage += debug

		Gdx.input.inputProcessor = stage

		player.resize(72f)
		resetPlayerPosition()
	}

	override fun render(delta: Float)
	{
		super.render(delta)

		updateArrow()
		updatePlayer(delta)

		stage.act(delta)
		stage.draw()
	}

	private fun updateArrow()
	{
		val previous = current

		if (Key.UP.isJustPressed)
		{
			current = 0
		}
		else if (Key.DOWN.isJustPressed)
		{
			current = 1
		}
		else if (Key.ENTER.isJustPressed)
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
		game.getScreen<LevelScreen>().load(0)
		game.setScreen<LevelScreen>()
	}

	private fun exitGame() = Gdx.app.exit()

	private fun resetPlayerPosition()
	{
		val y = Random.Default.nextFloat(player.height, stage.height - player.height)
		player moveTo Vector2(stage.width, y)
	}

	override fun dispose()
	{
		skin.disposeSafely()
		super.dispose()
	}
}