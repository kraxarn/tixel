package com.kraxarn.tixel.objects

import com.kraxarn.tixel.enums.Tile

/**
 * Tiles in a level
 */
class Map
{
	private val tiles = Array(height) { Array(width) { Tile.NONE } }

	operator fun get(x: Int, y: Int): Tile = tiles[x][y]

	operator fun set(x: Int, y: Int, tile: Tile)
	{
		tiles[x][y] = tile
	}

	companion object
	{
		/**
		 * Numer of tiles horizontally
		 */
		const val width = 75

		/**
		 * Number of tiles vertically
		 */
		const val height = 25
	}
}