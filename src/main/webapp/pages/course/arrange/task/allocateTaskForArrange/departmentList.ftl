<table width="100%">
    <tr class="infoTitle" valign="top" style="font-size:10pt;font-weight:bold;font-family:宋体">
        <td class="infoTitle" align="left" valign="bottom"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;归属院系/任务数</td>
    </tr>
    <tr>
        <td style="font-size:0pt"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
    </tr>
</table>
<table width="100%" id="departmentTable">
    <tr>
        <td class="padding" id="all" onclick="searchAll(departmentTable, this)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" style="color:blue;font-weight:bold">全部</td>
        <td class="padding" onclick="$('all').click()" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" width="15%" style="color:blue;font-weight:bold;text-align:right">${taskCount?default(0)}</td>
    </tr>
    <tr>
        <td style="color:green" class="padding" id="default" onclick="search(departmentTable, this)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">未归属的任务（${taskOutCount?default(0)}）</td>
        <td style="color:green" class="padding" onclick="$('default').click()" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" style="text-align:center">＋</td>
    </tr>
    <#list departments?sort_by("name") as department>
    <tr>
        <td id="${calendar.id}_${department.id}" class="padding" onclick="info(departmentTable, this)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="院系代码：${department.code}">${department.name}</td>
        <td class="padding" onclick="$('${calendar.id}_${department.id}').click()" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="任务数：0" style="text-align:right">0</td>
    </tr>
    </#list>
    <#list allocations?sort_by(["department", "name"]) as taskIn>
        <#if taskIn.tasks?size != 0>
    <tr>
        <td id="${taskIn.calendar.id}_${taskIn.department.id}" class="padding" onclick="info(departmentTable, this)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="院系代码：${taskIn.department.code}">${taskIn.department.name}</td>
        <td class="padding" onclick="$('${taskIn.calendar.id}_${taskIn.department.id}').click()" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="任务数：${taskIn.tasks?size}" style="text-align:right;BACKGROUND-COLOR:PaleGreen">${taskIn.tasks?size}</td>
    </tr>
        </#if>
    </#list>
    <input type="hidden" name="primaryKey" value=""/>
</table>
