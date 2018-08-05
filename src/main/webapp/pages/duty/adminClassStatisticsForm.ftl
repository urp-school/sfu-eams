<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table cellpadding="0" cellspacing="0" border="0" width="100%" >
   <tr>
    <td>
<#assign tableWidth="90%"/>
<#if RequestParameters['moduleName']?exists>
<#assign moduleName=RequestParameters['moduleName']>
<#else>
<#assign moduleName="StudentManager">
</#if>
<#assign formName="pageGoForm"/>
<#assign formAction="dutyRecordManager.do"/>
<#assign targetValue="_blank"/>
<#assign tableTitle="考勤统计项"/>
<#assign isCalendarNeed=true/>
<#assign showInput></#assign>
<#assign hiddenInput></#assign>
<#assign resetFormHiddenInput></#assign>
<#assign method="adminClassRecordStatistics"/>
<#assign resetMethod="adminClassStatisticsForm"/>
<#assign showInputTD>
<td class="brightStyle" colSpan="2" id="f_weekRang">
	&nbsp;统计周从<input type="text" name="weekBegin" maxlength="2" size="1" value="${RequestParameters['weekBegin']?if_exists}">到<input type="text" name="weekEnd" maxlength="2" size="1" value="${RequestParameters['weekEnd']?if_exists}">&nbsp;学期合计统计周从<input type="text" name="totalWeekBegin" maxlength="2" size="1" value="${RequestParameters['totalWeekBegin']?if_exists}">到<input type="text" name="totalWeekEnd" maxlength="2" size="1" value="${RequestParameters['totalWeekEnd']?if_exists}">
</td>
</#assign>
<#assign functionSearch>
function search(){
   	if (doValidate()) {
   		var form=document.${formName};
   		addAllParams(form);
      	form.submit();
    }
}
</#assign>
<#include "/pages/selector/adminClassSearchForm.ftl"/>
<#include "/templates/calendarSpecialitySelect.ftl"/>
		</td>
	</tr>
</table>
 <script>
	function doStatistics(){
				
       var a_fields = {
         'year':{'l':'<@bean.message key="attr.year2year"/>', 'r':true, 't':'f_year'},
         'term':{'l':'<@bean.message key="attr.term"/>', 'r':true, 't':'f_term'},
         'stdType':{'l':'<@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'}
       };
     
       var v = new validator(document.pageGoForm, a_fields, null);
       
       if (v.exec()) {
          document.pageGoForm.submit();
       }
	}

 </script>
</body>
<#include "/templates/foot.ftl"/>