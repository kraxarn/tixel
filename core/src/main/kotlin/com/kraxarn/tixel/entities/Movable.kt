package com.kraxarn.tixel.entities

import com.badlogic.gdx.math.Vector2

/**
 * Entity with position
 */
sealed interface Movable
{
	var position: Vector2
}