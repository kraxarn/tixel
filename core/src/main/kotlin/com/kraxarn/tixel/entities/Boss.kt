package com.kraxarn.tixel.entities

import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.enums.AtlasAnimation
import com.kraxarn.tixel.extensions.*
import com.kraxarn.tixel.objects.Tiles
import kotlin.math.abs
import kotlin.random.Random

class Boss(private val player: Player) : AnimatedSprite(AtlasAnimation.ENEMY_MOVING)
{
	var health = 5
	var isLockY = false

	private val isDead get() = health <= 0

	private val playerDistance
		get() = Vector2(abs(x - player.x), abs(y - player.y))

	private val speed: Float
		get()
		{
			val speed = MAX_SPEED - (health * ((MAX_SPEED - MIN_SPEED) / MAX_HEALTH))
			return if (isLockY && player.y + Tiles.TILE_SIZE < y) -speed / 2f else speed
		}

	fun update(isPaused: Boolean)
	{
		if (isDead || isPaused)
		{
			return
		}

		val distance = playerDistance
		val speed = speed

		val x = if (distance.x < width) 0f else if (player.x < x) speed else -speed
		val y = if (isLockY || distance.y < height) 0f else if (player.y < y) speed else -speed
		move(x, y)

		flipX = x > 0
	}

	private fun hurt(): Boolean
	{
		// TODO: PLay hurt sound
		health--
		position = if (isDead) Vector2.Zero else randomPosition()
		return isDead
	}

	private fun randomPosition(): Vector2
	{
		val random = Random.Default
		return Vector2(
			player.x
				+ random.nextInt(MIN_RANDOM_MOVE, MAX_RANDOM_MOVE)
				* (if (random.nextBoolean()) -1f else 1f),
			player.y
		)
	}

	companion object
	{
		private const val MIN_SPEED = 1f
		private const val MAX_SPEED = 8f

		private const val MAX_HEALTH = 5

		private const val MIN_RANDOM_MOVE = 400
		private const val MAX_RANDOM_MOVE = 600
	}
}