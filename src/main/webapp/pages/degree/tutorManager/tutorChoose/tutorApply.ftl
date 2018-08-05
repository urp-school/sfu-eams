<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">  
    <table id="teacherChoose"></table>
<script>
   var bar = new ToolBar('teacherChoose','导师遴选',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("下载表格","download(document.tutorManagerForm)");
   function download(form){
   		var tutorTypeId = document.getElementById("tutorTypeId").value;
   		if("4"==tutorTypeId){
   			var filName="sqsdjb.doc";
   			var displayName="硕士导师遴选表格.doc";
   		}else if("3"==tutorTypeId){
   			var filName="sqbdjb.doc";
   			var displayName="博士导师遴选表格.doc";
   		}
   		form.fileName.value=filName;
   		form.displayName.value=displayName;
   		form.action="tutorChoose_tutor.do?method=doDownloadApplyDoc";
   		form.submit();
   }
   function doApply(form){
   	 var tutorTypeId = document.getElementById("tutorTypeId").value;
   	 if(""==tutorTypeId){
   	 	alert("请选择申请导师类别");
   	 	return;
   	 }
   	 if("4"==tutorTypeId){
   		var errors="你确定要申请硕士生导师吗";
   	 }else if("3"==tutorTypeId){
   		var errors="你确定要申请博士生导师吗";
   	 }
   	 form.tutorTypeId.value=tutorTypeId;
   	 form.action="tutorChoose_tutor.do?method=tutorApply";
   	 if(confirm(errors)){
   	 	form.submit();
   	 }
   }
</script>

    <table>
  	 	<tr>
  	 		<td>申请导师类别:<select id='tutorTypeId' name='tutorTypeId'><option value='4'>硕导</option><option value='3'>博导</option></select></td>
  	 		<td><button onclick="doApply(document.tutorManagerForm)">申请导师</button></td>
  	 	</tr>
    </table>
    <table width="100%"  class="listTable">
  	 <form name='tutorManagerForm' method="post" action="" onsubmit="return false;"> 
  	 	<input name="fileName" type="hidden" value="">
  	 	<input name="displayName" type="hidden" value="">
  	 	<input name="tutorTypeId" type="hidden" value="">
    	<tr align="center" class="darkColumn">
	      <td>工号</td>
	      <td><@msg.message key="attr.personName"/></td>
	      <td>所有院系</td>
	      <td>申请导师类别</td>
	      <td>申请时间</td>
	      <td>是否通过</td>
	      <td>通过时间</td>
       </tr>
       <#assign class="grayStyle"/>
       <#list tutorApplys?if_exists as tutorApply>
       		<tr align="center" class="${class}">
       			<td>${tutorApply.teacher.code?if_exists}</td>
       			<td>${tutorApply.teacher.name?if_exists}</td>
       			<td>${tutorApply.teacher.department.name?if_exists}</td>
       			<td>${tutorApply.tutorType?if_exists.name?if_exists}</td>
       			<td><#if tutorApply.applyTime?exists>${tutorApply.applyTime?string("yyyy-MM-dd")}</#if></td>
       			<td><#if tutorApply.isPass?exists&&tutorApply.isPass>审核通过<#else>未审核通过</#if></td>
       			<td><#if tutorApply.passTime?exists>${tutorApply.passTime?string("yyyy-MM-dd")}</#if></td>
       		</tr>
       </#list>
       <input type="hidden" name="apply" value="apply">
       <tr class="darkColumn">
       		<td height="25px;" colspan="7"></td>
       </tr>
     </form> 
  <table>
</body>
<#include "/templates/foot.ftl"/>