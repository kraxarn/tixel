package com.kraxarn.tixel.objects

object Tile
{
	/**
	 * Maximum number of tiles horizontally
	 */
	const val levelTilesWidth = 75;

	/**
	 * Maximum number of tiles vertically
	 */
	const val levelTilesHeight = 25;

	/**
	 * Size of each tile in image asset
	 */
	const val tilesetSize = 18;

	/**
	 * How much to scale each tile before displaying
	 */
	const val tileScale = 3f;

	/**
	 * Total tile size with scaling applied
	 */
	const val tileSize = tilesetSize * tileScale;
}