package ar.edu.unahur.obj2.impostoresPaises

class Pais(
    val nombre: String, val codigoIso3: String, val poblacion: Int, val superficie: Double, val continente: String,
    var bloquesRegionales: List<String>, var lenguasOficiales: List<String>)
{
    var paisesLimitrofes: List<Pais> = listOf()

    fun esPlurinacional()= this.lenguasOficiales.size>1

    fun esUnaIsla()= this.paisesLimitrofes.isEmpty()

    fun densidadDePoblacion() = poblacion / superficie

    fun vecinoMasPoblado(): Pais = (paisesLimitrofes + this).maxByOrNull { it.poblacion }!!

    fun sonLimitrofes(otroPais: Pais) = this.paisesLimitrofes.any{it.nombre == otroPais.nombre}

    fun necesitanTraduccion(otroPais: Pais) = this.lenguasOficiales.intersect(otroPais.lenguasOficiales).isEmpty()

    fun sonPotencialesAliados(otroPais: Pais) = !this.necesitanTraduccion(otroPais) && this.compartenBloquesRegionales(otroPais)

    fun compartenBloquesRegionales(otroPais: Pais) = this.bloquesRegionales.intersect(otroPais.bloquesRegionales).isNotEmpty()

}






