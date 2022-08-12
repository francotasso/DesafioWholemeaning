package com.example.demo.repositories.impl

import com.example.demo.models.Meteorology
import com.example.demo.repositories.MeteorologyRepository
import com.example.demo.models.tomorrowIO.MeteorologyTomorrowIO
import org.springframework.web.client.RestTemplate
import org.springframework.http.ResponseEntity

abstract class MeteorologyRepositoryImpl(val meteorologyRepository: MeteorologyRepository) : MeteorologyRepository {
    override fun getMeteorology(id: String): Meteorology {
        val urlTomorrow = TomorrowIO.URL.replace("#API_KEY", TomorrowIO.API_KEY).replace("#ID", id)
        val restTemplate: RestTemplate = RestTemplate()
        val response: ResponseEntity<MeteorologyTomorrowIO> =
            restTemplate.getForEntity<MeteorologyTomorrowIO>(urlTomorrow, MeteorologyTomorrowIO::class.java)
        val data = response.getBody()
        val timelines = data?.timelines
        val interval = timelines?.get(0)?.intervals?.get(0)
        val meteorology: Meteorology = Meteorology(interval?.startTime, interval?.values?.temperature.toString())
        return meteorology
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

object TomorrowIO {
    const val URL =
        "https://api.tomorrow.io/v4/timelines?location=#ID&fields=temperature&timesteps=current&units=metric&apikey=#API_KEY"
    const val API_KEY = "MN02qNQATaG5zml4DLj3FGe3WNt0xf58"
}