package com.kraxarn.tixel.extensions

import com.kraxarn.tixel.entities.Sizeable

var Sizeable.width
	get() = this.size.x
	set(value)
	{
		this.size.x = value
	}

var Sizeable.height
	get() = this.size.y
	set(value)
	{
		this.size.y = value
	}

fun Sizeable.resize(size: Float)
{
	this.size.set(size, size)
}

fun Sizeable.resize(width: Float, height: Float)
{
	this.size.set(width, height)
}