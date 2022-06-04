package com.kraxarn.tixel.entities

import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.enums.AtlasAnimation
import com.kraxarn.tixel.enums.Direction
import com.kraxarn.tixel.enums.Key
import com.kraxarn.tixel.extensions.isPressed
import com.kraxarn.tixel.extensions.plus
import com.kraxarn.tixel.extensions.y
import com.kraxarn.tixel.objects.Level
import com.kraxarn.tixel.physics.Collision
import kotlin.math.max
import kotlin.math.min

class Player(private val hud: Hud) : AnimatedSprite(AtlasAnimation.PLAYER_RUNNING)
{
	private var velocity = Vector2()

	val speed get() = velocity

	// FIXME: Velocity Y is also 0 when hitting ceilings
	private val isGrounded get() = velocity.y == 0f

	private val direction
		get() = when
		{
			velocity.x < 0 -> Direction.LEFT
			velocity.x > 0 -> Direction.RIGHT
			else -> verticalDirection
		}

	fun update(level: Level, isPaused: Boolean)
	{
		if (!isPaused)
		{
			val speedLimitX = SPEED_LIMIT * hud.playerSpeedModifier.mod

			// Right
			if (Key.RIGHT.isPressed)
			{
				velocity.x = min(velocity.x + MOVE_ACCELERATION, speedLimitX)
			}
			else if (velocity.x > 0 && isGrounded)
			{
				velocity.x = max(velocity.x + MOVE_DECELERATION, 0f)
			}

			// Left
			if (Key.LEFT.isPressed)
			{
				velocity.x = max(velocity.x - MOVE_ACCELERATION, -speedLimitX)
			}
			else if (velocity.x < 0 && isGrounded)
			{
				velocity.x = min(velocity.x - MOVE_DECELERATION, 0f)
			}

			// Jump
			if (Key.JUMP.isPressed && isGrounded)
			{
				// TODO: Play jump sound
				super.isPaused = true
				frameIndex = 1
				velocity.y = JUMP_FORCE
			}
			else
			{
				velocity.y += GRAVITY
			}
		}

		// Check if sprite should be flipped
		flipX = direction == Direction.RIGHT

		// Update position
		if (!isPaused)
		{
			if (!hud.isDead)
			{
				updateCollision(level)
			}

			position += velocity

			if (velocity.x == 0f)
			{
				super.isPaused = true
				frameIndex = 0
			}
			else
			{
				super.isPaused = false
			}
		}

		// Die
		if (y > KILL_PLAIN)
		{
			if (!hud.isDead)
			{
				// TODO: Play fall sound
			}

			velocity = Vector2()
			position = level.safeSpawn
			hud.respawn()
		}
	}

	private fun updateCollision(level: Level)
	{
		Collision.update(rect, level, velocity, hud)
	}

	companion object
	{
		private const val MOVE_ACCELERATION = 1f
		private const val MOVE_DECELERATION = -0.4f

		private const val JUMP_FORCE = -10f
		private const val GRAVITY = 0.4f

		private const val SPEED_LIMIT = 7.5f
		private const val KILL_PLAIN = 2_000f
	}
}