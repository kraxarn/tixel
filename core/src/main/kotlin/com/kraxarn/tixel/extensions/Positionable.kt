package com.kraxarn.tixel.extensions

import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.entities.Positionable

var Positionable.x
	get() = this.position.x
	set(value)
	{
		this.position.x = value
	}

var Positionable.y
	get() = this.position.y
	set(value)
	{
		this.position.y = value
	}

fun Positionable.move(x: Float, y: Float)
{
	this.position.add(x, y)
}

infix fun Positionable.moveTo(position: Vector2)
{
	this.position.set(position)
}