package com.kraxarn.tixel.physics

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.kraxarn.tixel.entities.Hud
import com.kraxarn.tixel.enums.PlayerSpeedModifier
import com.kraxarn.tixel.enums.Tile
import com.kraxarn.tixel.enums.TileType
import com.kraxarn.tixel.extensions.copy
import com.kraxarn.tixel.extensions.div
import com.kraxarn.tixel.extensions.overlapsWith
import com.kraxarn.tixel.extensions.position
import com.kraxarn.tixel.objects.Level
import com.kraxarn.tixel.objects.LevelMap
import com.kraxarn.tixel.objects.Tiles

object Collision
{
	/**
	 * How many more tiles to check around the player
	 */
	private const val offset = 4

	fun update(playerRect: Rectangle, level: Level, velocity: Vector2, hud: Hud): TileType
	{
		var collides = TileType.EMPTY
		val playerPosition = playerRect.position
		val playerTile = (playerPosition / Tiles.tileSize)

		hud.playerSpeedModifier = PlayerSpeedModifier.DEFAULT

		val rectX = playerRect.copy(x = playerRect.x + velocity.x)
		if (willCollide(level, hud, playerTile, rectX, velocity))
		{
			velocity.x = 0f
			collides = TileType.TILE
		}

		val rectY = playerRect.copy(y = playerRect.y + velocity.y)
		if (willCollide(level, hud, playerTile, rectY, velocity))
		{
			velocity.y = 0f
			collides = TileType.TILE
		}

		return collides
	}

	private fun willCollide(level: Level, hud: Hud, position: Vector2, rect: Rectangle, velocity: Vector2): Boolean
	{
		val tileX = position.x.toInt()
		val tileY = position.y.toInt()

		val target = Rectangle()
		target.width = Tiles.tileSize
		target.height = Tiles.tileSize

		for (x in tileX - offset..tileX + offset)
		{
			if (x < 0 || x >= LevelMap.WIDTH)
			{
				continue
			}

			for (y in tileY - offset..tileY + offset)
			{
				if (y < 0 || y >= LevelMap.HEIGHT)
				{
					continue
				}

				target.x = x * Tiles.tileSize
				target.y = y * Tiles.tileSize
				if (!(rect overlapsWith target))
				{
					continue
				}

				val tile = level.map[x, y]
				if (tile.type == TileType.TILE)
				{
					val tileSize = Tiles.tileSize * 0.9f
					val tileRect = rect.copy()
					tileRect.y += tileRect.height - tileSize
					tileRect.height = tileSize

					return tileRect overlapsWith target
				}

				if (tile.type == TileType.ONE_WAY
					&& velocity.y > 0
					&& rect.y + rect.height * 0.75f < target.y)
				{
					return true
				}

				if (tile.type == TileType.ITEM)
				{
					collectItem(level, hud, x, y)
				}
			}
		}
		return false
	}

	private fun collectItem(level: Level, hud: Hud, x: Int, y: Int)
	{
		when (level.map[x, y])
		{
			Tile.EXIT ->
			{
				if (hud.gemCount == level.gemCount)
				{
					TODO("level switching not implemented yet")
				}
			}

			Tile.COIN ->
			{
				level.map[x, y] = Tile.NONE
				hud.addCoin()
			}

			Tile.GEM ->
			{
				level.map[x, y] = Tile.NONE
				hud.addGem()
			}

			Tile.WATER -> hud.playerSpeedModifier = PlayerSpeedModifier.WATER

			Tile.LAVA -> hud.kill()
			Tile.SPIKE -> hud.kill()

			else -> Unit
		}
	}
}