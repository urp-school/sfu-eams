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
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-3-13            Created
 *  
 ********************************************************************************/
package com.shufe.service.system.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ekingstar.commons.lang.StringUtil;
import com.ekingstar.eams.system.baseinfo.StudentType;

/**
 * 学生类别辅助类
 * 
 * @author chaostone
 * 
 */
public class StudentTypeUtil {
    
    /**
     * 返回学生类别的代码和层递标识的映射关系 该层递关系标识为给定列表中的学生类别之间的关系.
     * 
     * @param studentTypes
     */
    public static Map getLayerTags(List studentTypes) {
        Map layerMap = new HashMap();
        
        // max deepth is 10
        int layersNodes[] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        layersNodes[0] = 1;
        for (int i = 0; i < studentTypes.size(); i++) {
            boolean asChild = false;
            StudentType one = (StudentType) studentTypes.get(i);
            int j = i - 1;
            while (j >= 0) {
                if (((StudentType) studentTypes.get(j)).isSuperTypeOf(one)) {
                    String layerTamp = (String) layerMap.get(((StudentType) studentTypes.get(j))
                            .getCode());
                    layerMap.put(one.getCode(),
                            layerTamp
                                    + "-"
                                    + String.valueOf(layersNodes[StringUtil.countChar(layerTamp,
                                            '-') + 1]++));
                    asChild = true;
                    break;
                } else
                    j--;
            }
            if (!asChild) {
                layerMap.put(one.getCode(), String.valueOf(layersNodes[0]++));
                for (int k = 1; k < layersNodes.length; k++) {
                    layersNodes[k] = 1;
                }
            }
        }
        return layerMap;
    }
}
