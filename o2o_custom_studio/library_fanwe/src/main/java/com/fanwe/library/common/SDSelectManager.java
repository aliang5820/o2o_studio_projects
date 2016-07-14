package com.fanwe.library.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 选择管理器
 * 
 * @author Administrator
 * 
 * @param <T>
 */
public class SDSelectManager<T>
{
	private List<T> mListItem;
	private int mCurrentIndex = -1;
	private int mLastIndex = -1;
	private Map<Integer, T> mMapSelectedIndexItem = new LinkedHashMap<Integer, T>();
	private Mode mMode = Mode.SINGLE_MUST_ONE_SELECTED;
	private SDSelectManagerListener<T> mListener;
	private boolean mEnable = true;

	/**
	 * 设置是否开启管理功能
	 * 
	 * @param enable
	 */
	public void setEnable(boolean enable)
	{
		this.mEnable = enable;
	}

	/**
	 * 是否开启了管理功能，默认true，开启
	 * 
	 * @return
	 */
	public boolean isEnable()
	{
		return mEnable;
	}

	public int getLastIndex()
	{
		return mLastIndex;
	}

	public Mode getMode()
	{
		return mMode;
	}

	/**
	 * 设置选中模式
	 * 
	 * @param mode
	 */
	public void setMode(Mode mode)
	{
		if (mode != null)
		{
			clearSelected();
			this.mMode = mode;
		}
	}

	/**
	 * 是否单选模式
	 * 
	 * @return
	 */
	public boolean isSingleMode()
	{
		boolean single = false;
		switch (mMode)
		{
		case SINGLE:
		case SINGLE_MUST_ONE_SELECTED:
			single = true;
			break;
		case MULTI:
		case MULTI_MUST_ONE_SELECTED:
			single = false;
			break;
		default:
			break;
		}
		return single;
	}

	/**
	 * 项是否被选中
	 * 
	 * @param item
	 * @return
	 */
	public boolean isSelected(T item)
	{
		int index = indexOfItem(item);
		return isSelected(index);
	}

	/**
	 * 项是否被选中
	 * 
	 * @param index
	 * @return
	 */
	public boolean isSelected(int index)
	{
		boolean selected = false;
		if (index >= 0)
		{
			switch (mMode)
			{
			case SINGLE:
			case SINGLE_MUST_ONE_SELECTED:
				if (index == mCurrentIndex)
				{
					selected = true;
				}
				break;
			case MULTI:
			case MULTI_MUST_ONE_SELECTED:
				if (mMapSelectedIndexItem.containsKey(index))
				{
					selected = true;
				}
				break;

			default:
				break;
			}
		}
		return selected;
	}

	/**
	 * 获得选中项的位置(单选模式)
	 * 
	 * @return
	 */
	public int getSelectedIndex()
	{
		return mCurrentIndex;
	}

	/**
	 * 获得选中项(单选模式)
	 * 
	 * @return
	 */
	public T getSelectedItem()
	{
		return getItem(mCurrentIndex);
	}

	/**
	 * 获得选中项的位置(多选模式)
	 * 
	 * @return
	 */
	public List<Integer> getSelectedIndexs()
	{
		List<Integer> listIndex = new ArrayList<Integer>();
		if (!mMapSelectedIndexItem.isEmpty())
		{
			for (Entry<Integer, T> item : mMapSelectedIndexItem.entrySet())
			{
				listIndex.add(item.getKey());
			}
		}
		return listIndex;
	}

	/**
	 * 获得选中项(多选模式)
	 * 
	 * @return
	 */
	public List<T> getSelectedItems()
	{
		List<T> listItem = new ArrayList<T>();
		if (!mMapSelectedIndexItem.isEmpty())
		{
			for (Entry<Integer, T> item : mMapSelectedIndexItem.entrySet())
			{
				listItem.add(item.getValue());
			}
		}
		return listItem;
	}

	/**
	 * 设置监听对象
	 * 
	 * @param listener
	 */
	public void setListener(SDSelectManagerListener<T> listener)
	{
		this.mListener = listener;
	}

