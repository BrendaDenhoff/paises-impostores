package ar.edu.unahur.obj2.impostoresPaises

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.*

class PaisTest: DescribeSpec({
    val chile = Pais("Chile", "CHL", 18191900, 756102.0, "America", listOf("PA", "USAN"), listOf("Español"))
    val paraguay = Pais("Paraguay", "PRY", 6854536, 406752.0, "America", listOf("USAN"), listOf("Español"))
    val argentina = Pais("Argentina", "ARG", 43590400, 2780400.0, "America", listOf("USAN"), listOf("Español"))
    val malta = Pais("Malta", "MLT", 425384, 316.0, "Europa", listOf("EU"), listOf("Maltese", "Ingles"))
    val bolivia = Pais("Bolivia", "BOL", 10985059, 1098581.0, "America", listOf("USAN"), listOf("Español", "Aymara", "Quechua"))
    val brasil = Pais("Brasil", "BRA", 206135893, 8515767.0, "America", listOf("USAN"), listOf("Español", "Portugues"))
    val cuba = Pais("Cuba", "CUB", 11239004, 109884.0, "America", listOf(), listOf("Español"))

    chile.paisesLimitrofes = listOf(argentina, bolivia)
    paraguay.paisesLimitrofes = listOf(argentina, bolivia, brasil)
    argentina.paisesLimitrofes = listOf(chile, bolivia, paraguay, brasil)
    malta.paisesLimitrofes = listOf()
    bolivia.paisesLimitrofes = listOf(argentina, chile, brasil, paraguay)
    brasil.paisesLimitrofes = listOf(argentina, bolivia, paraguay)
    cuba.paisesLimitrofes = listOf()

    describe("Paises") {
        it("paises plurinacionales") {
            chile.esPlurinacional().shouldBeFalse()
            paraguay.esPlurinacional().shouldBeFalse()
            argentina.esPlurinacional().shouldBeFalse()
            malta.esPlurinacional().shouldBeTrue()
            bolivia.esPlurinacional().shouldBeTrue()
            brasil.esPlurinacional().shouldBeTrue()
            cuba.esPlurinacional().shouldBeFalse()
        }
        it("es una isla") {
            chile.esUnaIsla().shouldBeFalse()
            paraguay.esUnaIsla().shouldBeFalse()
            argentina.esUnaIsla().shouldBeFalse()
            malta.esUnaIsla().shouldBeTrue()
            bolivia.esUnaIsla().shouldBeFalse()
            brasil.esUnaIsla().shouldBeFalse()
            cuba.esUnaIsla().shouldBeTrue()
        }
        it("densidad") {
            chile.densidadDePoblacion().shouldBe(24.060113582558966)
            paraguay.densidadDePoblacion().shouldBe(16.851880261191095)
            argentina.densidadDePoblacion().shouldBe(15.677744209466264)
            malta.densidadDePoblacion().shouldBe(1346.1518987341772)
            bolivia.densidadDePoblacion().shouldBe(9.999316390871497)
            brasil.densidadDePoblacion().shouldBe(24.20638011819722)
            cuba.densidadDePoblacion().shouldBe(102.28062320265006)
        }

        it("vecino mas poblado") {
            chile.vecinoMasPoblado().shouldBe(argentina)
            paraguay.vecinoMasPoblado().shouldBe(brasil)
            argentina.vecinoMasPoblado().shouldBe(brasil)
            malta.vecinoMasPoblado().shouldBe(malta)
            bolivia.vecinoMasPoblado().shouldBe(brasil)
            brasil.vecinoMasPoblado().shouldBe(brasil)
            cuba.vecinoMasPoblado().shouldBe(cuba)
        }

        it("son limitrofes") {
            argentina.sonLimitrofes(chile).shouldBeTrue()
            paraguay.sonLimitrofes(chile).shouldBeFalse()
            malta.sonLimitrofes(bolivia).shouldBeFalse()
            bolivia.sonLimitrofes(brasil).shouldBeTrue()
        }

        it("necesitan traduccion") {
            argentina.necesitanTraduccion(bolivia).shouldBeFalse()
            argentina.necesitanTraduccion(malta).shouldBeTrue()
            chile.necesitanTraduccion(brasil).shouldBeFalse()
            malta.necesitanTraduccion(paraguay).shouldBeTrue()
        }

        it("son potencialmente aliados") {
            malta.sonPotencialesAliados(argentina).shouldBeFalse()
            bolivia.sonPotencialesAliados(chile).shouldBeTrue()
            chile.sonPotencialesAliados(paraguay).shouldBeTrue()
        }
    }
})

