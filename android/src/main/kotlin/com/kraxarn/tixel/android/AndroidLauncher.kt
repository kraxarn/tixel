package com.kraxarn.tixel.android

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.kraxarn.tixel.Game

/** Launches the Android application. */
class AndroidLauncher : AndroidApplication()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		initialize(Game(), AndroidApplicationConfiguration().apply {
			// Configure your application here.
		})
	}
}
