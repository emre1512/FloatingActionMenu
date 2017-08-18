package com.emredavarci.floatingactionmenu;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by M. Emre Davarci on 14.08.2017.
 */

public class ActionCircle extends View{

    private Paint paint;

    private int parentW,parentH;
    private float scaleR;

    private int defStyleAttr;

    GradientDrawable shape =  new GradientDrawable();

    Context context;
    private float scaleRatio;
    private int duration;
    private int color;
    private float shadowWidth = 4;

    float radius;

    public ActionCircle(Context context) {
        super(context);
        this.context = context;
    }

    public ActionCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public ActionCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.defStyleAttr = defStyleAttr;
        this.context = context;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ActionCircle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.defStyleAttr = defStyleAttr;
        this.context = context;

    }

    public void setScaleRatio(float scaleRatio) {
        this.scaleRatio = scaleRatio;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setShadowWidth(float shadowWidth) {
        this.shadowWidth = shadowWidth;
    }

    public void init(){

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);

        scaleR = 0;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStrokeWidth(radius-shadowWidth*2);
        paint.setShadowLayer(shadowWidth, 0, 0, Color.parseColor("#b5b5b5"));
        setLayerType(LAYER_TYPE_SOFTWARE, paint);

        setCustomAnimation();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        parentW = ((View) getParent()).getMeasuredWidth();
        parentH = ((View) getParent()).getMeasuredHeight();
    }

    private void setCustomShape(){

        float f= radius/2;
        shape.setCornerRadii(new float[]{f,f,f,f,f,f,f,f});
        shape.setColor(color);
        setBackground(shape);
    }

    ValueAnimator openAnim,closeAnim;
    private void setCustomAnimation(){
        openAnim = ValueAnimator.ofFloat(0f, scaleRatio);
        openAnim.setDuration(duration);
        openAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float)animation.getAnimatedValue();
                scaleR = scale*radius;
                invalidate();
            }
        });

        closeAnim = ValueAnimator.ofFloat(scaleRatio, 0f);
        closeAnim.setDuration(duration);
        closeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float)animation.getAnimatedValue();
                scaleR = scale*radius;
                invalidate();
            }
        });

    }

    public void openMenu(){
        openAnim.start();
    }

    public void closeMenu(){
        closeAnim.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawLine((parentW-scaleR-shadowWidth)/2,         (radius)/2,
                            (parentW+scaleR+shadowWidth)/2+0.001f,  (radius)/2,
                             paint);

    }

}
