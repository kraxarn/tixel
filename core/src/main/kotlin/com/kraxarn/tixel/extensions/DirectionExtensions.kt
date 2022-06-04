package com.kraxarn.tixel.extensions

import com.kraxarn.tixel.enums.Direction

operator fun Int.contains(value: Direction) =
	(this and value.flag) != 0