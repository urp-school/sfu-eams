<table id="backBar"></table>
	<#assign doClick>selectConfig()</#assign>
	<#include "timeAndAddressDiv.ftl"/>
	<#if isOpen?exists && isOpen == true>
	<@table.table sortable="true" id="tOpenId">
		<@table.thead>
			<@table.selectAllTd id="topicOpenId"/>
			<@table.sortTd name="attr.stdNo" width="8%" id="student.code"/>
			<@table.sortTd name="attr.personName" width="5%" id="student.name"/>
			<@table.td name="entity.studentType" width="10%"/>
			<@table.sortTd name="entity.speciality" width="8%" id="student.firstMajor.name"/>
			<@table.sortTd text="开题时间" width="8%" id="topicOpen.openReport.openOn"/>
			<@table.sortTd text="开题地点" width="10%" id="topicOpen.openReport.address"/>
			<@table.sortTd text="提交时间" width="6%" id="topicOpen.finishOn"/>
			<#if flag=="admin">
				<@table.td text="导师" width="6%"/>
				<@table.sortTd text="是否通过"  width="5%"  id="topicOpen.isPassed"/>
			</#if>
		</@>
        <@table.tbody datas=studentPage;thesisManage>
			<@table.selectTd id="topicOpenId" value=thesisManage.topicOpen.id/>
			<td><input type="hidden" id="stdId${(thesisManage.topicOpen.id)?if_exists}" name="stdId${(thesisManage.topicOpen.id)?if_exists}" value="${(thesisManage.student.id)?if_exists}">${(thesisManage.student.code)?if_exists}</td>
			<td><#if (thesisManage.topicOpen.downloadName)?exists><a href="thesisDownload.do?method=doDownLoad&thesisManageId=${thesisManage.id}&storeId=01">${thesisManage.student.name?if_exists}</a><#else>${(thesisManage.student.name)?if_exists}</#if></td>
			<td>${(thesisManage.student.type.name)?if_exists}</td>
			<td>${(thesisManage.student.firstMajor.name)?if_exists}</td>
			<td><#if (thesisManage.topicOpen.openReport.openOn)?exists>${(thesisManage.topicOpen.openReport.openOn)?string("yyyy-MM-dd")}<#else>未设置</#if></td>
			<td><#if (thesisManage.topicOpen.openReport.address)?exists>${thesisManage.topicOpen.openReport.address?html}<#else>未设置</#if></td>
			<td><#if (thesisManage.topicOpen.finishOn)?exists>${thesisManage.topicOpen.finishOn?string("yyyy-MM-dd")}<#else>未完成</#if></td>
			<#if flag=="admin" && isOpen?exists && isOpen == true>
				<td>${(thesisManage.student.teacher.name)?if_exists}</td>
				<td><#assign isPassed = (thesisManage.topicOpen.isPassed?string)?default("0")><#if isPassed == "1">通过<#elseif isPassed == "2">修改<#elseif isPassed == "3">不通过<#else>未设置</#if></td>
			</#if>
		</@>
	</@>
	
	<#else>
	<@table.table sortable="true" id="tOpenId">
		<@table.thead>
			<@table.selectAllTd id="topicOpenId"/>
			<@table.td name="attr.stdNo" width="8%" />
			<@table.td name="attr.personName" width="5%"/>
			<@table.td name="entity.studentType" width="10%"/>
			<@table.td name="entity.speciality" width="12%"/>
			<@table.td name="common.adminClass" width="14%"/>
			<@table.td text="所在年级" width="8%"/>
		</@>
		<@table.tbody datas=studentPage;student>
			<@table.selectTd id="topicOpenId" value=student.id/>
	      	<td>${student.code?if_exists}</td>
			<td>${student.name?if_exists}</td>
	      	<td>${(student.type.name)?if_exists}</td>
	      	<td>${(student.firstMajor.name)?if_exists}</td>
	      	<td>
		      	<#list student.adminClasses?if_exists as adminClass>
		      		${(adminClass.name)?if_exists}&nbsp;
		      	</#list>
	      	</td>
	      	<td>${student.enrollYear?if_exists}</td>
		</@>
	</@>
   	</#if>
	<form name="actionForm" method="post">
	  	<input type="hidden" name="keys" value="code,name,type.name,department.name,firstMajor.name">
	  	<input type="hidden" name="titles" value="学号,姓名,学生类别,院系,专业">
	  	<@searchParams/>
	</form>
	<script>
	   	var bar = new ToolBar('backBar', '<#if isOpen?exists && isOpen == true>开题学生列表<#else>学生列表</#if>', null, true, true);
	   	bar.setMessage('<@getMessage/>');
	   	<#if isOpen?exists && isOpen == true>
	   		bar.addItem("查看论文开题报告", "stdInfo(document.actionForm)");
	   	</#if>
	  	<#if flag?exists&&flag="admin"&&isOpen?exists&&isOpen==false>
	  		bar.addItem("<@msg.message key="action.export"/>", "doExport()", "excel.png");
	  	</#if>
	  	<#if flag?exists && flag =="admin" && isOpen?exists && isOpen == true>
	   		var configIsPassedMunu = bar.addMenu("论文开题设置",null);
	   		configIsPassedMunu.addItem("开题答辩通过", "configTopicOpen('1')");
	   		configIsPassedMunu.addItem("开题修改后答辩", "configTopicOpen('2')");
	   		configIsPassedMunu.addItem("开题答辩不通过", "configTopicOpen('3')");
	   		bar.addItem("设置时间地点", "displayConfig()", "detail.gif");
	   		
	   		function cancelCommit() {
	   			var topicOpenIdSeq = getSelectIds("topicOpenId");
		   		if ("" == topicOpenIdSeq) {
		    		alert("请选择一些选项");
		    		return;
		    	}
		    	if (confirm("你确定要取消这些学生的提交确认吗")) {
		    		form.action = "thesisTopicOpen.do?method=cancelStdCommit";
		    		setSearchParams(parent.form, form);
		  	    	addInput(form, 'topicOpenIdSeq', topicOpenIdSeq);
		    		form.submit();
		    	}
	   		}
	  	</#if>
	  	
	  	var form = document.actionForm;
	  	<#if flag?exists && flag == "admin">
	  		function configTopicOpen(value) {
	  	 		var idSeq = getSelectIds("topicOpenId");
	  	 		if ("" == idSeq) {
	  	 			alert("你选择论文开题选项");
	  	 			return;
	  	 		}
	  	 		
	  	 		var reminds = "";
	  	 		if ("1" == value) {
	  	 			reminds = "开题答辩通过";
	  	 		} else if ("2" == value) {
	  	 			reminds = "开题修改后答辩";
	  	 		} else {
	  	 			reminds = "开题答辩不通过";
	  	 		}
	  	 		if(confirm("你确定要把选择的开题设置为" + reminds + "吗?")) {
	  	 			form.action = "thesisTopicOpen.do?method=doUpdateIsPassed&isPassedValue=" + value;
		  	 		setSearchParams(parent.form, form);
		  	 		addInput(form, 'topicOpenIdSeq', idSeq);
		  	 		form.submit();
	  	 		}
	  		}
	  		
	  		var cForm = document.conditionForm
	   		function selectConfig() {
	   			var a_fields = {
	   				'time':{'l':'开题时间', 'r':true, 't':'f_time'},
	   				'address':{'l':'开题地点', 'r':true, 't':'f_address','mx':200}
	   			};
	   			
	   			var v = new validator(cForm, a_fields, null);
	   			if (v.exec()) {
			   		var selectSeq = getSelectIds("topicOpenId");
			   		var url = "thesisTopicOpen.do?method=doSettinTimeAndAddress";
			   		if ("" == selectSeq) {
			   			alert("请选择开题学生");return;
			   			/*if (confirm("确定要设置，查询范围内的学生开题的时间和地址吗?")) {
			   				url += "&flag=all";
			   			} else {
			   		 		return;
			   			}*/
			   		} else if (selectSeq.length > 0) {
			   			if (confirm("你确定要设置你所选择的时间和地址吗?")) {
			   				url += "&flag=select&selectSeq=" + selectSeq;
			   			} else {
			   				return;
			   			}
			   		}
			   		if (confirm("批量设置的开提时间是:" + cForm["time"].value + "\n批量设置的开题地点是:" + cForm["address"].value)) {
			   			cForm.action = url;
			   			setSearchParams(parent.form, cForm);
						<#list RequestParameters?keys as key>
							addInput(cForm, '${key}', '${RequestParameters[key]}');
						</#list>
			   			cForm.submit();
			   		}
			   	}
			}
	  	</#if>
	   	function displayConfig() {
	   		var divConfig = document.getElementById("configDiv");
	   		if(divConfig.style.display == "none") {
	   			divConfig.style.display = "block";
	   			f_frameStyleResize(self);
	   		} else {
	   			divConfig.style.display = "none";
	   		}
	   	}
	    function doExport() {
	    	form.action = "thesisTopicOpen.do?method=export";
	    	addHiddens(form,queryStr);
	    	form.submit();
	    }
	    
	    function affirmFunction(personType, isAffirm) {
	    	var topicOpenIdSeq = getSelectIds("topicOpenId");
	    	if ("" == topicOpenIdSeq) {
	    		alert("请选择一些选项");
	    		return;
	    	}
	    	form.action = "thesisTopicOpen.do?method=doModifyTopicOpen&isAffirm=" + isAffirm + "&personType=" + personType;
	    	setSearchParams(parent.form, form);
	  	    addInput(form, 'topicOpenIdSeq', topicOpenIdSeq);
	    	form.submit();
	    }
	</script>
