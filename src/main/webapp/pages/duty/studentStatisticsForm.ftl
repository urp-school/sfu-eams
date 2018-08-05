<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table cellpadding="0" cellspacing="0" border="0" width="100%" >
   <tr>
    <td>
<#assign formName="pageGoForm"/>
<#assign formAction="dutyRecordManager.do"/>
<#assign targetValue="_blank"/>
<#assign tableTitle="考勤统计项"/>
<#assign isCalendarNeed=true/>
<#assign select2ndSpeciality=true/>
<#assign showInput>
			<tr>
	  			<td class="grayStyle" width="15%" id="f_dateBegin">
					&nbsp;开始日期：
	     		</td>
	     		<td class="brightStyle">
           			<input type="text" name="dateBegin" onfocus="calendar()" size="10" value="${dateBegin?if_exists}" style="width:100px"/>
         		</td>
         		<td class="grayStyle" width="15%" id="f_dateEnd">
					&nbsp;结束日期：
	     		</td>
	     		<td class="brightStyle">
           			<input type="text" name="dateEnd" onfocus="calendar()" size="10" value="${dateEnd?if_exists}" style="width:100px"/>
         		</td>
	  		</tr>
	  		<tr>
	  			<td class="grayStyle" width="18%" id="f_totalDateBegin">
					&nbsp;学期合计开始日期：
	     		</td>
	     		<td class="brightStyle">
           			<input type="text" name="totalDateBegin" onfocus="calendar()" size="10" value="${totalDateBegin?if_exists}" style="width:100px"/>
         		</td>
         		<td class="grayStyle" width="18%" id="f_totalDateEnd">
					&nbsp;学期合计结束日期：
	     		</td>
	     		<td class="brightStyle">
           			<input type="text" name="totalDateEnd" onfocus="calendar()" size="10" value="${totalDateEnd?if_exists}" style="width:100px"/>
         		</td>
	  		</tr>
</#assign>
<#assign hiddenInput>
<input type="hidden" name="flag" id="flag"/>
</#assign>
<#assign method="studentRecordStatistics"/>
<#assign resetMethod="studentStatisticsForm"/>
<#assign moduleName >${moduleName?default('DutyRecordStatistics')}</#assign>
<#include "/pages/selector/stdSearchForm.ftl"/>
		</td>
	</tr>
</table>
</body>
 <script>
	function search(){
		var a_fields = {
	         'year':{'l':'<@bean.message key="attr.year2year"/>', 'r':true, 't':'f_year'},
	         'term':{'l':'<@bean.message key="attr.term"/>', 'r':true, 't':'f_term'},
	         'stdType':{'l':'<@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'}
       	};
     
       	var v = new validator(document.pageGoForm, a_fields, null);
       
       	if (v.exec()) {
       	  	document.getElementById("flag").value="doStatistics";
          	document.pageGoForm.submit();
       	}
       
    }    

 </script>
<#include "/templates/foot.ftl"/>