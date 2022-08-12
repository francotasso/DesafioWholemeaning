package com.example.demo.controller

import com.example.demo.models.Meteorology
import com.example.demo.services.impl.MeteorologyServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
class MeteorologyController {

    @Autowired
    lateinit var meteorologyServiceAPI: MeteorologyServiceImpl

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
            return meteorologyServiceAPI.getMeteorology(id)
        }
        /* save log on redis */
        return meteorologyServiceAPI.getMeteorology(id)
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