//$Id: UserImporterListener.java,v 1.1 2009-5-14 下午06:17:55 zhouqi Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author zhouqi
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * zhouqi              2009-5-14             Created
 *  
 ********************************************************************************/

package com.shufe.service.system.security;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.importer.ItemImporterListener;
import com.ekingstar.commons.utils.persistence.UtilService;
import com.ekingstar.security.model.User;

/**
 * @author zhouqi
 * 
 */
public class UserImporterListener extends ItemImporterListener {
    
    protected UtilService utilSerivce;
    
    public UserImporterListener(final UtilService utilSerivce) {
        this.utilSerivce = utilSerivce;
    }
    
    public void startTransferItem(TransferResult tr) {
        super.startTransferItem(tr);
    }
    
    public void endTransferItem(TransferResult tr) {
        User user = (User) importer.getCurrent();
        if (StringUtils.isEmpty(user.getName())) {
            tr.addFailure("error.parameters.needed", "User Logging Name");
            return;
        }
        if (null == user.getState()) {
            tr.addFailure("error.parameters.needed", "User State");
            return;
        }
        if (user.getState().intValue() != 0 && user.getState().intValue() != 1) {
            tr.addFailure("error.parameters.illegal", "User State");
            return;
        }
        Collection users = utilSerivce.load(User.class, "name", user.getName());
        if (CollectionUtils.isEmpty(users)) {
            tr.addFailure("error.model.notExist", user.getName());
            return;
        }
        if (user.getState().intValue() == 0 && StringUtils.isEmpty(user.getRemark())) {
            tr.addFailure("error.parameters.needed", "No remark cause unactive for User.");
        }
        if (tr.errors() == 0) {
            User user1 = (User) users.iterator().next();
            user1.setState(user.getState());
            user1.setRemark(user.getRemark());
            utilSerivce.saveOrUpdate(user1);
        }
    }
}
