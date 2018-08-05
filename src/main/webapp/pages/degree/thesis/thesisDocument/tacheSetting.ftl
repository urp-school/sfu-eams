<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body> 
	<table id="bar"></table>
	<table width="100%" align="center" class="formTable">
	  	<form name="conditionForm" method="post" action="" onsubmit="return false;">
	  	<@searchParams/>
	  	<input name="documentId" value="${RequestParameters['documentId']}" type="hidden"/>
	  	<input name="documentTacheSettingIds" value="" type="hidden"/>
	  	<input name="modelTacheSettingIds" value="" type="hidden"/>
        <tr>
	   		<td class="title">文档与规章</td>
	   		<td>
	   			<table class="listTable" width="100%">
	  					<tr>
	  						<td>可供选择的环节<br>
	  							<select name="documentSelected" MULTIPLE size="10" style="width:270px;" onDblClick="JavaScript:moveSelectedOption(this.form['documentSelected'], this.form['documentUsed'])">
	    						<#list availDocumentTaches as tacheSetting>
	    							<option value="${tacheSetting.id}">${tacheSetting.schedule.enrollYear} <@i18nName tacheSetting.schedule.studentType/> ${tacheSetting.tache.name}</option>
	    						</#list>
	    					</select>
	  						</td>
	  						<td>
	  							<input OnClick="JavaScript:moveSelectedOption(this.form['documentSelected'], this.form['documentUsed'])" type="button" value="&gt;"> 
	        				<br>
	        				<input OnClick="JavaScript:moveSelectedOption(this.form['documentUsed'], this.form['documentSelected'])" type="button" value="&lt;"> 
	  						</td>
	  						<td>使用的环节<br>
	  							<select name="documentUsed" MULTIPLE size="10" style="width:270px;" onDblClick="JavaScript:moveSelectedOption(this.form['documentUsed'], this.form['documentSelected'])">
	     						<#list documentTaches as tacheSetting>
	    							<option value="${tacheSetting.id}">${tacheSetting.schedule.enrollYear} <@i18nName tacheSetting.schedule.studentType/> ${tacheSetting.tache.name}</option>
	    						</#list>
	     					</select>
	  						</td>
	  					</tr>
	  				</table>
	  			</td>
	   	</tr>
	   	<tr>
	   		<td class="title">学生模板</td>
	   		<td>
	   			<table class="listTable" width="100%">
	  					<tr>
	  						<td>可供选择的环节<br>
	  							<select name="modelSelected" MULTIPLE size="10" style="width:270px;" onDblClick="JavaScript:moveSelectedOption(this.form['modelSelected'], this.form['modelUsed'])" >
	    						<#list availModelTaches as tacheSetting>
	    							<option value="${tacheSetting.id}">${tacheSetting.schedule.enrollYear} <@i18nName tacheSetting.schedule.studentType/> ${tacheSetting.tache.name}</option>
	    						</#list>
	    					</select>
	  						</td>
	  						<td>
	  							<input OnClick="JavaScript:moveSelectedOption(this.form['modelSelected'], this.form['modelUsed'])" type="button" value="&gt;"> 
	        				<br>
	        				<input OnClick="JavaScript:moveSelectedOption(this.form['modelUsed'], this.form['modelSelected'])" type="button" value="&lt;"> 
	  						</td>
	  						<td>使用的环节<br>
	  							<select name="modelUsed" MULTIPLE size="10" style="width:270px;" onDblClick="JavaScript:moveSelectedOption(this.form['modelUsed'], this.form['modelSelected'])">
	     						<#list modelTaches as tacheSetting>
	    							<option value="${tacheSetting.id}">${tacheSetting.schedule.enrollYear} <@i18nName tacheSetting.schedule.studentType/> ${tacheSetting.tache.name}</option>
	    						</#list>
	     					</select>
	  						</td>
	  					</tr>
	  				</table>
	  			</td>
	   	</tr>
	  </form>
	</table>
	<script language="javascript" >
		var bar = new ToolBar("bar", "修改文档关联的环节", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("保存", "save()");
		bar.addBack();
		
		var form = document.conditionForm;
		function save() {
		    if(confirm("确定设置?")){
		    	form['documentTacheSettingIds'].value = getAllOptionValue(form.documentUsed);
		     	form['modelTacheSettingIds'].value = getAllOptionValue(form.modelUsed);
	     		form.action = "thesisDocument.do?method=saveTacheSetting";
	        	form.submit();
        	}
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>