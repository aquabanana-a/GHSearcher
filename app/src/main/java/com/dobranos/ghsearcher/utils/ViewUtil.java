package com.dobranos.ghsearcher.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.dobranos.ghsearcher.R;

public class ViewUtil
{
//    public static int spToPx(float sp) { return (int)(AppMgr.getApplicationContext().getResources().getDisplayMetrics().scaledDensity * sp); }
//    public static float pxToSp(int px) { return (float)px / AppMgr.getApplicationContext().getResources().getDisplayMetrics().scaledDensity; }

    public static int getToolbarHeight(Context context)
    {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
            new int[] { R.attr.actionBarSize });
        int toolbarHeight = (int)styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

//    public static View buttonEffect(View button)
//    {
//        button.setOnTouchListener(new View.OnTouchListener()
//        {
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                switch(event.getAction())
//                {
//                    case MotionEvent.ACTION_DOWN:
//                    {
//                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
//                        v.invalidate();
//                        break;
//                    }
//                    case MotionEvent.ACTION_UP:
//                    {
//                        v.getBackground().clearColorFilter();
//                        v.invalidate();
//                        break;
//                    }
//                }
//                return false;
//            }
//        });
//        return button;
//    }

    public static int getViewHeight(View view)
    {
        WindowManager wm = (WindowManager)view.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int deviceWidth;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            Point size = new Point();
            display.getSize(size);
            deviceWidth = size.x;
        }
        else
        {
            deviceWidth = display.getWidth();
        }

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();
    }

    public static int getViewWidth(View view)
    {
        WindowManager wm = (WindowManager)view.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int deviceHeight;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            Point size = new Point();
            display.getSize(size);
            deviceHeight = size.y;
        }
        else
        {
            deviceHeight = display.getHeight();
        }

//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceHeight, View.MeasureSpec.AT_MOST);

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceHeight, View.MeasureSpec.EXACTLY);

        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredWidth();
    }

//    public static Display getDisplay()
//    {
//        WindowManager wm = (WindowManager) AppMgr.getCurrentContext().getSystemService(Context.WINDOW_SERVICE);
//        return wm.getDefaultDisplay();
//    }
//
//    public static void setText(TextView tv, String text, boolean isHtml, @Nullable Html.ImageGetter imgGeter)
//    {
//        if(isHtml)
//        {
//            tv.setMovementMethod(LinkMovementMethod.getInstance());
//            tv.setText(SafeURLSpan.parseSafeHtml(text, imgGeter));
//        }
//        else
//            tv.setText(StringUtil.acceptNewLines(text));
//    }

    // r.a(View view)
    public static final void toVisible(View view)
    {
        if (view.getVisibility() != View.VISIBLE)
            view.setVisibility(View.VISIBLE);
    }

    // r.a(View view, boolean z)
    public static final void toVisible(View view, boolean z)
    {
        if (z)
        {
            toVisible(view);
        }
        else
        {
            toGone(view);
        }
    }

    // r.b(View view)
    public static final void toGone(View view)
    {
        if (view.getVisibility() != View.GONE)
            view.setVisibility(View.GONE);
    }

    // r.c(View view)
    public static final void toInvisible(View view)
    {
        if (view.getVisibility() != View.INVISIBLE)
            view.setVisibility(View.INVISIBLE);
    }

    // r.d(View view)
    public static final boolean isVisible(View view)
    {
        return view.getVisibility() == View.VISIBLE;
    }

    // r.e(View view)
    public static final boolean isGone(View view)
    {
        return view.getVisibility() == View.GONE;
    }

    // r.a(View view, @IdRes int i)
    public static final <T extends View> T findViewById(View view, @IdRes int i)
    {
        return view.findViewById(i);
    }

    // r.f(View view)
    public static final void setDefaultForeground(View view)
    {
        if (Build.VERSION.SDK_INT < 23)
        {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.selector_transparent_to_primary));
        }
        else
        {
            view.setForeground(ContextCompat.getDrawable(view.getContext(), R.drawable.selector_transparent_to_primary));
        }
    }
}
