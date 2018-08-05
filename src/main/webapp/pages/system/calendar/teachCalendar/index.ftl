<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
	<table id="calendarFrameBar"></table>
	<table class="frameTable">
	  	<tr>
		  	<td class="frameTable_view" width="20%">
		  		<table  width="100%">
		    		<tr>
		      			<td class="infoTitle" align="left" valign="bottom">
			       			<img src="${static_base}/images/action/info.gif" align="top"/>
			       			<B>教学日历类别</B>
		      			</td>
		    		</tr>
		    		<tr>
				      	<td colspan="8" style="font-size:0px">
				          	<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
				      	</td>
		   			</tr>
				</table>
    			<table width="100%" align="left" class="listTable" id="schemeListTable">
			      	<form name="schemeListForm" method="post" target="contentListFrame" action="" onsubmit="return false;">
			      	<input type="hidden" name="calendarType" value="calendar"/>
			      	<#assign schemeId = RequestParameters["scheme.id"]?if_exists/>
			      	<input type="hidden" name="scheme.id" value="${schemeId?if_exists}"/>
	   				<tr class="darkColumn">
			         	<td><@bean.message key="attr.index"/></td>
				     	<td><@bean.message key="attr.name"/></td>
				   	</tr>
			       	<#list schemes as scheme>
					<tr>
						<td align="left">${scheme_index + 1}</td>
				        <td class="padding" id="scheme_${scheme.id}" onclick="javascript:setSelectedRow(schemeListTable,this);getTeachCalendars('${scheme.id}')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">${scheme.name}</td>
					</tr>
				   	</#list>
	   				</form>
      			</table>
	  		</td>
	  		<td valign="top">
		  		<iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="440" width="100%"></iframe>
	  		</td>
	  	</tr>
  	</table>
	<script>
	   	var bar = new ToolBar('calendarFrameBar','教学日历管理',null,true,true);
	   	bar.setMessage('<@getMessage/>');
	   	bar.addItem("新增日历方案","addSchemeForm()",'update.gif');
	   	
     	var form = document.schemeListForm;
	   	function getTeachCalendars(schemeId){
	      	document.schemeListForm['scheme.id'].value=schemeId;
	      	form.action="calendar.do?method=calendarList&orderBy=calendar.start desc";
	      	form.submit();
	   	}
	   	<#if schemes?exists>
	   	<#if schemeId?exists && schemeId != "">
	   	$("scheme_${schemeId}").onclick();
	   	<#else>
	   	$("scheme_${schemes?first.id}").onclick();
	   	</#if>
	   	</#if>
	   	
	   	function addSchemeForm() {
	   		form["scheme.id"].value = "";
	   		form.action = "calendar.do?method=editSchemeForm";
	   		form.submit();
	   	}
	</script>
</body>
<#include "/templates/foot.ftl"/>