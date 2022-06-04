package com.kraxarn.tixel.enums

import com.badlogic.gdx.Input

enum class Key(val key: Int)
{
	LEFT(Input.Keys.LEFT),
	RIGHT(Input.Keys.RIGHT),
	UP(Input.Keys.UP),
	DOWN(Input.Keys.DOWN),

	ENTER(Input.Keys.ENTER),
	JUMP(Input.Keys.UP),

	PAUSE(Input.Keys.ESCAPE),
}