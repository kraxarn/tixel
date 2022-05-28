package com.kraxarn.tixel.objects

object Tiles
{
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