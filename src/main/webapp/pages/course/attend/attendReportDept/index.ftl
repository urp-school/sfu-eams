<#include "/templates/head.ftl"/>
 <table id="taskBar"></table>
    <table class="frameTable_title">
        <tr>
        <td width="40%"> </td>
          <form name="taskForm" target="teachTaskListFrame" method="post" action="?method=index" onsubmit="return false;">
            <input type="hidden" name="task.calendar.id" value="${calendar.id}" />
            <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
    <table class="frameTable">
        <tr>
            <td valign="top" width="19%" class="frameTable_view">
               <#include "teachTaskSearchForm.ftl"/>
            </td>
          </form>
            <td valign="top" bgcolor="white">
                <iframe  src="#" id="teachTaskListFrame" name="teachTaskListFrame" scrolling="auto" marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
    <script>
    	var bar=new ToolBar("taskBar","教学任务（院系）",null,true,true);
    	bar.addItem('按班级查看考勤','showByClassPrepare()');
    	bar.addItem('查看学生考勤明细','showAttendDetail()');
    	search();
        function search(){
            var form = document.taskForm;
            taskForm.action = "?method=search";
            form.target="teachTaskListFrame";
            form.submit();
        }
        function showByClassPrepare(){
       		window.open("attendReportDept.do?method=showByClassPrepare","newWindow")
			
		}
		
		function showAttendDetail(){
			window.open("attendReportDept.do?method=showAttendDetailPrepare","newWindowtwo")
		}
        
    </script>
<#include "/templates/foot.ftl"/> 

  