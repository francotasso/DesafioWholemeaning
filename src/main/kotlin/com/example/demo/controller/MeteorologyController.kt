package com.example.demo.controller

import com.example.demo.models.Meteorology
import com.example.demo.models.tomorrowIO.MeteorologyTomorrowIO
import com.example.demo.services.api.MeteorologyServiceAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
class MeteorologyController {

    @Autowired
    lateinit var meteorologyServiceAPI: MeteorologyServiceAPI

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<Meteorology> {
        val meteorologies = meteorologyServiceAPI.getAllMeteorologies()
        return meteorologies
    }

    @GetMapping("/meteorology/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMeteorologyById(@PathVariable id: String): Meteorology {
        if ((0..1).random() > 0.2) {
            val urlTomorrow = TomorrowIO.URL.replace("#API_KEY", TomorrowIO.API_KEY).replace("#ID", id)
            val restTemplate: RestTemplate = RestTemplate()
            val response: ResponseEntity<MeteorologyTomorrowIO> =
                restTemplate.getForEntity<MeteorologyTomorrowIO>(urlTomorrow, MeteorologyTomorrowIO::class.java)
            val data = response.getBody()
            val timelines = data?.timelines
            val interval = timelines?.get(0)?.intervals?.get(0)
            val meteorology: Meteorology = Meteorology(interval?.startTime, interval?.values?.temperature.toString())
            return meteorology
        } else {
            /* save log on redis */
            return getMeteorologyById(id)
        }
    }

    @PostMapping("/save")
    fun save(@RequestBody meteorology: Meteorology): ResponseEntity<Meteorology> {
        var obj = meteorologyServiceAPI.createMeteorology(meteorology)
        return ResponseEntity<Meteorology>(meteorology, HttpStatus.OK)
    }

    @GetMapping("/delete/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Meteorology> {
        val meteorology = meteorologyServiceAPI.getMeteorology(id)
        if (meteorology != null) {
            meteorologyServiceAPI.deleteMeteorology(id)
        } else {
            return ResponseEntity<Meteorology>(HttpStatus.NO_CONTENT)
        }

        return ResponseEntity<Meteorology>(meteorology, HttpStatus.OK)
    }
}

object TomorrowIO {
    const val URL =
        "https://api.tomorrow.io/v4/timelines?location=#ID&fields=temperature&timesteps=current&units=metric&apikey=#API_KEY"
    const val API_KEY = "MN02qNQATaG5zml4DLj3FGe3WNt0xf58"
}