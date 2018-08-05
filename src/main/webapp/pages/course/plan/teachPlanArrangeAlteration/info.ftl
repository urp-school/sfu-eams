<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <#if alteration.happenStatus == 1>
    <table class="infoTable">
        <tr>
            <td class="title">所在年级</td>
            <td class="content">${alteration.afterPlanInfo.enrollTurn}</td>
            <td class="title">学期数</td>
            <td class="content">${alteration.afterPlanInfo.terms}<#if (alteration.afterPlanInfo.termValues)?exists>（${alteration.afterPlanInfo.termValues}）</#if></td>
        </tr>
        <tr>
            <td class="title">学生类别</td>
            <td class="content">${alteration.afterPlanInfo.stdTypeName}（${alteration.afterPlanInfo.stdTypeCode}）</td>
            <td class="title">院系</td>
            <td class="content">${alteration.afterPlanInfo.departmentName}（${alteration.afterPlanInfo.departmentCode}）</td>
        </tr>
        <tr>
            <td class="title">专业</td>
            <td class="content"><#if (alteration.afterPlanInfo.majorName)?exists>${alteration.afterPlanInfo.majorName}（${alteration.afterPlanInfo.majorCode}）</#if></td>
            <td class="title">专业方向</td>
            <td class="content"><#if (alteration.afterPlanInfo.majorTypeName)?exists>${alteration.afterPlanInfo.majorTypeName}（${alteration.afterPlanInfo.majorTypeCode}）</#if></td>
        </tr>
        <tr>
            <td class="title">总学时</td>
            <td class="content">${alteration.afterPlanInfo.creditHour}</td>
            <td class="title">总学分</td>
            <td class="content">${alteration.afterPlanInfo.credit}</td>
        </tr>
        <tr>
            <td class="title">计划创建时间</td>
            <td class="content">${alteration.createAt?string("yyyy-MM-dd")}</td>
            <td class="title">计划修改时间</td>
            <td class="content">${alteration.modifyAt?string("yyyy-MM-dd")}</td>
        </tr>
        <tr>
            <td class="title">指导老师</td>
            <td class="content" colspan="3">${(alteration.afterPlanInfo.teacherNames)?if_exists}</td>
        </tr>
        <tr>
            <td class="title">备注</td>
            <td class="content" colspan="3">${(alteration.afterPlanInfo.remark)?if_exists}</td>
        </tr>
        <tr>
            <td class="title">操作者</td>
            <td class="content">${alteration.alterationBy.name}</td>
            <td class="title">操作时间</td>
            <td class="content">${alteration.alterationAt?string("yyyy-MM-dd HH:mm:ss")}</td>
        </tr>
        <tr>
            <td class="title">操作位置：</td>
            <td class="content" colspan="3">${(alteration.alterationFrom)?if_exists}</td>
        </tr>
    </table>
    <#elseif alteration.happenStatus == 2>
    <table class="infoTable">
        <tr>
            <td class="title" style="text-align:center;font-weight:bold">修改/调整项</td>
            <td class="title" style="text-align:center;font-weight:bold">保存前</td>
            <td class="title" style="text-align:center;font-weight:bold">保存后</td>
            <td class="title" style="text-align:center;font-weight:bold">状态</td>
        </tr>
        <tr>
            <td class="title">所在年级</td>
            <td class="content">${alteration.beforePlanInfo.enrollTurn}</td>
            <td class="content">${alteration.afterPlanInfo.enrollTurn}</td>
            <td class="content" style="text-align:center">${(alteration.beforePlanInfo.enrollTurn == alteration.afterPlanInfo.enrollTurn)?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">学生类别</td>
            <td class="content">${alteration.beforePlanInfo.stdTypeName}（${alteration.afterPlanInfo.stdTypeCode}）</td>
            <td class="content">${alteration.afterPlanInfo.stdTypeName}（${alteration.afterPlanInfo.stdTypeCode}）</td>
            <td class="content" style="text-align:center">${(alteration.beforePlanInfo.stdTypeCode == alteration.afterPlanInfo.stdTypeCode)?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">院系</td>
            <td class="content">${alteration.beforePlanInfo.departmentName}（${alteration.afterPlanInfo.departmentCode}）</td>
            <td class="content">${alteration.afterPlanInfo.departmentName}（${alteration.afterPlanInfo.departmentCode}）</td>
            <td class="content" style="text-align:center">${(alteration.beforePlanInfo.departmentCode == alteration.afterPlanInfo.departmentCode)?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">专业</td>
            <td class="content"><#if (alteration.beforePlanInfo.majorName)?exists>${alteration.beforePlanInfo.majorName}（${alteration.beforePlanInfo.majorCode}）</#if></td>
            <td class="content"><#if (alteration.afterPlanInfo.majorName)?exists>${alteration.afterPlanInfo.majorName}（${alteration.afterPlanInfo.majorCode}）</#if></td>
            <td class="content" style="text-align:center">${((alteration.beforePlanInfo.majorCode)?default("") == (alteration.afterPlanInfo.majorCode)?default(""))?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">专业方向</td>
            <td class="content"><#if (alteration.beforePlanInfo.majorTypeName)?exists>${alteration.beforePlanInfo.majorTypeName}（${alteration.beforePlanInfo.majorTypeCode}）</#if></td>
            <td class="content"><#if (alteration.afterPlanInfo.majorTypeName)?exists>${alteration.afterPlanInfo.majorTypeName}（${alteration.afterPlanInfo.majorTypeCode}）</#if></td>
            <td class="content" style="text-align:center">${((alteration.beforePlanInfo.majorTypeCode)?default("") == (alteration.afterPlanInfo.majorTypeCode)?default(""))?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">学期数</td>
            <td class="content">${alteration.beforePlanInfo.terms}<#if (alteration.beforePlanInfo.termValues)?exists>（${alteration.beforePlanInfo.termValues}）</#if></td>
            <td class="content">${alteration.afterPlanInfo.terms}<#if (alteration.afterPlanInfo.termValues)?exists>（${alteration.afterPlanInfo.termValues}）</#if></td>
            <td class="content" style="text-align:center">${(alteration.beforePlanInfo.terms == alteration.afterPlanInfo.terms)?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">总学时</td>
            <td class="content">${alteration.beforePlanInfo.creditHour}</td>
            <td class="content">${alteration.afterPlanInfo.creditHour}</td>
            <td class="content" style="text-align:center">${(alteration.beforePlanInfo.creditHour == alteration.afterPlanInfo.creditHour)?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">总学分</td>
            <td class="content">${alteration.beforePlanInfo.credit}</td>
            <td class="content">${alteration.afterPlanInfo.credit}</td>
            <td class="content" style="text-align:center">${(alteration.beforePlanInfo.credit == alteration.afterPlanInfo.credit)?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">课程组数</td>
            <td class="content">${alteration.beforePlanInfo.groupCount}</td>
            <td class="content">${alteration.afterPlanInfo.groupCount}</td>
            <#assign modifyGroupHTML><A href="#" onclick="groupsInfo()">修改/调整</A></#assign>
            <td class="content" style="text-align:center">${(alteration.isModifyGroup)?string(modifyGroupHTML, "-")}</td>
        </tr>
        <tr>
            <td class="title">课程数</td>
            <td class="content">${alteration.beforePlanInfo.allCourseCount}</td>
            <td class="content">${alteration.afterPlanInfo.allCourseCount}</td>
            <#assign modifyPlanCourseHTML><A href="#" onclick="planCoursesInfo()">修改/调整</A></#assign>
            <td class="content" style="text-align:center">${(alteration.isModifyCourse)?string(modifyPlanCourseHTML, "-")}</td>
        </tr>
        <tr>
            <td class="title">指导老师</td>
            <td class="content">${(alteration.beforePlanInfo.teacherNames)?if_exists}</td>
            <td class="content">${(alteration.afterPlanInfo.teacherNames)?if_exists}</td>
            <td class="content" style="text-align:center">${((alteration.beforePlanInfo.teacherNames)?default("") == (alteration.afterPlanInfo.teacherNames)?default(""))?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">是否个人计划</td>
            <td class="content">${alteration.beforePlanInfo.isStdPerson?string("是", "否")}</td>
            <td class="content">${alteration.afterPlanInfo.isStdPerson?string("是", "否")}</td>
            <td class="content" style="text-align:center">${(alteration.beforePlanInfo.isStdPerson == alteration.afterPlanInfo.isStdPerson)?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">对应学生</td>
            <td class="content"><#if (alteration.beforePlanInfo.stdCode)?exists>${alteration.beforePlanInfo.stdName}（${alteration.beforePlanInfo.stdCode}）</#if></td>
            <td class="content"><#if (alteration.afterPlanInfo.stdCode)?exists>${alteration.afterPlanInfo.stdName}（${alteration.afterPlanInfo.stdCode}）</#if></td>
            <td class="content" style="text-align:center">${((alteration.beforePlanInfo.stdCode)?default("") == (alteration.afterPlanInfo.stdCode)?default(""))?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">是否确认</td>
            <td class="content">${alteration.beforePlanInfo.isConfirm?string("是", "否")}</td>
            <td class="content">${alteration.afterPlanInfo.isConfirm?string("是", "否")}</td>
            <td class="content" style="text-align:center">${(alteration.beforePlanInfo.isConfirm == alteration.afterPlanInfo.isConfirm)?string("-", "修改/调整")}</td>
        </tr>
        <tr>
            <td class="title">备注</td>
            <td class="content">${(alteration.beforePlanInfo.remark)?if_exists}</td>
            <td class="content">${(alteration.afterPlanInfo.remark)?if_exists}</td>
            <td class="content" style="text-align:center">${((alteration.beforePlanInfo.remark)?default("") == (alteration.afterPlanInfo.remark)?default(""))?string("-", "修改/调整")}</td>
        </tr>
    </table>
    <br>
    <table class="infoTable">
        <tr>
            <td class="title">计划创建时间</td>
            <td class="content">${alteration.createAt?string("yyyy-MM-dd")}</td>
            <td class="title">计划修改时间</td>
            <td class="content">${alteration.modifyAt?string("yyyy-MM-dd")}</td>
        </tr>
        <tr>
            <td class="title">操作者</td>
            <td class="content">${alteration.alterationBy.name}</td>
            <td class="title">操作时间</td>
            <td class="content">${alteration.alterationAt?string("yyyy-MM-dd HH:mm:ss")}</td>
        </tr>
        <tr>
            <td class="title">操作位置：</td>
            <td class="content" colspan="3">${(alteration.alterationFrom)?if_exists}</td>
        </tr>
    </table>
    <#else>
    <table class="infoTable">
        <tr>
            <td class="title">所在年级</td>
            <td class="content">${alteration.beforePlanInfo.enrollTurn}</td>
            <td class="title">学期数</td>
            <td class="content">${alteration.beforePlanInfo.terms}<#if (alteration.beforePlanInfo.termValues)?exists>（${alteration.beforePlanInfo.termValues}）</#if></td>
        </tr>
        <tr>
            <td class="title">学生类别</td>
            <td class="content">${alteration.beforePlanInfo.stdTypeName}（${alteration.beforePlanInfo.stdTypeCode}）</td>
            <td class="title">院系</td>
            <td class="content">${alteration.beforePlanInfo.departmentName}（${alteration.beforePlanInfo.departmentCode}）</td>
        </tr>
        <tr>
            <td class="title">专业</td>
            <td class="content"><#if (alteration.beforePlanInfo.majorName)?exists>${alteration.beforePlanInfo.majorName}（${alteration.beforePlanInfo.majorCode}）</#if></td>
            <td class="title">专业方向</td>
            <td class="content"><#if (alteration.beforePlanInfo.majorTypeName)?exists>${alteration.beforePlanInfo.majorTypeName}（${alteration.beforePlanInfo.majorTypeCode}）</#if></td>
        </tr>
        <tr>
            <td class="title">总学时</td>
            <td class="content">${alteration.beforePlanInfo.creditHour}</td>
            <td class="title">总学分</td>
            <td class="content">${alteration.beforePlanInfo.credit}</td>
        </tr>
        <tr>
            <td class="title">课程组数</td>
            <td class="content">${alteration.beforePlanInfo.groupCount}</td>
            <td class="title">课程数</td>
            <td class="content">${alteration.beforePlanInfo.allCourseCount}</td>
        </tr>
        <tr>
            <td class="title">指导老师</td>
            <td class="content" colspan="3">${(alteration.beforePlanInfo.teacherNames)?if_exists}</td>
        </tr>
        <tr>
            <td class="title">备注</td>
            <td class="content" colspan="3">${(alteration.beforePlanInfo.remark)?if_exists}</td>
        </tr>
        <tr>
            <td class="title">是否个人计划</td>
            <td class="content">${alteration.beforePlanInfo.isStdPerson?string("是", "否")}</td>
            <td class="title">是否确认</td>
            <td class="content">${alteration.beforePlanInfo.isConfirm?string("是", "否")}</td>
        </tr>
        <tr>
            <td class="title">对应学生</td>
            <td class="content" colspan="3"><#if (alteration.beforePlanInfo.stdCode)?exists>${alteration.beforePlanInfo.stdName}（${alteration.beforePlanInfo.stdCode}）</#if></td>
        </tr>
        <tr>
            <td class="title">计划创建时间</td>
            <td class="content">${alteration.createAt?string("yyyy-MM-dd")}</td>
            <td class="title">计划修改时间</td>
            <td class="content">${alteration.modifyAt?string("yyyy-MM-dd")}</td>
        </tr>
        <tr>
            <td class="title">操作者</td>
            <td class="content">${alteration.alterationBy.name}</td>
            <td class="title">操作时间</td>
            <td class="content">${alteration.alterationAt?string("yyyy-MM-dd HH:mm:ss")}</td>
        </tr>
        <tr>
            <td class="title">操作位置：</td>
            <td class="content" colspan="3">${(alteration.alterationFrom)?if_exists}</td>
        </tr>
    </table>
    </#if>
    <#assign status><#if alteration.happenStatus == 1><font color="blue">新建</font><#elseif alteration.happenStatus == 2>调整/修改<#else><font color="red">删除</font></#if></#assign>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="alterationId" value="${alteration.id}"/>
    </form>
    <script>
        var bar = new ToolBar("bar", "培养计划日志详细信息（${status?js_string}）", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        var form = document.actionForm;
        
        function groupsInfo() {
            form.action = "teachPlanArrangeAlteration.do?method=groupsInfo";
            form.submit();
        }
        
        function planCoursesInfo() {
            form.action = "teachPlanArrangeAlteration.do?method=planCoursesInfo";
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>