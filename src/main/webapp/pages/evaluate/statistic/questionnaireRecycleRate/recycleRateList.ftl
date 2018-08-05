<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="mergeTableTd('tableId');f_frameStyleResize(self)">
	<table id="bar"></table>
   <#if (result.recycleRateList?size==0)>查询条件内的评教回收率还未统计。
   <#else>
   <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
   <form name="listForm" method="post" action="" onsubmit="return false;">
  	<#list RequestParameters?keys as key>
  		<input type="hidden" name="${key}" value="${RequestParameters[key]}" />
  	</#list>
   <tr>
     <td>
     <table id="tableId" width="100%" align="center" class="listTable">
     <tr>
     	<td align="center" class="darkColumn" colspan="10">
     		<@bean.message key="field.recycleRate.statisticRecycleRate"/>
     	</td>
     </tr>
	   <tr align="center" class="brightStyle">
	   	<td><B>&nbsp;</B></td>
	     <td><B>院系</B></td>
	     <td><B>学生类别</B></td>
	     <td><B><@bean.message key="field.recycleRate.evaluateAll"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.totleAll"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.recycleRate"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.evaluatePerson"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.totlePerson"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.recycleRate"/></B></td>
	     <td width="10%"><B><@bean.message key="field.recycleRate.statisticTime"/></B></td>
	   </tr>
	   <#assign t1=0>
	   <#assign t2=0>
	   <#assign t3=0>
	   <#assign t4=0>
	   <#if result.recycleRateList?exists>
	   		<#assign totleEvaluatePerson></#assign>
	   		<#assign totleEvaluateAmount></#assign>
	   		<#assign class="grayStyle" >
	   		<#list result.recycleRateList?if_exists as recycleRate>
	   	   <#if 0!=recycleRate.totleEvaluateAmount&&0!=recycleRate.totlePerson>
	   		<tr class="${class}">
	   			<td><input type="radio" name="departmentId" value="${recycleRate.department.id}"></td>
	    		<td  align="center">
	    			<a href="javascript:doSubmit('${recycleRate.department.id}')">${recycleRate.department.name?if_exists}</a>
	    		</td>
	    		<td  align="center">
	    		 ${recycleRate.studentType.name?if_exists}	
	    		</td>
	    		<td  align="center">
	     		${recycleRate.evaluateAmount?if_exists}
	     		<#assign t1=t1+recycleRate.evaluateAmount>		
	    		</td>
	    		<td  align="center">
	    		 ${recycleRate.totleEvaluateAmount?if_exists}
	    		 <#assign t2=t2+recycleRate.totleEvaluateAmount>		
	    		</td>
	    		<td  align="center">
	    			${recycleRate.amountRate?if_exists?string("##0.0#")}%
	    		</td>
	    		<td  align="center">
	     		${recycleRate.evaluatePerson?if_exists}
	     		<#assign t3=t3+recycleRate.evaluatePerson>		
	    		</td>
	    		<td  align="center">
	    		 ${recycleRate.totlePerson?if_exists}
	    		 <#assign t4=t4+recycleRate.totlePerson>		
	    		</td>
	    		<td  align="center">
	    		${recycleRate.totleRate?if_exists?string("##0.0#")}%
	    		</td>
	    		<td  align="center">
	    			<#if recycleRate.modifiedTime?exists>
	    				&nbsp;${recycleRate.modifiedTime}
	    			<#else>
	    				&nbsp;${recycleRate.statisticTime?string("yyyy-MM-dd")}
	    			</#if>
	    		</td>
	   		</tr>
	   		<#if class=="grayStyle"><#assign class="brightStyle"/><#else><#assign class="grayStyle"/></#if>
	   	</#if>
	   	</#list>
	   </#if>
	   	<tr class="${class}">
	   		<td	colspan="3" align="center">合计</td>
	   		<td align="center">${t1}</td>
	   		<td align="center">${t2}</td>
	   		<td align="center">
	   		<#if t2==0>
	   			&nbsp;0.0
	   		<#else>
	   			${(t1/t2*100)?string("##0.0")}%
	   		</#if>
	   		</td>
	   		<td align="center">${t3}</td>
	   		<td align="center">${t4}</td>
	   		<td align="center">
	   		<#if t4==0>
	   			&nbsp;0.0
	   		<#else>
	   			${(t3/t4*100)?string("##0.0")}%
	   		</#if>
	   		</td>
	   		<td align="center">
	   		
	   		</td>
	   	</tr>
	   	<tr class="darkColumn">
	   		<td height="20px;" colspan="10">
	   		</td>
	   	</tr>
	  </table>
	  </td>
	  </tr>
	  </table> 
	  </form>
	</#if>
	<script>
		var bar = new ToolBar("bar", "回收率统计", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("开课院系","detailOfOpenCollege()");
		bar.addItem("上课院系","detailOfStdCollege()");
		
		function mergeTableTd(tableId){
		    if(null==document.getElementById(tableId))return;
			var rowsArray = document.getElementById(tableId).rows;
			var value=rowsArray[1].cells[1];
			for(var i=2;i<rowsArray.length-2;i++){
				nextTd=rowsArray[i].cells[1];
				if(nextTd.innerHTML==value.innerHTML){
					rowsArray[i].removeChild(nextTd.previousSibling);
					rowsArray[i].removeChild(nextTd);
					var rowspanValue= new Number(value.rowSpan);
					rowspanValue++;
					value.rowSpan=rowspanValue;
					value.previousSibling.rowSpan=rowspanValue;
				}else{
					value=nextTd;
				}
			}
		}
		
		function doSubmit(departmentId){
		    var url="questionnaireRecycleRateAction.do?method=doGetDetailInfoOfTask&departmentId="+departmentId+"&studentOrTeacher=";
			if(confirm("你要查看开课院系还是学生所属院系\n想查看这个学院的学生所上的课程请点击确定按钮\n想查看这个学院所开设的课程的信息请点击取消按钮")==true){
				url+="student";
			}else{
				url+="teacher";
			}
			document.listForm.action=url;
			document.listForm.submit();
		}
		
		function getId(name){
			return(getRadioValue(document.getElementsByName(name)));
		}
		
		function detailOfOpenCollege(){
		 	var departmentId=getId("departmentId");
		 	if(""==departmentId){
		 		alert("请选择一个部门");
		 		return;
		 	}
			var url="questionnaireRecycleRateAction.do?method=doGetDetailInfoOfTask&departmentId="+departmentId+"&studentOrTeacher=teacher";
			document.listForm.action=url;
			document.listForm.submit();
		}
		
		function detailOfStdCollege(){
			var departmentId=getId("departmentId");
		 	if(""==departmentId||departmentId.indexOf(",")>-1){
		 		alert("请选择一个部门");
		 		return;
		 	}
			var url="questionnaireRecycleRateAction.do?method=doGetDetailInfoOfTask&departmentId="+departmentId+"&studentOrTeacher=student";
			document.listForm.action=url;
			document.listForm.submit();
		}
		
		function printCollege(){
			print();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>