<table id="stdListBar" width="100%"></table>
   	<#assign doClick>configTimeAndAddress()</#assign>
  	<#include "configDiv.ftl"/>
  	<#if hasApplyFlag?exists && "true"==hasApplyFlag>
  	  <@table.table width="100%" id="listTable" sortable="true">
  		<@table.thead>
  			<@table.selectAllTd id="preAnswerId"/>
  			<@table.sortTd name="attr.stdNo"  width="12%" id="student.code"/>
  			<@table.sortTd name="attr.personName"  width="8%" id="student.name"/>
  			<@table.sortTd name="entity.studentType" width="12%" id="student.type.name"/>
  			<@table.sortTd name="entity.speciality" width="12%" id="student.firstMajor.name"/>
  			<@table.sortTd text="论文题目" id="preAnswer.thesisManage.topicOpen.thesisTopic"/>
  			<@table.td text="导师姓名" width="8%"/>
  			<@table.sortTd text="答辩时间" width="10%" id="preAnswer.answerTime"/>
  			<@table.sortTd text="答辩地点" id="preAnswer.answerAddress"/>
  			<@table.sortTd text="提交时间" width="10%" id="preAnswer.finishOn"/>
  			<@table.sortTd text="是否通过" width="8%" id="preAnswer.isPassed"/>
  		</@>
		<@table.tbody datas=preAnswerPage;perAnswer>
		  	<#assign thesisManage=perAnswer.thesisManage>
  			<@table.selectTd id="preAnswerId" value=perAnswer.id/>
      		<td>${thesisManage.student.code?if_exists}</td>
  			<td>${thesisManage.student.name?if_exists}</td>
      		<td>${(thesisManage.student.type.name)?if_exists}</td> 	 
      		<td>${thesisManage.student.firstMajor?if_exists.name?if_exists}</td>
      		 <td title="${(thesisManage.topicOpen.thesisTopic?html)?if_exists}" nowrap><span style="display:block;width:120px;overflow:hidden;text-overflow:ellipsis;">${(thesisManage.topicOpen.thesisTopic?html)?if_exists}</span></td>
  			<td>${(thesisManage.student.teacher.name)?if_exists}</td>
  			<td><#if perAnswer.answerTime?exists>${perAnswer.answerTime?string("yyyy-MM-dd")}<#else>--</#if></td>
  			<td><#if (perAnswer.answerAddress)?exists>${(perAnswer.answerAddress?html)?if_exists}<#else>--</#if></td>
  			<td><#if (perAnswer.finishOn)?exists>${(perAnswer.finishOn)?string("yyyy-MM-dd")}<#else>未完成</#if></td>
  			<td>${((perAnswer.isPassed)?string("通过","未通过"))?default("--")}</td>
  		</@>
  	 </@>
  	 
  	 <#else>
  	 <@table.table width="100%">
  		<@table.thead>
  			<@table.selectAllTd id="preAnswerId"/>
  			<@table.td name="attr.stdNo"  width="12%"/>
  			<@table.td name="attr.personName" width="8%"/>
  			<@table.td name="entity.studentType" width="12%"/>
  			<@table.td name="entity.speciality" width="12%"/>
  			<@table.td name="common.adminClass"/>
  		</@>
  		<@table.tbody datas=preAnswerPage;perAnswer>
		  	<#assign thesisManage=perAnswer>
  			<@table.selectTd id="preAnswerId" value=perAnswer.id/>
      		<td>${thesisManage.student.code?if_exists}</td>
  			<td>${thesisManage.student.name?if_exists}</td>
      		<td>${(thesisManage.student.type.name)?if_exists}</td> 	 
      		<td>${thesisManage.student.firstMajor?if_exists.name?if_exists}</td>
      		<td><#list thesisManage.student.adminClasses as adminClass>${(adminClass.name?html)?if_exists}</#list></td>
  		</@>
  	 </@>
  	</#if>
  	<form name="listForm" action="#" method="post" onSubmit="return false;"></form>
  	<form name="pageGoForm" method="post">
		<#if hasApplyFlag?exists>
			<input type="hidden" name="isApply" value="${hasApplyFlag}">
    		<#if hasApplyFlag?exists&&"true"==hasApplyFlag>
    			<input type="hidden" name="keys" value="thesisManage.student.code,thesisManage.student.name,thesisManage.student.type.name,thesisManage.student.department.name,thesisManage.student.firstMajor.name,thesisManage.topicOpen.thesisTopic,thesisManage.student.teacher.name,answerNum,answerTime,answerAddress,advice,finishOn,isPassed">
    			<input type="hidden" name="titles" value="学号,姓名,学生类别,院系,专业,论文题目,导师姓名,答辩次数,答辩时间,答辩地点,专家意见,答辩提交时间,是否通过">
    		<#else>
    			<input type="hidden" name="keys" value="code,name,type.name,department.name,firstMajor.name"/>
    			<input type="hidden" name="titles" value="学号,姓名,学生类别,院系,专业"/>
    		</#if>
    	</#if>
	    <#assign paginationName="preAnswerPage" />
    </form>	 
