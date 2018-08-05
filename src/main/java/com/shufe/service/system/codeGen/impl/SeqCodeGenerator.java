//$Id: SeqCodeGenerator.java,v 1.1 2007-2-4 上午10:03:48 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-2-4         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.codeGen.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ekingstar.commons.model.Entity;
import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.utils.persistence.UtilDao;
import com.shufe.model.system.codeGen.CodeScript;

public class SeqCodeGenerator extends ScriptCodeGenerator {
    
    public static final String SEQ = "seq";
    
    private UtilDao utilDao;
    
    public void setUp() throws Exception {
    	//事先注册seq变量
        interpreter.set("seq", SEQ);
        interpreter.set("dao", utilDao);
    }
    
    /**
     * 根据请求的实体，生成代码
     */
    public String gen(Entity entity, CodeScript codeScript) {
        // 在必要时查找相应的生成脚本
        if (null == codeScript) {
            EntityQuery codeGenScriptQuery = new EntityQuery(CodeScript.class, "codeScript");
            codeGenScriptQuery.add(new Condition("codeScript.codeClassName=:codeClassName",
                    EntityUtils.getEntityClassName(entity.getClass())));
            List scripts = (List) utilDao.search(codeGenScriptQuery);
            if (scripts.size() != 1)
                return null;
            codeScript = (CodeScript) scripts.get(0);
            try {
                String code = (String) PropertyUtils.getProperty(entity, codeScript.getAttr());
                if (isValidCode(code))
                    return code;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        setScript(codeScript.getScript());
        int seqLength = -1;
        //替换自动代码生成中的seq[x]
        if (StringUtils.contains(getScript(), SEQ)) {
            seqLength = NumberUtils
                    .toInt(StringUtils.substringBetween(getScript(), SEQ + "[", "]"));
            
            setScript(StringUtils.replace(getScript(), SEQ + "["
                    + StringUtils.substringBetween(getScript(), SEQ + "[", "]") + "]", SEQ));
        }
        String code = super.gen(entity, codeScript);
        List seqs = new ArrayList();
        if (-1 != seqLength) {
            try {
                EntityQuery query = new EntityQuery(Class.forName(codeScript.getCodeClassName()),
                        "entity");
                query.setSelect("select substr(entity." + codeScript.getAttr() + ","
                        + (code.indexOf(SEQ) + 1) + "," + seqLength + ")");
                query.add(new Condition(" entity." + codeScript.getAttr() + " like :codeExample",
                        StringUtils.replace(code, SEQ, "%")));
                query.add(new Condition("length(entity." + codeScript.getAttr() + ")="
                        + (code.length() - SEQ.length() + seqLength)));
                seqs = (List) utilDao.search(query);
                Collections.sort(seqs);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            synchronized (this) {
                int newSeqNo = 0;
                for (Iterator iter = seqs.iterator(); iter.hasNext();) {
                    String seqNo = (String) iter.next();
                    if (NumberUtils.toInt(seqNo) - newSeqNo >= 2) {
                        break;
                    } else {
                        newSeqNo = NumberUtils.toInt(seqNo);
                    }
                }
                newSeqNo++;
                String seqNo = String.valueOf(newSeqNo);
                if (0 != seqLength) {
                    seqNo = StringUtils.repeat("0", seqLength - seqNo.length()) + newSeqNo;
                }
                code = StringUtils.replace(code, SEQ, seqNo);
            }
        }
        return code;
    }
    
    public void setUtilDao(UtilDao utilDao) {
        this.utilDao = utilDao;
    }
}
