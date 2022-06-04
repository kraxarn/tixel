package com.kraxarn.tixel.screens

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.entities.Boss
import com.kraxarn.tixel.entities.Hud
import com.kraxarn.tixel.entities.Pause
import com.kraxarn.tixel.entities.Player
import com.kraxarn.tixel.enums.Tile
import com.kraxarn.tixel.enums.TileType
import com.kraxarn.tixel.extensions.isFinal
import com.kraxarn.tixel.extensions.overlapsWith
import com.kraxarn.tixel.extensions.times
import com.kraxarn.tixel.objects.Level
import com.kraxarn.tixel.objects.LevelLoader
import com.kraxarn.tixel.objects.LevelMap
import com.kraxarn.tixel.objects.Tiles
import ktx.assets.toInternalFile
import ktx.log.logger

class LevelScreen : Screen()
{
	private val log = logger<LevelScreen>()

	// Level
	private var level: Level? = null
	private var tiles: TextureAtlas? = null
	private val items = TextureAtlas("atlas/items.atlas")

	// Entities
	private val hud = Hud()
	private val pause = Pause()
	private val player = Player(hud)
	private var boss: Boss? = null

	// Level switching
	private var currentLevelIndex = -1

	override fun render(delta: Float)
	{
		super.render(delta)
	}

	private fun load(index: Int)
	{
		val level = LevelLoader.get(index) ?: throw IllegalStateException("Invalid level index: $index")
		this.level = level
		currentLevelIndex = index

		boss = null
		loadEntities()

		// TODO: Load music

		tiles = TextureAtlas("atlas/${level.tileset}.atlas".toInternalFile())

		// TODO: Camera stuff
		player.position = level.safeSpawn

		hud.reload()

		// TODO: Level title
	}

	private fun nextLevel()
	{
		val index = if (LevelLoader.isValid(currentLevelIndex + 1)) currentLevelIndex + 1 else 0
		// TODO: Play complete sound
		load(index)
	}

	private fun loadEntities()
	{
		level?.forEach { x, y, tile ->
			// Currently, there's only one entity
			if (tile.type == TileType.ENTITY && tile == Tile.BOSS)
			{
				boss = Boss(player)
				boss?.position = Vector2(x.toFloat(), y.toFloat()) * Tiles.TILE_SIZE
				boss?.isLockY = level?.isFinal ?: false
			}
		}
	}

	private fun updateEntities()
	{
		if (pause.paused)
		{
			return
		}

		if (boss != null && !hud.isDead && player.rect overlapsWith boss?.rect)
		{
			// Normal boss: Player always dies when touching
			// Final boss: Boss takes damage if hit from above, otherwise, kill player

			val isFinal = level?.isFinal ?: false
			if (!isFinal || player.speed.y <= 0)
			{
				hud.kill()
				// Reset boss
				boss = null
				loadEntities()
			}
			else if (boss?.hurt() == true)
			{
				TODO("Credits not implemented")
			}
		}
	}

	private fun updateCamera()
	{
		TODO("Camera not implemented")
	}

	private fun drawMap()
	{
		level?.forEach { x, y, tile ->
			if (tile.type == TileType.EMPTY)
			{
				return@forEach
			}

			val xPos = x * Tiles.TILE_SIZE
			val yPos = y * Tiles.TILE_SIZE

			if (tile.type == TileType.TILE || tile.type == TileType.ONE_WAY)
			{
				// TODO: Cache this
				val region = tiles?.findRegion(tile.id.toString())
				stage.batch.draw(region, xPos, yPos)
			}
			else if (tile.type == TileType.ITEM)
			{
				if (tile != Tile.EXIT || hud.gemCount == level?.gemCount)
				{
					// TODO: Cache this
					val region = items.findRegion(tile.id.toString())
					stage.batch.draw(region, xPos, yPos)
				}
			}
		}
	}

	companion object
	{
		const val LEVEL_WIDTH = LevelMap.WIDTH * Tiles.TILE_SIZE
		const val LEVEL_HEIGHT = LevelMap.HEIGHT * Tiles.TILE_SIZE
	}
}