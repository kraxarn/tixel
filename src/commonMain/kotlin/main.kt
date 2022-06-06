import com.soywiz.korge.Korge
import com.soywiz.korge.scene.Module
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.ScaleMode
import com.soywiz.korma.geom.SizeInt
import constants.Colors
import scenes.LevelScene
import scenes.MenuScene

suspend fun main() = Korge(Korge.Config(Game))

object Game : Module()
{
	override val mainScene get() = MenuScene::class
	override val windowSize get() = SizeInt(1280, 720)
	override val bgcolor get() = Colors.background
	override val title get() = "tixel"
	override val icon get() = "images/icon.png"
	override val scaleMode get() = ScaleMode.SHOW_ALL
	override val clipBorders get() = false

	override suspend fun AsyncInjector.configure()
	{
		mapPrototype { MenuScene() }
		mapPrototype { LevelScene() }
	}
}