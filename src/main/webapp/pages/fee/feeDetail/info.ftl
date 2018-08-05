<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" onload="refreshFeeList();">
	<table id="feeInfoBar"></table>
	<script>
	   var bar = new ToolBar('feeInfoBar','收费明细',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addBack("<@bean.message key="action.back"/>");
	</script>
	<table width="100%" align="center" class="listTable">
		<tr class="darkColumn">
    		<td colspan="4" align="center"><@bean.message key="field.feeDetail.modifyFeeDetailObject"/></td>
  		</tr>
  		<tr>
            <td class="grayStyle" align="center"  id="f_stdNo"><@msg.message key="attr.stdNo"/>:</td>
  		    <td class="brightStyle">${feeDetail.std.code?if_exists}</td>
            <td class="grayStyle" align="center"><@msg.message key="attr.personName"/>:</td>
  		    <td class="brightStyle">${feeDetail.std.name?if_exists}</td>
  		</tr>
  		<tr>
            <td class="grayStyle" align="center"><@msg.message key="entity.speciality"/>:</td>
  		    <td id="speciality" class="brightStyle"><@i18nName feeDetail.std.firstMajor?if_exists/></td>
            <td class="grayStyle" align="center">班级:</td>
  		    <td id="adminClass" class="brightStyle"><@getBeanListNames feeDetail.std.adminClasses?if_exists/></td>
  		</tr>
  		<tr>
           <td class="grayStyle" align="center">学年度:</td>				
           <td class="brightStyle" align="left">${feeDetail.calendar?if_exists.year?if_exists}</td>
           <td class="grayStyle" align="center"><@bean.message key="field.feeDetail.term"/>:</td>
			<td class="brightStyle" align="left">${feeDetail.calendar?if_exists.term?if_exists}</td>
  		</tr>  		
   		<tr>
			<td class="grayStyle" align="center" >收费部门:</td>
			<td class="brightStyle" align="left"><@i18nName feeDetail.depart/></td>
			<td class="grayStyle" align="center" id="f_totleBill">收费类型:</td>
			<td class="brightStyle" align="left"><@i18nName feeDetail.type/></td>				
		</tr>
		<tr>
			<td class="grayStyle" align="center">
				<@bean.message key="field.feeDetail.shouldPaidFee"/>:
			</td>
			<td class="brightStyle" align="left">${feeDetail.shouldPay?default(0)?string("##0.0")}</td>
			<td class="grayStyle" align="center">
				<@bean.message key="field.feeDetail.hasPaidFee"/>:
			</td>
			<td class="brightStyle" align="left">${feeDetail.payed?default(0)?string("##0.0")}</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center" >
				<@bean.message key="field.feeDetail.invoiceNumber"/>:
			</td>
			<td class="brightStyle" align="left" >${feeDetail.invoiceCode?if_exists}</td>
			<td class="grayStyle" align="center" >收费方式:</td>
			<td class="brightStyle" align="left" ><@i18nName feeDetail.mode/></td>			
		</tr>
		<tr>
			<td class="grayStyle" align="center">币种:</td>
			<td class="brightStyle" align="left"><@i18nName  feeDetail.currencyCategory/></td>		
			<td class="grayStyle" align="center" id="f_changeRate">
				<@bean.message key="field.feeDetail.exchangeRate"/>:
			</td>
			<td class="brightStyle" align="left">${feeDetail.rate?default(0)?string("##0.0")}</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center">收费人:</td>
			<td class="brightStyle" align="left">${feeDetail.whoAdded}</td>		
			<td class="grayStyle" align="center">缴费日期:</td>
			<td class="brightStyle" align="left" >${feeDetail.createAt?if_exists?string("yyyy-MM-dd")}</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center">最后修改人:</td>
			<td class="brightStyle" align="left">${feeDetail.whoModified}</td>		
			<td class="grayStyle" align="center" >修改时间:</td>
			<td class="brightStyle" align="left">${feeDetail.modifyAt?if_exists?string("yyyy-MM-dd")}</td>
		</tr>		
		<tr>
			<td class="grayStyle" align="center" id="f_remark">
				<@bean.message key="field.feeDetail.remark"/>
			</td>
			<td class="brightStyle" align="left" colspan="3">${feeDetail.remark?if_exists}</td>
		</tr>
	</table>
	<script>
    function refreshFeeList(){
    <#if RequestParameters['messages']?exists>
      if(!window.top.opener.closed)
      window.top.opener.parent.doQuery();
      setTimeout('self.close()',1000);
    </#if>
    }
	</script>
 </body>
 <#include "/templates/foot.ftl"/>