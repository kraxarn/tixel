package com.kraxarn.tixel.objects

import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.enums.Tile
import com.kraxarn.tixel.extensions.times

/**
 * Loaded level
 */
class Level(data: LevelData)
{
	val name = data.name
	val tileset = data.tileset
	val music = data.music
	val map = data.getMap()

	val gemCount = data.getGemCount()
	val spawn: Vector2 = data.getSpawn() ?: Vector2.Zero

	fun setTile(x: Int, y: Int, tile: Tile)
	{
		map[x, y] = tile
	}

	val safeSpawn: Vector2
		get()
		{
			val playerPosition = spawn * Tiles.tileSize
			playerPosition.y -= Tiles.tileSize * 0.5f
			return playerPosition
		}
}