package com.fanwe.fragment;

import com.fanwe.constant.Constant.EnumLoginState;
import com.fanwe.work.AppRuntimeWorker;

public class LoginBaseFragment extends BaseFragment
{

	protected void changeViewState()
	{
		EnumLoginState state = AppRuntimeWorker.getLoginState();
		switch (state)
		{
		case LOGIN_EMPTY_PHONE:
			changeViewLoginEmptyPhone();
			break;
		case UN_LOGIN:
			changeViewUnLogin();
			break;
		case LOGIN_NEED_BIND_PHONE:
			changeViewNeedBindPhone();
			break;

		default:
			break;
		}
	}

	protected void changeViewNeedBindPhone()
	{
		// TODO Auto-generated method stub

	}

	protected void changeViewUnLogin()
	{
		// TODO Auto-generated method stub

	}

	protected void changeViewLoginEmptyPhone()
	{
		// TODO Auto-generated method stub

	}

}
