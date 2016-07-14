package com.fanwe.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class SDImageUtil
{

	public static Bitmap drawable2Bitmap(Drawable drawable)
	{

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public static byte[] Bitmap2Bytes(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static Bitmap Bytes2Bimap(byte[] b)
	{
		if (b != null && b.length != 0)
		{
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else
		{
			return null;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Drawable Bitmap2Drawable(Bitmap bitmap)
	{
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}
	
}
