package com.fanwe.library.utils;

import java.io.File;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

public class SDViewBinder
{

	public static boolean mCanLoadImageFromUrl = true;

	public static void setRatingBar(RatingBar ratingBar, float rating)
	{
		ratingBar.setRating(rating);
	}

	public static void setTextView(TextView textView, CharSequence content, CharSequence emptyTip)
	{
		if (!TextUtils.isEmpty(content))
		{
			textView.setText(content);
		} else
		{
			if (!TextUtils.isEmpty(emptyTip))
			{
				textView.setText(emptyTip);
			} else
			{
				textView.setText("");
			}
		}
	}

	public static void setTextView(TextView textView, CharSequence content)
	{
		setTextView(textView, content, null);
	}

	public static void setTextView(EditText editText, CharSequence content)
	{
		if (!TextUtils.isEmpty(content))
		{
			editText.setText(content);
		} else
		{
			editText.setText("");
		}
	}

	public static void setTextViewHtml(TextView textView, String contentHtml)
	{
		setTextViewHtml(textView, contentHtml, null);
	}

	public static void setTextViewHtml(TextView textView, String contentHtml, String emptyTip)
	{
		CharSequence content = contentHtml;
		if (!TextUtils.isEmpty(contentHtml))
		{
			content = Html.fromHtml(contentHtml);
		}
		setTextView(textView, content, emptyTip);
	}

	public static void setTextViewsVisibility(TextView textView, CharSequence content)
	{
		if (TextUtils.isEmpty(content))
		{
			SDViewUtil.hide(textView);
		} else
		{
			textView.setText(content);
			SDViewUtil.show(textView);
		}
	}

	public static void setImageViewsVisibility(ImageView imageView, int resId)
	{
		if (resId <= 0)
		{
			SDViewUtil.hide(imageView);
		} else
		{
			imageView.setImageResource(resId);
			SDViewUtil.show(imageView);
		}
	}

	public static boolean setViewsVisibility(View view, boolean visible)
	{
		if (visible)
		{
			SDViewUtil.show(view);
		} else
		{
			SDViewUtil.hide(view);
		}
		return visible;
	}

	public static boolean setViewsVisibility(View view, int visible)
	{
		if (visible == 1)
		{
			SDViewUtil.show(view);
			return true;
		} else
		{
			SDViewUtil.hide(view);
			return false;
		}
	}

	private static boolean canLoadImageFromUrl(String url)
	{
		if (mCanLoadImageFromUrl)// 可以从url加载图片
		{
			return true;
		} else
		{
			File cache = ImageLoader.getInstance().getDiskCache().get(url);
			if (cache != null && cache.exists() && cache.length() > 0)
			{
				return true;
			} else
			{
				return false;
			}
		}
	}

	public static void setImageViewResource(ImageView imageView, int resId, boolean setZeroResId)
	{
		if (resId == 0)
		{
			if (setZeroResId)
			{
				imageView.setImageResource(resId);
			} else
			{

			}
		} else
		{
			imageView.setImageResource(resId);
		}
	}

	@Deprecated
	public static void setImageView(ImageView imageView, String url)
	{
		setImageView(url, imageView, null, null, null);
	}

	public static void setImageView(String url, ImageView imageView)
	{
		setImageView(url, imageView, null, null, null);
	}

	public static void setImageView(String uri, ImageView imageView, DisplayImageOptions options)
	{
		setImageView(uri, imageView, options, null, null);
	}

	public static void setImageView(String uri, ImageView imageView, ImageLoadingListener listener)
	{
		setImageView(uri, imageView, null, listener, null);
	}

	public static void setImageView(String uri, ImageView imageView, ImageLoadingListener listener, ImageLoadingProgressListener progressListener)
	{
		setImageView(uri, imageView, null, listener, progressListener);
	}

	public static void setImageView(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener)
	{
		setImageView(uri, imageView, options, listener, null);
	}

	public static void setImageView(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener,
			ImageLoadingProgressListener progressListener)
	{
		if (!canLoadImageFromUrl(uri))
		{
			return;
		}
		try
		{
			ImageLoader.getInstance().displayImage(uri, imageView, options, listener, progressListener);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
