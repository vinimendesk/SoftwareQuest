package br.uepa.softwarequest1

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform