@file:JvmName("Lwjgl3Launcher")

package com.kraxarn.tixel.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.kraxarn.tixel.Game
import javax.swing.JOptionPane
import kotlin.system.exitProcess

fun main()
{
	try
	{
		Lwjgl3Application(Game(), Lwjgl3ApplicationConfiguration().apply {
			setTitle("Tixel")
			setWindowedMode(1280, 720)
			setWindowIcon("image/icon.png")
		})
	}
	catch (e: Exception)
	{
		e.printStackTrace()

		JOptionPane.showMessageDialog(
			null, e.stackTraceToString(),
			"Fatal error", JOptionPane.ERROR_MESSAGE
		)
		exitProcess(e.hashCode())
	}
}
