package com.kraxarn.tixel.extensions

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

fun SpriteBatch.draw(region: TextureRegion, position: Vector2, size: Vector2)
{
	this.draw(region, position.x, position.y, size.x, size.y)
}