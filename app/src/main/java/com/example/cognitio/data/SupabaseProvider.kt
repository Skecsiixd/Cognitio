package com.example.cognitio.data

import com.example.cognitio.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseProvider {

    val client = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_KEY
    ) {
        install(Auth)
        install(Postgrest)
    }

    /** Biztosítja, hogy van bejelentkezett felhasználó. Ha nincs, belép az egy userrel. */
    suspend fun ensureSignedIn() {
        client.auth.awaitInitialization()          // betölti az elmentett munkamenetet
        if (client.auth.currentSessionOrNull() == null) {
            client.auth.signInWith(Email) {
                email = BuildConfig.APP_USER_EMAIL
                password = BuildConfig.APP_USER_PASSWORD
            }
        }
    }
}