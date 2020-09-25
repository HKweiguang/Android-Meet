package com.huangk.framework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.huangk.framework.R;

public class TouchPictuteView extends View {

    /**
     * 背景
     */
    private Bitmap bgBitmap;
    /**
     * 背景画笔
     */
    private Paint mPaintbg;

    /**
     * 空白块
     */
    private Bitmap mNullBitmap;
    /**
     * 空白块画笔
     */
    private Paint mPaintNull;

    /**
     * 移动方块
     */
    private Bitmap mMoveBitmap;
    /**
     * 移动画笔
     */
    private Paint mPaintMove;

    private int mWidth, mHeight;

    private static int CARD_SIZE = 200;

    private int lineW, lineH;

    private int movex = 200;
    private int errorValue = 10;

    public TouchPictuteView(Context context) {
        super(context);
        init();
    }

    public TouchPictuteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchPictuteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintbg = new Paint();
        mPaintNull = new Paint();
        mPaintMove = new Paint();

        mNullBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_null_card);
        CARD_SIZE = mNullBitmap.getWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        lineW = (mWidth >> 2) * 3;
        lineH = mHeight >> 1 - CARD_SIZE >> 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBg(canvas);
        drawNullCard(canvas);
        drawMoveCard(canvas);
    }

    /**
     * 绘制背景
     */
    private void drawBg(Canvas canvas) {
        // 获取图片
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_bg);
        // 创建一个空的 bitmap
        bgBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        // 将图片绘制到空的 bitmap
        Canvas bgCanvas = new Canvas(bgBitmap);
        bgCanvas.drawBitmap(mBitmap, null, new Rect(0, 0, mWidth, mHeight), mPaintbg);
        // 将 bitmap 绘制到 view 上
        canvas.drawBitmap(bgBitmap, null, new Rect(0, 0, mWidth, mHeight), mPaintbg);
    }

    /**
     * 绘制空白块
     */
    private void drawNullCard(Canvas canvas) {
        // 绘制
        canvas.drawBitmap(mNullBitmap, lineW, lineH, mPaintNull);
    }

    /**
     * 绘制移动的方块
     */
    private void drawMoveCard(Canvas canvas) {
        // 截取空白块位置坐标的 Bitmap 图像
        mMoveBitmap = Bitmap.createBitmap(bgBitmap, lineW, lineH, CARD_SIZE, CARD_SIZE);
        // 绘制在 View 上
        canvas.drawBitmap(mMoveBitmap, movex, lineH, mPaintMove);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // 防止越界
                if (event.getX() > 0 && event.getX() < (mWidth - CARD_SIZE)) {
                    movex = (int) event.getX();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (movex > (lineW - errorValue) && movex < (lineW + errorValue)) {
                    if (mListener != null) {
                        mListener.onResult();
                    }
                }
                break;
        }
        return true;
    }

    private OnViewResultListener mListener;

    public void setOnViewResultListener(OnViewResultListener mListener) {
        this.mListener = mListener;
    }

    public interface OnViewResultListener {
        void onResult();
    }
}
