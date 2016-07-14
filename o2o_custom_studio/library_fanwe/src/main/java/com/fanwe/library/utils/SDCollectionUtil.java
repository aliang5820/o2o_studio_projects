package com.fanwe.library.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SDCollectionUtil
{

	public static boolean isEmpty(List<?> list)
	{
		if (list != null && !list.isEmpty())
		{
			return false;
		} else
		{
			return true;
		}
	}

	public static <T> List<T> subListToSize(List<T> list, int size)
	{
		List<T> listReturn = null;
		if (!isEmpty(list) && list.size() >= size && size > 0)
		{
			listReturn = new ArrayList<T>();
			for (int i = 0; i < size; i++)
			{
				T t = list.get(i);
				listReturn.add(t);
			}
		}
		return listReturn;
	}

	public static <T> List<T> subListToSizeAvailable(List<T> list, int size)
	{
		List<T> listReturn = null;
		if (!isEmpty(list) && size > 0)
		{
			int loopCount = 0;
			int listSize = list.size();
			if (size <= listSize)
			{
				loopCount = size;
			} else
			{
				loopCount = listSize;
			}
			listReturn = new ArrayList<T>();
			for (int i = 0; i < loopCount; i++)
			{
				T t = list.get(i);
				listReturn.add(t);
			}
		}
		return listReturn;
	}

	public static <T> T get(List<T> list, int index)
	{
		T t = null;
		if (isIndexLegalInList(list, index))
		{
			t = list.get(index);
		}
		return t;
	}

	public static <T> T getLast(List<T> list, int index)
	{
		T t = null;
		if (isIndexLegalInList(list, index))
		{
			index = list.size() - 1 - index;
			t = list.get(index);
		}
		return t;
	}

	public static <T> boolean isIndexLegalInList(List<T> list, int index)
	{
		if (!isEmpty(list) && index >= 0 && index < list.size())
		{
			return true;
		} else
		{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(List<T> list)
	{
		T[] arr = null;
		if (!isEmpty(list))
		{
			T item = list.get(0);
			if (item != null)
			{
				Class<?> clazzItem = item.getClass();
				arr = (T[]) Array.newInstance(clazzItem, list.size());
				list.toArray(arr);
			}
		}
		return arr;
	}

	public static <T> List<List<T>> splitList(List<T> listModel, int countPerList)
	{
		List<List<T>> listGroupModel = new ArrayList<List<T>>();
		List<T> listPageModel = new ArrayList<T>();

		if (listModel != null && !listModel.isEmpty())
		{
			for (int i = 0; i < listModel.size(); i++)
			{
				listPageModel.add(listModel.get(i));
				if (i != 0)
				{
					if ((i + 1) % (countPerList) == 0)
					{
						listGroupModel.add(listPageModel);
						listPageModel = new ArrayList<T>();
					}
				}
			}

			if (listPageModel.size() > 0)
			{
				listGroupModel.add(listPageModel);
			}
		}
		return listGroupModel;
	}

	public static <T> List<List<T>> splitListLinked(List<T> listModel, int countPerList)
	{
		List<List<T>> listGroupModel = new ArrayList<List<T>>();
		List<T> listPageModel = new ArrayList<T>();

		if (listModel != null && !listModel.isEmpty())
		{
			boolean needBackIndex = false;
			for (int i = 0; i < listModel.size(); i++)
			{
				if (needBackIndex)
				{
					needBackIndex = false;
					listPageModel.add(listModel.get(i - 1));
				} else
				{
					listPageModel.add(listModel.get(i));
				}

				if (i != 0)
				{
					if ((i + 1) % (countPerList) == 0)
					{
						needBackIndex = true;
						listGroupModel.add(listPageModel);
						listPageModel = new ArrayList<T>();
					}
				}
			}

			if (listPageModel.size() > 0)
			{
				listGroupModel.add(listPageModel);
			}
		}
		return listGroupModel;
	}

	public static <T> List<T> subList(List<T> list, int start, int end)
	{
		List<T> listReturn = null;
		if (end >= start && isIndexLegalInList(list, start) && isIndexLegalInList(list, end))
		{
			listReturn = new ArrayList<T>();
			for (int i = start; i <= end; i++)
			{
				T t = list.get(i);
				listReturn.add(t);
			}
		}
		return listReturn;
	}

	public static <T> List<T> subList(List<T> list, int start)
	{
		List<T> listReturn = null;
		if (isIndexLegalInList(list, start))
		{
			listReturn = new ArrayList<T>();
			for (int i = start; i < list.size(); i++)
			{
				T t = list.get(i);
				listReturn.add(t);
			}
		}
		return listReturn;
	}

}
