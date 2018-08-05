<#include "/templates/head.ftl"/>
<body> 
<#assign labInfo>向排课组添加教学任务选项</#assign>
<#include "/templates/back.ftl"/>
 <table width="80%" align="center" class="listTable">
   <form name="taskGroupForm" method="post" action="" onsubmit="return false;">
    <#if taskGroup.id?exists>
    <input type="hidden" name="taskGroup.id" value="${taskGroup.id}"/>
    </#if>
    <input type="hidden" name="taskGroup.name" value="${taskGroup.name?if_exists}"/>
    <input type="hidden" name="taskGroup.priority" value="${taskGroup.priority?if_exists}"/>
    <input type="hidden" name="taskGroup.remark" value="${taskGroup.remark?if_exists}"/>
    <input type="hidden" name="taskGroup.isSameTime" value="${taskGroup.isSameTime?string("1","0")}"/>
    <input type="hidden" name="taskIds" value="${RequestParameters['taskIds']?if_exists}"/>
	   <tr class="darkColumn">
        <td align="center">选项</td>
        <td align="center">取值</td>
        <td align="center">说明</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_name">&nbsp;合并建议时间：</td>	     
	     <td class="brightStyle" >
	        <input name="options.addSuggestTime" type="radio" value="1" checked/>是
	        <input name="options.addSuggestTime" type="radio" value="0" />否
         </td>
         <td class="brightStyle">将该教学任务的排课建议时间合并到该组内的时间内<br>该操作会升高相应时间段的优先级</td>
       </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_name">&nbsp;合并建议教室：</td>	     
	     <td class="brightStyle" >
	        <input name="options.addSuggestRoom" type="radio" value="1" checked/>是
	        <input name="options.addSuggestRoom" type="radio" value="0" />否
         </td>
         <td class="brightStyle">将该教学任务的排课建议教室合并到该组内的教室内</td>
       </tr>
       <#if taskGroup.isSameTime?default(false)>
           <#assign yesChecked = "checked">
           <#assign noChecked = "">
       <#else>
           <#assign yesChecked = "">
           <#assign noChecked = "checked">
       </#if>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_name">&nbsp;合并教师：</td>	     
	     <td class="brightStyle" >
	        <input name="options.mergeTeacher" type="radio" value="1" ${yesChecked}/>是
	        <input name="options.mergeTeacher" type="radio" value="0" ${noChecked}/>否
         </td>
         <td class="brightStyle">相同教师的教学任务将进行合并</td>
       </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_name">&nbsp;共享班级：</td>	     
	     <td class="brightStyle" >
	        <input name="options.shareAdminClass" type="radio" value="1" ${yesChecked}/>是
	        <input name="options.shareAdminClass" type="radio" value="0" ${noChecked}/>否
         </td>
         <td class="brightStyle">将该组内的班级共享到所有任务上</td>
       </tr>
	   <tr class="darkColumn">
	     <td colspan="6" align="center" >	       
	       <input type="button" value="<@bean.message key="action.next"/>" name="saveButton" onClick="save(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset"  name="resetButton" value="<@bean.message key="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
	   </form>
</table>
<script>
    function save(form){
        form.action="taskGroup.do?method=addTasks";
        form.submit();
        //parent.setRefreshGroupListTime(500,true);
    }
</script>
</body>
<#include "/templates/foot.ftl"/> 