	public SDSelectManagerListener<T> getListener()
	{
		return mListener;
	}

	public void setItems(T[] items)
	{
		List<T> listItem = new ArrayList<T>();
		if (items != null && items.length > 0)
		{
			for (int i = 0; i < items.length; i++)
			{
				listItem.add(items[i]);
			}
		}
		setItems(listItem);
	}

	public void setItems(List<T> items)
	{
		this.mListItem = items;
		resetIndex();
		initItems(mListItem);
	}

	public void appendItems(T[] items, boolean addAll)
	{
		List<T> listItem = new ArrayList<T>();
		if (items != null && items.length > 0)
		{
			for (int i = 0; i < items.length; i++)
			{
				listItem.add(items[i]);
			}
		}
		appendItems(listItem, addAll);
	}

	public void appendItems(List<T> items, boolean addAll)
	{
		if (items != null && !items.isEmpty())
		{
			if (mListItem != null && !mListItem.isEmpty())
			{
				if (addAll)
				{
					mListItem.addAll(items);
				}
				initItems(items);
			} else
			{
				setItems(items);
			}
		}
	}

	private void initItems(List<T> items)
	{
		if (items != null && !items.isEmpty())
		{
			T item = null;
			int index = 0;
			for (int i = 0; i < items.size(); i++)
			{
				item = items.get(i);
				index = indexOfItem(item);
				initItem(index, item);
			}
		}
	}

	/**
	 * 设置数据后会遍历调用此方法对每个数据进行初始化
	 * 
	 * @param index
	 * @param item
	 */
	protected void initItem(int index, T item)
	{

	}

	private void resetIndex()
	{
		switch (mMode)
		{
		case SINGLE:
		case SINGLE_MUST_ONE_SELECTED:
			mCurrentIndex = -1;
			break;
		case MULTI:
		case MULTI_MUST_ONE_SELECTED:
			mMapSelectedIndexItem.clear();
			break;

		default:
			break;
		}

	}

	private boolean isIndexLegal(int index)
	{
		if (mListItem != null && index >= 0 && index < mListItem.size())
		{
			return true;
		}
		return false;
	}

	/**
	 * 设置最后一次选中的位置选中(单选模式)
	 */
	public void selectLastIndex()
	{
		setSelected(mLastIndex, true);
	}

	/**
	 * 选中全部(多选模式)
	 */
	public void selectAll()
	{
		if (mListItem != null)
		{
			for (int i = 0; i < mListItem.size(); i++)
			{
				setSelected(i, true);
			}
		}
	}

	/**
	 * 模拟点击该项
	 * 
	 * @param index
	 */
	public void performClick(int index)
	{
		if (isIndexLegal(index))
		{
			boolean selected = isSelected(index);
			setSelected(index, !selected);
		}
	}

	/**
	 * 模拟点击该项
	 * 
	 * @param item
	 */
	public void performClick(T item)
	{
		performClick(indexOfItem(item));
	}

	/**
	 * 设置该项的选中状态
	 * 
	 * @param item
	 * @param selected
	 */
	public void setSelected(T item, boolean selected)
	{
		int index = indexOfItem(item);
		setSelected(index, selected);
	}

