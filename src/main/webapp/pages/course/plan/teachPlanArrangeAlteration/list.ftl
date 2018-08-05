<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <@table.table id="courseArrangeAlteration" width="100%" sortable="true">
        <@table.thead>
            <@table.selectAllTd id="alterationId"/>
            <@table.td text="所在年级"/>
            <@table.td text="学生类别"/>
            <@table.td text="所属院系"/>
            <@table.td text="专业类别"/>
            <@table.td text="专业方向"/>
            <@table.sortTd text="计划创建时间" id="alteration.creatAt"/>
            <@table.sortTd text="计划修改时间" id="alteration.modifyAt"/>
            <@table.sortTd text="操作者" id="alteration.alterationBy.name"/>
            <@table.sortTd text="操作时间" id="alteration.alterationAt"/>
            <#if !RequestParameters["alteration.happenStatus"]?exists || RequestParameters["alteration.happenStatus"] == "">
                <@table.sortTd text="记录状态" id="alteration.happenStatus"/>
            </#if>
        </@>
        <@table.tbody datas=alterations;alteration>
            <#if (!RequestParameters["alteration.happenStatus"]?exists || RequestParameters["alteration.happenStatus"] == "" || RequestParameters["alteration.happenStatus"] == "1") && alteration.happenStatus == 1>
                <@table.selectTd id="alterationId" value=alteration.id/>
            <td style="color:blue">${alteration.afterPlanInfo.enrollTurn}</td>
            <td style="color:blue">${alteration.afterPlanInfo.stdTypeName}</td>
            <td style="color:blue">${alteration.afterPlanInfo.departmentName}</td>
            <td style="color:blue">${(alteration.afterPlanInfo.majorName)?if_exists}</td>
            <td style="color:blue">${(alteration.afterPlanInfo.majorTypeName)?if_exists}</td>
            <td style="color:blue">${alteration.createAt?string("yyyy-MM-dd")}</td>
            <td style="color:blue">${alteration.modifyAt?string("yyyy-MM-dd")}</td>
            <td style="color:blue">${alteration.alterationBy.name}</td>
            <td style="color:blue">${alteration.alterationAt?string("yyyy-MM-dd HH:mm:ss")}</td>
                <#if !RequestParameters["alteration.happenStatus"]?exists || RequestParameters["alteration.happenStatus"] == "">
            <td style="color:blue"><A href="#" onclick="infoData('${alteration.id}')">新建</A></td>
                </#if>
            <#elseif (!RequestParameters["alteration.happenStatus"]?exists || RequestParameters["alteration.happenStatus"] == "" || RequestParameters["alteration.happenStatus"] == "2") && alteration.happenStatus == 2>
                <@table.selectTd id="alterationId" value=alteration.id/>
            <td>${alteration.afterPlanInfo.enrollTurn}</td>
            <td>${alteration.afterPlanInfo.stdTypeName}</td>
            <td>${alteration.afterPlanInfo.departmentName}</td>
            <td>${(alteration.afterPlanInfo.majorName)?if_exists}</td>
            <td>${(alteration.afterPlanInfo.majorTypeName)?if_exists}</td>
            <td>${alteration.createAt?string("yyyy-MM-dd")}</td>
            <td>${alteration.modifyAt?string("yyyy-MM-dd")}</td>
            <td>${alteration.alterationBy.name}</td>
            <td>${alteration.alterationAt?string("yyyy-MM-dd HH:mm:ss")}</td>
                <#if !RequestParameters["alteration.happenStatus"]?exists || RequestParameters["alteration.happenStatus"] == "">
            <td><A href="#" onclick="infoData('${alteration.id}')">修改/调整</A></td>
                </#if>
            <#else>
                <@table.selectTd id="alterationId" value=alteration.id/>
            <td style="color:red">${alteration.beforePlanInfo.enrollTurn}</td>
            <td style="color:red" title="${alteration.beforePlanInfo.stdTypeName?html}" nowrap><span style="display:block;width:50px;overflow:hidden;text-overflow:ellipsis;">${alteration.beforePlanInfo.stdTypeName}</span></td>
            <td style="color:red" title="${alteration.beforePlanInfo.departmentName?html}" nowrap><span style="display:block;width:70px;overflow:hidden;text-overflow:ellipsis;">${alteration.beforePlanInfo.departmentName}</span></td>
            <td style="color:red" title="${(alteration.beforePlanInfo.majorName)?if_exists?html}" nowrap><span style="display:block;width:60px;overflow:hidden;text-overflow:ellipsis;">${(alteration.beforePlanInfo.majorName)?if_exists}</span></td>
            <td style="color:red" title="${(alteration.beforePlanInfo.majorTypeName)?if_exists?html}" nowrap><span style="display:block;width:60px;overflow:hidden;text-overflow:ellipsis;">${(alteration.beforePlanInfo.majorTypeName)?if_exists}</span></td>
            <td style="color:red">${alteration.createAt?string("yyyy-MM-dd")}</td>
            <td style="color:red">${alteration.modifyAt?string("yyyy-MM-dd")}</td>
            <td style="color:red">${alteration.alterationBy.name}</td>
            <td style="color:red">${alteration.alterationAt?string("yyyy-MM-dd HH:mm:ss")}</td>
                <#if !RequestParameters["alteration.happenStatus"]?exists || RequestParameters["alteration.happenStatus"] == "">
            <td style="color:red"><A href="#" onclick="infoData('${alteration.id}')">删除</A></td>
                </#if>
            </#if>
        </@>
    </@>
    <#assign status><#if !RequestParameters["alteration.happenStatus"]?exists || RequestParameters["alteration.happenStatus"] == "">全部<#elseif RequestParameters["alteration.happenStatus"] == "1"><font color="blue">新建</font><#elseif RequestParameters["alteration.happenStatus"] == "2">调整/修改<#else><font color="red">删除</font></#if></#assign>
    <@htm.actionForm name="actionForm" action="teachPlanArrangeAlteration.do" entity="alteration"></@>
    <script>
        var bar = new ToolBar("bar", "培养计划日志记录（${status?js_string}）", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("<@msg.message key="action.look"/>", "infoData()");
        bar.addItem("删除", "remove()");
        
        function infoData(id) {
            var alterationId = id;
            if (null == alterationId) {
                alterationId = getSelectId("alterationId");
            }
            if (isEmpty(alterationId) || isMultiId(alterationId)) {
                alert("请选择一个要查看的记录。");
                return;
            }
            form.action = "teachPlanArrangeAlteration.do?method=info";
            addInput(form, "alterationId", alterationId, "hidden");
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>