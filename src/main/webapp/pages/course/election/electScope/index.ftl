<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<BODY>
<table id="backBar"></table>
    <table  width="100%" class="frameTable_title">
        <tr>
            <td class="infoTitle"><@bean.message key="info.searchForm"/></td>
            <td>|</td>
    <form name="taskForm" method="post" action="electScope.do?method=index" onsubmit="return false;">
        <input type="hidden" name="task.calendar.id" value="${calendar.id}"/>
            <td class="infoTitle">
                <select name="task.electInfo.isElectable" onChange="searchTask()" style="width:80px">
                    <#--华政增加“全部”-->
                    <option value="">全部</option>
                    <option value="1" selected><@bean.message key="attr.electable"/></option>
                    <option value="0"><@bean.message key="attr.unelectable"/></option>
                </select>
            </td>
            <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
    <table class="frameTable" height="85%">
        <tr>
            <td class="frameTable_view" style="width:160px">
                <#include "searchForm.ftl"/>
            </td>
    </form>
            <td valign="top">
                <iframe src="#" id="taskListFrame" name="taskListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar('backBar','<@bean.message key="info.elect.courseSetting"/>',null,true,true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("选课人数上限检查","maxStdCountCheck()",null,"检查选课上限和教室听课容量不一致的课程");
        bar.addHelp("<@msg.message key="action.help"/>");
        
        var form =document.taskForm;
        
        function maxStdCountCheck(){
            form.action="electScope.do?method=maxStdCountCheck"
            form.submit();
        }
        
        function searchTask(){
            var isAll = null == form["task.electInfo.isElectable"].value || "" == form["task.electInfo.isElectable"].value;
            if (isAll) {
                form.action="electScope.do?method=search";
            } else {
                form.action="electScope.do?method=taskList";
            }
            taskForm.target="taskListFrame";
            taskForm.submit();
        }
        
        searchTask();
    </script>
</body>
<#include "/templates/foot.ftl"/>