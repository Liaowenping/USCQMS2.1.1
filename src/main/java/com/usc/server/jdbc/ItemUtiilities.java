package com.usc.server.jdbc;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.usc.app.action.mate.MateFactory;
import com.usc.obj.api.USCObject;
import com.usc.server.util.SystemTime;
import com.usc.server.util.uuid.USCUUID;

public class ItemUtiilities
{
	public ItemUtiilities()
	{
	}

	public static Map<String, Object> newDataItem(String user, String itemNo, Map<String, Object> data, boolean b)
			throws Exception
	{
		String strID = null;
		if (b)
		{
			strID = USCUUID.UUID();
		} else
		{
			if (data.containsKey("ID"))
			{
				strID = (String) data.get("ID");
			}
		}
		if (strID == null)
		{
			throw new Exception("object ID is null");
		}
		data.put("ID", strID);
		data.put("DEL", false);
		Map<String, Object> newMap = DBUtil.insertRecord(itemNo, data, user);
//		newMap.put("USC_OBJECT", itemNo);
		return newMap;
	}

	public static Map<String, Object> newDataItem(String itemno, String user, Map<String, Object> data) throws Exception
	{
		data.put("CUSER", user);
		data.put("CTIME", SystemTime.getTimestamp());
		return newDataItem(user, itemno, data, true);
	}

	public static List getClasObjectResult(String nodeID, String itemNo, String classTableName, int page)
	{
		String sql = "EXISTS(SELECT 1 FROM " + classTableName + " WHERE del=?  AND itemid="
				+ MateFactory.getItemInfo(itemNo).getTableName() + ".id AND nodeid=?)";
		Object[] objects = new Object[]
		{ 0, nodeID };
		List list = DBUtil.getSQLResultByConditionLimit(itemNo, sql, objects, new int[]
		{ Types.INTEGER, Types.VARCHAR }, page);
		return list;

	}

	public static List getClasObjectResult(String nodeID, String itemNo, String classTableName)
	{
		String sql = "EXISTS(SELECT 1 FROM " + classTableName + " WHERE del=?  AND itemid="
				+ MateFactory.getItemInfo(itemNo).getTableName() + ".id AND nodeid=?)";
		Object[] objects = new Object[]
		{ 0, nodeID };
		List list = DBUtil.getSQLResultByCondition(itemNo, sql, objects, new int[]
		{ Types.INTEGER, Types.VARCHAR });
		return list;

	}

	public static List getClasNodeResult(USCObject nodeObject)
	{
		String nodeID = nodeObject.getID();
		String bItemNo = nodeObject.getFieldValueToString("itemno");

		return getClasNodeResult(nodeObject.getItemNo(), nodeID, bItemNo);

	}

	public static List getClasNodeResult(String nodeItemNo, String nodeID, String bItemNo)
	{
		String sql = "PID=? AND ITEMNO=?";
		Object[] objects = new Object[]
		{ nodeID, bItemNo };
		List list = DBUtil.getSQLResultByCondition(nodeItemNo, sql, objects, new int[]
		{ Types.VARCHAR, Types.VARCHAR });
		return list;

	}

	public static List getClasNodeData(String nodeItemNo, String bItemNo)
	{
		String sql = "ITEMNO=?";
		Object[] objects = new Object[]
		{ bItemNo };
		List list = DBUtil.getSQLResultByCondition(nodeItemNo, sql, objects, new int[]
		{ Types.VARCHAR });
		return list;

	}
}
