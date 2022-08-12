package com.example.demo.services.api

import com.example.demo.models.Meteorology
import com.example.demo.services.exception.MeteorologyNotFoundException

interface MeteorologyServiceAPI {
    @Throws(MeteorologyNotFoundException::class)
    fun getMeteorology(id: String): Meteorology

    @Throws(MeteorologyNotFoundException::class)
    fun getAllMeteorologies(): List<Meteorology>

    @Throws(MeteorologyNotFoundException::class)
    fun updateMeteorology(id: String, meteorology: Meteorology): Meteorology

    fun createMeteorology(meteorology: Meteorology): Meteorology

    @Throws(MeteorologyNotFoundException::class)
    fun deleteMeteorology(id: String)
}