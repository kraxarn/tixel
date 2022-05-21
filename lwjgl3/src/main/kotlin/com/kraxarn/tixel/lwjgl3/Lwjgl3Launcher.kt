@file:JvmName("Lwjgl3Launcher")

package com.kraxarn.tixel.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.kraxarn.tixel.Game

fun main()
{
	Lwjgl3Application(Game(), Lwjgl3ApplicationConfiguration().apply {
		setTitle("Tixel")
		setWindowedMode(1280, 720)
		setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
		setWindowIcon("image/icon.png")
		setForegroundFPS(60)
	})
}
