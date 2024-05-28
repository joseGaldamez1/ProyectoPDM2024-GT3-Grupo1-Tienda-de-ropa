package sv.edu.ues.proyectopdm2024gt3grupo1

import retrofit2.http.GET

interface PostApiService {
    @GET("products/category/men's%20clothing")
    suspend fun getuserPost(): ArrayList<PostModel>
}