package com.example.demo.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDate

@RedisHash("Meteorology")
data class Meteorology(
    @Indexed val date: LocalDate?,
    // val location: Location,
    val degrees: String
) {
    @get:Id
    val id: String? = null
}