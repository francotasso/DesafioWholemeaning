package com.example.demo.services.exception

import java.lang.Exception

class MeteorologyNotFoundException(override val message: String): Exception(message)