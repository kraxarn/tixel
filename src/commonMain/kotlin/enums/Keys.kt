package enums

import com.soywiz.korev.Key

enum class Keys(val key: Key)
{
	LEFT(Key.LEFT),
	RIGHT(Key.RIGHT),
	UP(Key.UP),
	DOWN(Key.DOWN),

	ENTER(Key.ENTER),
	JUMP(Key.UP),

	PAUSE(Key.ESCAPE),
}