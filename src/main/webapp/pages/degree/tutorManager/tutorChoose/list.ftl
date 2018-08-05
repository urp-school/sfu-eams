<#include "/templates/head.ftl"/>
<BODY>
    <table id="tutorChoose"></table>
    <@table.table width="100%" id="tutorApplyTableId" sortable="true">
    	<@table.thead>
    		<@table.selectAllTd id="applyId"/>
    		<@table.sortTd text="工号" id="tutorApply.teacher.code"/>
    		<@table.sortTd name="attr.personName" id="tutorApply.teacher.name"/>
    		<@table.sortTd text="所有院系" id="tutorApply.teacher.department.name"/>
    		<@table.sortTd text="申请导师类别" id="tutorApply.tutorType.name"/>
    		<@table.sortTd text="申请时间" id="tutorApply.applyTime"/>
    		<@table.sortTd text="是否通过" id="tutorApply.isPass"/>
    		<@table.sortTd text="通过时间" id="tutorApply.passTime"/>
    	</@>
    	<@table.tbody datas=tutorApplys;tutorApply>
    		<@table.selectTd id="applyId" value=tutorApply.id/>
   			<td>${(tutorApply.teacher.code)?if_exists}</td>
   			<td><@i18nName (tutorApply.teacher)?if_exists/></td>
   			<td><@i18nName (tutorApply.teacher.department)?if_exists/></td>
   			<td><@i18nName (tutorApply.tutorType)?if_exists/></td>
   			<td><#if tutorApply.applyTime?exists>${tutorApply.applyTime?string("yyyy-MM-dd")}</#if></td>
   			<td><#if tutorApply.isPass?exists&&tutorApply.isPass>审核通过<#else>未审核通过</#if></td>
   			<td><#if tutorApply.passTime?exists>${tutorApply.passTime?string("yyyy-MM-dd")}</#if></td>
    	</@>
    </@>
  	<form name='tutorManagerForm' method="post" action="tutorChoose.do?method=doList" onsubmit="return false;">
       <input type="hidden" name="tutorApplyIdSeq" value="">
       <input type="hidden" id="parameters" name="parameters" value="">
       <input type="hidden" name="keyValue" value="teacher.code,teacher.name,teacher.department.name,tutorType.name,applyTime,isPass">
       <input type="hidden" name="titleValue" value="工号,<@msg.message key="attr.personName"/>,所有院系,申请导师类别,申请时间,是否通过">
  	</form> 
    <script>
	    var bar = new ToolBar("tutorChoose","申请教师列表",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    bar.addItem("<@msg.message key="action.export"/>",'exportObject(document.tutorManagerForm)'); 
	    bar.addItem("同意申请",'agreeApply(document.tutorManagerForm)');
	    
	    function exportObject(form){
			form.action="tutorChoose.do?method=export";
    		addHiddens(form,queryStr);
			form.submit();
	    }
	    function agreeApply(form){
	    	var applyIdSeq = getCheckBoxValue(document.getElementsByName("applyId"))
	        if(""==applyIdSeq){
	        	alert("请选择要申请的对象");
	        	return;
	        }
	        form.tutorApplyIdSeq.value=applyIdSeq;
			form.action="tutorChoose.do?method=doAffirm";
			addInput(form, "params", queryStr, "hidden");
			form.submit();
	    }
    </script>
 </body>
<#include "/templates/foot.ftl"/>