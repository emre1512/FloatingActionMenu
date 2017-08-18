package com.emredavarci.floatingactionmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by M. Emre Davarci on 15.08.2017.
 */

public class FloatingActionMenu extends RelativeLayout {

    private int defStyleAttr;

    private float scaleRatio;

    private int duration;
    private int bgColor;
    private float radius;
    private int menuIconRotateAngle;
    private float menuIconWidth;
    private float menuIconHeight;
    private float shadowWidth;
    private int menuIconId;
    private int firstActionId;
    private int secondActionId;
    private int thirdActionId;
    private int fourthActionId;

    private int[] actions;

    private int fadeOutFadeInDuration = 100;

    private static float itemContainerScaleRatio = 1.25f;

    private ClickEvent clickEvent;

    RelativeLayout menuIconContainer;
    RelativeLayout menuItemsContainer;

    private ActionCircle fs;
    private Animation menuIconOpenAnim, menuIconCloseAnim, menuRevealAnim, menuFadeOutAnim,
            scaleDownAnim,scaleUpAnim;

    private boolean isOpen = false;
    private boolean isAnimating = false;

    public FloatingActionMenu(Context context) {
        super(context);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        createMenu(context, attrs);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.defStyleAttr = defStyleAttr;
        createMenu(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FloatingActionMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.defStyleAttr = defStyleAttr;
        createMenu(context, attrs);
    }

    private void createMenu(Context c, AttributeSet attrs){

        TypedArray ta = c.obtainStyledAttributes(attrs, R.styleable.ActionCircle, defStyleAttr, 0);

        scaleRatio = ta.getFloat(R.styleable.ActionCircle_scaleRatio, 3.5f);
        duration = ta.getInt(R.styleable.ActionCircle_duration, 200);
        bgColor = ta.getColor(R.styleable.ActionCircle_bgColor, Color.parseColor("#4ad6bd"));
        radius = ta.getDimension(R.styleable.ActionCircle_radius, 80);
        menuIconRotateAngle = (-1) * ta.getInt(R.styleable.ActionCircle_menuIconRotateAngle, 225);
        menuIconHeight = ta.getDimension(R.styleable.ActionCircle_menuIconHeight, radius);
        menuIconWidth = ta.getDimension(R.styleable.ActionCircle_menuIconWidth, radius);
        shadowWidth = ta.getDimension(R.styleable.ActionCircle_shadowWidth, 2);
        menuIconId = ta.getResourceId(R.styleable.ActionCircle_menuIcon, R.drawable.default_menu_icon);
        firstActionId = ta.getResourceId(R.styleable.ActionCircle_firstActionIcon, R.drawable.fav_icon);
        secondActionId = ta.getResourceId(R.styleable.ActionCircle_secondActionIcon, R.drawable.share_icon);
        thirdActionId = ta.getResourceId(R.styleable.ActionCircle_thirdActionIcon, R.drawable.search_icon);
        fourthActionId = ta.getResourceId(R.styleable.ActionCircle_fourthActionIcon, R.drawable.settings_icon);

        ta.recycle();

        addMenuCircle(c);
        addMenuItems(c);
        addMenuIcon(c);
    }

    private void addMenuItems(Context c){

        actions = new int[5];

        actions[0] = firstActionId;
        actions[1] = secondActionId;
        actions[2] = secondActionId;
        actions[3] = thirdActionId;
        actions[4] = fourthActionId;

        menuItemsContainer = new RelativeLayout(c);
        menuItemsContainer.setVisibility(INVISIBLE);
        LayoutParams rlParams = new LayoutParams((int)(radius*scaleRatio*itemContainerScaleRatio),(int)radius);
        rlParams.addRule(CENTER_HORIZONTAL,TRUE);
        menuItemsContainer.setLayoutParams(rlParams);

        LinearLayout layout = new LinearLayout(c);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(radius*scaleRatio*itemContainerScaleRatio),(int)radius);
        layout.setWeightSum(actions.length);
        layout.setLayoutParams(params);

        for (int i = 0; i < actions.length; i++){
            final ImageView v = new ImageView(c);
            LayoutParams p = new LayoutParams((int)menuIconWidth, (int)menuIconHeight);
            p.addRule(CENTER_HORIZONTAL,TRUE);
            v.setAdjustViewBounds(true);
            v.setScaleType(ImageView.ScaleType.FIT_XY);
            v.setLayoutParams(p);

            LinearLayout r = new LinearLayout(c);
            LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams((int)menuIconWidth,
                    (int)menuIconHeight);
            rParams.gravity = Gravity.CENTER;
            r.setPadding((int)(((radius*scaleRatio*itemContainerScaleRatio)/actions.length-menuIconWidth))/2,0,0,0);
            rParams.weight = 1;
            r.setLayoutParams(rParams);
            r.addView(v);
            layout.addView(r);

            if(i != actions.length/2){
                v.setBackgroundResource(actions[i]);
                setClickAnim(v);

                final int finalI;
                if(i > actions.length/2){
                    finalI = i-1;
                }else{
                    finalI = i;
                }
                layout.getChildAt(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(clickEvent != null){
                            clickEvent.onClick(finalI);
                        }
                        fs.closeMenu();
                        menuIconContainer.startAnimation(menuIconCloseAnim);
                        animateClick(v);
                    }
                });
            }

        }
        menuItemsContainer.addView(layout);
        addView(menuItemsContainer);

        setItemConteinerAnim();
    }

    private void addMenuCircle(Context c){
        fs = new ActionCircle(c);
        fs.setDuration(duration);
        fs.setColor(bgColor);
        fs.setScaleRatio(scaleRatio);
        fs.setRadius(radius);
        fs.setShadowWidth(shadowWidth);
        fs.init();
        addView(fs);
    }

    private void addMenuIcon(Context c){
        menuIconContainer = new RelativeLayout(c);
        LayoutParams containerParams = new LayoutParams((int)radius, (int)radius);
        containerParams.addRule(CENTER_HORIZONTAL,TRUE);
        containerParams.addRule(CENTER_VERTICAL,TRUE);
        menuIconContainer.setLayoutParams(containerParams);
        containerParams.setMargins(0,0,0,0);

        ImageView menuIcon = new ImageView(c);
        LayoutParams lp = new LayoutParams((int)menuIconWidth, (int)menuIconHeight);
        lp.addRule(CENTER_HORIZONTAL,TRUE);
        lp.addRule(CENTER_VERTICAL,TRUE);
        menuIcon.setPadding(0,0,0,0);
        menuIcon.setAdjustViewBounds(true);
        menuIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        menuIcon.setLayoutParams(lp);
        menuIcon.setBackgroundResource(menuIconId);

        menuIconContainer.addView(menuIcon);
        addView(menuIconContainer);

        setMenuIconAnim();
    }

    private void setMenuIconAnim(){
        menuIconOpenAnim = new RotateAnimation(0, menuIconRotateAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        menuIconOpenAnim.setFillAfter(true);
        menuIconOpenAnim.setDuration(duration);
        menuIconOpenAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimating = false;
                isOpen = true;
                menuItemsContainer.startAnimation(menuRevealAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        menuIconCloseAnim = new RotateAnimation(menuIconRotateAngle, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                                                Animation.RELATIVE_TO_SELF, 0.5f);
        menuIconCloseAnim.setFillAfter(true);
        menuIconCloseAnim.setDuration(duration);
        menuIconCloseAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimating = true;
                menuItemsContainer.startAnimation(menuFadeOutAnim);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimating = false;
                isOpen = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        menuIconContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAnimating){
                    if(!isOpen){
                        fs.openMenu();
                        menuIconContainer.startAnimation(menuIconOpenAnim);
                    }else{
                        fs.closeMenu();
                        menuIconContainer.startAnimation(menuIconCloseAnim);
                    }
                }
            }
        });

    }

    private void setItemConteinerAnim(){
        menuRevealAnim = new AlphaAnimation(0, 1.0f);
        menuRevealAnim.setDuration(fadeOutFadeInDuration);
        menuRevealAnim.setFillAfter(true);

        menuFadeOutAnim = new AlphaAnimation(1.0f, 0);
        menuFadeOutAnim.setDuration(fadeOutFadeInDuration);
        menuFadeOutAnim.setFillAfter(true);
    }

    public interface ClickEvent{
        void onClick(int index);
    }

    public void setClickEvent(ClickEvent clickEvent){
        this.clickEvent = clickEvent;
    }

    private void setClickAnim(View v){
        scaleDownAnim = new ScaleAnimation(1f, 0.8f, 1f, 0.8f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleDownAnim.setFillAfter(true);
        scaleDownAnim.setDuration(20);

        scaleUpAnim = new ScaleAnimation(0.8f, 1f,0.8f, 1f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleUpAnim.setFillAfter(true);
        scaleUpAnim.setDuration(20);
        scaleUpAnim.setStartOffset(20);
    }

    private void animateClick(View v){
        v.startAnimation(scaleDownAnim);
        v.startAnimation(scaleUpAnim);
    }

    public float getScaleRatio() {
        return scaleRatio;
    }

    public void setScaleRatio(float scaleRatio) {
        this.scaleRatio = scaleRatio;
        invalidate();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        invalidate();
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        invalidate();
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public int getMenuIconRotateAngle() {
        return menuIconRotateAngle;
    }

    public void setMenuIconRotateAngle(int menuIconRotateAngle) {
        this.menuIconRotateAngle = menuIconRotateAngle;
        invalidate();
    }

    public float getMenuIconWidth() {
        return menuIconWidth;
    }

    public void setMenuIconWidth(float menuIconWidth) {
        this.menuIconWidth = menuIconWidth;
        invalidate();
    }

    public float getMenuIconHeight() {
        return menuIconHeight;
    }

    public void setMenuIconHeight(float menuIconHeight) {
        this.menuIconHeight = menuIconHeight;
        invalidate();
    }

    public float getShadowWidth() {
        return shadowWidth;
    }

    public void setShadowWidth(float shadowWidth) {
        this.shadowWidth = shadowWidth;
        invalidate();
    }

    public int getMenuIconId() {
        return menuIconId;
    }

    public void setMenuIconId(int menuIconId) {
        this.menuIconId = menuIconId;
        invalidate();
    }

    public int getFirstActionId() {
        return firstActionId;
    }

    public void setFirstActionId(int firstActionId) {
        this.firstActionId = firstActionId;
        invalidate();
    }

    public int getSecondActionId() {
        return secondActionId;
    }

    public void setSecondActionId(int secondActionId) {
        this.secondActionId = secondActionId;
        invalidate();
    }

    public int getThirdActionId() {
        return thirdActionId;
    }

    public void setThirdActionId(int thirdActionId) {
        this.thirdActionId = thirdActionId;
        invalidate();
    }

    public int getFourthActionId() {
        return fourthActionId;
    }

    public void setFourthActionId(int fourthActionId) {
        this.fourthActionId = fourthActionId;
        invalidate();
    }


}
