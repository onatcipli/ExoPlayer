package com.example.exoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.exoplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var exoPlayer: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playWhenReady = true

    companion object {
        const val URL =
            "https://b356b5.entrypoint.cloud.wowza.com/app-9hkT7463/ngrp:544d4519_all/playlist.m3u8"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        preparePlayer()
    }

    private fun preparePlayer() {
        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer?.playWhenReady = true
        binding.playerView.player = exoPlayer
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem =
            MediaItem.fromUri(URL)
        val mediaSource =
            HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
        exoPlayer?.setMediaSource(mediaSource)
        exoPlayer?.seekTo(playbackPosition)
        exoPlayer?.playWhenReady = playWhenReady
        val playerListener = object :Player.Listener {
        }
        exoPlayer?.addListener(playerListener)
        exoPlayer?.prepare()
    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            playbackPosition = player.currentPosition
            playWhenReady = player.playWhenReady
            player.release()
            exoPlayer = null
        }
    }
}