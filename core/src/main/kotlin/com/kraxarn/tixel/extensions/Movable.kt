package com.kraxarn.tixel.extensions

import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.entities.Movable

var Movable.x
	get() = this.position.x
	set(value)
	{
		this.position.x = value
	}

var Movable.y
	get() = this.position.y
	set(value)
	{
		this.position.y = value
	}

fun Movable.move(x: Float, y: Float)
{
	this.position.add(x, y)
}

infix fun Movable.moveTo(position: Vector2)
{
	this.position.set(position)
}