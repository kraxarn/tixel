package com.kraxarn.tixel.entities

import com.badlogic.gdx.math.Vector2

/**
 * Entity with position
 */
sealed interface Positionable
{
	var position: Vector2
}