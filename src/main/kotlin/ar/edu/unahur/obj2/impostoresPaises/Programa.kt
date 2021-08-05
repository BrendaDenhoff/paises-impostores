package ar.edu.unahur.obj2.impostoresPaises

// Acá encapsulamos el manejo de la consola real, desacoplandolo del programa en sí
object Consola {
  fun leerLinea() = readLine()
  fun escribirLinea(contenido: String) {
    println(contenido)
  }
}


object Programa {

    var entradaSalida = Consola
    var api = RestCountriesAPI()
    var observatorio = Observatorio(api)

    fun iniciar() {

        var opcionElegida: Int? = null
        while (opcionElegida != 6) {
            entradaSalida.escribirLinea(
                "Elija la informacion que desea: \n1-Informacion de un pais\n2-Informacion entre dos paises\n3-Los 5 Codigos ISO de los paises mas poblados" +
                        "\n4-Continente con mayor numero de plurinacionales\n5-Conocer el promedio de densidad poblacional de los paises-isla\n6-Salir"
            )

            opcionElegida = entradaSalida.leerLinea()?.toIntOrNull()
            if (opcionElegida == null) {
                entradaSalida.escribirLinea("Porfavor ingresar una opcion")
            }
            when (opcionElegida) {
                1 -> {informacionDeUnPais()}
                2 -> {informacionEntreDosPaises()}
                3 -> {codigoIso()}
                4 -> {continenteConPlurinacional()}
                5 -> {promedioDeDensidadPoblacional()}
                6 -> {salir()}
                else->{ entradaSalida.escribirLinea("Ingresar una opcion correcta")}
            }
        }
    }

    fun informacionDeUnPais()
    {
        var pais = elegirPais()
        entradaSalida.escribirLinea(
            "Informacion del pais: ${pais.nombre} \nEs plurinacional: ${pais.esPlurinacional()} \nEs una isla: ${pais.esUnaIsla()}" +
                    "\nDensidad de poblacion: ${pais.densidadDePoblacion()} \nVecino mas poblado: ${pais.vecinoMasPoblado().nombre}"
        )

    }

    fun informacionEntreDosPaises() {
        var pais1 = elegirPais()
        var pais2 = elegirPais()
        entradaSalida.escribirLinea(
            "Informacion entre: ${pais1.nombre} y ${pais2.nombre} \nNecesitan traduccion: ${observatorio.necesitanTraduccion(pais1.nombre, pais2.nombre)}" +
                    "\nSon potencialmente aliados ${observatorio.sonPotencialesAliados(pais1.nombre, pais2.nombre)} \nSon limitrofes: ${observatorio.sonLimitrofes(pais1.nombre, pais2.nombre)}"
        )
    }

    fun codigoIso() {
        entradaSalida.escribirLinea("Los 5 codigos ISO de los paises con mayor densidad de poblacion son: ${observatorio.isoDeCincoPaisesConMayorDensidadPoblacion()}")
    }

    fun continenteConPlurinacional() {
        entradaSalida.escribirLinea("Continente con mayor numero de plurinacionalidad: ${observatorio.continenteConMasPaisesPlurinaciones()}")
    }

    fun promedioDeDensidadPoblacional() {
        entradaSalida.escribirLinea("Promedio de densidad poblacional de los paises que son islas: ${observatorio.promedioDeDensidadPoblacionEnIslas()}")
    }
    fun salir() {
        entradaSalida.escribirLinea("Gracias vuelva prontos")
    }

    fun elegirPais(): Pais {
        entradaSalida.escribirLinea("Ingrese el nombre del pais")
        var pais = entradaSalida.leerLinea()
        while (pais?.isBlank() == true || !paisEncontrado(pais!!)) {
            while (pais?.isBlank() == true) {
                entradaSalida.escribirLinea("Porfavor ingrese un pais")
                pais = entradaSalida.leerLinea()
            }
            if (!paisEncontrado(pais!!)) {
                entradaSalida.escribirLinea("Porfavor ingrese un pais que exista")
                pais = entradaSalida.leerLinea()
            }
        }
        return observatorio.buscarPais(pais!!)
    }

    fun paisEncontrado(pais: String): Boolean {
        return api.buscarPaisesPorNombre(pais).isNotEmpty()
    }
}