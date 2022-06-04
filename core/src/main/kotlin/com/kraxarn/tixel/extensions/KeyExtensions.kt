package com.kraxarn.tixel.extensions

import com.badlogic.gdx.Gdx
import com.kraxarn.tixel.enums.Key

val Key.isJustPressed: Boolean
	get() = Gdx.input.isKeyJustPressed(this.key)

val Key.isPressed: Boolean
	get() = Gdx.input.isKeyPressed(this.key)