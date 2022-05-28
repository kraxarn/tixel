package com.kraxarn.tixel.extensions

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array
import com.kraxarn.tixel.enums.AtlasAnimation
import ktx.assets.toInternalFile

val AtlasAnimation.keyFrames: Array<TextureAtlas.AtlasRegion>
	get() = TextureAtlas(this.path.toInternalFile()).findRegions(this.region)