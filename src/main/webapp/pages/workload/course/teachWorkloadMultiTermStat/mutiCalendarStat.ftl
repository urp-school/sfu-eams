<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="backBar" width="100%"></table>
	<table align="center" width="100%" class="listTable">
	  <form name="listForm" method="post" action="" onsubmit="return false;">
	  		<input type="hidden" name="stdTypeIdSeq" value="<#list stdTypeList?if_exists as stdType>#{stdType.id}<#if stdType_has_next>,</#if></#list>">
	  		<input type="hidden" name="calendarIdSeq" value="<#list condtionCalendars?if_exists as calendar>#{calendar.id}<#if calendar_has_next>,</#if></#list>">
	  		<tr class="darkColumn">
	  			<td align="center">&nbsp;</td>
	  			<td>开课院系</td>
	  			<#list stdTypeList?if_exists as stdType>
	  			<td><#if stdType?exists>${stdType.name}</#if></td>
	  			</#list>
	  			<td>合计</td>
	  		</tr>
	  		<#assign class="grayStyle">
	  		<#list collegeList?if_exists as college>
	  			<tr class="${class}">
	  			<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
	  				<td><input type="radio" name="departmentId" value="${college.id}"></td>
	  				<td><#if college?exists>${college.name}</#if></td>
	  				<#list stdTypeList?if_exists as stdType>
	  					<td>${departAndStdTypeMap[college.id+"-"+stdType.id]?default(0)?string("##0.0")}</td>
	  				</#list>
	  				<td>${departAndStdTypeMap[college.id+"-0"]?default(0)?string("##0.0")}</td>
	  			</tr>
	  		</#list>
	  		<tr  class="${class}">
	  			<td colspan="2" align="center">合计</td>
	  			<#list stdTypeList?if_exists as stdType>
	  					<td>${departAndStdTypeMap["0-"+stdType.id]?default(0)?string("##0.0")}</td>
	  			</#list>
	  			<td>${departAndStdTypeMap["0-0"]?default(0)?string("##0.0")}</td>
	  		</tr>
	  		<tr class="darkColumn">
	  			<td colspan="${stdTypeList?size+3}" height="25px;"></td>
	  		</tr>
	  </from>
	</table>
	<script>
	   var bar = new ToolBar('backBar','开课院系工作量信息表',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("查看部门详细信息","deDetailInfo()");
	   bar.addPrint("<@msg.message key="action.print"/>");
	   bar.addBack();
	   
	   function getIds(){
	       return(getRadioValue(document.getElementsByName("departmentId")));
	   }
	  function deDetailInfo(){
	    var form =document.listForm;
	  	var departId = getIds();
	  	if(""==departId){alert("请选择部门");return;}
	  	form.action="teachWorkloadMultiTermStat.do?method=info&selectDepartId="+departId;
	  	form.submit();
	  }
	</script>
</body>
<#include "/templates/foot.ftl"/>