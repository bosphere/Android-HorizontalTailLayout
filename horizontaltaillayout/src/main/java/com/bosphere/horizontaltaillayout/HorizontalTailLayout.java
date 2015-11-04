package com.bosphere.horizontaltaillayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * A layout that allows a child view (at 0 index) to have one or many trailing tail views which will always stay adjacent
 * to the child view. If not enough space this layout will prioritize making room for the last tail view, 2nd last tail
 * view, and so on.
 */
public class HorizontalTailLayout extends ViewGroup {

    private boolean mCenterAlign = false;

    public HorizontalTailLayout(Context context) {
        super(context);
    }

    public HorizontalTailLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HorizontalTailLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HorizontalTailLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray a = getContext().obtainStyledAttributes(
                    attrs, R.styleable.HorizontalTailLayout, 0, 0);
            try {
                mCenterAlign = a.getBoolean(R.styleable.HorizontalTailLayout_center, false);
            } finally {
                a.recycle();
            }
        }
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int pl = getPaddingLeft();
        int pt = getPaddingTop();
        if (mCenterAlign) {
            int parentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            int sumWidth = 0;
            for (int i = 0, count = getChildCount(); i < count; i++) {
                View child = getChildAt(i);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                sumWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }

            if (parentWidth > sumWidth) {
                pl += (parentWidth - sumWidth) * 0.5;
            }
        }

        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int cl = pl + lp.leftMargin;
            int cr = cl + child.getMeasuredWidth();
            int ct = pt + lp.topMargin;
            int cb = ct + child.getMeasuredHeight();
            child.layout(cl, ct, cr, cb);
            pl = cr;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int usedWith = 0;
        int maxHeight = 0;
        for (int i = count - 1; i >= 0; i--) {
            View view = getChildAt(i);
            if (view.getVisibility() == View.GONE) {
                continue;
            }
            final LayoutParams lp = (LayoutParams) view.getLayoutParams();
            measureChildWithMargins(view, widthMeasureSpec, usedWith, heightMeasureSpec, 0);
            usedWith += view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            maxHeight = Math.max(maxHeight, view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
        }

        int highSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, highSpec);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
