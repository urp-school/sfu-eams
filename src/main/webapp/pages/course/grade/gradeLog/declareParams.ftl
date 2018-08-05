        <#assign START><span style="color:blue;font-weight:bold" title="开始录入新课成绩。">开始</span></#assign>
        <#assign ADD><span style="color:green;font-weight:bold">添加</span></#assign>
        <#assign EDIT><span style="color:deeppink;font-weight:bold">修改</span></#assign>
        <#assign REMOVE><span style="color:red;font-weight:bold">删除</span></#assign>
        <#assign gradeTypeMap = {}/>
        <#list gradeTypes as gradeType>
            <#assign gradeTypeMap = gradeTypeMap + {gradeType.id?string:gradeType.name}/>
        </#list>
        <#macro getContext context>
            <#assign contextTemp = context?default("")?split("<br>")/>
            <#assign gradeElements = []/>
            <#if (contextTemp)?exists && (contextTemp?size >= 2)>
                <#assign gradeHInfo = contextTemp[0]?split("_")/>
                <#list 0..(gradeHInfo?size - 1) as i>
                    <#assign gradeElements = gradeElements + [gradeHInfo[i]?replace("当初：", "--:")?split(":")]/>
                </#list>
            </#if>
            <#if gradeElements?exists && gradeElements?size != 0>当初：最终成绩: ${gradeElements[0][0]} [<#if (gradeElements?size > 2)><#list 2..(gradeElements?size - 1) as i>${gradeTypeMap[gradeElements[i][0]]}: ${gradeElements[i][1]}<#if gradeElements[i + 1]?exists>, </#if></#list></#if>]<br>${contextTemp[1]}<#else>${context?default("")}</#if>
            <#return>
        </#macro>

