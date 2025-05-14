package com.example.supabasetest.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.supabasetest.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.text.removePrefix

class MySupabaseClient {

    lateinit var client: SupabaseClient
    lateinit var storage: Storage

    private val supabaseUrl = BuildConfig.SUPABASE_URL
    private val supabaseKey = BuildConfig.SUPABASE_KEY

    constructor() {
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
            install(Storage)
        }
        storage = client.storage
    }

    suspend fun getAllStudents(): List<Student> {
        return client.from("Student").select().decodeList<Student>()
    }

    suspend fun getStudent(id: String): Student {
        return client.from("Student").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<Student>()
    }

    suspend fun insertStudent(name: String, mark: Double, imageName: String) {
        val student = Student(name = name, mark = mark, image = imageName)
        client.from("Student").insert(student)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage(imageFile: ByteArray): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val imageName = storage.from("images").upload(path = "image_${fechaHoraActual.format(formato)}.png", data = imageFile)
        return buildImageUrl(imageFileName = imageName.path)
    }

    fun buildImageUrl(imageFileName: String) = "${this.supabaseUrl}/storage/v1/object/public/images/${imageFileName}"

    suspend fun updateStudent(id: String, name: String, mark: Double, imageName: String, imageFile: ByteArray) {
        val imageName = storage.from("images").update(path = imageName, data = imageFile)
        client.from("Student").update({
            set("name", name)
            set("mark", mark)
            set("image", buildImageUrl(imageFileName = imageName.path))
        }) {
            filter {
                eq("id", id)
            }
        }
    }

    suspend fun deleteStudent(id: String) {
        client.from("Student").delete {
            filter {
                eq("id", id)
            }
        }
    }

    suspend fun deleteImage(imageName: String){
        val imgName = imageName.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        client.storage.from("images").delete(imgName)
    }
}