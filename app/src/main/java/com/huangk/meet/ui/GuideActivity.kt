package com.huangk.meet.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.huangk.framework.base.BasePageAdapter
import com.huangk.framework.base.BaseUIActivity
import com.huangk.framework.manager.MediaPlayerManager
import com.huangk.framework.utils.AnimUtils
import com.huangk.framework.utils.LogUtil
import com.huangk.meet.R
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : BaseUIActivity(), View.OnClickListener {

    private var view1: View? = null
    private var view2: View? = null
    private var view3: View? = null

    private val mPageList = arrayListOf<View>()

    private var mPageAdapter: BasePageAdapter? = null

    private var iv_guide_start: ImageView? = null
    private var iv_guide_night: ImageView? = null
    private var iv_guide_smile: ImageView? = null

    private val mediaPlayerManager = MediaPlayerManager()

    private var mAnim: ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        initView()
    }

    override fun onRestart() {
        super.onRestart()
        mediaPlayerManager.statusListener(play = {}, pause = {
            mediaPlayerManager.continuePlay()
        }, stop = {
            initPlayer()
        })
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayerManager.isPlaying()) {
            mediaPlayerManager.pausePlay()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerManager.stopPlay()
        mediaPlayerManager.recyle()
    }

    private fun initView() {
        view1 = View.inflate(this@GuideActivity, R.layout.layout_pager_guide_1, null)
        view2 = View.inflate(this@GuideActivity, R.layout.layout_pager_guide_2, null)
        view3 = View.inflate(this@GuideActivity, R.layout.layout_pager_guide_3, null)

        mPageList.apply {
            add(view1!!)
            add(view2!!)
            add(view3!!)
        }

        // 预加载
        mViewPager.offscreenPageLimit = mPageList.size

        mPageAdapter = BasePageAdapter(mPageList)
        mViewPager.adapter = mPageAdapter

        iv_guide_start = view1?.findViewById(R.id.iv_guide_star)
        iv_guide_night = view2?.findViewById(R.id.iv_guide_night)
        iv_guide_smile = view3?.findViewById(R.id.iv_guide_smile)

        val animStart = (iv_guide_start?.background) as AnimationDrawable
        val animNight = (iv_guide_night?.background) as AnimationDrawable
        val animSmile = (iv_guide_smile?.background) as AnimationDrawable

        animStart.start()
        animNight.start()
        animSmile.start()

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                seletePoint(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        startMusic()

        iv_music_switch.setOnClickListener(this)
        tv_guide_skip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.run {
            iv_music_switch.setOnClickListener {
                when {
                    mediaPlayerManager.getStatus() == MediaPlayerManager.Companion.MediaStatus.PLAY -> {
                        mediaPlayerManager.pausePlay()
                        mAnim?.pause()
                        iv_music_switch.setImageResource(R.mipmap.img_guide_music_off)
                    }
                    mediaPlayerManager.getStatus() == MediaPlayerManager.Companion.MediaStatus.PAUSE -> {
                        mediaPlayerManager.continuePlay()
                        mAnim?.start()
                        iv_music_switch.setImageResource(R.mipmap.img_guide_music)
                    }
                }
            }
            tv_guide_skip.setOnClickListener {
                startActivity(Intent(this@GuideActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    /**
     * 设置小圆点
     */
    private fun seletePoint(position: Int) {
        when (position) {
            0 -> {
                iv_guide_point_1.setImageResource(R.mipmap.img_guide_point_p)
                iv_guide_point_2.setImageResource(R.mipmap.img_guide_point)
                iv_guide_point_3.setImageResource(R.mipmap.img_guide_point)
            }
            1 -> {
                iv_guide_point_1.setImageResource(R.mipmap.img_guide_point)
                iv_guide_point_2.setImageResource(R.mipmap.img_guide_point_p)
                iv_guide_point_3.setImageResource(R.mipmap.img_guide_point)
            }
            2 -> {
                iv_guide_point_1.setImageResource(R.mipmap.img_guide_point)
                iv_guide_point_2.setImageResource(R.mipmap.img_guide_point)
                iv_guide_point_3.setImageResource(R.mipmap.img_guide_point_p)
            }
        }
    }

    private fun startMusic() {
        initPlayer()

        mAnim = AnimUtils.rotation(iv_music_switch)
        mAnim?.start()
    }

    /**
     * 初始化播放器
     */
    private fun initPlayer() {
        val openRawResourceFd = resources.openRawResourceFd(R.raw.guide)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaPlayerManager.startPlayer(openRawResourceFd)
            mediaPlayerManager.setLooping(true)
        }
        mediaPlayerManager.setOnProgressListener(object :
            MediaPlayerManager.OnMusicProgressListener {
            override fun OnProgress(progress: Int, pos: Int) {
                LogUtil.log(
                    "progress: $progress All: ${mediaPlayerManager.getDuration()}",
                    LogUtil.LogLevel.E
                )
            }
        })
    }
}