<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <#assign itemCapation = [
        "教学任务",
        "培养计划"
    ]/>
    <#macro courseTypeValue course>${(!course?exists || !course.extInfo?exists || (course.extInfo.courseType.isPractice)?default(false) == false)?string("", "<font color=\"red\">（实践）</font>")}</#macro>
    <#assign noPlan><font color="red">当前任务没能培养计划，或没有该属性。</font></#assign>
    <#list adminClasses as adminClass>
    <#assign adminClassIdValue=adminClass.id?string/>
    <table class="infoTable">
        <tr>
            <td class="darkColumn" style="text-align:center;font-weight:bold" colspan="${itemCapation?size}">${adminClass.name}</td>
        </tr>
        <#if !adminClassMap[adminClassIdValue][2]?exists || adminClassMap[adminClassIdValue][2]?size == 0>
        <tr>
            <td class="content" colspan="${itemCapation?size}" style="color:blue">当前班级没有开设任何课程。</td>
        </tr>
        <#else>
        <tr>
            <td class="title" style="text-align:center">${itemCapation[0]}</td>
            <td class="title" style="text-align:center">${itemCapation[1]}</td>
        </tr>
            <#assign planCourses = adminClassMap[adminClassIdValue][0]?if_exists/>
            <#assign taskCourses = adminClassMap[adminClassIdValue][1]?if_exists/>
            <#assign all = adminClassMap[adminClassIdValue][2]?if_exists/>
            <#assign onCampusTimeNotFound = adminClassMap[adminClassIdValue][3]?if_exists/>
            <#assign count = 0/>
            <#list all as course>
                <#if onCampusTimeNotFound>
        <tr>
            <td class="content" colspan="${itemCapation?size}">当前<font color="red">${adminClass.enrollYear}的所在年级</font>，<font color="red">没有配置</font>教学日历的<font color="red">在校时间,不能核对</font>。</td>
        </tr>
                    <#break/>
                <#elseif (!planCourses?exists || planCourses?size == 0) && (taskCourses?exists || taskCourses?size != 0)>
        <tr>
            <td class="content" colspan="${itemCapation?size}">当前班级的教学任务全部没有培养计划，是手工添加的。</td>
        </tr>
                    <#break/>
                <#elseif (planCourses?exists || planCourses?size != 0) && (!taskCourses?exists || taskCourses?size == 0)>
        <tr>
            <td class="content" colspan="${itemCapation?size}"><input type="checkbox" name="chkRecord" value="A_${adminClass.id}"/>当前班级的培养计划，没有生成教学任务。</td>
        </tr>
                    <#break/>
                <#else>
                    <#if planCourses?seq_contains(course) && !taskCourses?seq_contains(course)>
        <tr>
            <td class="content"><input type="checkbox" name="chkRecord" value="P_${adminClass.id}_${course.id}"/>无</td>
            <td class="content">${course.name}（${course.code}）<@courseTypeValue course/></td>
        </tr>
                    <#elseif !planCourses?seq_contains(course) && taskCourses?seq_contains(course)>
        <tr>
            <td class="content"><input type="checkbox" name="chkRecord" value="T_${adminClass.id}_${course.id}"/>${course.name}（${course.code}）<@courseTypeValue course/></td>
            <td class="content">无</td>
        </tr>
                    <#else>
                        <#assign count = count + 1/>
                        <#if count == all?size>
        <tr>
            <td class="content" colspan="${itemCapation?size}" style="color:green">当前教学任务全部由培养计划生成，没有新加或遗缺。</td>
        </tr>
                        </#if>
                    </#if>
                </#if>
            </#list>
        </#if>
    </table>
        <#if adminClass_has_next><br></#if>
    </#list>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="autoSetting" value=""/>
        <input type="hidden" name="calendarId" value="${RequestParameters["task.calendar.id"]}"/>
        <input type="hidden" name="params" value="${RequestParameters["params"]?if_exists}"/>
    </form>
    <script>
        var bar = new ToolBar("bar", "本学期教学任务核对(以班级为单位)", null ,true, false);
        bar.setMessage('<@getMessage/>');
        bar.addItem("生成任务", "gen()", "new.gif", "生成本学期的教学任务。");
        //bar.addItem("删除任务", "removeTask()", "delete.gif");
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        var form = document.actionForm;
        <#--
            **** 上面复选框的value中，P开头表示可生成任务，T开头表示可删除任务，A开头表示可以生成这个班级的全部任务（从计划中）。 ****
        -->
        function gen() {
            var chk_record = getSelectIds("chkRecord");
            if (isEmpty(chk_record)) {
                alert("请选择要操作的记录。");
                return;
            }
            if (null != chk_record.match(new RegExp("T", "gi"))) {
                alert("所选择的记录已经生成教学任务了。");
                return;
            }
            if (confirm("确定要将所选择的记录生成为教学任务吗？\n")) {
                form.action = "teachTaskCheck.do?method=gen";
                form["autoSetting"].value = chk_record;
                form.submit();
            }
        }
        
        function removeTask() {
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>