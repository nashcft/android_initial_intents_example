package com.nashcft.android.initial.intents.example

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nashcft.android.initial.intents.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "test")
            }
            val intents = packageManager.queryIntentActivities(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            ).mapIndexed { index, resolveInfo ->
                Intent().apply {
                    setPackage(resolveInfo.activityInfo.packageName)
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "initial intent: $index")
                }
            }

            startActivity(Intent.createChooser(
                when {
                    Build.VERSION.SDK_INT > Build.VERSION_CODES.P || Build.VERSION.CODENAME == "Q" -> intent
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> Intent()
                    else -> intents.first()
                },
                null
            ).apply {
                putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toTypedArray())
            })
        }
    }
}
