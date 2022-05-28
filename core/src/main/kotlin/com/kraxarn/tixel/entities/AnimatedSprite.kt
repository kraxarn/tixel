package com.kraxarn.tixel.entities

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.enums.AtlasAnimation
import com.kraxarn.tixel.extensions.draw
import com.kraxarn.tixel.extensions.keyFrames
import com.kraxarn.tixel.extensions.originalSize

open class AnimatedSprite(animation: AtlasAnimation)
	: Animation<AtlasRegion>(animation.frameDuration, animation.keyFrames),
	Positionable, Sizeable
{
	var time = 0f

	override var position = Vector2()
	override var size = this.getKeyFrame(0f).originalSize
}

fun Batch.draw(sprite: AnimatedSprite, delta: Float)
{
	sprite.time += delta
	this.draw(sprite.getKeyFrame(sprite.time, true), sprite.position, sprite.size)
}