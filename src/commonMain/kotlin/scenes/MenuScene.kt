package scenes

import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.UISkin
import com.soywiz.korge.ui.textColor
import com.soywiz.korge.ui.textFont
import com.soywiz.korge.ui.uiSkin
import com.soywiz.korge.view.Container
import com.soywiz.korim.font.readTtfFont
import com.soywiz.korio.file.std.resourcesVfs
import components.DebugInfo
import constants.Colors

class MenuScene : Scene()
{
	override suspend fun Container.sceneInit()
	{
		uiSkin = UISkin {
			textFont = resourcesVfs["fonts/menu.ttf"].readTtfFont()
			textColor = Colors.foreground
		}

		val debugSkin = UISkin {
			textFont = resourcesVfs["fonts/debug.ttf"].readTtfFont()
			textColor = Colors.foreground
		}

		addChild(DebugInfo(debugSkin, views.gameWindow, this))
	}
}