package components

import com.soywiz.korge.ui.*
import com.soywiz.korge.view.View
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.alignLeftToLeftOf
import com.soywiz.korge.view.alignTopToTopOf
import com.soywiz.korgw.GameWindow

class DebugInfo(skin: UISkin, private val gameWindow: GameWindow, view: View)
	: UIGridFill(110.0, 50.0, 2)
{
	private val fps: UIText
	private val delta: UIText

	init
	{
		uiSkin = skin
		alignTopToTopOf(view, 8.0)
		alignLeftToLeftOf(view, 16.0)

		uiText("FPS:")
		fps = uiText("0")

		uiText("Delta:")
		delta = uiText("0 ms")

		addUpdater {
			fps.text = "${gameWindow.fps}"
			delta.text = "${it.millisecondsInt} ms"
		}
	}
}