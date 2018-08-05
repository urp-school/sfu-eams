<#include "/templates/head.ftl"/>
<#include "/pages/degree/thesis/processManageBar.ftl"/>
<#assign notice><font color="red"><#if (annotate.isDoubleBlind)?exists&&annotate.isDoubleBlind>双盲已选中!<#else>双盲未选中!</#if></font></#assign>
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','${student.name?if_exists}的论文评阅书',null,true,true);
   bar.setMessage('<@getMessage/>');
   var message = document.getElementById("message");
   //message.innerHTML+='${notice}';
   <#if "master"==stdTypeFlag>
   	bar.addItem("上海市双盲链接","window.open('http://lwms.seei.shec.edu.cn')");
   </#if>
  	<#if (thesisManage.schedule)?exists>
  		<#assign schedule = thesisManage.schedule/>
		<@processBar id="03" personType="std"/>
  	</#if>
   bar.addItem("修改","edit()");
</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <#assign disPlay=true>
  <#include "selfEvaluateTable.ftl"/>
  <#if (annotate.annotateBooks)?exists>
  <table class="listTable" width="100%">
  		<tr class="darkColumn">
  			<td align="center" colspan="5">论文评阅书成绩</td>
  		</tr>
		<tr class="grayStyle">
			<td>论文编号</td>
			<td>成绩</td>
			<td>平均成绩</td>
			<td>学位水平</td>
			<td>是否同意答辩</td>
		</tr>
   <#list annotate.annotateBooks?if_exists?sort_by("serial") as book>
   		<#if book_index%2==0><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
		<tr class="${class}">
			<td>${book.serial?if_exists}</td>
			<td><#if book.evaluateIdea?if_exists.mark?exists>${book.evaluateIdea.mark?string("##0.0")}</#if></td>
			<td><#if annotate.avgMark?exists>${annotate.avgMark?string("##0.0")}</#if></td>
			<td><#if book.evaluateIdea?if_exists.learningLevel?exists><#if book.evaluateIdea.learningLevel=="A">优秀<#elseif book.evaluateIdea.learningLevel=="B">良好<#elseif book.evaluateIdea.learningLevel=="C">一般<#elseif book.evaluateIdea.learningLevel=="D">不及格</#if></#if></td>
			<td><#if book.isReply?exists><#if book.isReply=="A">同意答辩<#elseif book.isReply=="M">修改后答辩<#elseif book.isReply=="D">不同意答辩</#if></#if></td>
		</tr>
 </#list>
 	<tr class="darkColumn">
 		<td  colspan="5">
 			注意：您必须完成表格（<#if "master"==stdTypeFlag>S1-S7<#elseif "doctor"==stdTypeFlag>B1-B9<#else>T1-T7</#if>）的填写、审核和提交，才能看到评审结果，双盲评审期为1个月，请耐心等待
 		</td>
 	</tr>
 </table>
 </#if>
 <script>
 	var form = document.listForm;
    function downLoadTemplate(type,value){
   		if(<#if (annotate.id)?exists>false<#else>true</#if>){
   			alert("请先填写你的论文自评表");
   			return;
   		}
   		form.action="thesisManageStd.do?method=export";
   		addInput(form,"template",type+"_"+value+".xls");
   	    addInput(form,"fileName","${student.name}的学位数据表");
   	    form.submit();
   }
   function edit(){
   		form.action="annotateStd.do?method=edit";
   		form.submit();
   }
 </script>
</body>
<#include "/templates/foot.ftl"/>