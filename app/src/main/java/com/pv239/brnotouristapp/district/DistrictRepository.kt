package com.pv239.brnotouristapp.district

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DistrictRepository {

    private val _districtList = MutableLiveData<List<District>>()
    val districtList: LiveData<List<District>> = _districtList

    fun loadDistricts() {
        val exampleDistricts = listOf(
            District(
                "Střed",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Stred.jpg/bbcca6b9-bdb3-7707-3453-579ab518a68e",
                49.194167,
                16.608611
            ),
            District(
                "Sever",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Sever.jpg/766cdcfb-8eff-df97-1921-6ccb9857b759",
                49.215,
                16.626389
            ),
            District(
                "Jih",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Jih.jpg/2f458e15-24b4-7f9f-4b61-e604bfbfb6a3",
                49.1745,
                16.6228
            ),
            District(
                "Bohunice",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Bohunice.jpg/853e7e08-2c3e-1887-dd0e-5afd276ba8e9",
                49.169444,
                16.579167
            ),
            District(
                "Bosonohy",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Bosonohy.jpg/cf35e406-6728-ed15-1c46-6cb1f6b3fe84",
                49.176111,
                16.529167
            ),
            District(
                "Bystrc",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Bystrc.jpg/4d9218c5-d39e-0495-9693-8d4b560f735c",
                49.224722,
                16.523889
            ),
            District(
                "Chrlice",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Chrlice.jpg/583a0470-a3c5-a0f0-4902-0eaa60655eb7",
                49.134167,
                16.652778
            ),
            District(
                "Ivanovice",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Ivanovice.jpg/98511ded-7789-fab0-7822-795ac40b4b3b",
                49.264722,
                16.564722
            ),
            District(
                "Jehnice",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Jehnice.jpg/0a414720-b2a4-a9a2-aa1a-a8cdb317b14d",
                49.271111,
                16.596111
            ),
            District(
                "Jundrov",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Jundrov.jpg/f39174a8-a5a5-4895-f4c2-0048270ae40f",
                49.207778,
                16.555278
            ),
            District(
                "Kníničky",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Kninicky.jpg/0f17603a-b46d-8a74-be7f-738b2f49e969",
                49.236111,
                16.527778
            ),
            District(
                "Kohoutovice",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Kohoutovice.jpg/ef89a4be-4ce9-466d-88da-e20f5b8288d5",
                49.1933,
                16.5369
            ),
            District(
                "Komín",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Komin.jpg/911acc8d-c6b7-d5e3-0200-113762bfbe8a",
                49.220556,
                16.554167
            ),
            District(
                "Královo Pole",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Kralovo_Pole.jpg/a6879eed-acaf-81a6-12eb-872e2e52764d",
                49.223611,
                16.594444
            ),
            District(
                "Líšeň",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Lisen.jpg/d095951a-fa02-9e7a-7c62-69e1818fb88f",
                49.2075,
                16.6861
            ),
            District(
                "Maloměřice",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Malomerice_Obrany.jpg/60c38655-4e23-56d2-5706-0c31d5315a1a",
                49.223889,
                16.643889
            ),
            District(
                "Medlánky",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Medlanky.jpg/a1e7babc-198a-5f5e-a41a-4335bd4271fd",
                49.241667,
                16.573889
            ),
            District(
                "Nový Lískovec",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Novy_Liskovec.jpg/2ede97f4-5a02-7822-d3fb-18b240ff92c2",
                49.177778,
                16.559444
            ),
            District(
                "Ořešín",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Oresin.jpg/e0efdec7-4915-6abb-c7bd-934b64662a15",
                49.276667,
                16.607222
            ),
            District(
                "Slatina",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Slatina.jpg/03735011-1117-1aef-c5ff-d096ef84ef90",
                49.178333,
                16.684722
            ),
            District(
                "Starý Lískovec",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Stary_Liskovec.jpg/fcf93de6-1d0e-6e0d-91bd-3be792c1aca8",
                49.169167,
                16.562222
            ),
            District(
                "Tuřany",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Turany.jpg/f3e11cd0-ceef-e400-bc3f-2d58ace9b676",
                49.1472,
                16.6631
            ),
            District(
                "Vinohrady",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Vinohrady.jpg/a4021c5c-8bf8-6026-aa41-bbf46eeb6ef1",
                49.2058,
                16.6583
            ),
            District(
                "Útěchov",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Utechov.jpg/dac2e68c-b8c1-a33e-90e0-860825384f00",
                49.287222,
                16.631111
            ),
            District(
                "Černovice",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Cernovice.jpg/930ae7ea-fbef-1fe6-2e25-e22b3eb73625",
                49.182778,
                16.636944
            ),
            District(
                "Řečkovice",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Reckovice_Mokra_Hora.jpg/f27189a6-a5c4-2bd8-a853-b4ba82a83144",
                49.2525,
                16.5825
            ),
            District(
                "Žabovřesky",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Zabovresky.jpg/a96509ac-2aeb-c776-a82d-72910435a537",
                49.214444,
                16.575278
            ),
            District(
                "Žebětín",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Zebetin.jpg/06ad3b72-b003-9005-5882-d90e472f5b25",
                49.206944,
                16.487778
            ),
            District(
                "Židenice",
                "https://www.brno.cz/documents/20121/0/heraldika_merkantil_Brno-Zidenice.jpg/642841b4-551b-3ee6-03c8-fde312a7b852",
                49.196111,
                16.6475
            )
        )
        _districtList.value = exampleDistricts
    }
}