class ObservatorioTest: DescribeSpec({
        describe("Observatorio") {
        val api = mockk<RestCountriesAPI>()
        val Observatorio = Observatorio(api)
        val espaniol = Language("español")
        val ingles = Language("ingles")
        val chino = Language("chino")
        val unasur = RegionalBloc("UNASUR", "Union de Naciones Sudamericanas")
        val eu = RegionalBloc("EU", "EU")
        val oc = RegionalBloc("oc", "oc")
        val afr = RegionalBloc("AFR", "AFR")
        val asi = RegionalBloc("as", "as")
        every {api.todosLosPaises()} returns listOf(
            Country("Argentina",
                "ARG",
                "Buenos Aires",
                "America",
                40000000,
                32424.0,
                listOf("BLV"),
                listOf(espaniol),
                listOf(unasur)),
            Country(
                "Espania",
                "ESP",
                "Madrid",
                "Europa",
                12312312,
                32424.0,
                listOf(),
                listOf(espaniol),
                listOf(eu)),
            Country(
                "Australia",
                "AUS",
                "Canberra",
                "Ocenia",
                434342,
                89.0,
                listOf(),
                listOf(ingles),
                listOf(oc)),
            Country(
                "Egipto",
                "EGY",
                "El cario",
                "Africa",
                40893829,
                695254.0,
                listOf(),
                listOf(ingles),
                listOf(afr)),
            Country("Bolivia",
                "BLV",
                "La paz",
                "America",
                978237,
                1623.0,
                listOf("ARG"),
                listOf(espaniol),
                listOf(unasur)),
            Country(
                "China",
                "CHN",
                "Pekin",
                "Asia",
                7823479901,
                23423145342.0,
                listOf(),
                listOf(chino),
                listOf(asi)),
            Country(
                "Mexico",
                "MXC",
                "DF",
                "America",
                34568634,
                32412243.0,
                listOf("CHN"),
                listOf(espaniol, ingles),
                listOf(unasur)),
            Country(
                "Nigeria",
                "NIG",
                "Abuya",
                "Africa",
                7823479901,
                23423145342.0,
                listOf("MXC"),
                listOf(ingles),
                listOf(RegionalBloc("AFR", "AFR")))
        )


        every { api.buscarPaisesPorNombre("Argentina")} returns listOf(
            Country("Argentina",
                "ARG",
                "Buenos Aires",
                "America",
                40000000,
                32424.0,
                listOf("BLV"),
                listOf(espaniol),
                listOf(unasur))
        )
        every {api.buscarPaisesPorNombre("Espania")} returns listOf(
            Country(
                "Espania",
                "ESP",
                "Madrid",
                "Europa",
                12312312,
                32424.0,
                listOf(),
                listOf(espaniol),
                listOf(eu))
        )
        every {api.buscarPaisesPorNombre("Australia")} returns listOf(
            Country(
                "Australia",
                "AUS",
                "Canberra",
                "Ocenia",
                434342,
                89.0,
                listOf(),
                listOf(ingles),
                listOf(oc))
        )
        every {api.buscarPaisesPorNombre("Egipto")} returns listOf(
            Country(
                "Egipto",
                "EGY",
                "El cario",
                "Africa",
                40893829,
                695254.0,
                listOf(),
                listOf(ingles),
                listOf(afr))
        )
        every {api.buscarPaisesPorNombre("Bolivia")} returns listOf(
            Country("Bolivia",
                "BLV",
                "La paz",
                "America",
                978237,
                1623.0,
                listOf("ARG"),
                listOf(espaniol),
                listOf(unasur))
        )
        every {api.buscarPaisesPorNombre("China")} returns listOf(
            Country(
                "China",
                "CHN",
                "Pekin",
                "Asia",
                7823479901,
                23423145342.0,
                listOf("MXC"),
                listOf(chino),
                listOf(asi))
        )
        every {api.buscarPaisesPorNombre("Mexico")} returns listOf(
            Country(
                "Mexico",
                "MXC",
                "DF",
                "America",
                34568634,
                32412243.0,
                listOf("CHN", "NIG"),
                listOf(espaniol, ingles),
                listOf(unasur))
        )
        every {api.buscarPaisesPorNombre("Nigeria")} returns listOf(
            Country(
                "Nigeria",
                "NIG",
                "Abuya",
                "Africa",
                7823479901,
                23423145342.0,
                listOf("MXC"),
                listOf(ingles),
                listOf(RegionalBloc("AFR", "AFR"))
            )
        )

            ////////////////////////////////////////////////////////////

            every { api.paisConCodigo("ARG")} returns (
                Country("Argentina",
                    "ARG",
                    "Buenos Aires",
                    "America",
                    40000000,
                    32424.0,
                    listOf("BLV"),
                    listOf(espaniol),
                    listOf(unasur))
            )
            every {api.paisConCodigo("ESP")} returns (
                Country(
                    "Espania",
                    "ESP",
                    "Madrid",
                    "Europa",
                    12312312,
                    32424.0,
                    listOf(),
                    listOf(espaniol),
                    listOf(eu))
            )
            every {api.paisConCodigo("AUS")} returns (
                Country(
                    "Australia",
                    "AUS",
                    "Canberra",
                    "Ocenia",
                    434342,
                    89.0,
                    listOf(),
                    listOf(ingles),
                    listOf(oc))
            )
            every {api.paisConCodigo("EGY")} returns (
                Country(
                    "Egipto",
                    "EGY",
                    "El cario",
                    "Africa",
                    40893829,
                    695254.0,
                    listOf(),
                    listOf(ingles),
                    listOf(afr))
            )
            every {api.paisConCodigo("BLV")} returns (
                Country("Bolivia",
                    "BLV",
                    "La paz",
                    "America",
                    978237,
                    1623.0,
                    listOf("ARG"),
                    listOf(espaniol),
                    listOf(unasur))
            )
            every {api.paisConCodigo("CHN")} returns (
                Country(
                    "China",
                    "CHN",
                    "Pekin",
                    "Asia",
                    7823479901,
                    23423145342.0,
                    listOf("MXC"),
                    listOf(chino),
                    listOf(asi))
            )
            every {api.paisConCodigo("MXC")} returns (
                Country(
                    "Mexico",
                    "MXC",
                    "DF",
                    "America",
                    34568634,
                    32412243.0,
                    listOf("CHN", "NIG"),
                    listOf(espaniol, ingles),
                    listOf(unasur))
            )
            every {api.paisConCodigo("NIG")} returns (
                Country(
                    "Nigeria",
                    "NIG",
                    "Abuya",
                    "Africa",
                    7823479901,
                    23423145342.0,
                    listOf("MXC"),
                    listOf(ingles),
                    listOf(RegionalBloc("AFR", "AFR"))
                )
            )

        it("son limitrofes") {
            Observatorio.sonLimitrofes("Argentina", "Bolivia").shouldBeTrue()
            Observatorio.sonLimitrofes("Australia", "Argentina").shouldBeFalse()
            Observatorio.sonLimitrofes("Mexico", "China").shouldBeTrue()
        }

        it("necesitan traduccion") {
            Observatorio.necesitanTraduccion("Argentina", "Espania").shouldBeFalse()
            Observatorio.necesitanTraduccion("Nigeria", "Australia").shouldBeFalse()
            Observatorio.necesitanTraduccion("Nigeria", "Mexico").shouldBeFalse()
        }
        it("son potencialesAliados") {
            Observatorio.sonPotencialesAliados("Argentina", "Nigeria").shouldBeFalse()
            Observatorio.sonPotencialesAliados("Bolivia", "Mexico").shouldBeTrue()
            Observatorio.sonPotencialesAliados("Espania", "China").shouldBeFalse()
        }
        it("iso de cinco paises con mayor densidad de poblacion") {
            Observatorio.isoDeCincoPaisesConMayorDensidadPoblacion().shouldBe(listOf("AUS", "ARG", "BLV", "ESP", "EGY"))
        }

        it("continente con mas paises plurinacionales")
        {
            Observatorio.continenteConMasPaisesPlurinaciones().shouldBe("America")
        }
        it("Densidad poblacion es los paises que son islas")
        {
            Observatorio.promedioDeDensidadPoblacionEnIslas().shouldBe(7153)
        }
    }
})

