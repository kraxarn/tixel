package com.kraxarn.tixel.extensions

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.math.Vector2

val AtlasRegion.originalSize
	get() = Vector2(this.originalWidth.toFloat(), this.originalHeight.toFloat())