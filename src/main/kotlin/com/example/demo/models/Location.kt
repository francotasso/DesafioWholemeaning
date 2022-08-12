package com.example.demo.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash("Location")
data class Location(
    @Indexed val label: String
) {
    @get:Id
    var id: String? = null
}

object Coordinates {
    const val SANTIAGO = "-33.45694,-70.64827"
    const val ZURICH = "47.36667,8.55"
    const val AUCKLAND = "-36.84853,174.76349"
    const val SIDNEY = "-33.86785,151.20732"
    const val LONDON = "51.50853,-0.12574"
    const val GEORGIA = "33.24788,-83.44116"
}