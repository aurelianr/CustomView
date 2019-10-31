package com.roscasend.android.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

// TODO: If you are using androidx

// TODO: If you are using appcompat
//import android.support.annotation.Nullable;
//import android.support.v7.widget.AppCompatRadioButton;

public class MyRadioButton extends AppCompatRadioButton {

    private View view;
    private TextView textView;
    private ImageView imageView;

    public MyRadioButton(Context context) {
        super(context);
        init(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
            imageView.setImageBitmap(resource);
            redrawLayout();
            return false;
        }
    };

//    public void setImageResource(int resId) {
//        Glide.with(getContext())
//                .asBitmap()
//                .load(resId)
//                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(), new MaskTransformation(R.drawable.rounded_corner))))
//                .listener(requestListener)
//                .submit();
//    }
//
//    public void setImageBitmap(Bitmap bitmap) {
//        Glide.with(getContext())
//                .asBitmap()
//                .load(bitmap)
//                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(), new MaskTransformation(R.drawable.rounded_corner))))
//                .listener(requestListener)
//                .submit();
//    }

    public void setImageResource(int resId) {
        Glide.with(getContext())
                .asBitmap()
                .load(resId)
                .apply(RequestOptions.bitmapTransform(
                        new MultiTransformation<>(
                                new CenterCrop(),
                                new RoundedCornersTransformation(24, 0, RoundedCornersTransformation.CornerType.ALL))
                        )
                )
                .listener(requestListener)
                .submit();
    }

    public void setImageBitmap(Bitmap bitmap) {
        Glide.with(getContext())
                .asBitmap()
                .load(bitmap)
                .apply(RequestOptions.bitmapTransform(
                        new MultiTransformation<>(
                                new CenterCrop(),
                                new RoundedCornersTransformation(24, 0, RoundedCornersTransformation.CornerType.ALL))
                        )
                )
                .listener(requestListener)
                .submit();
    }
    // setText is a final method in ancestor, so we must take another name.
    public void setTextWith(int resId) {
        textView.setText(resId);
        redrawLayout();
    }

    public void setTextWith(CharSequence text) {
        textView.setText(text);
        redrawLayout();
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.my_radio_button_content, null);
        textView = view.findViewById(R.id.textView);
        imageView = view.findViewById(R.id.imageView);
        redrawLayout();
    }

    private void redrawLayout() {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(getResources(), bitmap), null, null, null);
        view.setDrawingCacheEnabled(false);
    }

}