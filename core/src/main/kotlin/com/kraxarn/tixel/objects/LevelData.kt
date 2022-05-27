package com.kraxarn.tixel.objects

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.kraxarn.tixel.enums.Tile
import ktx.json.readArrayValue
import ktx.json.readValue

/**
 * Level loaded from json
 */
class LevelData(
	var name: String = "",
	var tileset: String = "",
	var music: String = "",
	var map: MutableList<MutableList<Int>> = mutableListOf(),
) : Json.Serializable
{
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

	fun getGemCount(): Int = map.flatten().count { it == Tile.GEM.id }

	fun getSpawn(): Vector2?
	{
		for (y in 0 until map.count())
		{
			for (x in 0 until map[y].count())
			{
				if (map[y][x] == Tile.SPAWN.id)
				{
					return Vector2(x.toFloat(), y.toFloat())
				}
			}
		}

		return null
	}
}