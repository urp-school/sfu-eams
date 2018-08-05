package com.shufe.service.std.graduation.audit.impl.rule;

import org.beanfuse.rule.Context;
import org.beanfuse.rule.engine.RuleExecutor;

/**
 * 在学制规定的年限内获得毕业资格 检查类
 * 
 * @author zhihe
 * 
 */
public class GraduateLifeChecker implements RuleExecutor {
    
    public boolean execute(Context context) {
        return true;
    }
}
