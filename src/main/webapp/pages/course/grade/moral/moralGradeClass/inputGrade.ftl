<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/onReturn.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="myBar" width="100%"></table>
 <script>
   //检查分数的合法性
   function alterErrorScore(input,msg){
      input.value="";      
      alert(msg);
   }
   function checkScore(input){
      var score= input.value;
      if(""!=score){
         if(isNaN(score)){
            alterErrorScore(input,score+" 不是数字");
         }
         else if(!/^\d*\.?\d*$/.test(score)){
            alterErrorScore(input,"请输入0或正实数");
         }
         else if(parseFloat(score)>100){
            alterErrorScore(input,"百分制输入不允许超过100分");
         }
      }
      if(!error){
         input.style.backgroundColor='white';
      }
   }
 </script>
 <#macro displayGrade(index,std)>
    <td>${index+1}</td>
    <td>${std.code}</td>
    <td>${std.name}</td>
    <#if gradeMap[std.id?string]?exists>
    	<#local grade=gradeMap[std.id?string]>
    	<#if !grade.isPublished>
    	 <td><input class="text" onfocus="this.style.backgroundColor='yellow'"
           onblur="this.style.backgroundColor='white'"
           onchange="checkScore(this)" 
           id="std_${std.id}"
           name="std_${std.id}" 
           value="${grade.score}" style="width:80px" maxlength="10" tabindex=${index+1}/></td>
        <#else>
        <td>${grade.score}</td>
        </#if>
    <#else>
     <td><input class="text" onfocus="this.style.backgroundColor='yellow'"
           onblur="this.style.backgroundColor='white'"
           onchange="checkScore(this)" 
           id="std_${std.id}"
           name="std_${std.id}" 
           value="" style="width:80px" maxlength="10" tabindex=${index+1}/></td>
    </#if>
</#macro>

 	 <div align='center' style="font-size:15px"><B><@i18nName systemConfig.school/>${calendar.studentType.name}德育成绩登记表</B><br>
 	  <@msg.message key="attr.year2year"/>${calendar.year} &nbsp;<@msg.message key="attr.term"/> ${calendar.term}</font>
 	 </div>
	<#if adminClass.students?size == 0>
		<#list 1..2 as i><br></#list>
		<table width="90%" align="center" style="background-color:yellow">
			<tr style="color:red"><th>当前没有可以录入成绩的学生。<th></tr>
		</table>
		<#list 1..2 as i><br></#list>
	</#if>
 	 <form name="gradeForm" method="post" onsubmit="return false;" action="">
 	 <input name="adminClassId" value="${adminClass.id}" type="hidden"/>
 	 <input name="calendarId" value="${calendar.id}" type="hidden"/>
 	 <table width='75%' align='center' border='0' style="font-size:13px">
 	 	<tr>
	 	 	<td width='25%'>班级:${adminClass.name}</font></td>
	 	 	<td width='40%'>人数:${adminClass.students?size}</font></td>
 	 	</tr>
 	 </table>
     <table width="75%" class="listTable" align="center" onkeypress="onReturn.focus(event)";>
	   <tr align="center">
	     <td align="center" width="5%"><@msg.message key="attr.index"/></td>
	     <td align="center" width="12%"><@bean.message key="attr.stdNo"/></td>
	     <td width="15%"><@bean.message key="attr.personName"/></td>
	     <td>德育成绩</td>
	     <td align="center" width="5%"><@msg.message key="attr.index"/></td>     
	     <td align="center" width="12%"><@bean.message key="attr.stdNo"/></td>
	     <td width="15%"><@bean.message key="attr.personName"/></td>
	     <td>德育成绩</td>
	   </tr>
	   <#assign students=adminClass.students?sort_by('code')?if_exists>
	   <#assign pageSize=((students?size+1)/2)?int />
	   <#list students as student>
	   <tr>
		   <#if students[student_index]?exists>		   
		     <@displayGrade student_index,students[student_index]/>
		   <#else>
		      <#break>
		   </#if>
		   	   
		   <#assign j=student_index+pageSize> 
		   <#if students[j]?exists>
			    <@displayGrade j,students[j]/>
		   <#else>
		       <td>&nbsp;</td>
		       <td>&nbsp;</td>
		       <td>&nbsp;</td>
		       <td>&nbsp;</td>
		   </#if>
		   <#if ((student_index+1)*2>=students?size)><#break></#if>
	   </tr>
	   </#list>
     </table>
   </form>
  <table width="100%" style="font-size:15px">
    <tr>
    <td align="center" id="submitTd">
             说明:如果不能打印成绩单,表明仍有些成绩你没有录入
      &nbsp;&nbsp;&nbsp; <button onclick="saveGrade()">提交</button>
    </td>
   </tr>
 </table>
<script>
   var bar = new ToolBar("myBar","德育成绩录入",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addClose("<@msg.message key="action.close"/>");
   function saveGrade(){
      var form =document.gradeForm;
      form.action="?method=saveGrade";
      if(confirm("确认提交成绩?")){
        document.getElementById('submitTd').innerHTML="成绩提交中,请稍侯...";
        form.submit();
      }
   }
   onReturn=new OnReturn(document.gradeForm);
   <#list students as std>
   onReturn.elemts[${std_index}]='std_${std.id}';
   </#list>
</script>
 </body>
<#include "/templates/foot.ftl"/>      