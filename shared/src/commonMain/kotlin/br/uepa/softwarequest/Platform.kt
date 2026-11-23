package br.uepa.softwarequest

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform