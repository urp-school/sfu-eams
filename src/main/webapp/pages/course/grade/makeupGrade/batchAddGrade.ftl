<#include "/templates/head.ftl"/>
<#include "/templates/print.ftl"/>
<object id="factory2" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="css/smsx.cab#Version=6,2,433,14"></object> 
<style>
.printTableStyle {
	border-collapse: collapse;
    border:solid;
	border-width:2px;
    border-color:#006CB2;
  	vertical-align: middle;
  	font-style: normal; 
	font-size: 10pt; 
}
table.printTableStyle td{
	border:solid;
	border-width:0px;
	border-right-width:2;
	border-bottom-width:2;
	border-color:#006CB2;
        height:26px;
}
</style>

<#macro emptyTd count>
     <#list 1..count as i>
     <td></td>
     </#list>
</#macro>
<body>

     <table width="80%" align="center" border="0"  >
	   <tr>
	    <td align="center" colspan="5" style="font-size:17pt" >
	     <B><@i18nName systemConfig.school/>补缓考成绩录入</B>
	    </td>
	   </tr>
	   <tr><td colspan="5">&nbsp;</td></tr>
	 </table>	 
	 
	 <table width="80%" align="center" border="0"  >
	 <tr class="infoTitle">
	       <td >学年学期：${calendar.year}学年 <#if calendar.term='1'>第一学期<#elseif calendar.term='2'>第二学期<#else>${calendar.term}</#if></td>
		   <td ><@msg.message key="attr.courseNo"/>：${course.code}</td>
		   <td ><@msg.message key="attr.courseName"/>：<@i18nName course?if_exists/></td>
		   <td ><@msg.message key="attr.teachDepart"/>：<@i18nName teachDepart/></td>
		   <td >考试学生：${examTakeList?size}</td>
	 </tr>
	 </table>
	 <form name="actionForm" action="" method="post" onsubmit="return false;">
	 <input type="hidden" id="courseIds" name="courseIds" value="${course.id}@${teachDepart.id}" />
	 <input type="hidden" id="calendar.id" name="calendar.id" value="${calendar.id}" />
	 <table class="printTableStyle"  width="80%"  align="center" >
	   <tr class="darkColumn" align="center">
	     <td width="4%" >序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="12%"><@msg.message key="attr.personName"/></td>	     
	     <td width="8%">备注</td>
	     <td width="8%" >考试成绩</td>

	     <td width="4%">序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="12%"><@msg.message key="attr.personName"/></td>
	     <td width="8%">备注</td>
	     <td width="8%" >考试成绩</td>
	   </tr>
	   <#assign examTakeList=examTakeList?sort_by(['std','code'])>
       <#assign pageNo=(((examTakeList?size-1)/2)?int)  />
	   <#list 0..pageNo as i>
	   <tr class="brightStyle" >
         <#if examTakeList[i]?exists>
	     <td>${i+1}</td>
	     <td>${examTakeList[i]?if_exists.std?if_exists.code}</td>
	     <td><@i18nName examTakeList[i]?if_exists.std?if_exists/></td>
	     <td><@i18nName examTakeList[i]?if_exists.examType/></td>
	     <#assign examGradeNo=examTakeList[i].id?string  />
	     <#if examTakeList[i].examType==MAKEUP>
	     <td>
	     	<select id="${examTakeList[i].id}" name="${examTakeList[i].id}"  TABINDEX="${i+1}" style="width:100px">
	     		<option value="" <#if examGradeMap[examGradeNo]?if_exists.score?if_exists?string="">selected</#if>>...</option>
	     		<option value="60" <#if examGradeMap[examGradeNo]?if_exists.score?if_exists?string="60">selected</#if>>及格</option>
	     		<option value="0" <#if examGradeMap[examGradeNo]?if_exists.score?if_exists?string="0">selected</#if>>不及格</option>
	     	</select>
	     </td>
	     <#else>
	     <td><input id="${examTakeList[i].id}" maxlength="2" name="${examTakeList[i].id}" TABINDEX="${i+1}" value="${examGradeMap[examGradeNo]?if_exists.score?if_exists}"  style="width:100px"/></td>
         </#if>
         </#if>
         
         <#assign nextExam=i+pageNo+1>
         <#if examTakeList[nextExam]?exists>
	     <td>${nextExam+1}</td>
	     <td>${examTakeList[nextExam].std?if_exists.code}</td>
	     <td><@i18nName examTakeList[nextExam].std?if_exists/></td>
	     <td><@i18nName examTakeList[nextExam].examType/></td>
		 <#assign examGradeNo=examTakeList[nextExam].id?string  />
		 <#if examTakeList[nextExam].examType==MAKEUP>
	     <td>
	     	<select id="${examTakeList[nextExam].id}" name="${examTakeList[nextExam].id}"  TABINDEX="${nextExam+1}" style="width:100px">
	     		<option value="" <#if examGradeMap[examGradeNo]?if_exists.score?if_exists?string="">selected</#if>>...</option>
	     		<option value="60" <#if examGradeMap[examGradeNo]?if_exists.score?if_exists?string="60">selected</#if>>及格</option>
	     		<option value="0" <#if examGradeMap[examGradeNo]?if_exists.score?if_exists?string="0">selected</#if>>不及格</option>
	     	</select>
	     </td>
	     <#else>
	     <td><input id="${examTakeList[nextExam].id}" maxlength="2" name="${examTakeList[nextExam].id}"  TABINDEX="${nextExam+1}" value="${examGradeMap[examGradeNo]?if_exists.score?if_exists}" style="width:100px"/></td>
	     </#if>
         <#elseif examTakeList[i+1]?exists>
          <@emptyTd count=5/>
         </#if>
	   </tr>
	   </#list>	 
	   
     </table>
     <table class="printTableStyle"  width="80%"  align="center">
       <tr>
	   	<td align="center">请选择成绩录入后的状态: 
	   	  <select id="grade.state" name="grade.state" style="width:100px">
	     		<option value="1" >确认</option>
	     		<option value="0" >新录入</option>
	     		<option value="2" >发布</option>
	      </select>
	   	</td>
	   </tr>  
     </table>
     </form>
     <CENTER>
		<button onclick="saveGrade()"  class="notprint" >提交</button>
     </CENTER>
     <script>
		var form =document.actionForm;
		function saveGrade(){
			form.action = "makeupGrade.do?method=batchSaveCourseGrade"
			form.submit();
		}
	 </script>
 </body>
<#include "/templates/foot.ftl"/>