package ar.edu.unahur.obj2.impostoresPaises


class Observatorio(val api: RestCountriesAPI) {
    val adaptador = Adaptador(api)

    fun buscarPais(pais: String) = adaptador.convertirAPais(pais)

    fun sonLimitrofes(pais1: String, pais2: String) = buscarPais(pais1).sonLimitrofes(buscarPais(pais2))

    fun necesitanTraduccion(pais1: String, pais2: String) = buscarPais(pais1).necesitanTraduccion(buscarPais(pais2))

    fun sonPotencialesAliados(pais1: String, pais2: String) = buscarPais(pais1).sonPotencialesAliados(buscarPais(pais2))

    fun isoDeCincoPaisesConMayorDensidadPoblacion(): List<String> {
        return listaTodosLosPaises().sortedByDescending { it.densidadDePoblacion() }.take(5).map { it.codigoIso3 }

    }

    fun listaTodosLosPaises() = api.todosLosPaises().map { adaptador.transformarCountry(it) }


    fun promedioDeDensidadPoblacionEnIslas(): Int {

        return listaDeIslas().sumBy { it.densidadDePoblacion().toInt() }
    }

    fun listaDeIslas() = listaTodosLosPaises().filter {it.esUnaIsla()}

    fun listaConPaisesConContinente(continente: String) =
        listaTodosLosPaises().filter { it.continente == continente }

    fun cantidadPaisesPlurinacionales(continente: String) =
        listaConPaisesConContinente(continente).count { it.esPlurinacional() }

    fun continenteConMasPaisesPlurinaciones() =
        listaTodosLosPaises().groupBy { it.continente }.mapValues { cantidadPaisesPlurinacionales(it.key) }.maxByOrNull { it.value }?.key
}