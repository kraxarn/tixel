package com.kraxarn.tixel.extensions

import com.kraxarn.tixel.objects.Level

val Level.isFinal get() = this.name == "Final Boss"