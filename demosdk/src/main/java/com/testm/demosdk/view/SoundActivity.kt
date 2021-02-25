package com.testm.demosdk.view

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.integration.android.IntentIntegrator
import com.testm.demosdk.R
import com.testm.demosdk.model.SoundItemUi
import com.testm.demosdk.view.adapters.OnClickListener
import com.testm.demosdk.view.adapters.SoundsAdapter
import com.testm.demosdk.viewmodels.SoundViewModel
import com.testm.demosdk.viewmodels.SoundViewModelFactory
import kotlinx.android.synthetic.main.activity_main_second.*


class SoundActivity: AppCompatActivity() {

    var mediaPlayer: MediaPlayer? = null

    lateinit var adapter: SoundsAdapter

    companion object {
        val TAG = SoundActivity::class.java.simpleName
    }

    val soundViewModel: SoundViewModel by viewModels({SoundViewModelFactory()})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_second)

        soundListView.layoutManager = LinearLayoutManager(this)
        adapter = SoundsAdapter(arrayOf(), object : OnClickListener {
            override fun onClick(item: SoundItemUi) {
                playSound(Uri.parse(item.uri))
            }
        })
        soundListView.adapter = adapter

        // todo add diff
        soundViewModel.fileList.observe(this, Observer {
            adapter.setItems(it.toTypedArray())
        })

        soundViewModel.isRefreshing.observe(this, Observer {
            progress_circular.visibility = if (it) View.VISIBLE else View.GONE
        })

        // soundViewModel.getSounds("https://s3-eu-west-1.amazonaws.com/s3.testm.com/AppData/examJson.json")

        readBarcode()
    }

    private fun readBarcode() {
        IntentIntegrator(this).initiateScan();
    }

    fun playSound(uri: Uri) {
        stopPlaying()
        mediaPlayer = MediaPlayer.create(this, uri)
        mediaPlayer?.start()

    }

    fun stopPlaying(){
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onStop() {
        super.onStop()

        stopPlaying()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                soundViewModel.refreshSounds(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}