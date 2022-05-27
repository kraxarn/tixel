package com.kraxarn.tixel.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.kraxarn.tixel.Colors
import com.kraxarn.tixel.enums.Direction
import com.kraxarn.tixel.extensions.draw
import com.kraxarn.tixel.objects.Level
import com.kraxarn.tixel.skins.MenuSkin
import ktx.actors.onClick
import ktx.actors.plusAssign
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.graphics.use
import ktx.json.fromJson
import ktx.log.logger
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class MenuScreen : KtxScreen
{
	private val log = logger<MenuScreen>()

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
	private val arrowMin = 64f
	private val arrowMax = 82f
	private val arrowDuration = 0.4f

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
			arrow.x = arrowMax
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
			arrow += moveTo(getArrowX(), getArrowY(), arrowDuration / 2f, Interpolation.circle)
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
			arrowDirection = if (arrowDirection == Direction.RIGHT) Direction.LEFT else Direction.RIGHT
			arrow += moveTo(getArrowX(), arrow.y, arrowDuration, getArrowInterpolation())
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

	private fun getArrowInterpolation(): Interpolation
	{
		return when (arrowDirection)
		{
			Direction.LEFT -> Interpolation.circleOut
			Direction.RIGHT -> Interpolation.circleIn
			else -> throw IllegalStateException()
		}
	}

	private fun getArrowX(): Float
	{
		return when (arrowDirection)
		{
			Direction.LEFT -> arrowMin
			Direction.RIGHT -> arrowMax
			else -> throw IllegalStateException()
		}
	}

	private fun updatePlayer(delta: Float)
	{
		playerTime += delta
		playerPos.x -= 87.5f * delta

		if (playerPos.x < -playerSize.x)
		{
			resetPlayerPosition()
		}

		stage.batch.use {
			it.draw(player.getKeyFrame(playerTime, true), playerPos, playerSize)
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