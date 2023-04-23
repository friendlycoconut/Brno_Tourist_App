package com.pv239.brnotouristapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.pv239.brnotouristapp.databinding.FragmentMapBinding
import com.pv239.brnotouristapp.district.District
import com.pv239.brnotouristapp.district.DistrictListAdapter
import com.pv239.brnotouristapp.district.DistrictRepository
import com.pv239.brnotouristapp.place.Place

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MapFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private val districtRepository = DistrictRepository()

    private var _binding: FragmentMapBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mMap = googleMap
        showLocation(49.195061, 16.606836)
        loadPlaces().map { place ->
            val marker = mMap.addMarker(MarkerOptions()
                .position(LatLng(place.lat, place.lng))
                .title(place.point))
            marker?.tag = place
        }
        mMap.setOnMarkerClickListener {
            showDetails(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        districtRepository.loadDistricts()

        binding.districtList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val districtListAdapter = DistrictListAdapter {
            showLocation(it.lat, it.lng)
        }

        binding.districtList.adapter = districtListAdapter

        val districtListObserver = Observer<List<District>> {
            districtListAdapter.submitList(it)
        }
        districtRepository.districtList.observe(requireActivity(), districtListObserver)


        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (!isGranted) {
                    Toast.makeText(context, "Permission needed.", Toast.LENGTH_SHORT).show()
                }
            }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding.findLocationButton.setOnClickListener { findLocation() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun findLocation(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token
            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                if (location == null)
                    Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    showLocation(location.latitude, location.longitude)
                }
            }

    }

    private fun showLocation(lat: Double, lng: Double) {
        mMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                    .target(LatLng(lat, lng))
                    .zoom(15f)
                    .tilt(30f)
                    .build()
            )
        )
    }

    private fun showDetails(marker: Marker): Boolean {
        val place: Place = marker.tag as Place
        val action = MapFragmentDirections.actionMapFragmentToPlaceDetails(
            place.point,
            place.address,
            place.imageUrl,
            place.text
        )
        findNavController().navigate(action)
        return true
    }

    private fun loadPlaces(): List<Place> {
        return listOf(
            Place(
                "SOCHY V BRNĚ",
                "Pocta T. A. Edisonovi (Tomáš Medek)",
                49.196,
                16.615,
                "Malinovského náměstí, Brno-město, 602 00 Brno, Česko",
                "https://www.gotobrno.cz/wp-content/uploads/2017/04/dalsi-misto-e1498827225842.jpg",
                "V ulicích Brna narazíte na objekty, o kterých občas nebudete vědět, co si myslet. Některé vyvolávají vášně, kolem jiných se často chodí bez povšimnutí. Vydejte se za poznáním některých soch a instalací v brněnském veřejném prostoru. Rozhodně se zastavte na Moravském náměstí, kde nepřehlédnete umělecké alegorie čtyř Platónových ctností. Níže podrobněji popsané sochy Odvahy a Spravedlnosti doplňuje podlouhlá kašna představující Mírnost. Moudrost či prozíravost symbolizuje bronzový model Brna v roce 1645, kdy čelilo obléhání švédským vojskem. O kousek dál, ale stále na Moravském náměstí, stojí monumentální socha sovětského vojáka. Na Špilberku můžete prohlédnout skrz tvář Jana Skácela a na Zelném trhu zase zvednout hlavu k nahému Mozartovi. Zastavte se také u dvou nepříliš nápadných soch, které vytvořil akademický sochař Jiří Marek, anebo u výrazně nápadného časostroje na náměstí Svobody. A z Malinovského náměstí, kde jsou umístěny obří propletené žárovky, je to jen pár kroků k památníku věnovanému třem československým odbojům, jenž se nachází na začátku Rooseveltovy ulice. Radnice ve spolupráci s Domem umění města Brna připravuje každé dva roky ojedinělý projekt Sochy v ulicích – Brno Art Open. Během letních měsíců zaplní a oživí veřejný prostor nejrůznější instalace současného umění. Úkolem přehlídky je představit brněnskému publiku a návštěvníkům města kvalitní soubor sochařských exponátů od vybraného okruhu umělců. V Brně je však soch a objektů drobné architektury mnohem více. Prozkoumejte je na stránkách Sochařské Brno!"
            ),
            Place(
                "BRNO GREGORA JOHANNA MENDELA",
                "Budova Technického učení (dnes budova Masarykovy univerzity)",
                49.197,
                16.603,
                "Komenského nám. 220/2, Brno-město, 602 00 Brno-Brno-střed, Česko",
                "https://www.gotobrno.cz/wp-content/uploads/2017/06/mendlak_augustini01_monika-hlavacova-pocket-media-s-r-o.jpg",
                "Vydejte se po stopách ikony moderní biologie G. J. Mendela, jenž je díky svým studiím o zákonech dědičnosti právem považován za otce genetiky. Gregor Johann Mendel je jednou z nejvýznamnějších osobností nejen brněnské historie. V roce 1866 sepsal proslulou publikaci Pokusy s rostlinnými hybridy, která položila základy moderní genetice. Světoznámý vědec prožil v Brně podstatnou část života. Johann se narodil 20. července 1822 v moravskoslezských Hynčicích nedaleko dnešního Nového Jičína. Po absolvování gymnázia v Opavě studoval na filozofické fakultě olomoucké univerzity. Na přání matky vstoupil do semináře a v roce 1843 přišel do augustiniánského kláštera sv. Tomáše na Starém Brně a přijal řeholní jméno Gregor. Ve 28 letech, kdy působil jako suplent ve Znojmě, se přihlásil na vídeňskou univerzitu k učitelským zkouškám z přírodopisu a fyziky, u nichž ovšem neuspěl. Mezi lety 1851 a 1853 studoval ve Vídni matematiku, fyziku, chemii, botaniku, zoologii a paleontologii. Po návratu do Brna se od roku 1856 věnoval křížení hrachu a na základě svých pokusů formuloval tři pravidla, později známá pod názvem Mendelovy zákony dědičnosti. V roce 1867 byl zvolen opatem augustiniánského kláštera a k této funkci mu časem přibývaly další, kvůli čemuž byl nucen stále více zanedbávat svou výzkumnou činnost. Posledních deset let života strávil vyčerpávajícím sporem s rakouskou vládou kvůli zvýšené dani z klášterního majetku. Gregor Johann Mendel patřil mezi významné osobnosti města už za svého života, ovšem jeho celosvětový přínos biologii byl doceněn až posmrtně. Tento okruh vás zavede na místa, jež jsou s osobností Gregora Johanna Mendela spojena. Podíváte se mimo jiné do kláštera, kde působil a prováděl své pokusy, do muzeí mapujících jeho život a práci, do budov, kde zněly jeho přednášky, nebo třeba na místo věčného odpočinku."
            ),
            Place(
                "VELKÝ OKRUH",
                "Kapucínské náměstí",
                49.192,
                16.61,
                "Kapucínské náměstí, Brno-město, Brno, Česko",
                "https://www.gotobrno.cz/wp-content/uploads/2017/06/spilberk-leto-pavel-gabzdyl.jpg",
                "Pokud máte dost času a sil, vydejte se na velký okruh centrem Brna. Projděte si některá z nejzajímavějších a nejvýznamnějších míst. Začnete nedaleko hlavního vlakového nádraží – na Kapucínském náměstí, kde můžete navštívit brněnské mumie, odtud vyjdete ke katedrále na Petrově a do Denisových sadů. Překonáte rušné Šilingrovo náměstí a Husovu ulici a přes Špilberk se dostanete k pozoruhodnému pravoslavnému kostelu. Na cestě zpět do historického jádra města načerpáte síly a informace v novém infocentru TO JE Brno v Panenské ulici. Hned vedle je Dominikánské náměstí s Mincmistrovským sklepem, Novou radnicí a kostelem sv. Michala. Projdete pár dalších svérázných náměstí a na každém z nich objevíte něco zajímavého, například časostroj, na kterém není poznat čas, přerostlou jezdeckou sochu, místo, kde se pije pivo vsedě na chodníku, nebo obří propletené žárovky. A pokud budete chtít po této procházce odjet z města pryč, na nádraží to máte opět kousek."
            )
        );
    }
}