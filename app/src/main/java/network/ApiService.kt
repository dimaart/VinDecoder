package network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // Url endpoint vin is dynamic
    @GET("/api/vehicles/decodevinvalues/{vin}?format=json")
    fun getData(
        @Path("vin")
        vinNumber: String
    ): Call<myData>
}