<script>
  	var form = document.listForm;
   	var bar = new ToolBar('stdListBar','<#if hasApplyFlag?exists&&"true"==hasApplyFlag>申请预答辩学生列表<#else>学生列表</#if>',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	<#if flag?exists&&"admin"==flag>
   	<#if hasApplyFlag?exists&&"true"==hasApplyFlag>
   	bar.addItem("设置时间地点","displayConfig()");
   	var configMenu = bar.addMenu('设置结果',null,"detail.gif");
   	configMenu.addItem("设置为通过","configIsPass('1')");
   	configMenu.addItem("设置为未通过","configIsPass('0')");
   	</#if>
   	bar.addItem("<@msg.message key="action.export"/>","doExpStd()","excel.png");
   	bar.addHelp("<@msg.message key="action.help"/>");
   	function configIsPass(isPass){
   		form.action="preAnswer.do?method=configPass&isPass="+isPass;
   		setSearchParams(parent.document.listForm,form);
   		addInput(form,"isPassed",isPass);
   		addInput(form,'pageNo',getPageNo());
   		addInput(form,'pageSize',getPageSize());
   		submitId(form,"preAnswerId",true,null,"你确定要设置吗?");
   	}
   	function cancleAffirm(){
   		form.action="preAnswer.do?method=cancleAffirm";
   		setSearchParams(parent.document.listForm,form);
   		addInput(form,'pageNo',getPageNo());
   		addInput(form,'pageSize',getPageSize());
   		submitId(form,"preAnswerId",true,null,"你确定要设置吗?");
   	}
   	function displayConfig(){
   		var divConfig = document.getElementById("configDiv");
   		if(divConfig.style.display=="none"){
   			divConfig.style.display="block";
   			f_frameStyleResize(self);
   		}
   		else divConfig.style.display="none";
   	}
   	function configTimeAndAddress(){
   		var url="preAnswer.do?method=doUpdate";
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
   	    var preAnswerIdSeq = getIds("preAnswerId");
   	    if(preAnswerIdSeq.length>0){
   	    	if(!confirm("你确定修改所选择的预答辩时间和地点吗")){
   	    		return;
   	    	}
   	    	addInput(document.conditionForm,"flag","select");
   	    	addInput(document.conditionForm,"answerIdSeq",preAnswerIdSeq)
   	    	
   	    }else{
   	    	if(!confirm("你确定要修改所能查询到的学生的预答辩时间和地点吗?\n该操作不建议执行,你确定要继续吗")){
   	    		return;
   	    	}
   	    	addInput(document.conditionForm,"flag","all");
   	    }
   		if(confirm("批量设置的预答辩时间是:"+time.value+"\n批量设置的预答辩地点是:"+address.value)){
   			document.conditionForm.action=url;
   			setSearchParams(parent.form,document.conditionForm);
   			document.conditionForm.submit();
   		}
   }
   	function doExpStd(){
   		document.pageGoForm.action="preAnswer.do?method=export";
   		addHiddens(document.pageGoForm,queryStr);
   		document.pageGoForm.submit();
   	}
   <#else>
   	bar.addHelp("<@msg.message key="action.help"/>");
   </#if>
</script>
