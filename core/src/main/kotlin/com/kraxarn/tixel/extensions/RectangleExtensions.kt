package com.kraxarn.tixel.extensions

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

infix fun Rectangle?.overlapsWith(rect: Rectangle?) =
	this != null && rect != null && this.overlaps(rect)

fun Rectangle.copy(x: Float = this.x, y: Float = this.y, width: Float = this.width, height: Float = this.height) =
	Rectangle(x, y, width, height)

val Rectangle.position get() = Vector2(this.x, this.y)