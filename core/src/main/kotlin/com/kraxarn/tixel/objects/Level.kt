package com.kraxarn.tixel.objects

import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import ktx.json.readArrayValue
import ktx.json.readValue

class Level(
	var name: String = "",
	var tileset: String = "",
	var music: String = "",
	var map: List<List<Int>> = emptyList(),
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
		json.writeValue("name", name)
		json.writeValue("tileset", tileset)
		json.writeValue("music", music)
	}
}