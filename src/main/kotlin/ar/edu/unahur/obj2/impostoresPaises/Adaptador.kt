package ar.edu.unahur.obj2.impostoresPaises

class Adaptador(val api: RestCountriesAPI) {


    fun convertirAPais(pais: String) = transformarCountryConLimitrofes(buscarCountry(pais))


    fun buscarCountry(pais: String) = api.buscarPaisesPorNombre(pais).first()

    fun transformarCountry(country: Country): Pais {
        return Pais(country.name, country.alpha3Code, country.population.toInt(), (country.area?:10).toDouble(), country.region,
                country.regionalBlocs.map { it.name }, country.languages.map { it.name } )

    }

    fun transformarCountryConLimitrofes(country: Country): Pais {
        val pais = transformarCountry(country)
        pais.paisesLimitrofes = transformarListaAPais(country.borders)
        return pais
    }

    fun transformarListaAPais(list: List<String>): List<Pais> {
        return list.map {api.paisConCodigo(it)}.map { transformarCountry(it) }
    }
}
