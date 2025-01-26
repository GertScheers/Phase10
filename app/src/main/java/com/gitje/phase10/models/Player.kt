package com.gitje.phase10.models

import java.util.UUID

class Player(var name: String, var key: String = UUID.randomUUID().toString()) {
    var stage: Int = 1
    var points: Int = 0
}