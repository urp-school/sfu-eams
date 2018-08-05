<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  	<table id="backBar" width="100%"></table>
  	<@table.table width="100%" headIndex="1">
		<form name="teachModuleForm" method="post" action="" onsubmit="return false;">
			<tr align="center" class="darkColumn" bgcolor="#ffffff" onKeyDown="javascript:enterQuery()">
				<td><img src="${static_base}/images/action/search.png"  align="top" onClick="query()" alt="在结果中过滤"/></td>
	    		<td>
	           		<select  name="teachModulus.studentType.id"  style="width:100%">
						<option value="">全部</option>
						<#list stdTypes?sort_by("name")?if_exists as stdType>
							<option value="${stdType.id}" <#if RequestParameters['teachModulus.studentType.id']?if_exists?string==stdType.id?string>selected</#if>>${stdType.name}</option>
						</#list>
	           		</select> 	     
	     		</td>
	     		<td>
	           		<select  name="teachModulus.courseCategory.id"  style="width:100%">
						<option value="">全部</option>
						<#list categorys?sort_by("name")?if_exists as category>
							<option value="${category.id}" <#if RequestParameters['teachModulus.courseCategory.id']?if_exists?string==category.id?string>selected</#if>>${category.name}</option>
						</#list>
	           		</select> 	     
	     		</td>
	     		<td><input type='text' name='teachModulus.minPeople' value='${RequestParameters['teachModulus.minPeople']?if_exists}' style="width:100%"></td>
	     		<td><input type='text' name='teachModulus.modulusValue' value='${RequestParameters['teachModulus.modulusValue']?if_exists}' style="width:100%"></td>
         		<td><select  name="teachModulus.department.id"  style="width:100%">
					<option value="">全部</option>
					<#list departments?sort_by("name")?if_exists as department>
						<option value="${department.id}" <#if RequestParameters['teachModulus.department.id']?if_exists?string==department.id?string>selected</#if>>${department.name}</option>
					</#list>
	          	 	</select>        
         		</td>
	   		</tr>
	   	</form>
	  	<@table.thead>
	  		<@table.selectAllTd id="teachModulusId"/>
	  		<@table.td name="field.teachModulus.studentType"/>
	  		<@table.td name="field.teachModulus.courseCategory"/>
	  		<@table.td name="field.teachModulus.personNumber"/>
	  		<@table.td name="field.teachModulus.teachModulus"/>
	  		<@table.td name="field.teachModulus.createDepart"/>
	  	</@>
	  	<@table.tbody datas=teachModuluses;teachModulus>
	  		<@table.selectTd id="teachModulusId" value=teachModulus.id/>
   			<td align="center">${teachModulus.studentType.name}</td>
   			<td align="center">${teachModulus.courseCategory.name}</td>
   			<td align="center">[${teachModulus.minPeople}, ${teachModulus.maxPeople})</td>
   			<td align="center">${teachModulus.modulusValue?string("#0.00")}</td>
   			<td align="center">${teachModulus.department.name}</td>
	  	</@>
  	</@>
<script language="javascript">
   	var bar = new ToolBar('backBar','<@bean.message key="field.teachModulus.teachModulusList"/>',null,true,true);
   	bar.setMessage('<@getMessage/>');
  	bar.addItem("<@msg.message key="action.export"/>","exportObject()");
   	bar.addItem("<@msg.message key="action.add"/>","addObject()");
   	bar.addItem("<@msg.message key="action.edit"/>","updateObject()");
   	bar.addItem("<@msg.message key="action.delete"/>","deleteObjects()");
   	
   	var form = document.teachModuleForm;
   	var action="teachModulus.do";
    function addObject(){
    	form.action=action+"?method=edit";
    	form.submit();
    }
    
 	function updateObject(){
 		var id = getSelectId("teachModulusId");
 		if (id == null || id == "" || id.lastIndexOf(',') != -1) {
 			alert("<@bean.message key="field.evaluate.selectSingle"/>");
    		return;
 		}
 		form.action = action + "?method=edit&teachModulusId=" + id;
 		form.submit();
 	}
 	
 	function deleteObjects(){
 		var ids = getSelectIds("teachModulusId");
 		if (ids == null || ids == "") {
 			alert("<@bean.message key="field.evaluate.selectSingle"/>");
    		return;
 		}
 		form.action = action + "?method=remove&teachModulusIds=" + ids;
 		form.submit();
 	}
 	
 	function pageGo(pageNo) {
       	document.pageGoForm.pageNo.value = pageNo;
       	document.pageGoForm.submit();
    }
    
    function query() {
    	form.action = action+"?method=index";
    	form.submit();
    }
    
    function enterQuery() {
    	 if (window.event.keyCode == 13) {
    	 	query();
    	 }
    }
    
    function exportObject() {
    	form.action = action+"?method=export";
    	form.submit();
    }
</script>
</body>
<#include "/templates/foot.ftl"/>