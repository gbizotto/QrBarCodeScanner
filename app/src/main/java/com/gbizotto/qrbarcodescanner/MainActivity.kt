package com.gbizotto.qrbarcodescanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Bluebird
    private val bluebirdIntentAction =
        "kr.co.bluebird.android.bbapi.action.BARCODE_CALLBACK_DECODING_DATA"
    private val blueBirdCodeExtra = "EXTRA_BARCODE_DECODING_DATA"

    // Chainway C6000
    private val chainwayC6000IntentAction = "com.rscja.android.DATA_RESULT"
    private val chainwayC6000CodeExtra = "data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerBroadcastReceiver()
    }

    private fun registerBroadcastReceiver() {
        this.registerReceiver(broadcastReceiver, IntentFilter(bluebirdIntentAction))
        this.registerReceiver(broadcastReceiver, IntentFilter(chainwayC6000IntentAction))
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.v(
                MainActivity::class.java.simpleName,
                "broadcastReceiver, intent.action = ${intent.action}"
            )

            var code = ""

            when (intent.action) {
                bluebirdIntentAction -> if (intent.hasExtra(blueBirdCodeExtra)) {
                    code = String(intent.getByteArrayExtra(blueBirdCodeExtra))
                }
                chainwayC6000IntentAction -> if (intent.hasExtra(chainwayC6000CodeExtra)) {
                    code = intent.getStringExtra(chainwayC6000CodeExtra)
                }
                else -> code = ""
            }

            txt_ean.text = code
        }
    }
}
