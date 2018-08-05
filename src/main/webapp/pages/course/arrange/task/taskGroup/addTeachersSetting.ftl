<#include "/templates/head.ftl"/>
<script src="dwr/interface/examActivityService.js"></script>
<script src='dwr/interface/teacherDAO.js'></script>
<body>
    <table id="bar"></table>
    <#assign teachers = taskGroup.teachers/>
    <#assign beenTeacherIds><#list teachers as teacher>${teacher.id},</#list></#assign>
    <#assign beenTeacherNames><#list teachers as teacher>${teacher.name}<#if teacher_has_next>,</#if></#list></#assign>
    <table class="formTable" width="100%">
        <tr>
            <td class="darkColumn" style="text-align:center;font-weight:bold">添加老师</td>
        </tr>
    </table>
    <table class="formTable" width="100%">
        <form method="post" action="" name="actionForm" onsubmit="return false;">
            <input type="hidden" name="taskGroup.id" value="${taskGroup.id}"/>
        <tr>
            <td class="title">已有教师(添加/修改结果)：</td>
            <td><label id="teacherNames" width="100%">${beenTeacherNames}</label><input type="hidden" name="teacherIds" value="${beenTeacherIds}"/></td>
        </tr>
        <tr>
            <td class="title" width="10%">院系/部门：</td>
            <td width="40%">
                <select id="department" onmouseover="displayDepartList(event);" onChange="displayTeacherList('teacher',this.value,true)" style="width:220px">
                    <option value=""></option>
                </select>
                <button onclick="resetTeacher()">还原</button>
            </td>
        </tr>
        <tr>
            <td class="title">教师：</td>
            <td>
                <select id="teacher" style="width:220px" onmouseover="displayTeacherList('teacher',department.value,false);">
                    <option value=""></option>
                </select>
                <button onclick="addTeacher()">添加</button>
            </td>
        </tr>
        </form>
    </table>
    <table class="formTable" width="100%">
        <tr>
            <td class="darkColumn" align="center"><button onclick="save()">保存</button></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "[${taskGroup.name}]排课组添加老师", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("保存", "save()");
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        var departmentList= new Array();
        <#list teacherDepartList as department>
        departmentList[${department_index}] = {'id':'${department.id}','name':'<@i18nName department/>'};
        </#list>
        
        var form = document.actionForm;
        
        function addTeacher() {
            var teacher = $('teacher');
            if (null == teacher.value || "" == teacher.value) {
                alert("请选择要添加的老师。");
                return;
            }
            if(form['teacherIds'].value.indexOf("," + teacher.value + ",") == -1) {
                form['teacherIds'].value += teacher.value + ",";
                if ("" != $("teacherNames").innerHTML) {
                    $("teacherNames").innerHTML += ",";
                }
                $("teacherNames").innerHTML += DWRUtil.getText('teacher');
            }
        }
        
        function resetTeacher() {
            form['teacherIds'].value = "<#if beenTeacherIds?exists && beenTeacherIds != "">,</#if>${beenTeacherIds}";
            $('teacherNames').innerHTML = "${beenTeacherNames}";
        }
        
        resetTeacher();
        
        function save() {
            var ddd = $("teacherNames").innerHTML.match(new RegExp(",", "gi"));
            var teacherCount = (((ddd == null) ? 0 : ddd.length) + 1) - ${taskGroup.taskList?size};
            if (teacherCount == 0) {
                alert("当前没有添加老师，无需保存。");
                return;
            }
            if (confirm("[${taskGroup.name}]排课组添加了" + teacherCount + "位任课老师，保存后将增加" + teacherCount + "条与组内相同的教学任务。\n\n要继续吗？")) {
                form.action = "taskGroup.do?method=saveGroup";
                form.target = "_parent";
                form.submit();
            }
        }
    </script>
    <script language="JavaScript" type="text/JavaScript" src="scripts/course/DepartTeacher.js"></script>
</body>
<#include "/templates/foot.ftl"/>