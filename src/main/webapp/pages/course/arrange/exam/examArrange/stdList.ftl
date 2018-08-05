<#include "/templates/head.ftl"/>
<body >
<table id="examTakeBar"></table>
<script>
  var bar = new ToolBar("examTakeBar","参加考试名单",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addBack("<@msg.message key="action.back"/>");
</script>
 <table class="listTable" width="100%">
	   <tr class="darkColumn" align="center">
	     <td align="center" class="select"> 
	        <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('examTakeId'),event);"/>
	     </td>
	     <td width="6%" >序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="15%"><@msg.message key="attr.personName"/></td>
	     <td width="15%">考试地点</td>
	     <td width="15%">考试情况</td>
	     <td width="10%">备注</td>
	   </tr>
    <#list teachTask.arrangeInfo.getExamActivities(examType) as activity>
	 <#assign examTakes = activity.examTakes/>
	   <#list examTakes as take>
	   <tr class="brightStyle" align="center" onclick="onRowChange(event)">
	     <td  class="select"><input type="checkBox" name="examTakeId" value="${take.id}"/></td>
	     <td>${take_index + 1}</td>
	     <td>${take.std.code}</td>
	     <td><@i18nName take.std/></td>
	     <td><@i18nName activity.room?if_exists/></td>
	     <td><@i18nName take.examStatus/></td>
	     <td></td>
	   </tr>
	   </#list>
     </#list>
     </table>
 </body>
<#include "/templates/foot.ftl"/>