	/**
	 * 设置该位置的选中状态
	 * 
	 * @param index
	 * @param selected
	 */
	public void setSelected(int index, boolean selected)
	{
		if (!mEnable)
		{
			return;
		}

		if (!isIndexLegal(index))
		{
			return;
		}

		switch (mMode)
		{
		case SINGLE_MUST_ONE_SELECTED:
			if (selected)
			{
				if (mCurrentIndex == index)
				{

				} else
				{
					int tempCurrentIndex = mCurrentIndex;
					mCurrentIndex = index;

					normalItem(tempCurrentIndex);
					selectItem(mCurrentIndex);

					mLastIndex = mCurrentIndex;
				}
			} else
			{
				if (mCurrentIndex == index)
				{

				} else
				{

				}
			}
			break;
		case SINGLE:
			if (selected)
			{
				if (mCurrentIndex == index)
				{

				} else
				{
					int tempCurrentIndex = mCurrentIndex;
					mCurrentIndex = index;

					normalItem(tempCurrentIndex);
					selectItem(mCurrentIndex);

					mLastIndex = mCurrentIndex;
				}
			} else
			{
				if (mCurrentIndex == index)
				{
					int tempCurrentIndex = mCurrentIndex;
					mCurrentIndex = -1;

					normalItem(tempCurrentIndex);
				} else
				{

				}
			}
			break;
		case MULTI_MUST_ONE_SELECTED:
			if (selected)
			{
				if (mMapSelectedIndexItem.containsKey(index))
				{

				} else
				{
					mMapSelectedIndexItem.put(index, getItem(index));
					selectItem(index);
				}
			} else
			{
				if (mMapSelectedIndexItem.containsKey(index))
				{
					if (mMapSelectedIndexItem.size() == 1)
					{

					} else
					{
						mMapSelectedIndexItem.remove(index);
						normalItem(index);
					}
				} else
				{

				}
			}
			break;
		case MULTI:
			if (selected)
			{
				if (mMapSelectedIndexItem.containsKey(index))
				{

				} else
				{
					mMapSelectedIndexItem.put(index, getItem(index));
					selectItem(index);
				}
			} else
			{
				if (mMapSelectedIndexItem.containsKey(index))
				{
					mMapSelectedIndexItem.remove(index);
					normalItem(index);
				} else
				{

				}
			}
			break;

		default:
			break;
		}
	}

	private void normalItem(int index)
	{
		if (isIndexLegal(index))
		{
			notifyNormal(index, getItem(index));
		}
	}

	private void selectItem(int index)
	{
		if (isIndexLegal(index))
		{
			notifySelected(index, getItem(index));
		}
	}

	protected void notifyNormal(int index, T item)
	{
		if (mListener != null)
		{
			mListener.onNormal(index, item);
		}
	}

	protected void notifySelected(int index, T item)
	{
		if (mListener != null)
		{
			mListener.onSelected(index, item);
		}
	}

	/**
	 * 获得该位置的item
	 * 
	 * @param index
	 * @return
	 */
	public T getItem(int index)
	{
		T item = null;
		if (isIndexLegal(index))
		{
			item = mListItem.get(index);
		}
		return item;
	}

	/**
	 * 返回item的位置
	 * 
	 * @param item
	 * @return
	 */
	public int indexOfItem(T item)
	{
		int index = -1;
		if (item != null && mListItem != null)
		{
			index = mListItem.indexOf(item);
		}
		return index;
	}

	/**
	 * 清除选中
	 */
	public void clearSelected()
	{
		switch (mMode)
		{
		case SINGLE:
		case SINGLE_MUST_ONE_SELECTED:
			if (mCurrentIndex >= 0)
			{
				int tempCurrentIndex = mCurrentIndex;
				resetIndex();
				normalItem(tempCurrentIndex);
			}
			break;
		case MULTI:
		case MULTI_MUST_ONE_SELECTED:
			List<Integer> listIndexs = getSelectedIndexs();
			if (listIndexs != null)
			{
				for (Integer index : listIndexs)
				{
					mMapSelectedIndexItem.remove(index);
					normalItem(index);
				}
				resetIndex();
			}
			break;
		default:
			break;
		}
	}

	public interface SDSelectManagerListener<T>
	{
		public void onNormal(int index, T item);

		public void onSelected(int index, T item);
	}

	public enum Mode
	{
		/** 单选，必须选中一项 */
		SINGLE_MUST_ONE_SELECTED,
		/** 单选，可以一项都没选中 */
		SINGLE,
		/** 多选，必须选中一项 */
		MULTI_MUST_ONE_SELECTED,
		/** 多选，可以一项都没选中 */
		MULTI;
	}
}
