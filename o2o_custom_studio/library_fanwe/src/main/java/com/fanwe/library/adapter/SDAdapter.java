package com.fanwe.library.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.common.SDSelectManager.Mode;
import com.fanwe.library.common.SDSelectManager.SDSelectManagerListener;
import com.fanwe.library.utils.SDHandlerUtil;

public abstract class SDAdapter<T> extends BaseAdapter
{

	protected List<T> mListModel = new ArrayList<T>();
	protected LayoutInflater mInflater;
	public Activity mActivity;

	/** 保存每个position的parentView */
	private SparseArray<ViewGroup> mMapPositionParentView = new SparseArray<ViewGroup>();
	/** 保存每个itemView对应的position */
	private Map<View, Integer> mMapViewPosition = new LinkedHashMap<View, Integer>();

	private SDSelectManager<T> mSelectManager = new SDSelectManager<T>();
	private ItemStateListener<T> mListenerItemState;
	private ItemClickListener<T> mListenerItemClick;

	public SDAdapter(List<T> listModel, Activity activity)
	{
		mSelectManager.setMode(Mode.SINGLE);
		mSelectManager.setListener(mDefaultSelectListener);
		setData(listModel);
		this.mActivity = activity;
		this.mInflater = mActivity.getLayoutInflater();
	}

	public void setListenerItemState(ItemStateListener<T> listenerItemState)
	{
		this.mListenerItemState = listenerItemState;
	}

	public void setListenerItemClick(ItemClickListener<T> listenerItemClick)
	{
		this.mListenerItemClick = listenerItemClick;
	}

	/**
	 * 获得选择管理器
	 * 
	 * @return
	 */
	public SDSelectManager<T> getSelectManager()
	{
		return mSelectManager;
	}

	/**
	 * 默认选择管理器监听
	 */
	private SDSelectManagerListener<T> mDefaultSelectListener = new SDSelectManagerListener<T>()
	{

		@Override
		public void onNormal(int index, T item)
		{
			onNormalItem(index, item);
			if (mListenerItemState != null)
			{
				mListenerItemState.onNormal(index, item);
			}
		}

		@Override
		public void onSelected(int index, T item)
		{
			onSelectedItem(index, item);
			if (mListenerItemState != null)
			{
				mListenerItemState.onSelected(index, item);
			}
		}
	};

	/**
	 * 判断数据实体是否实现了SDSelectable接口
	 * 
	 * @return
	 */
	private boolean isSDSelectableModels(List<?> listModel)
	{
		boolean result = false;
		if (listModel != null && !listModel.isEmpty())
		{
			Object t = listModel.get(0);
			if (t instanceof SDSelectable)
			{
				result = true;
			}
		}
		return result;
	}

	/**
	 * 将实体中的选中状态同步到选择管理器中，常用于设置数据后调用，只有实体实现了SDSelectable接口调用此方法才有效
	 */
	protected void synchronizedSelected(List<?> listModel)
	{
		if (isSDSelectableModels(listModel))
		{
			int size = listModel.size();
			SDSelectable sModel = null;
			for (int i = 0; i < size; i++)
			{
				sModel = (SDSelectable) listModel.get(i);
				if (mSelectManager.isSelected(i) != sModel.isSelected())
				{
					mSelectManager.setSelected(i, sModel.isSelected());
				}
			}
		}
	}

	/**
	 * item被置为正常状态的时候回调
	 * 
	 * @param position
	 * @param item
	 */
	protected void onNormalItem(int position, T item)
	{
		if (item instanceof SDSelectable)
		{
			SDSelectable selectable = (SDSelectable) item;
			selectable.setSelected(false);
		}
		updateItem(position);
	}

	/**
	 * item被置为选中状态的时候回调
	 * 
	 * @param position
	 * @param item
	 */
	protected void onSelectedItem(int position, T item)
	{
		if (item instanceof SDSelectable)
		{
			SDSelectable selectable = (SDSelectable) item;
			selectable.setSelected(true);
		}
		updateItem(position);
	}

	/**
	 * 获得adapter的实体集合
	 * 
	 * @return
	 */
	public List<T> getData()
	{
		return mListModel;
	}

