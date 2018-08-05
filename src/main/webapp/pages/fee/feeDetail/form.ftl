<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script type='text/javascript' src='dwr/interface/feeDetailAction.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>

<script language="javascript">
	function doAction(form,extra){
	    if($('message').innerHTML.length>0) {if(!confirm("抬头提示有错误信息，是否强制提交?")) return;}
		var a_fields = {
			 'feeDetail.invoiceCode':{'l':'<@bean.message key="field.feeDetail.invoiceNumber"/>', 'r':true, 't':'f_invoiceCode','f':'alphanum', 'mx':50},			 
			 'feeDetail.payed':{'l':'<@bean.message key="field.feeDetail.hasPaidFee"/>','r':true,'t':'f_payed','f':'real','mx':20},
             'feeDetail.rate':{'l':'汇率','t':'f_rate','f':'real','r':true},
			 'feeDetail.createAt':{'l':'缴费日期','t':'f_createAt','f':'date','r':true},
			 'feeDetail.remark':{'l':'<@bean.message key="field.feeDetail.remark"/>','t':'f_remark','mx':200},
			 'feeDetail.depart.id':{'l':'<@bean.message key="field.feeDetail.feeOfDepart"/>','r':true,'t':'f_depart'}
	    };
		var v = new validator(form, a_fields, null);
		if (v.exec()) {
			form.action = "feeDetail.do?method=save";
			if(null!=extra){
			   form.action+=extra;
			}			
			if(confirm("确定保存修改的信息了？"))
			  form.submit();
		}
	}
  //设定货币和汇率
  var currencyCategory =new Array();  
  <#list currencyTypeList as currencyCategory>
    currencyCategory[${currencyCategory_index}]={'id':'${currencyCategory.id}','name':'<@i18nName currencyCategory/>','rateToRMB':'${currencyCategory.rateToRMB?default(1)?string("##0.00")}'};					
  </#list>
  
  function setCurrencyCategory(){
      DWRUtil.addOptions('currencyCategorySelect',currencyCategory,'id','name');
      setSelected($('currencyCategorySelect'),'${feeDetail.currencyCategory.id?if_exists}');
  }
  function refreshFeeList(){
    <#if RequestParameters['messages']?exists>
      if(!window.top.opener.closed)
      window.top.opener.parent.doQuery();
    </#if>
  }
 </script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" onload="refreshFeeList();setCurrencyCategory();">
	<table id="feeInfoBar"></table>
	<table width="100%" align="center" class="listTable" id="feeDetailTable" onkeyPress="onReturn.focus(event)">
    	<form name="feeDetailForm" method="post" action="" onsubmit="return false;">
		<tr class="darkColumn">
    		<td colspan="4" align="center"><@bean.message key="field.feeDetail.modifyFeeDetailObject"/></td>
  		</tr>
  		<tr>
  		    <input type="hidden" name="feeDetail.id" value="${feeDetail.id}">
 		    <input type="hidden" name="feeDetail.std.id"	value="${feeDetail.std.id}">
            <td class="grayStyle" align="center"  id="f_stdNo"><@msg.message key="attr.stdNo"/><font color="red">*</font>:</td>
  		    <td class="brightStyle">${feeDetail.std.code}</td>
            <td class="grayStyle" align="center"><@msg.message key="attr.personName"/>:</td>
  		    <td id="name" class="brightStyle">${feeDetail.std.name?if_exists}</td>
  		</tr>
  		<tr>
            <td class="grayStyle" align="center"><@msg.message key="entity.speciality"/>:</td>
  		    <td id="speciality" class="grayStyle"><@i18nName feeDetail.std.firstMajor?if_exists/></td>
            <td class="grayStyle" align="center">班级:</td>
  		    <td id="adminClass" class="grayStyle"><@getBeanListNames feeDetail.std.adminClasses?if_exists/></td>
  		</tr>
  		<tr>
            <td class="grayStyle" align="center"><@msg.message key="entity.studentType"/>:</td>
  		    <td id="feeDetailStdType" class="grayStyle"><@i18nName feeDetail.std.type?if_exists/></td>
   	        <input id="stdType"  name="calendar.studentType.id" type="hidden" value="${feeDetail.std.type?if_exists.id?if_exists}">
            <td class="grayStyle" align="center"><@msg.message key="entity.department"/>:</td>
  		    <td id="department" class="grayStyle"><@i18nName feeDetail.std.department?if_exists/></td>
  		</tr>
  		<tr>
           <td class="grayStyle" align="center"><@bean.message key="field.feeDetail.academic"/>:</td>				
           <td class="brightStyle" align="left">
				<select id="year" name="calendar.year" style="width:100px;" TABINDEX="3">
					<option value="${feeDetail.calendar?if_exists.year?if_exists}"></option>
				</select>
				<select id="term" name="calendar.term" TABINDEX="4" style="width:100px;">
					<option value="${feeDetail.calendar?if_exists.term?if_exists}"></option>
				</select>
			</td>
            <td class="grayStyle" align="center">
				<@bean.message key="field.feeDetail.selectCurrencyCategory"/></td>
			<td class="brightStyle" align="left">
				<select id="currencyCategorySelect" name="feeDetail.currencyCategory.id" style="width:60px;" TABINDEX="6"></select>				
			</td>
  		</tr>
   		<tr>
			<td class="grayStyle" align="center" id="f_depart"><@bean.message key="field.feeDetail.feeOfDepart"/></td>
			<td class="brightStyle" align="left" >
				<select name="feeDetail.depart.id" style="width:150px;" TABINDEX="5"  >
					<#list adminList?if_exists as department>
						<option value="${department.id}" <#if department.id?string== feeDetail.depart?if_exists.id?if_exists?string>selected</#if>>${department.name}</option>
					</#list>
				</select>
			</td>
			<td class="grayStyle" align="center" id="f_rate"><@bean.message key="field.feeDetail.exchangeRate"/><font color="red">*</font>:</td>
			<td><input name="feeDetail.rate" id="rate" style="width:40px" onchange="isNumber(this);"value="${feeDetail.rate?default(0)?string("##0.0")}" maxlength="8"/></td>				
		</tr>
		<tr>
			<td class="grayStyle" align="center">收费方式:</td>
			<td class="brightStyle" align="left">
				<select name="feeDetail.mode.id" style="width:150px;" TABINDEX="7">
					<#list feeModeList?if_exists as mode>
						<option value="${mode.id}" <#if mode.id?string == feeDetail.mode?if_exists.id?if_exists?string>selected</#if>><@i18nName mode/></option>
					</#list>
				</select>
			</td>
			<td class="grayStyle" align="center">收费项目:</td>
			<td class="brightStyle" align="left">
				<select name="feeDetail.type.id" style="width:150px;" TABINDEX="8">
					<#list sort_byI18nName(feeTypeList) as type>
						<option value="${type.id}" <#if type.id?string == feeDetail.type?if_exists.id?if_exists?string>selected</#if>><@i18nName type/></option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center" id="f_shouldPay"><@bean.message key="field.feeDetail.shouldPaidFee"/>:</td>
			<td class="brightStyle" align="left" id="shouldPay">
			<input type="text" name="feeDetail.shouldPay" maxlength="10" onchange='isNumber(this);' style="width:100px;" value="<#if feeDetail.shouldPay?exists>${feeDetail.shouldPay?string("##0.00")}</#if>" TABINDEX="9" maxlength="10"/>
		    </td>
			<td class="grayStyle" align="center" id="f_payed">本次缴费:</td>
			<td class="brightStyle" align="left">
				<input type="text" name="feeDetail.payed" maxlength="10" style="width:150px;" value="${feeDetail.payed?default(0)?string("##0.00")}" TABINDEX="10" maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center" id="f_invoiceCode"><@bean.message key="field.feeDetail.invoiceNumber"/><font color="red">*</font>:</td>
			<td class="brightStyle" align="left">
				<input type="text" name="feeDetail.invoiceCode" style="width:150px;" value="${feeDetail.invoiceCode?if_exists}" TABINDEX="11" maxlength="15"/>
			</td>
			<td class="grayStyle" align="center" id="f_createAt">缴费日期<font color="red">*</font>:	</td>
			<td class="brightStyle" align="left">
				<input type="text" name="feeDetail.createAt" style="width:80px" value="${feeDetail.createAt?if_exists?string("yyyy-MM-dd")}" onfocus="calendar(this.form['feeDetail.createAt'])"/>
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center" id="f_remark"><@bean.message key="field.feeDetail.remark"/></td>
			<td class="brightStyle" align="left" colspan="3">
				<textarea name="feeDetail.remark" cols="50" TABINDEX="12">${feeDetail.remark?if_exists}</textarea>
			</td>
		</tr>
		<tr>
			<td class="darkColumn" colspan="4" align="center">
                 <input type="button" value="<@msg.message key="action.save"/>(S)" accesskey="S" name="button1" onClick="doAction(this.form);" class="buttonStyle"  TABINDEX="12"/>&nbsp;
			</td>
		</tr>
	</table>
	</form>
	<script src='dwr/interface/calendarDAO.js'></script>
    <script src='scripts/common/CalendarSelect.js'></script>
	<script language="JavaScript" type="text/JavaScript" src="scripts/common/onReturn.js"></script>
    <script language="JavaScript" type="text/JavaScript" src="scripts/feeDetail/feeDetail.js"></script>
	<script>
    dd.initYearSelect();
   	var bar = new ToolBar('feeInfoBar','修改收费明细',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	bar.addBack("<@bean.message key="action.back"/>");
   	<#if std_outof_shool?exists && std_outof_shool>
   		$("error").innerHTML = msg_outof_shool;
   	</#if>
	</script>
 </body>
 <#include "/templates/foot.ftl"/>