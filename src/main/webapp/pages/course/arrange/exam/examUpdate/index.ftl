<#include "/templates/head.ftl"/>
<BODY> 
    <table id="taskBar"></table>
    <table class="frameTable_title">
        <tr>
	        <td style="width:50px"><font color="blue"><@bean.message key="action.advancedQuery"/></font></td>
           <form name="taskForm" target="teachTaskListFrame" method="post" action="examUpdate.do?method=index" onsubmit="return false;">
            <td>|</td>
            <input type="hidden" name="task.calendar.id" value="${calendar.id}"/>
            <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
    <table class="frameTable">
        <tr>
            <td valign="top" width="19%" class="frameTable_view">
                <#include "../teachTaskSearchForm.ftl"/>
            </td>
          </form>
            <td valign="top" bgcolor="white">
                <iframe  src="#" id="teachTaskListFrame" name="teachTaskListFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
	<script>
		var bar=new ToolBar("taskBar","考试设置管理",null,true,true);
		bar.addHelp("<@msg.message key="action.help"/>");
		
        search();
        function search(pageNo,pageSize,orderBy){
            var form = document.taskForm;
            form.target="teachTaskListFrame";
            taskForm.action="teachTaskCollege.do?method=listSiva";
            form.submit();
        }
        
		function checkArrangeInfo(){
	   	    var url="teachTaskCollege.do?method=checkArrangeInfo&calendar.studentType.id=${studentType.id}&calendar.id=${calendar.id}";
		    window.open(url, '', 'toolbar=yes, menubar=yes,location=yes,scrollbars=yes,top=0,left=0,width=500,height=550,status=yes,resizable=yes');   	    
		}
		
		function stateModule(){
		    var url="teachTaskCollege.do?method=taskOfCourseTypeList&calendar.studentType.id=${studentType.id}&calendar.id=${calendar.id}&orderBy=courseType.name";
		    window.open(url, '', 'toolbar=yes, menubar=yes,location=yes,scrollbars=yes,top=0,left=0,width=500,height=550,status=yes,resizable=yes');
		}
		
		function statTeachTask(){
		    self.location="teachTaskStat.do?teachTask.calendar.id="+document.taskForm['task.calendar.id'].value+"&method=index";
		}
		
		function multiClassTaskList(){
		    self.location="teachTaskStat.do?method=multiClassTaskList&teachTask.calendar.id="+document.taskForm['task.calendar.id'].value;
		}
		
	    function gpList(){
	        self.location="teachTaskStat.do?method=gpList&teachTask.calendar.id="+document.taskForm['task.calendar.id'].value;
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/> 
  