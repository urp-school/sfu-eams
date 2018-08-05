<#include "/templates/head.ftl"/>
<BODY>
<table id="stdList" width="100%"></table>
<@table.table   width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="degreeApplyId"/>
      <@table.sortTd  text="学号" id="degreeApply.student.code"/>
      <@table.sortTd  name="attr.personName" id="degreeApply.student.name"/>
      <@table.sortTd  name="entity.college" id="degreeApply.student.department.name"/>
      <@table.sortTd  name="entity.speciality" id="degreeApply.student.firstMajor.name"/>
      <@table.sortTd  name="entity.studentType" id="degreeApply.student.type.name"/>
      <@table.sortTd  text="导师姓名" id="degreeApply.student.teacher.name"/>
      <@table.sortTd  text="提交时间" id="degreeApply.commitOn"/>
      <@table.sortTd  text="是否通过" id="degreeApply.isAgree"/>
    </@>
    <@table.tbody datas=degreeApplys; degreeApply>
    	<@table.selectTd id="degreeApplyId" value="${degreeApply.id}"/>
    	<td>${(degreeApply.student.code)?if_exists}</td>
	 	<td><input type="hidden" id="stdName${degreeApply.id}" value="${degreeApply.student.name}">${degreeApply.student.name}</td>
      	<td>${(degreeApply.student.department.name)?if_exists}</td>      	 
      	<td>${(degreeApply.student.firstMajor.name)?if_exists}</td>
      	<td><input type="hidden" id="stdType${degreeApply.id}" value="<#if "3"==(degreeApply.student.type.degree.id)?default(0)?string>D<#elseif "2"==(degreeApply.student.type.degree.id)?default(0)?string>M<#elseif "102"==(degreeApply.student.type.degree.id)?default(0)?string||"103"==(degreeApply.student.type.degree.id)?default(0)?string>E</#if>">${degreeApply.student.type?if_exists.name?if_exists}</td>
	  	<td>${(degreeApply.student.teacher.name)?if_exists}</td>
	  	<td><#if (degreeApply.commitOn)?exists>${degreeApply.commitOn?string("yyyy-MM-dd")}</#if></td>
	  	<td><#if "true"==degreeApply.isAgree?default(false)?string>通过<#else>不通过</#if></td>
    </@>
</@>

<form name="listForm" method="post" action="" onsubmit="return false;">
</form>
<script>
   var bar = new ToolBar('stdList','学生列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   var degreeInfo = bar.addMenu("学位数据表","stdInfo('doDetail')");
   degreeInfo.addItem("下载学位数据表","downloadFile('degreeInfo')");
   var degreeCheck = bar.addMenu("博士学位申请人情况表","stdInfo('degreeCheck')");
   degreeCheck.addItem("下载博士学位申请人情况表","downloadFile('doctorDegreeCheck')");
   var menuAffirm = bar.addMenu("确认通过",null);
   menuAffirm.addItem("通过","affirm('true')");
   menuAffirm.addItem("不通过","affirm('false')");
   var form = document.listForm;
    function stdInfo(value){
    	var degreeApplyId = getIds();
    	if(""==degreeApplyId||degreeApplyId.indexOf(",")!=-1){
    		alert("请选择单个选项");
    		return;
    	}
    	if("degreeCheck"==value){
    		var stdTypeValue =document.getElementById("stdType"+degreeApplyId).value;
    		if("D"!=stdTypeValue){
    			alert("请选择博士生");
    			return;
    		}
    	}
    	form.action="degreeApply.do?method="+value;
    	addInput(form,"degreeApplyId",degreeApplyId);
    	form.submit();
    }
    function affirm(value){
      form.action="degreeApply.do?method=affirm";
      setSearchParams(parent.form,form);
      addInput(form,"affirm",value);
      submitId(form,"degreeApplyId",true,null,"你确认提交这个信息吗?");
    }
    function downloadFile(value){
        var degreeApplyId = getIds();
        if(""==degreeApplyId||degreeApplyId.indexOf(",")!=-1){
    		alert("请选择单个选项");
    		return;
    	}
    	var tempName ="学位数据表";
    	var stdTypeValue =document.getElementById("stdType"+degreeApplyId).value;
    	var stdName = document.getElementById("stdName"+degreeApplyId).value
    	if("doctorDegreeCheck"==value){
    		tempName="博士学位申请人情况表";
    		if("D"!=stdTypeValue){
    			alert("请选择博士生");
    			return;
    		}
    	}
    	addInput(form,"degreeApplyId",degreeApplyId);
    	addInput(form,"fileName",stdName+tempName);
    	addInput(form,"template",value+"_"+stdTypeValue+".xls");
    	addInput(form,"templateType",value);
    	form.action="degreeApply.do?method=export";
    	form.submit();
    }
     function getIds(){
       return(getCheckBoxValue(document.getElementsByName("degreeApplyId")));
    }
</script>
</body>
<#include "/templates/foot.ftl"/>
