package com.kraxarn.tixel.entities

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.kraxarn.tixel.enums.Direction
import com.kraxarn.tixel.skins.MenuSkin

class MenuArrow(skin: MenuSkin) : Image(skin, MenuSkin.arrow)
{
	var direction = Direction.RIGHT

	init
	{
		x = MAX_X
	}

	val targetInterpolation: Interpolation
		get() = when (direction)
		{
			Direction.LEFT -> Interpolation.circleOut
			Direction.RIGHT -> Interpolation.circleIn
			else -> throw IllegalStateException()
		}

	val targetX: Float
		get() = when (direction)
		{
			Direction.LEFT -> MIN_X
			Direction.RIGHT -> MAX_X
			else -> throw IllegalStateException()
		}

	companion object
	{
		const val MIN_X = 64f
		const val MAX_X = 82f
		const val ANIMATION_DURATION = 0.4f
		const val MOVE_DURATION = ANIMATION_DURATION / 2f
	}
}