package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Андрей on 02.08.2017.
 */

public class RoundedImageView extends android.support.v7.widget.AppCompatImageView {

    int width, heigth;

    Paint paintFill;

    int color = Color.TRANSPARENT;

    public RoundedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintFill = new Paint();
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        heigth = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        View view = getRootView();
        Drawable background = view.getBackground();
        if(background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        paintFill.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circleInside(canvas);
    }

    private void circleInside(Canvas canvas){
        Path path = new Path();
        path.reset();
        if(!albumOrientation()){
            RectF oval = new RectF(0, heigth - (heigth / 2 + width / 2), width, (heigth / 2 + width / 2));
            path.moveTo(0, 0);
            path.lineTo(0, heigth);
            path.lineTo(width, heigth);
            path.lineTo(width, heigth / 2);
            path.arcTo(oval, 0, 360);
            path.lineTo(width, 0);
            path.close();
        }
        else {
            RectF oval = new RectF(width - (width / 2 + heigth / 2), 0,(width / 2 + heigth / 2), heigth);
            path.moveTo(0, 0);
            path.lineTo(0, heigth);
            path.lineTo(width, heigth);
            path.lineTo(width, heigth / 2);
            path.arcTo(oval, 0, 360);
            path.lineTo(width, heigth / 2);
            path.lineTo(width, 0);
            path.close();
        }
        canvas.drawPath(path, paintFill);

    }

    private boolean albumOrientation(){
        boolean result = false;
        if (width > heigth) result = true;
        return result;
    }
}
