package com.huangk.framework.manager

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi

class MediaPlayerManager {

    private val mediaPlayer = MediaPlayer()

    private var MEDIA_STATUS = MediaStatus.STOP

    /**
     * 计算歌曲进度
     */
    private lateinit var mHandler: Handler

    init {
        mHandler = Handler(Looper.getMainLooper()) { msg ->
            onProgressListener?.run {
                if (msg.what == H_PROGRESS) {
                    val currentPosition = getCurrentPosition()
                    val pos = ((currentPosition.toFloat() / getDuration().toFloat()) * 100).toInt()
                    OnProgress(getCurrentPosition(), pos)
                    mHandler.sendEmptyMessageDelayed(H_PROGRESS, 1000)
                }
            }
            false
        }
    }

    /**
     * 是否在播放
     */
    fun isPlaying() = mediaPlayer.isPlaying

    fun getStatus() = MEDIA_STATUS

    fun statusListener(play: () -> Unit, pause: () -> Unit, stop: () -> Unit) {
        val status = getStatus()
        when (status) {
            MediaStatus.PLAY -> play()
            MediaStatus.PAUSE -> pause()
            MediaStatus.STOP -> stop()
        }
    }

    /**
     * 播放
     */
    fun startPlayer(path: String) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            mediaPlayer.start()
            MEDIA_STATUS = MediaStatus.PLAY
            mHandler.sendEmptyMessage(H_PROGRESS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 播放
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun startPlayer(path: AssetFileDescriptor) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            mediaPlayer.start()
            MEDIA_STATUS = MediaStatus.PLAY
            mHandler.sendEmptyMessage(H_PROGRESS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 暂停播放
     */
    fun pausePlay() {
        if (isPlaying()) {
            mediaPlayer.pause()
            MEDIA_STATUS = MediaStatus.PAUSE
            mHandler.removeMessages(H_PROGRESS)
        }
    }

    /**
     * 继续播放
     */
    fun continuePlay() {
        mediaPlayer.start()
        MEDIA_STATUS = MediaStatus.PLAY
        mHandler.sendEmptyMessage(H_PROGRESS)
    }

    /**
     * 停止播放
     */
    fun stopPlay() {
        mediaPlayer.stop()
        MEDIA_STATUS = MediaStatus.STOP
        mHandler.removeMessages(H_PROGRESS)
    }

    /**
     * 释放资源
     */
    fun recyle() {
        mediaPlayer.release()
    }

    /**
     * 获取当前位置
     */
    private fun getCurrentPosition() = mediaPlayer.currentPosition

    /**
     * 获取总时长
     */
    fun getDuration() = mediaPlayer.duration

    /**
     * 是否循环
     */
    fun setLooping(isLooping: Boolean) {
        mediaPlayer.isLooping = isLooping
    }

    fun seekTo(ms: Int) {
        mediaPlayer.seekTo(ms)
    }

    /**
     * 播放结束
     */
    fun setOnCompltetionListener(listener: MediaPlayer.OnCompletionListener) =
        mediaPlayer.setOnCompletionListener(listener)

    /**
     * 播放错误
     */
    fun setOnErrorListener(listener: MediaPlayer.OnErrorListener) =
        mediaPlayer.setOnErrorListener(listener)

    /**
     * 播放进度
     */
    fun setOnProgressListener(listener: OnMusicProgressListener) {
        onProgressListener = listener
    }

    private var onProgressListener: OnMusicProgressListener? = null

    interface OnMusicProgressListener {
        fun OnProgress(progress: Int, pos: Int)
    }

    companion object {

        private const val H_PROGRESS: Int = 1000

        /**
         * 播放状态
         */
        enum class MediaStatus {
            /**
             * 播放
             */
            PLAY,

            /**
             * 暂停
             */
            PAUSE,

            /**
             * 停止
             */
            STOP,
        }

    }
}