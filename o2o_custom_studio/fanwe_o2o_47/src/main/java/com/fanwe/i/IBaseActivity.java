package com.fanwe.i;

import android.support.v4.app.Fragment;

public interface IBaseActivity
{
	public void baseInit();

	public void replaceFragment(Fragment fragment, int containerId);

	public void showFragment(Fragment fragment);

	public void hideFragment(Fragment fragment);

}
