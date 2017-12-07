package com.jk.shadeview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jk.shadeview.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuke02 on 2017/12/6.
 */

public class LoadTextView extends View {


    private static final String TAG = "LoadTextView";
    private TypedArray typedArray;
    private String content;
    private TextPaint mPaint;
    private float textSize;
    private static final float DEFAULT_SIZE = 18;
    private int color;
    private int textWidth;
    private int textHeight;
    private int index = 0;
    private ScheduledExecutorService executorService;
    private char[] chars;
    private StringBuffer sBuffer;
    private int duration = 0;

    public LoadTextView(Context context) {
        super(context);
        init();
    }

    public LoadTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadTextView);
        content = typedArray.getString(R.styleable.LoadTextView_content);
        textSize = typedArray.getDimension(R.styleable.LoadTextView_textSize, DEFAULT_SIZE);
        color = typedArray.getColor(R.styleable.LoadTextView_textColor, Color.RED);
        typedArray.recycle();
        init();
    }


    private void init() {
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(textSize);
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);

        chars = content.toCharArray();
        sBuffer = new StringBuffer();
        final Rect rect = new Rect();
        mPaint.getTextBounds(content, 0, content.length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
        //线程池管理线程
        executorService = Executors.newScheduledThreadPool(1);
    }

    /**
     * 设置显示间隔时间
     * @param time
     */
    public void setDuration(int time){
        this.duration = time;
    }

    /**
     * 开始绘制文字
     */
    public void startShow(){
        if (duration > 0){
            executorService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    if (index < content.length()){
                        postInvalidate();
                        index++;
                    }else {
                        //关闭线程 放置内存泄露
                        executorService.shutdown();
                    }
                }
            },0,duration,TimeUnit.MILLISECONDS);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        sBuffer.append(chars[index-1]);
        canvas.drawText(sBuffer.toString(),0,textHeight,mPaint);
    }
}
