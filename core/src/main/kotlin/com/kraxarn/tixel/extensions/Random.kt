package com.kraxarn.tixel.extensions

import kotlin.random.Random

fun Random.nextFloat(from: Float, until: Float) =
	from + (this.nextFloat() * (until - from))