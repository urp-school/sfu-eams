<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script>
 	function doChange(form){
 		var path=new String(document.location);
 		var action=path.substring(path.lastIndexOf("/")+1,path.length);
 		form.action =action;
 		form.submit();
 	}
 </script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <form name="feeTypeform" method="post" action="" onsubmit="return false;">
 <table  width="100%" height="20" border="0" cellpadding="0" cellspacing="0">

 <tr>
 	<td align="center">
 		<h2>历年交费信息</h2>
 	</td>
 </tr>
 <tr>
 <td>
  <table width="100%" align="center" class="listTable">
  		<tr>
  			<td>
  				<select id="calendarId" name="calendar.id" style="width:100px;" onchange="doChange(this.form)">
  					<#if !conditionCalendar?exists>
  						<option value="">全部</option>
  					</#if>
  					<#list calendars?if_exists?sort_by("year") as calendar>
  						<#if conditionCalendar?exists&&conditionCalendar.id==calendar.id>
  							<option value="${calendar.id}" selected>${calendar.year}  ${calendar.term}</option>
  						<#else>
  							<option value="${calendar.id}">${calendar.year}  ${calendar.term}</option>
  						</#if>
  					</#list>
  					<#if conditionCalendar?exists>
  						<option value="">全部</option>
  					</#if>
  				</select>
  			</td>
  			<td>
  				<select id="feeTypeId" name="feeType.id" style="width:100px;" onchange="doChange(this.form)">
  					<#if !conditionFeeType?exists>
  						<option value="">全部</option>
  					</#if>
  					<#list feeTypes?if_exists as feeType>
  						<#if conditionFeeType?exists&&conditionFeeType.id==feeType.id>
  							<option value="${feeType.id}" selected>${feeType.name?if_exists}</option>
  						<#else>
  							<option value="${feeType.id}">${feeType.name?if_exists}</option>
  						</#if>
  					</#list>
  					<#if conditionFeeType?exists>
  						<option value="">全部</option>
  					</#if>
  				</select>
  			</td>
  			<td>
  			</td>
  			<td>
  			</td>
  			</td>
  			<td>
  		</tr>
  		<tr align="center" class="darkColumn">
  				<td><B><@bean.message key="field.feeDetail.teachCalendar"/></B></td>
  				<td><B><@bean.message key="field.feeType.feeTypeCn"/></B></td>
  				<td><B><@bean.message key="field.feeDetail.hasPaidBill"/></B></td>
  				<td><B><@bean.message key="field.feeDetail.totleBill"/></B></td>
  				<td><B>缴费日期</B></td>
  			</tr>
  				<#list fees?if_exists as feeDetailInfo>
  					<#if feeDetailInfo_index%2==1 ><#assign class="grayStyle" ></#if>
   				<#if feeDetailInfo_index%2==0 ><#assign class="brightStyle" ></#if>
   				<tr class="${class}">
   					<td align="center">
   						${feeDetailInfo.calendar.year}&nbsp;&nbsp;${feeDetailInfo.calendar.term}
   					</td>
   					<td align="center">
   						${feeDetailInfo.type.name?if_exists}
   					</td>
   					<td align="center">
   						${feeDetailInfo.toRMB?if_exists}
   					</td>
   					<td align="center">
   						${feeDetailInfo.shouldPay?if_exists}
   					</td>
   					<td>${feeDetailInfo.createAt?string("yyyy-MM-dd")}</td>
   				</tr>
  				</#list>
  			</td>
  			</tr>
  			<tr align="center" class="darkColumn">
  				<td height="25px;" colspan="5">
  				</td>
  			</tr>
  		</table>
  	</td>
  </tr>
  </table>
  </form>
 </body>
 <#include "/templates/foot.ftl"/>