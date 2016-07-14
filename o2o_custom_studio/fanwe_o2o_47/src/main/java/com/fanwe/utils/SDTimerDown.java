package com.fanwe.utils;

import android.widget.TextView;

import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDTimer;
import com.fanwe.library.utils.SDTimer.SDTimerListener;

public class SDTimerDown
{
	private SDTimer mSdTimer = new SDTimer();

	public void startCount(final TextView tv, final long timeSecond, final SDTimerDownListener listener)
	{
		if (listener != null)
		{
			listener.onStart();
		}
		mSdTimer.startWork(0, 1000, new SDTimerListener()
		{
			long time = timeSecond;

			@Override
			public void onWorkMain()
			{

				if (time > 0)
				{
					final long day = SDDateUtil.getDuringDay(time * 1000);
					final long hour = SDDateUtil.getDuringHours(time * 1000);
					final long min = SDDateUtil.getDuringMinutes(time * 1000);
					final long sec = SDDateUtil.getDuringSeconds(time * 1000);
					if (tv != null)
					{
						tv.setText(day + "天" + hour + "时" + min + "分" + sec + "秒");
						time--;
					} else
					{
						stopCount();
					}

					if (listener != null)
					{
						listener.onTick();
					}
				} else
				{
					if (tv != null)
					{
						tv.setText("已过期");
						if (listener != null)
						{
							listener.onTickFinish();
						}
					} else
					{
						stopCount();
					}
				}
			}

			@Override
			public void onWork()
			{
			}
		});
	}

	public void stopCount()
	{
		mSdTimer.stopWork();
	}

	public interface SDTimerDownListener
	{
		public void onStart();

		public void onTick();

		public void onTickFinish();
	}

}
