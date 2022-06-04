package com.kraxarn.tixel.entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage

class Pause
{
	private val shape = ShapeRenderer()

	var paused = false

	fun update(stage: Stage)
	{
		if (!paused)
		{
			return
		}

		shape.color = Color(0x00_00_00_7f)
		shape.rect(0f, 0f, stage.width, stage.height)
		shape.end()

		// TODO: Draw text
	}
}