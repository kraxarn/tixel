package com.kraxarn.tixel.extensions

import com.badlogic.gdx.math.Vector2

operator fun Vector2.times(value: Float): Vector2 =
	Vector2(this.x * value, this.y * value)

operator fun Vector2.div(value: Float): Vector2 =
	Vector2(this.x / value, this.y / value)

operator fun Vector2.plus(value: Vector2): Vector2 =
	Vector2(this.x + value.x, this.y + value.y)

operator fun Vector2.plus(value: Float): Vector2 =
	Vector2(this.x + value, this.y + value)

operator fun Vector2.minus(value: Float): Vector2 =
	Vector2(this.x - value, this.y - value)