package com.lambda.auto.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Hashtable;

/**
 * Copyright (C) 2016
 * This file is part of the Epiphyllum B7 System.
 * <p/>
 * filename :自动换行的LinearLayout
 * action :
 *
 * @author :  shuangyu.he
 * @version : 7.1
 * @date : 2016-11-18 17:32
 * modify :
 */

public class AutoNextLineLinearLayout extends LinearLayout {
    private int mLeft;
    private int mRight;
    private int mTop;
    private int mBottom;
    //记录我每个子View的位置
    private Hashtable map = new Hashtable();
    private int mCount;
    private int mWidth;
    private int mX;
    //所占的行数
    private int mLine = 0;

    public AutoNextLineLinearLayout(Context context) {
        super(context);
    }

    public AutoNextLineLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getLine() {
        return mLine;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mCount = getChildCount();

        mX = 0;
        int mY = 0;
        mLeft = 0;
        mRight = 0;
        mTop = getPaddingTop();
        mBottom = 0;

        int j = 0;
        int line = 1;

        View lastview = null;

        for (int i = 0; i < mCount; i++) {
            final View child = getChildAt(i);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            // 此处增加onLayout中的换行判断，用于计算所需的高度
            int childW = child.getMeasuredWidth();
            int childH = child.getMeasuredHeight();

            // 将每次子控件宽度进行统计叠加，如果大于设定的宽度则需要换行，高度即Top坐标也需重新设置

            mX += childW + getPaddingLeft();

            Position position = new Position();
            mLeft = getPosition(i - j, i);
            mRight = mLeft + child.getMeasuredWidth();
            if (mX >= mWidth) {
                //默认就有getPaddingLeft的padding值
                //在此换行mX重新改为最初始的值
                mX = childW + getPaddingLeft();
                //top高度叠加
                mY += (childH + getPaddingTop());
                j = i;
                //默认每次换行的控件左边位置
                mLeft = getPaddingLeft();
                mRight = mLeft + child.getMeasuredWidth();
                mTop = mY;

                line += 1;
            }
            mLine = line;
            mBottom = mTop + child.getMeasuredHeight();
            // 每次的高度必须记录 否则控件会叠加到一起
            mY = mTop;
            position.left = mLeft;
            position.top = mTop;
            position.right = mRight;
            position.bottom = mBottom;
            //记录高度
            map.put(child, position);
        }

        setMeasuredDimension(mWidth, mBottom + getPaddingTop());

    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(0, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            Position pos = (Position) map.get(child);

            if (pos != null) {
                child.layout(pos.left, pos.top, pos.right, pos.bottom);

            } else {
                Log.i("pp3", "666");
            }
        }
    }

    private int getPosition(int IndexInRow, int childIndex) {
        Log.i("596it", "jj");

        if (IndexInRow > 0) {
            int i = getPosition(IndexInRow - 1, childIndex - 1) + (getChildAt(childIndex - 1).getMeasuredWidth() + getPaddingLeft());

            return i;

        }

        return getPaddingLeft();
    }


    private class Position {
        private int left;
        private int top;
        private int right;
        private int bottom;
    }
}
