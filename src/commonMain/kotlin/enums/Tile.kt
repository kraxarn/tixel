package enums

enum class Tile(val id: Int, val type: TileType)
{
	// Invalid
	NONE(-1, TileType.EMPTY),

	// From level atlas
	TILE_RIGHT(0, TileType.TILE),
	TILE_LEFT(1, TileType.TILE),
	TILE_MIDDLE(2, TileType.TILE),
	TILE_CENTER(3, TileType.TILE),
	ONE_WAY_RIGHT(4, TileType.ONE_WAY),
	ONE_WAY_SINGLE(5, TileType.ONE_WAY),
	ONE_WAY_CENTER(6, TileType.ONE_WAY),
	ONE_WAY_LEFT(7, TileType.ONE_WAY),

	// From item atlas
	SPAWN(50, TileType.ITEM),
	EXIT(51, TileType.ITEM),
	COIN(52, TileType.ITEM),
	GEM(53, TileType.ITEM),
	WATER(54, TileType.ITEM),
	LAVA(55, TileType.ITEM),
	SPIKE(56, TileType.ITEM),
	BOSS(57, TileType.ENTITY),
}