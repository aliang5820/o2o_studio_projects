package com.fanwe.library.mediaplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;

public class SDMediaPlayer
{
	private Context mContext;
	private MediaPlayer mPlayer;
	private EnumState mState = EnumState.Idle;
	private String mStrAudioFilePath;
	private String mStrAudioUrl;
	private SDMediaPlayerListener mListener;

	public SDMediaPlayer(Context context)
	{
		this.mContext = context;
		create();
	}


	public void setListener(SDMediaPlayerListener listener)
	{
		this.mListener = listener;
	}

	public String getAudioFilePath()
	{
		return mStrAudioFilePath;
	}

	public String getAudioUrl()
	{
		return mStrAudioUrl;
	}

	public void playAudioRaw(int resId)
	{
		reset();
		try
		{
			AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(resId);
			mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			setState(EnumState.Initialized);
			notifyInitialized();
			start();
		}catch (Exception e)
		{
			notifyException(e);
		}
	}

	public void playAudioUrl(String url)
	{
		reset();
		try
		{
			mPlayer.setDataSource(url);
			this.mStrAudioUrl = url;
            setState(EnumState.Initialized);
            notifyInitialized();
			start();
		} catch (Exception e)
		{
			notifyException(e);
		}
	}


	public void playAudioFile(String path)
	{
		reset();
        try {
            mPlayer.setDataSource(path);
			this.mStrAudioFilePath = path;
            setState(EnumState.Initialized);
            notifyInitialized();
			start();
		} catch (Exception e)
		{
			notifyException(e);
		}
	}


	public boolean isPlaying()
	{
		return mState == EnumState.Playing;
	}

	public boolean isPause()
	{
		return mState == EnumState.Paused;
	}

	/**
	 * 开始播放
	 */
	public void start()
	{
		switch (mState)
		{
		case Idle:

			break;
		case Initialized:
			prepareAsyncPlayer();
			break;
		case Preparing:

			break;
		case Prepared:
			startPlayer();
			break;
		case Playing:

			break;
		case Paused:
			startPlayer();
			break;
		case Completed:
			startPlayer();
			break;
		case Stopped:
			prepareAsyncPlayer();
			break;

		default:
			break;
		}
	}

	/**
	 * 暂停播放
	 */
	public void pause()
	{
		switch (mState)
		{
		case Idle:

			break;
		case Initialized:

			break;
		case Preparing:

			break;
		case Prepared:

			break;
		case Playing:
			pausePlayer();
			break;
		case Paused:

			break;
		case Completed:

			break;
		case Stopped:

			break;

		default:
			break;
		}
	}

	/**
	 * 停止播放
	 */
	public void stop()
	{
		switch (mState)
		{
		case Idle:

			break;
		case Initialized:

			break;
		case Preparing:

			break;
		case Prepared:
			stopPlayer();
			break;
		case Playing:
			stopPlayer();
			break;
		case Paused:
			stopPlayer();
			break;
		case Completed:
			stopPlayer();
			break;
		case Stopped:

			break;

		default:
			break;
		}
	}

	public void reset()
	{
		stop();
		resetPlayer();
	}

	public void release()
	{
		stop();
		releasePlayer();
	}

	public void create()
	{
		if (mPlayer != null)
		{
			release();
		}
		mPlayer = new MediaPlayer();
		mPlayer.setOnErrorListener(mListenerOnError);
		mPlayer.setOnPreparedListener(mListenerOnPrepared);
		mPlayer.setOnCompletionListener(mListenerOnCompletion);
	}

	private void setState(EnumState state)
	{
		this.mState = state;
	}

	private void prepareAsyncPlayer()
	{
		setState(EnumState.Preparing);
		notifyPreparing();
		mPlayer.prepareAsync();
	}

	private void startPlayer()
	{
		setState(EnumState.Playing);
		notifyPlaying();
		mPlayer.start();
	}

	private void pausePlayer()
	{
		setState(EnumState.Paused);
		notifyPaused();
		mPlayer.pause();
	}

	private void stopPlayer()
	{
		setState(EnumState.Stopped);
		notifyStopped();
		mPlayer.stop();
	}

	private void resetPlayer()
	{
		setState(EnumState.Idle);
		notifyReset();
		mPlayer.reset();
	}

	private void releasePlayer()
	{
		setState(EnumState.Released);
		notifyReleased();
		mPlayer.release();
	}

	// notify

	protected void notifyPreparing()
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onPreparing();
		}
	}

	protected void notifyPrepared()
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onPrepared();
		}
	}

	protected void notifyPlaying()
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onPlaying();
		}
	}

	protected void notifyPaused()
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onPaused();
		}
	}

	protected void notifyCompletion()
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onCompletion();
		}
	}

	protected void notifyStopped()
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onStopped();
		}
	}

	protected void notifyReset()
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onReset();
		}
	}

	protected void notifyInitialized()
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onInitialized();
		}
	}

	protected void notifyReleased()
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onReleased();
		}
	}

	protected void notifyError(MediaPlayer mp, int what, int extra)
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onError(mp, what, extra);
		}
	}

	protected void notifyException(Exception e)
	{
		// TODO Auto-generated method stub
		if (mListener != null)
		{
			mListener.onException(e);
		}
	}

	// listener
	private OnErrorListener mListenerOnError = new OnErrorListener()
	{

		@Override
		public boolean onError(MediaPlayer mp, int what, int extra)
		{
			resetPlayer();
			notifyError(mp, what, extra);
			return true;
		}
	};

	private OnPreparedListener mListenerOnPrepared = new OnPreparedListener()
	{

		@Override
		public void onPrepared(MediaPlayer mp)
		{
			setState(EnumState.Prepared);
			notifyPrepared();
			start();
		}
	};

	private OnCompletionListener mListenerOnCompletion = new OnCompletionListener()
	{

		@Override
		public void onCompletion(MediaPlayer mp)
		{
			setState(EnumState.Completed);
			notifyCompletion();
		}
	};

	public enum EnumState
	{
		/** 已经释放资源 */
		Released,
		/** 空闲，还没设置dataSource */
		Idle,
		/** 已经设置dataSource，还未播放 */
		Initialized,
		/** 准备中 */
		Preparing,
		/** 准备完毕 */
		Prepared,
		/** 已经启动播放 */
		Playing,
		/** 已经暂停播放 */
		Paused,
		/** 已经播放完毕 */
		Completed,
		/** 调用stop方法后的状态 */
		Stopped;
	}

	public interface SDMediaPlayerListener
	{
		public void onReleased();
		public void onReset();
		public void onInitialized();
		public void onPreparing();
		public void onPrepared();
		public void onPlaying();
		public void onPaused();
		public void onCompletion();
		public void onStopped();
		public void onError(MediaPlayer mp, int what, int extra);
		public void onException(Exception e);

	}

}
