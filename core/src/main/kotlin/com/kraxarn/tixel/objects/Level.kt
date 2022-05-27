package com.kraxarn.tixel.objects

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.kraxarn.tixel.enums.Tile
import com.kraxarn.tixel.extensions.times
import ktx.json.readArrayValue
import ktx.json.readValue

class Level(
	var name: String = "",
	var tileset: String = "",
	var music: String = "",
	var map: List<List<Int>> = emptyList(),
) : Json.Serializable
{
	private val gemCount: Int = getGemCount()
	private val spawn: Vector2 = getSpawn() ?: Vector2.Zero

	override fun read(json: Json, jsonData: JsonValue)
	{
		name = json.readValue(jsonData, "name")
		tileset = json.readValue(jsonData, "tileset")
		music = json.readValue(jsonData, "music")
		map = json.readArrayValue(jsonData, "map")
	}

	override fun write(json: Json)
	{
		throw UnsupportedOperationException("Levels cannot be written")
	}

	private fun getGemCount(): Int = map.flatten().count { it == Tile.GEM.id }

	private fun getSpawn(): Vector2?
	{
		for (y in 0..map.count())
		{
			for (x in 0..map[y].count())
			{
				if (map[y][x] == Tile.SPAWN.id)
				{
					return Vector2(x.toFloat(), y.toFloat())
				}
			}
		}
		return null
	}

	val safeSpawn: Vector2
		get()
		{
			val playerPosition = spawn * Tiles.tileSize
			playerPosition.y -= Tiles.tileSize * 0.5f
			return playerPosition
		}
}