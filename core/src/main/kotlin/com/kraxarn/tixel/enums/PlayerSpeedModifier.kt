package com.kraxarn.tixel.enums

enum class PlayerSpeedModifier(val mod: Float)
{
	/**
	 * Default speed
	 */
	DEFAULT(1f),

	/**
	 * Speed while underwater
	 */
	WATER(0.5f),
}