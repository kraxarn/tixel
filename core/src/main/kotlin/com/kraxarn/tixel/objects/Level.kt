package com.kraxarn.tixel.objects

import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.extensions.times

/**
 * Loaded level
 */
class Level(data: LevelData)
{
	val name = data.name
	val tileset = data.tileset
	val music = data.music
	val map = data.levelMap

	val gemCount = data.gemCount
	val spawn: Vector2 = data.spawn ?: Vector2.Zero

	val safeSpawn: Vector2
		get()
		{
			val playerPosition = spawn * Tiles.tileSize
			playerPosition.y -= Tiles.tileSize * 0.5f
			return playerPosition
		}
}