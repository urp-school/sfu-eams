<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(self,0)">
  <table id="stdListBar" width="100%"></table>
<script>
   	var bar = new ToolBar('stdListBar','学生列表',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	//bar.addItem("初始化","doInitializte()")
   	bar.addItem("<@msg.message key="action.export"/>","doExpStd()");
   	var passMenu=bar.addMenu("是否通过",null);
   	passMenu.addItem("通过","configIsPassed('true')");
   	passMenu.addItem("不通过","configIsPassed('false')");
    bar.addItem("设置时间地点","displayConfig()");
    bar.addItem("录入分数","insertMark()");
    //bar.addItem("取消学生确认","cancelAffirm()");
   	function displayConfig(){
   		var divConfig = document.getElementById("configDiv");
   		if(divConfig.style.display=="none"){
   			divConfig.style.display="block";
   			f_frameStyleResize(self);
   		}
   		else divConfig.style.display="none";
   	}
   	function configTimeAndAddress(){
   		var url="formalAnswerAdmin.do?method=doUpdate&flag=timeAndAddress";
   	    var notice="";
   		var time = document.getElementById("timeId");
   		if(""==time.value){
   			notice+="开题时间必须填写\n";
   		}
   		var address = document.getElementById("addressId");
   		if(""==address.value){
   			notice+="开题地点必须填\n";
   		}
   		if(address.value.length>100){
   			notice+="开题地点字数不能大于100\n";
   		}
   		if(""!=notice){
   	    	alert(notice);
   	    	return;
   	    }
   		if(confirm("批量设置的预答辩时间是:"+time.value+"\n批量设置的预答辩地点是:"+address.value)){
   			document.conditionForm.action=url;
   			setSearchParams(parent.form,document.conditionForm);
   			submitId(document.conditionForm,"formalAnswerId",true);
   		}
   }
   	</script>
   	<#assign doClick>configTimeAndAddress()</#assign>
  	<#include "timeAndAddress.ftl"/>
  <table width="100%" align="center" class="listTable">
  <@table.table width="100%" align="center" id="formalAnswerList" sortable="true">
  	<@table.thead>
  		<@table.selectAllTd id="formalAnswerId"/>
  		<@table.sortTd name="attr.stdNo" id="formalAnswer.student.code"/>
  		<@table.sortTd name="attr.personName"  id="formalAnswer.student.name"/>
  		<@table.sortTd name="entity.college"  id="formalAnswer.student.department.name"/>
  		<@table.sortTd name="entity.studentType"  id="formalAnswer.student.type.name"/>
  		<@table.sortTd name="entity.speciality"  id="formalAnswer.student.firstMajor.name"/>
  		<@table.sortTd text="导师姓名"  id="formalAnswer.student.teacher.name"/>
  		<@table.sortTd text="答辩时间" id="formalAnswer.time"/>
  		<@table.sortTd text="答辩地点"  id="formalAnswer.address"/>
  		<@table.sortTd text="答辩提交时间"  id="formalAnswer.finishOn"/>
  		<@table.sortTd text="答辩成绩"  id="formalAnswer.formelMark"/>
  		<@table.sortTd text="是否通过"  id="formalAnswer.isPassed"/>
  	</@>
  	<@table.tbody datas=answerPagi;formalAnswer>
  		<@table.selectTd id="formalAnswerId" value="${formalAnswer.id}"/>
  		<td>${formalAnswer.student.code?if_exists}</td>
  		<td><#if formalAnswer.downloadName?exists><a href="thesisDownload.do?method=doDownLoad&thesisManageId=${formalAnswer.thesisManage.id}&storeId=05">${formalAnswer.student.name?if_exists}</a><#else>${formalAnswer.student.name?if_exists}</#if></td>
      	<td><@i18nName formalAnswer.student.department?if_exists/></td>
      	<td><@i18nName (formalAnswer.student.type)?if_exists/></td>   	 
      	<td><@i18nName (formalAnswer.student.firstMajor)?if_exists/></td>
      	<td><@i18nName (formalAnswer.student.teacher)?if_exists/></td>
      	<td><#if formalAnswer.time?exists>${formalAnswer.time?string("yyyy-MM-dd")}</#if></td>
      	<td>${(formalAnswer.address?html)?if_exists}</td>
      	<td><#if formalAnswer.finishOn?exists>${formalAnswer.finishOn?string("yyyy-MM-dd")}<#else>未完成</#if></td>
      	<td>${formalAnswer.formelMark?if_exists}</td>
      	<td><#if formalAnswer.isPassed?exists&&formalAnswer.isPassed>通过<#elseif formalAnswer.isPassed?exists&&!formalAnswer.isPassed>不通过</#if></td>
  	</@>
  </@>
    <form name="pageGoForm" method="post" action="" onSubmit="return false;">
    	<input type="hidden" name="keys" value="student.code,student.name,student.firstMajor.name,student.teacher.name,thesisManage.topicOpen.topicOpenName,formelMark,time,address,finishOn,isPassed">
    	<input type="hidden" name="titles" value="学号,姓名,专业,导师姓名,论文标题,答辩分数,答辩时间,答辩地点,答辩完成时间,是否通过">
	</form>
  </table>
  <script>
   var form = document.pageGoForm
    function doExpStd(){
        form.action="formalAnswerAdmin.do?method=export";
        transferParams(parent.document.listForm,form,null,false);
        form.submit();
    }
    function doInitializte(){
       form.action="formalAnswerAdmin.do?method=doInitializated";
   	   form.submit();
    }
    function cancelAffirm(){
   		if(confirm("你确定要取消这些学生论文答辩确认吗?")){
   			form.action="formalAnswerAdmin.do?method=cancelAffirm";
   			setSearchParams(parent.form,form);
   			submitId(form,"formalAnswerId",true);
   		}
   	}
   	function configIsPassed(bool){
   		form.action="formalAnswerAdmin.do?method=doUpdate&flag=isPassed&isPassed="+bool;
   		setSearchParams(parent.form,form);
   		submitId(form,"formalAnswerId",true);
   	}
   	function insertMark(){
   		form.action="formalAnswerAdmin.do?method=doInsertMark&isInsert=load";
   		submitId(form,"formalAnswerId",true);
   	}
  </script>
</body>
<#include "/templates/foot.ftl"/>