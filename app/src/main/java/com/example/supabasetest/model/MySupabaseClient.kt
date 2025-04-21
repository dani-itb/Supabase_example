package com.example.supabasetest.model

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

class MySupabaseClient() {

    lateinit var client: SupabaseClient

    constructor(supabaseUrl: String, supabaseKey: String): this(){
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
        }
    }

    suspend fun getAllStudents(): List<Student> {
        return client.from("Student").select().decodeList<Student>()
    }

    suspend fun getStudent(id: String): Student{
        return client.from("Student").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<Student>()
    }

    suspend fun insertStudent(student: Student){
        client.from("Student").insert(student)
    }

    suspend fun updateStudent(id: String, name: String, mark: Double){
        client.from("Student").update({
            set("name", name)
            set("mark", mark)
        }) {
            filter {
                eq("id", id)
            }
        }
    }

    suspend fun deleteStudent(id: String){
        client.from("Student").delete{
            filter {
                eq("id", id)
            }
        }
    }
}