<#include "/templates/head.ftl"/>
<BODY topmargin=0 leftmargin=0>	
  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','统计表',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem('导出统计表','exportTable()');
	   bar.addPrint();
	   bar.addBack();
   </script>
   <div align="center"><h2><@i18nName systemConfig.school/>${enrollYear}级研究生科研成果统计表</h2></div>
<table align="center" class="listTable">
    <tr class="darkColumn" align="center">
    	<td rowspan="2">院系名称</td>
    	<td rowspan="2">学生类别</td>
    	<td rowspan="2">考核人数</td>
    	<td colspan="${publicTypes?size+1}">发表论文(篇)</td>
    	<td rowspan="2">人均发表论文数</td>
    	<td colspan="${projectTypes?size+literatureTypes?size}">科研课题</td>
    </tr>
    <tr class="darkColumn" align="center">
        <#list publicTypes?sort_by("code") as publicType>
    		<td>${publicType.name}</td>
    	</#list>
    	   <td>总计</td>
    	 <#list projectTypes?sort_by("code") as projectType>
    		<td>${projectType.name}</td>
    	</#list>
    	 <#list literatureTypes?sort_by("code") as literatureType>
    		<td>${literatureType.name}</td>
    	</#list>
    </tr>
    <#assign class="grayStyle">
    <#list resultMap["department"]?if_exists as department>
    	<#list resultMap[department.id+'_stdType'] as stdType>
    		<tr class="${class}" align="center">
    		<#if stdType_index==0>
    			<td rowspan="${resultMap[department.id+'_stdType']?size}">${department.name}</td>
    		</#if>
    		<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
    		<td>${stdType.name}</td>
    		<td>${resultMap[department.id+"_"+stdType.id+"_std"]?default(0)}</td>
    		<#list publicTypes?sort_by("code") as publicType>
    			<td>${resultMap[department.id+"_"+stdType.id+"_thesis"+publicType.id]?default(0)}</td>
    		</#list>
    		<td>${resultMap[department.id+"_"+stdType.id+"_total"]?default(0)}</td>
    		<td><#if "0"!=resultMap[department.id+"_"+stdType.id+"_std"]?default(0)?string>${(resultMap[department.id+"_"+stdType.id+"_total"]/resultMap[department.id+"_"+stdType.id+"_std"])?string("##0.0")}<#else>0</#if></td>
    		<#list projectTypes?sort_by("code") as projectType>
    			<td>${resultMap[department.id+"_"+stdType.id+"_project"+projectType.id]?default(0)}</td>
    		</#list>
    		<#list literatureTypes?sort_by("code") as literatureType>
    			<td>${resultMap[department.id+"_"+stdType.id+"_literature"+literatureType.id]?default(0)}</td>
    		</#list>
    		</tr>
    	</#list>
    </#list>
    <tr class="${class}" align="center">
    	<td colspan="2">合计</td>
    	<td>${resultMap["0_0_std"]?default(0)}</td>
    	<#list publicTypes?sort_by("code") as publicType>
    		<td>${resultMap["0_0_thesis"+publicType.id]?default(0)}</td>
    	</#list>
    	<td>${resultMap["0_0_total"]?default(0)}</td>
    	<td><#if "0"!=resultMap["0_0_std"]?default(0)?string>${(resultMap["0_0_total"]/resultMap["0_0_std"])?string("##0.0")}<#else>0</#if></td>
    	<#list projectTypes?sort_by("code") as projectType>
    		<td>${resultMap["0_0_project"+projectType.id]?default(0)}</td>
    	</#list>
    	<#list literatureTypes?sort_by("code") as literatureType>
    		<td>${resultMap["0_0_literature"+literatureType.id]?default(0)}</td>
    	</#list>
    </tr>
    <tr class="darkColumn" align="center">
    	<td height="25px;" colspan="${projectTypes?size+literatureTypes?size+publicTypes?size+5}"></td>
    </tr>
    <form name="statisticForm" method="post" action="" onsubmit="return false;">
    	<input type="hidden" name="excelType" value="statisStudyProducts"/>
		<input type="hidden" name="template" value="statisStudyProducts.xls">
		<input type="hidden" name="fileName" value="科研成果统计表">
		<input type="hidden" name="enrollYear" value="${enrollYear}">
    </form>
</table>
<script>
	var form = document.statisticForm;
	function exportTable(){
		form.action="studyProduct.do?method=export";
		transferParams(parent.form,form,"studyProduct");
		form.submit();
	}
</script>