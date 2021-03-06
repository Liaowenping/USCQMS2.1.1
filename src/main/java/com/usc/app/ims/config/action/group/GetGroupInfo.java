package com.usc.app.ims.config.action.group;

import com.usc.app.action.a.AbstractAction;
import com.usc.obj.api.USCObject;
import com.usc.obj.util.USCObjectQueryHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lwp
 * @DATE: 2019/11/19 9:55
 * @Description: 根据groupId获取群组信息
 **/
public class GetGroupInfo extends AbstractAction {
    @Override
    public Object executeAction() throws Exception {
        //根据groupId获取群组信息
        Map<String,Object> groupInfo = new HashMap<>();
        USCObject group = USCObjectQueryHelper.getObjectByID("CHAT_GROUP", (String) context.getExtendInfo("groupId"));
        groupInfo.put("id",group.getID());
        groupInfo.put("createBy",group.getFieldValueToString("CREATE_BY"));
        groupInfo.put("photo",group.getFieldValueToString("PHOTO"));
        groupInfo.put("groupName",group.getFieldValueToString("GROUP_NAME"));
        groupInfo.put("remarks",group.getFieldValueToString("REMARK"));
        return groupInfo;
    }

    @Override
    public boolean disable() throws Exception {
        return false;
    }
}
