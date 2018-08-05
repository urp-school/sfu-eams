<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script type='text/javascript' src='dwr/interface/feeDetailAction.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>

<script language="javascript" >
	function doAction(form,extra){
	    if($('message').innerHTML.length>0) {if(!confirm("抬头提示有错误信息，是否强制提交?")) return;}
		var a_fields = {
 		     'feeDetail.std.code':{'l':'<@msg.message key="attr.stdNo"/>', 'r':true, 't':'f_code','f':'alphanum', 'mx':16},
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
			if(confirm("确定保存新增信息了？"))
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
	<script>
	   var bar = new ToolBar('feeInfoBar','添加收费明细',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addBack("<@bean.message key="action.back"/>");
	   <#if std_outof_shool?exists&& std_outof_shool>
	   $('message').innerHTML=msg_outof_shool;
	   </#if>
	</script>

	<table width="100%" align="center" class="listTable" id="feeDetailTable" onkeyPress="onReturn.focus(event)">
    	<form name="feeDetailForm" method="post" action="" onsubmit="return false;">
		<tr class="darkColumn">
    		<td colspan="4" align="center"><@bean.message key="field.feeDetail.modifyFeeDetailObject"/></td>
  		</tr>
  		<tr>
            <td class="grayStyle" align="center"  id="f_code"><@msg.message key="attr.stdNo"/><font color="red">*</font>:</td>
  		    <td>
    		  <input type="text" name="feeDetail.std.code" maxlength="32" TABINDEX="1" value="${(feeDetail.std.code)?if_exists}" onchange="getStd()" style="width:90px">
    		  <input type="hidden"  id="stdId" name="feeDetail.std.id"	value="${(feeDetail.std.id)?if_exists}" >    		  
    		  <input type="button" class="buttonStyle" id="queryDetailButton" value="<@msg.message key="action.info"/>" disabled onclick="queryStdDetail()">
    		</td>
            <td class="grayStyle" align="center"><@msg.message key="attr.personName"/>:</td>
  		    <td id="name" class="grayStyle">${(feeDetail.std.name)?if_exists}</td>
  		</tr>
  		<tr>
            <td class="grayStyle" align="center"><@msg.message key="entity.speciality"/>:</td>
  		    <td id="speciality" class="grayStyle"><@i18nName (feeDetail.std.firstMajor)?if_exists/></td>
            <td class="grayStyle" align="center">班级1:</td>
  		    <td id="adminClass" class="grayStyle"><@getBeanListNames (feeDetail.std.adminClasses)?if_exists/></td>
  		</tr>
  		<tr>
            <td class="grayStyle" align="center"><@msg.message key="entity.studentType"/>:</td>
  		    <td id="feeDetailStdType" class="grayStyle"><@i18nName (feeDetail.std.type)?if_exists/></td>
   	        <input id="stdType"  name="calendar.studentType.id" type="hidden" value="${(feeDetail.std.type)?if_exists.id?if_exists}">
            <td class="grayStyle" align="center">院系:</td>
  		    <td id="department" class="grayStyle"><@i18nName (feeDetail.std.department)?if_exists/></td>
  		</tr>
  		<tr>
  		   <td class="grayStyle" align="center">经费来源:</td>
  		   <td colspan="3" id="feeOrigin"></td>  		   
  		</tr>
  		<tr>
           <td class="grayStyle" align="center"><@bean.message key="field.feeDetail.academic"/>:</td>				
           <td class="brightStyle" align="left">
				<select id="year" name="calendar.year" style="width:100px;" TABINDEX="3" onchange="setTimeout('changeFeeValue()',500);">
					<option value="${(feeDetail.calendar.year)?if_exists}"></option>
				</select>
				<select id="term" name="calendar.term" TABINDEX="4" style="width:100px;" onchange="setTimeout('changeFeeValue()',500);">
					<option value="${(feeDetail.calendar.term)?if_exists}"></option>
				</select>
			</td>
            <td class="grayStyle" align="center">
				<@bean.message key="field.feeDetail.selectCurrencyCategory"/></td>
			<td class="brightStyle" align="left">
				<select id="currencyCategorySelect" name="feeDetail.currencyCategory.id" style="width:60px;" TABINDEX="6" onchange="changeRate(event)"></select>				
			</td>
  		</tr>
   		<tr>
			<td class="grayStyle" align="center" id="f_depart"><@bean.message key="field.feeDetail.feeOfDepart"/></td>
			<td class="brightStyle" align="left">
				<select name="feeDetail.depart.id" style="width:150px;" TABINDEX="5">
					<#list adminList?if_exists as department>
						<option value="${(department.id)?if_exists}" <#if department.id?string== feeDetail.depart?if_exists.id?if_exists?string>selected</#if>>${department.name}</option>
					</#list>
				</select>
				<button onclick="settingFeeOfDepart()">设为首选</button>
			</td>
			<td class="grayStyle" align="center" id="f_rate"><@bean.message key="field.feeDetail.exchangeRate"/><font color="red">*</font>:</td>
			<td><input name="feeDetail.rate" id="rate" maxlength="10" style="width:40px" onchange="isNumber(this);"value="${(feeDetail.rate)?default(0)?string("##0.0")}"></td>				
		</tr>
		<tr>
			<td class="grayStyle" align="center">收费方式:</td>
			<td class="brightStyle" align="left">
				<select name="feeDetail.mode.id" style="width:150px;" TABINDEX="7">
					<#list feeModeList?if_exists as mode>
						<option value="${(mode.id)?if_exists}" <#if mode.id?string == feeDetail.mode?if_exists.id?if_exists?string>selected</#if>><@i18nName mode/></option>
					</#list>
				</select>
			</td>
			<td class="grayStyle" align="center">收费项目:</td>
			<td class="brightStyle" align="left">
				<select name="feeDetail.type.id" style="width:150px;" onchange="changeFeeValue()" TABINDEX="8">
					<#list sort_byI18nName(feeTypeList) as type>
						<option value="${type.id}" <#if type.id?string == feeDetail.type?if_exists.id?if_exists?string>selected</#if>><@i18nName type/></option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center" id="f_shouldPay"><@bean.message key="field.feeDetail.shouldPaidFee"/>:</td>
			<td class="brightStyle" align="left" id="shouldPay">
			</td>
			<td class="grayStyle" align="center" >已经缴纳:</td>
			<td class="brightStyle" align="left" id="hasPayed">${hasPay?if_exists}</td>
		</tr>
        <tr>
            <td class="grayStyle" align="center">剩余</td>
            <td class="brightStyle" align="left" id="remainder"></td>
			<td class="grayStyle" align="center" id="f_payed">本次缴费:</td>
			<td class="brightStyle" align="left">
				<input type="text" name="feeDetail.payed" style="width:150px;" value="${(feeDetail.payed)?default(0)?string("##0.00")}" TABINDEX="10">
			</td>
	    </tr>
		<tr>
			<td class="grayStyle" align="center" id="f_invoiceCode"><@bean.message key="field.feeDetail.invoiceNumber"/><font color="red">*</font>:</td>
			<td class="brightStyle" align="left">
				<input type="text" name="feeDetail.invoiceCode" maxlength="32" style="width:150px;" value="${(feeDetail.invoiceCode)?if_exists}" TABINDEX="11">
			</td>
			<td class="grayStyle" align="center" id="f_createAt">缴费日期<font color="red">*</font>:	</td>
			<td class="brightStyle" align="left">
				<input type="text" name="feeDetail.createAt" maxlength="10" style="width:80px" value="${(feeDetail.createAt?string("yyyy-MM-dd"))?if_exists}">
                <input type="button" value="日期" class="buttonStyle"  style="width:40px" onclick="calendar(this.form['feeDetail.createAt'])">
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center" id="f_remark"><@bean.message key="field.feeDetail.remark"/></td>
			<td class="brightStyle" align="left" colspan="3">
				<textarea name="feeDetail.remark" cols="50" TABINDEX="12">${(feeDetail.remark)?if_exists}</textarea>
			</td>
		</tr>
		<tr>
			<td class="darkColumn" colspan="4" align="center">				
                <input type="button" value="<@msg.message key="action.save"/>(S)" accesskey="S" name="button1" onClick="doAction(this.form);" class="buttonStyle"  TABINDEX="12"/>&nbsp;
				<input type="button" value="保存并添加下一个" name="button2" onClick="doAction(this.form,'&addAnother=1');" class="buttonStyle" TABINDEX="13"/>&nbsp;
                <input type="button" value="保存并添加该同学的下个收费" name="button3" onClick="doAction(this.form,'&addAnother=1&sameStd=1');" class="buttonStyle"  TABINDEX="14"/>&nbsp;                
			</td>
		</tr>
	</table>
	</form>
	<script src='dwr/interface/calendarDAO.js'></script>
    <script src='scripts/common/CalendarSelect.js'></script>
	<script language="JavaScript" type="text/JavaScript" src="scripts/common/onReturn.js"></script>
    <script language="JavaScript" type="text/JavaScript" src="scripts/feeDetail/feeDetail.js"></script>
	<script>
		var form = document.feeDetailForm;
	   form['feeDetail.std.code'].focus();
	   if(form['feeDetail.std.code'].value!=""){
	      getStd();
	   }
	   //设置cookie
	   function settingFeeOfDepart() {
	   		setCookie("feeDetail.depart.id", form["feeDetail.depart.id"].value);
	   		alert("设置成功！\n该默认值30天内有效。");
	   }
       function getPreferFeeOfDepart() {
            if(null!=getCookie("feeDetail.depart.id")){
               setSelected(form["feeDetail.depart.id"],getCookie("feeDetail.depart.id"));
	    	}
	   }
   	   getPreferFeeOfDepart();
	</script>
 </body>
 <#include "/templates/foot.ftl"/>