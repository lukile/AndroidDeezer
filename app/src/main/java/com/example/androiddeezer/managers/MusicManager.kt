package com.example.androiddeezer.managers

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast
import com.example.androiddeezer.models.Track
import org.json.JSONObject
import com.google.gson.Gson



class MusicManager(context: Context) {

    val gson = Gson()

    private var mediaPlayer = MediaPlayer()
    private var context:Context = context
    private var settings: SharedPreferences? = context.getSharedPreferences("DeezerApp", 0)

    companion object {
        fun newInstance(contextInstance: Context): MusicManager {
            return MusicManager(contextInstance)
        }
    }

    public fun play(){
        try {
            settings?.edit()?.putBoolean("isActive", true)
            val previewUri: Uri = Uri.parse(getCurrentTrack().getPreview())
            mediaPlayer.setDataSource(context, previewUri)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: Exception) {
            Toast.makeText(context, "The file does not exist", Toast.LENGTH_LONG).show()
        }
    }
    public fun stop(){
        settings?.edit()?.putBoolean("isActive", false)
    }
    public fun pause(){
        mediaPlayer.pause()
    }
    public fun next(position: Int){

    }
    public fun previous(position: Int){

    }

    public fun isActive(): Boolean{
        return settings!!.getBoolean("isActive", false)
    }

    public fun getCurrentTrack(): Track{
        val jsonTrack = settings?.getString("track", "")
        val track = gson.fromJson(jsonTrack, Track::class.java)
        return track
    }
    public fun setCurrentTrack(track: Track){
        val trackJson = gson.toJson(track)
        settings?.edit()?.putString("track", trackJson)?.apply()
    }
}