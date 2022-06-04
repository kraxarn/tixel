package com.kraxarn.tixel.objects

object Tiles
{
	/**
	 * Size of each tile in image asset
	 */
	const val TILESET_SIZE = 18;

	/**
	 * How much to scale each tile before displaying
	 */
	const val TILE_SCALE = 3f;

	/**
	 * Total tile size with scaling applied
	 */
	const val TILE_SIZE = TILESET_SIZE * TILE_SCALE;
}