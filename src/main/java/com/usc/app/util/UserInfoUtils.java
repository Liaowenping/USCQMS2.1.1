package com.usc.app.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.usc.cache.redis.RedisUtil;
import com.usc.obj.api.USCObject;
import com.usc.obj.api.bean.UserInformation;
import com.usc.obj.api.type.user.UserInfoObject;
import com.usc.obj.util.USCObjectQueryHelper;
import com.usc.server.jdbc.SDBUtils;
import com.usc.util.ObjectHelperUtils;

public class UserInfoUtils

{

	public final static String USER_SESSION_KEY = "userInfo";

	public static final String USER_CLIENT_SESSION_KEY = "clientID";

	private static RedisUtil redisUtil = RedisUtil.getInstanceOfObject();

	private static Map<String, UserInformation> users = new ConcurrentHashMap<String, UserInformation>();

	public static boolean userExistence(String userName)
	{

		return getUserInformation(userName) != null ? true : false;
	}

	public static String getPassWord(String userName)
	{
		return SDBUtils.getUserPS(userName);
	}

	public static UserInfoObject getUserInfoObject(String userName)
	{
		Object userInfo = redisUtil.hget("USERINFODATA", userName);
		return userInfo != null ? (UserInfoObject) userInfo : null;
	}

	public static UserInformation getUserInformation(String userName)
	{
		if (userName == null)
		{
			return null;
		}
		if (users.containsKey(userName))
		{
			return users.get(userName);
		}
		UserInfoObject userInfo = getUserInfoObject(userName);
		if (userInfo == null)
		{
			return null;
		}
		UserInformation information = userInfo.getUserInfomation();
		putUserInfomation(information);
		return information;
	}

	public static void putUserInfomation(UserInformation information)
	{
		if (information != null)
		{
			users.put(information.getUserName(), information);
		}

	}

	public static Map<String, Object> createUserInfoMap(String userName)
	{
		UserInformation information = getUserInformation(userName);
		Map<String, Object> infoMap = BeanConverter.toMap(information);
		return infoMap;
	}

	public static String getUserWKContextID(UserInformation userInformation)
	{
		Map<?, ?> map = SDBUtils.getUserWKContextID(userInformation);
		return ObjectHelperUtils.isEmpty(map) ? null : String.valueOf(map.get("ID"));
	}

	public static boolean isSuperAdministrator(String userName)
	{
		if (userName.equals("admin"))
		{
			return true;
		}

		return false;
	}

	public static USCObject getUserInfoByClientID(String clientID)
	{
		String condition = "ID=(SELECT SUID FROM WKCLIENT WHERE ID='" + clientID + "')";
		USCObject user = USCObjectQueryHelper.getObjectByCondition("SUSER", condition);
//		List<Map<String, Object>> maps = DBUtil.getSQLResultByCondition("SUSER", condition, new Object[]
//		{ clientID }, new int[]
//		{ Types.VARCHAR });
//		if (maps != null)
//		{
//			return maps.get(0);
//		}
		return user;
	}

	public static String getDefaultPassWord()
	{
		Properties properties;
		try
		{
			properties = PropertiesLoaderUtils.loadAllProperties("res/us/userps.properties");
			if (properties != null)
			{
				HashMap<String, String> map = new HashMap<String, String>();
				Enumeration<Object> keys = properties.keys();
				while (keys.hasMoreElements())
				{
					String key = (String) keys.nextElement();
					if (key.equals("defuault"))
					{
						return properties.getProperty(key);
					}
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return "123";
	}

	public static String getDefaultPassWordLength()
	{
		Properties properties;
		try
		{
			properties = PropertiesLoaderUtils.loadAllProperties("res/us/userps.properties");
			if (properties != null)
			{
				HashMap<String, String> map = new HashMap<String, String>();
				Enumeration<Object> keys = properties.keys();
				while (keys.hasMoreElements())
				{
					String key = (String) keys.nextElement();
					if (key.equals("length"))
					{
						return properties.getProperty(key);
					}
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return "123";
	}
}
