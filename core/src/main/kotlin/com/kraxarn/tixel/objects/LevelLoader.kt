package com.kraxarn.tixel.objects

import com.badlogic.gdx.utils.Json
import ktx.assets.toInternalFile
import ktx.json.fromJson
import ktx.log.logger

object LevelLoader
{
	private const val MIN_INDEX = 0
	private const val MAX_INDEX = 9

	private val log = logger<LevelLoader>()

	fun get(index: Int): Level?
	{
		val filename = "level/${if (index > 4) 2 else 1}${'a' + (index % 5)}.json"
		val file = filename.toInternalFile()
		if (!file.exists())
		{
			log.error { "Failed to load \"$filename\": File doesn't exist" }
			return null
		}
		return Level(Json().fromJson(file))
	}

	fun isValid(index: Int) = index in MIN_INDEX..MAX_INDEX
}