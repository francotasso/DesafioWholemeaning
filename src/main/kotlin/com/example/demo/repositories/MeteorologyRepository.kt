package com.example.demo.repositories

import com.example.demo.models.Meteorology
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MeteorologyRepository : CrudRepository<Meteorology, String>