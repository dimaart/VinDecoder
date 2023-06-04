package network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/api/vehicles/decodevinvalues/{vin}?format=json")
    fun getData(
        @Path("vin")
        vinNumber: String
    ): Call<myData>
}