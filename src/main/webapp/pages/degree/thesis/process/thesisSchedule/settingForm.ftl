<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body> 
	<table id="bar"></table>
	<table width="100%" align="center" class="formTable">
	  	<form name="conditionForm" method="post" action="" onsubmit="return false;">
	   	<tr class="darkColumn" align="center">
	     	<td colspan="2">进度步骤信息</td>
	   	</tr>
	   	<tr>
	     	<td class="title" width="25%" id="f_name">步骤名称<font color="red">*</font>：</td>
	      	<td><@htm.i18nSelect datas=taches?sort_by("code") selected=(tacheSetting.tache.id?string)?default("") name="tacheSetting.tache.id"  style="width:150px;" /></td>
	   	</tr>
	    <tr>
	     	<td class="title" id="f_planedTime">计划完成时间<font color="red">*</font>：</td>
	     	<td><input type="text" name="tacheSetting.planedTimeOn" maxlength="10" value="${tacheSetting.planedTimeOn?string("yyyy-MM-dd")}" onfocus="calendar();f_frameStyleResize(self,0)"/></td>
	   	</tr>
	   	<tr>
	   		<td class="title" id="f_tutorAffirm">&nbsp;是否需要导师确认<font color="red">*</font>:</td>
	     	<td>
	     		<select name="tacheSetting.isTutorAffirm" style="width:150px">
	     			<option value="">请选择</option>
	     			<option value="1" <#if tacheSetting?if_exists.isTutorAffirm?exists&&tacheSetting.isTutorAffirm>selected</#if>>是</option>
	     			<option value="0" <#if tacheSetting?if_exists.isTutorAffirm?exists&&!tacheSetting.isTutorAffirm>selected</#if>>否</option>
	     		</select>
	     	</td>
	   	</tr>
	    <tr>
	     	<td class="title" id="f_remark">&nbsp;步骤备注：</td>
	     	<td><textarea name="tacheSetting.settingRemark" cols="30" rows="3">${tacheSetting?if_exists.settingRemark?if_exists}</textarea></td>
	   	</tr>
	   <tr>
	   		<td class="title">文档与规章</td>
	   		<td>
	   			<table class="listTable" width="100%">
	  					<tr>
	  						<td>可供选择的文档<br>
	  							<select name="documentSelected" MULTIPLE size="10" style="width:270px;" onDblClick="JavaScript:moveSelectedOption(this.form['documentSelected'], this.form['documentUsed'])">
	    						<#list supportDocuments as degreeDocument>
	    							<option value="${degreeDocument.id}">${degreeDocument.name}</option>
	    						</#list>
	    					</select>
	  						</td>
	  						<td>
	  							<input OnClick="JavaScript:moveSelectedOption(this.form['documentSelected'], this.form['documentUsed'])" type="button" value="&gt;"> 
	        				<br>
	        				<input OnClick="JavaScript:moveSelectedOption(this.form['documentUsed'], this.form['documentSelected'])" type="button" value="&lt;"> 
	  						</td>
	  						<td>使用的文档<br>
	  							<select name="documentUsed" MULTIPLE size="10" style="width:270px;" onDblClick="JavaScript:moveSelectedOption(this.form['documentUsed'], this.form['documentSelected'])">
	     						<#list tacheSetting.thesisDocuments?if_exists as thesisDocuments>
	    							<option value="${thesisDocuments.id}">${thesisDocuments.name}</option>
	    						</#list>
	     					</select>
	  						</td>
	  					</tr>
	  				</table>
	  			</td>
	   	</tr>
	   	<tr>
	   		<td class="title">学生填写模板下载</td>
	   		<td>
	   			<table class="listTable" width="100%">
	  					<tr>
	  						<td>可供选择的文档<br>
	  							<select name="modelSelected" MULTIPLE size="10" style="width:270px;" onDblClick="JavaScript:moveSelectedOption(this.form['modelSelected'], this.form['modelUsed'])" >
	    						<#list supportModels as supportModel>
	    							<option value="${supportModel.id}">${supportModel.name}</option>
	    						</#list>
	    					</select>
	  						</td>
	  						<td>
	  							<input OnClick="JavaScript:moveSelectedOption(this.form['modelSelected'], this.form['modelUsed'])" type="button" value="&gt;"> 
	        				<br>
	        				<input OnClick="JavaScript:moveSelectedOption(this.form['modelUsed'], this.form['modelSelected'])" type="button" value="&lt;"> 
	  						</td>
	  						<td>使用的文档<br>
	  							<select name="modelUsed" MULTIPLE size="10" style="width:270px;" onDblClick="JavaScript:moveSelectedOption(this.form['modelUsed'], this.form['modelSelected'])">
	     						<#list tacheSetting.thesisModels?if_exists as thesisModel>
	    							<option value="${thesisModel.id}">${thesisModel.name}</option>
	    						</#list>
	     					</select>
	  						</td>
	  					</tr>
	  				</table>
	  			</td>
	   	</tr>
	   	<tr class="darkColumn" align="center">
	     	<td colspan="2">
	       		<input type="hidden" name="scheduleId" value="${scheduleId?if_exists}">
	       		<input type="hidden" name="thesisDocumentIdSeq" value="">
	       		<input type="hidden" name="thesisModelIdSeq" value="">
	       		<input type="hidden" name="tacheSetting.id" value="${tacheSetting.id?if_exists}">
		   		<input type="button" value="保存" name="button1" onClick="doAction()" class="buttonStyle"/>&nbsp;
	     	</td>
	   	</tr>
	   	</form>
	</table>
	<script language="javascript" >
		var bar = new ToolBar("bar", "<#if tacheSetting.id?exists>添加进度步骤信息<#else>修改进度步骤信息</#if>", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("保存", "doAction()");
		bar.addBack();
		
		var form = document.conditionForm;
		function doAction() {
	    	form['thesisDocumentIdSeq'].value = getAllOptionValue(form.documentUsed);
	     	form['thesisModelIdSeq'].value = getAllOptionValue(form.modelUsed);
	     	
	     	var a_fields = {
	         	'tacheSetting.planedTimeOn':{'l':'计划完成时间', 'r':true, 't':'f_planedTime'},
	         	'tacheSetting.isTutorAffirm':{'l':'是否需要导师确认', 'r':true, 't':'f_tutorAffirm'},
	         	'tacheSetting.settingRemark':{'l':'步骤备注', 't':'f_remark','mx':200}        
	     	};
	     	var v = new validator(form , a_fields, null);
	     	if (v.exec()) {
	     		form.action = "thesisSchedule.do?method=saveTache";
	        	form.submit();
	        }
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>