	/**
	 * 更新adapter的数据集合，并刷新adapter
	 * 
	 * @param listModel
	 */
	public void updateData(List<T> listModel)
	{
		setData(listModel);
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetChanged()
	{
		clearViews();
		super.notifyDataSetChanged();
	}

	private void clearViews()
	{
		mMapPositionParentView.clear();
		mMapViewPosition.clear();
	}

	/**
	 * 添加数据
	 * 
	 * @param listModel
	 */
	public void appendData(List<T> listModel)
	{
		if (mListModel != null && listModel != null && listModel.size() > 0)
		{
			mListModel.addAll(listModel);
			mSelectManager.appendItems(listModel, false);
			synchronizedSelected(listModel);
			notifyDataSetChanged();
		}
	}

	/**
	 * 给adapter设置数据
	 * 
	 * @param listModel
	 */
	public void setData(List<T> listModel)
	{
		if (listModel != null)
		{
			this.mListModel = listModel;
		} else
		{
			this.mListModel = new ArrayList<T>();
		}
		mSelectManager.setItems(mListModel);
		synchronizedSelected(mListModel);
	}

	public boolean isPositionLegal(int position)
	{
		if (mListModel != null && !mListModel.isEmpty() && position >= 0 && position < mListModel.size())
		{
			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public int getCount()
	{
		if (mListModel != null)
		{
			return mListModel.size();
		} else
		{
			return 0;
		}
	}

	@Override
	public T getItem(int position)
	{
		if (isPositionLegal(position))
		{
			return mListModel.get(position);
		} else
		{
			return null;
		}
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		beforeOnGetView(position, convertView, parent);
		convertView = onGetView(position, convertView, parent);
		afterOnGetView(position, convertView, parent);
		return convertView;
	}

	protected void beforeOnGetView(int position, View convertView, ViewGroup parent)
	{
		mMapPositionParentView.put(position, parent);
	}

	/**
	 * 重写此方法，不要重写public View getView(int position, View convertView, ViewGroup
	 * parent)方法
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	protected View onGetView(int position, View convertView, ViewGroup parent)
	{
		return convertView;
	}

	protected void afterOnGetView(final int position, final View convertView, ViewGroup parent)
	{
		if (mListenerItemClick != null)
		{
			convertView.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					if (mListenerItemClick != null)
					{
						mListenerItemClick.onClick(position, getItem(position), convertView);
					}
				}
			});
		}
		putItemView(position, convertView);
	}

	private void putItemView(int position, View view)
	{
		mMapViewPosition.put(view, position);
	}

	/**
	 * 刷新position对应的itemView
	 * 
	 * @param position
	 */
	public void updateItem(int position)
	{
		View itemView = getItemView(position);
		if (itemView != null)
		{
			onUpdateView(position, itemView, getItemParent(position), getItem(position));
		}
	}

	/**
	 * 刷新position对应的itemView，数据会根据提供的model来绑定，并替换原list中该位置的model
	 * 
	 * @param position
	 * @param model
	 */
	public void updateItem(int position, T model)
	{
		if (mListModel != null && isPositionLegal(position))
		{
			mListModel.set(position, model);
			updateItem(position);
		}
	}

	/**
	 * 刷新model对应的itemView
	 * 
	 * @param model
	 */
	public void updateItem(T model)
	{
		updateItem(indexOf(model));
	}

	/**
	 * 获得该position对应的itemView
	 * 
	 * @param position
	 * @return
	 */
	public View getItemView(int position)
	{
		View itemView = null;
		for (Entry<View, Integer> item : mMapViewPosition.entrySet())
		{
			if (Integer.valueOf(position).equals(item.getValue()))
			{
				itemView = item.getKey();
				break;
			}
		}
		return itemView;
	}

	/**
	 * 获得该position对应到parentView
	 * 
	 * @param position
	 * @return
	 */
	public ViewGroup getItemParent(int position)
	{
		return mMapPositionParentView.get(position);
	}

	/**
	 * 若重写此方法，则应该把需要刷新的逻辑写在重写方法中，然后不调用super的方法，此方法会在调用updateItem方法刷新某一项时候触发
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @param model
	 */
	protected void onUpdateView(int position, View convertView, ViewGroup parent, T model)
	{
		getView(position, convertView, getItemParent(position));
	}

	/**
	 * 移除该position对应的项
	 * 
	 * @param position
	 */
	public void removeItem(int position)
	{
		if (isPositionLegal(position))
		{
			mListModel.remove(position);
			notifyDataSetChanged();
		}
	}

	/**
	 * 移除该model对应的项
	 * 
	 * @param t
	 */
	public void removeItem(T t)
	{
		removeItem(indexOf(t));
	}

	public int indexOf(T t)
	{
		int index = -1;
		if (t != null && mListModel != null)
		{
			index = mListModel.indexOf(t);
		}
		return index;
	}

	public void notifyDataSetChanged(long delay)
	{
		if (delay <= 0)
		{
			notifyDataSetChanged();
		} else
		{
			SDHandlerUtil.runOnUiThreadDelayed(new Runnable()
			{

				@Override
				public void run()
				{
					notifyDataSetChanged();
				}
			}, delay);
		}
	}

	// util method
	@SuppressWarnings("unchecked")
	public static <V extends View> V get(int id, View convertView)
	{
		SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
		if (viewHolder == null)
		{
			viewHolder = new SparseArray<View>();
			convertView.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null)
		{
			childView = convertView.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (V) childView;
	}

	@SuppressWarnings("unchecked")
	public static <V extends View> V find(int id, View convertView)
	{
		return (V) convertView.findViewById(id);
	}

	public interface SDSelectable
	{
		public boolean isSelected();

		public void setSelected(boolean selected);
	}

	public interface ItemStateListener<T>
	{
		public void onNormal(int position, T item);

		public void onSelected(int position, T item);
	}

	public interface ItemClickListener<T>
	{
		public void onClick(int position, T item, View view);
	}

}
