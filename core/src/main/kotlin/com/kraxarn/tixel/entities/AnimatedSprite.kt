package com.kraxarn.tixel.entities

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.enums.AtlasAnimation
import com.kraxarn.tixel.enums.Direction
import com.kraxarn.tixel.extensions.draw
import com.kraxarn.tixel.extensions.keyFrames
import com.kraxarn.tixel.extensions.originalSize

open class AnimatedSprite(animation: AtlasAnimation)
	: Animation<AtlasRegion>(animation.frameDuration, animation.keyFrames),
	Positionable, Sizeable
{
	var time = 0f

	var flipX = false
	var flipY = false

	var isPaused = false

	override var position = Vector2()
	override var size = this.getKeyFrame(0f).originalSize

	val rect get() = Rectangle(position.x, position.y, size.x, size.y)

	val verticalDirection
		get() = if (flipX) Direction.RIGHT else Direction.LEFT

	val horizontalDirection
		get() = if (flipY) Direction.UP else Direction.DOWN

	var frameIndex: Int
		get() = getKeyFrameIndex(time)
		set(value)
		{
			time = value * frameDuration
		}
}

fun Batch.draw(sprite: AnimatedSprite, delta: Float)
{
	if (!sprite.isPaused)
	{
		sprite.time += delta
	}

	val region = sprite.getKeyFrame(sprite.time, true)
	val flipX = sprite.flipX != region.isFlipX
	val flipY = sprite.flipY != region.isFlipY
	region.flip(flipX, flipY)

	this.draw(region, sprite.position, sprite.size)
}