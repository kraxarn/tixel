package com.kraxarn.tixel.enums

enum class AtlasAnimation(val frameDuration: Float, val path: String, val region: String)
{
	// Player
	PLAYER_RUNNING(0.15f, "atlas/player.atlas", "running"),

	// Enemies
	ENEMY_MOVING(0.15f, "atlas/enemy.atlas", "moving")
}