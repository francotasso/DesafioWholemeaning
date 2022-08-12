package com.example.demo.services.impl

import com.example.demo.services.api.MeteorologyServiceAPI
import com.example.demo.models.Meteorology
import com.example.demo.repositories.MeteorologyRepository
import com.example.demo.services.exception.MeteorologyNotFoundException
import org.springframework.stereotype.Service

@Service
abstract class MeteorologyServiceImpl(val meteorologyRepository: MeteorologyRepository) : MeteorologyServiceAPI {

    override fun getMeteorology(id: String) = meteorologyRepository.findById(id).orElseThrow {
        MeteorologyNotFoundException("Unable to find meteorology for $id id")
    }

    override fun getAllMeteorologies(): List<Meteorology> = meteorologyRepository.findAll().toList()

    override fun updateMeteorology(id: String, meteorology: Meteorology): Meteorology {
        val meteorology = getMeteorology(id).copy(meteorology.date, meteorology.degrees)
        return meteorologyRepository.save(meteorology)
    }

    override fun createMeteorology(meteorology: Meteorology): Meteorology {
        return meteorologyRepository.save(Meteorology(meteorology.date, meteorology.degrees))
    }

    override fun deleteMeteorology(id: String) = meteorologyRepository.deleteById(id)
}