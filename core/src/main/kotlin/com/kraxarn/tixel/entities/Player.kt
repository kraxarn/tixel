package com.kraxarn.tixel.entities

import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.enums.AtlasAnimation
import com.kraxarn.tixel.enums.Direction

class Player(hud: Hud) : AnimatedSprite(AtlasAnimation.PLAYER_RUNNING)
{
	private var velocity = Vector2()

	val speed get() = velocity

	// FIXME: Velocity Y is also 0 when hitting ceilings
	val isGrounded get() = velocity.y == 0f

	val direction
		get() = when
		{
			velocity.x < 0 -> Direction.LEFT
			velocity.x > 0 -> Direction.RIGHT
			else -> verticalDirection
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