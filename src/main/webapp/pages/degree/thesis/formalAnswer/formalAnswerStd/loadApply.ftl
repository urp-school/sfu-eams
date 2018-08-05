<#include "/templates/head.ftl"/>
<#include "/pages/degree/thesis/processManageBar.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<table align="center" class="listTable" width="100%">
<form name="applyForm" method="post" action="" onSubmit="return false;">
	<input type="hidden" name="apply" value="apply">
	<tr>
		<td colspan="4" class="darkColumn" align="center">申请论文答辩</td>
	</tr>
	<tr>
		<td width="20%" class="grayStyle">论文题目</td>
		<td colspan="3"><#if (thesisManage.thesis.name)?exists>${thesisManage.thesis.name}<#else>${(thesisManage.topicOpen.topicOpenName)?if_exists}</#if></td>
	</tr>
	<tr>
		<td class="grayStyle" width="20%">答辩时间</td>
		<td width="30%"><#if (thesisManage.formalAnswer.time)?exists>${thesisManage.formalAnswer.time?string("yyyy-MM-dd")}</#if></td>
		<td class="grayStyle" width="20%">答辩地点</td>
		<td>${(thesisManage.formalAnswer.address)?if_exists}</td>
	</tr>
	<#if (thesisManage.formalAnswer.formelMark)?exists>
	<tr>
		<td class="grayStyle" width="20%">答辩成绩</td>
		<td>${thesisManage.formalAnswer.formelMark}</td>
		<td></td>
		<td></td>
	</tr>
	</#if>
	<tr>
		<td colspan="4" align="center" class="darkColumn">
		<input type="hidden" name="formalAnswerId" value="${(thesisManage.formalAnswer.id)?if_exists}">
		<button name="button1" class="buttonStyle" onclick="doApply()" <#if (thesisManage.formalAnswer.id)?exists>disabled</#if>>申请</button><font color="red"><#if (thesisManage.formalAnswer.finishOn)?exists>[已完成论文答辩确认]<#elseif (thesisManage.formalAnswer.id)?exists>[已申请]</#if></font>
		</td>
	</tr>
</form>
</table>
<script>
   var form = document.applyForm;
   var bar = new ToolBar('backBar','申请答辩',null,true,true);
   bar.setMessage('<@getMessage/>');
  	<#if (thesisManage.schedule)?exists>
  		<#assign schedule = thesisManage.schedule/>
		<@processBar id="05" personType="std"/>
  	</#if>
   <#if "3"==(thesisManage.student.type.degree.id)?default(0)?string>
   bar.addItem("下载论文答辩模板","downloadTemplate('degreeApplyBook','D')");
   </#if>
   <#if messages?exists>
   	message.innerHTML+='<@bean.message key=messages/>';
   </#if>
   bar.addItem("论文答辩完成确认","affirm()");
   function doApply(){
      if(confirm("你确定要提交论文答辩申请吗?")){
   	   		form.action="formalAnswerStd.do?method=doApply";
   	   		form.submit();
   	   }
   }
   function affirm(){
   	  	if(<#if (thesisManage.formalAnswer.id)?exists>false<#else>true</#if>){
   			alert("请你先提交论文答辩申请,然后才能确认论文答辩完成确认");
   			return;
   		}
   		if (!${(thesisManage.formalAnswer.isPassed?string)?default("false")}) {
   			$("error").innerHTML = "<font color=\"red\">论文答辩未通过！</font>";
   			return;
   		}
   		if(<#if (thesisManage.formalAnswer.finishOn)?exists>true<#else>false</#if>){
   			alert("你已经确认完成了论文答辩,请不要重复提交");
   			return;
   		}
   		form.action="formalAnswerStd.do?method=affirm";
   		form.submit();
   }
   function downloadTemplate(type,value){
       form.action="thesisManageStd.do?method=export";
       if(<#if (thesisManage.formalAnswer.id)?exists>false<#else>true</#if>){
       		alert("你还没有提交论文答辩申请");
       		return;
       }
       addInput(form,"template",type+"_"+value+".xls");
   	   addInput(form,"fileName","${thesisManage.student.name}的论文答辩信息表");
   	   addInput(form,"exportFlag","degreeInfo");
   	   form.submit();
   }
</script>
</body>     
<#include "/templates/foot.ftl"/>