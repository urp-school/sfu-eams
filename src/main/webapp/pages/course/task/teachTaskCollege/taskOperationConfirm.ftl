<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="formTable" width="70%" align="center" cellspacing="0" cellpadding="0">
        <form method="post" action="" name="actionForm" onsubmit="return false;">
            <input type="hidden" name="taskId" value="${task.id}"/>
        <tr>
            <td colspan="2" class="darkColumn" style="font-weight:bold;text-align:center">班级增删前状况</td>
        </tr>
        <tr>
            <td class="title" width="30%">课程序号：</td>
            <td>${task.seqNo}</td>
        </tr>
        <tr>
            <td class="title">任务课程：</td>
            <td>${task.course.name}（${task.course.code}）</td>
        </tr>
        <tr>
            <td class="title">任务班级：</td>
            <td>
                <table class="listTable" width="100%">
                    <tr>
                        <td>班级</td>
                        <td>人数（计划/实际/有效）</td>
                    </tr>
                    <#assign removeAdminClassIds = ""/>
                    <#assign addAdminClassIds = ""/>
                    <#list taskAdminClasses as adminClass>
                        <#if adminClassSet?seq_contains(adminClass)>
                    <tr>
                        <td>${adminClass.name}（${adminClass.code}）</td>
                        <td>${(adminClass.planStdCount)?default(0)}/${(adminClass.actualStdCount)?default(0)}/${(adminClass.stdCount)?default(0)}</td>
                    </tr>
                        <#else>
                            <#assign removeAdminClassIds = removeAdminClassIds + "," + adminClass.id/>
                    <tr style="background-color:LightPink">
                        <td>${adminClass.name}（${adminClass.code}）</td>
                        <td>${(adminClass.planStdCount)?default(0)}/${(adminClass.actualStdCount)?default(0)}/${(adminClass.stdCount)?default(0)}</td>
                    </tr>
                        </#if>
                    </#list>
                    <#list adminClassSet as adminClass>
                        <#if !taskAdminClasses?seq_contains(adminClass)>
                            <#assign addAdminClassIds = addAdminClassIds + "," + adminClass.id/>
                    <tr style="background-color:LightGreen">
                        <td>${adminClass.name}（${adminClass.code}）</td>
                        <td>${(adminClass.planStdCount)?default(0)}/${(adminClass.actualStdCount)?default(0)}/${(adminClass.stdCount)?default(0)}</td>
                    </tr>
                        </#if>
                    </#list>
                </table>
            <input type="hidden" name="removeAdminClassIds" value="${removeAdminClassIds}"/>
            <input type="hidden" name="addAdminClassIds" value="${addAdminClassIds}"/>
            </td>
        </tr>
        <tr>
            <td class="title">操作：</td>
            <td><input type="checkbox" name="operation" value="1" checked/><span style="background-color:LightPink"/>移除班级时同步学生</span>&nbsp;<input type="checkbox" name="operation" value="2" checked/><span style="background-color:LightGreen"/>添加班级时同步学生</span><br><font color="red">注：只针对教学班操作，不对行政班操作。</font></td>
        </tr>
            <input type="hidden" name="params" value="${RequestParameters["params"]?replace("&method=search", "")?if_exists}"/>
            <input type="hidden" name="isToDo" value=""/>
            <input type="hidden" name="practiceCourse" value="${RequestParameters["params"]?if_exists}"/>
            <input type="hidden" name="operations" value=",1,2,"/>
        </form>
        <tr>
            <td colspan="2" class="darkColumn" style="text-align:center"><button onclick="finish(1)" title="保存后返回任务列表。">更新完成</button></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "班级操作确认（状态：已指定学生）", null ,true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("直接完成", "finish(0)", "save.gif", "对班级不作操作，直接保存返回列表。");
        bar.addBackOrClose("返回", "关闭");
        
        var form = document.actionForm;
        
        function finish(isToDo) {
            if (isToDo == 0) {
                if (!confirm("保持原有的教学班保存任务吗？")) {
                    return;
                }
            } else {
                if (!confirm("要按下面的操作保存任务吗？")) {
                    return;
                }
            }
            form.action = "?method=saveTaskAdminClass";
            form["isToDo"].value = isToDo;
            form["operations"].value = getSelectIds("operation");
            
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>