<table id="searchTable" class="searchForm">
<form name="feeDetailForm" method="post" action="" onsubmit="return false;">
	<tr>
		<td class="grayStyle">学年度学期</td>
		<td><@bean.message key="entity.studentType"/></td>
		<td>
			<select id="stdType" name="feeDetail.calendar.studentType.id" style="width:100%">
				<option value=""><@bean.message key="common.all"/></option>
			</select>
		</td>
		<td><@bean.message key="attr.year2year"/></td>
		<td>
			<select id="year" name="feeDetail.calendar.year" style="width:100%">
				<option value=""><@bean.message key="common.all"/></option>
			</select>
		</td>
		<td><@bean.message key="attr.term"/></td>
		<td colspan="3">
			<select id="term" name="feeDetail.calendar.term" style="width:100%">
				<option value=""><@bean.message key="common.all"/></option>
			</select>
		</td>
	</tr>
	</tr>
		<td class="grayStyle"><@msg.message key="entity.student"/></td>
		<td><@msg.message key="attr.stdNo"/></td>
		<td><input type="text" name="feeDetail.std.code" maxlength="32" style="width:100%" tabindex="1"/></td>
		<td><@bean.message key="field.feeDetail.studentName"/></td>
		<td><input type="text" name="feeDetail.std.name" maxlength="20" style="width:100%"/></td>
		<td>发票</td>
		<td colspan="3"><input type="text" name="feeDetail.invoiceCode" maxlength="32" style="width:100%"/></td>
	</tr>
   	<tr> 
     	<td class="grayStyle"><@bean.message key="research.harvest.type"/></td>
     	<td><@bean.message key="entity.studentType"/></td>
     	<td>
          	<select id="std_stdTypeOfSpeciality" name="feeDetail.std.type.id" style="width:100%">
            	<option value=""><@bean.message key="filed.choose"/></option>
          	</select>
        </td>
        <td><@bean.message key="common.college"/></td>
        <td>
	       	<select id="std_department" name="feeDetail.std.department.id" style="width:100%">
	     	  	<option value=""><@bean.message key="filed.choose"/>...</option>
	       	</select>
	    </td>
     	<td><@bean.message key="entity.speciality"/></td>
     	<td>
	       	<select id="std_speciality" name="feeDetail.std.firstMajor.id" style="width:100%">
	     	  	<option value=""><@bean.message key="filed.choose"/>...</option>
	       	</select>
	    </td>
     	<td><@bean.message key="entity.specialityAspect"/></td>
     	<td>
	       	<select id="std_specialityAspect" name="feeDetail.std.firstAspect.id" style="width:100%">
	     	  	<option value=""><@bean.message key="filed.choose"/>...</option>
	       	</select>
     	</td>
    </tr>  
    <tr>
    	<td class="grayStyle"><@msg.message key="common.adminClass"/></td>
    	<td colspan="2"><input type="text" name="className" maxlength="20" value="${className?default("")}" style="width:100%"/></td>
    	<td class="blankRightSpace"><button onClick="search()" style="width: 60%"><@bean.message key="action.query"/></button></td>
    	<td colspan="5" class="blankLeftSpace"></td>
    </tr>
	<tr>
		<td class="grayStyle"><@bean.message key="field.feeDetail.addFeeCondition.sortCondition"/></td>
		<td colspan="8" class="checkBoxTdStyle">
		    <input type="checkBox" name="byWhat" value="feeDetail.calendar.year desc,feeDetail.calendar.term desc" checked>学年度,学期 &nbsp;&nbsp;&nbsp;
		    <input type="checkBox" name="byWhat" value="feeDetail.type.name" checked>收费类型 &nbsp;&nbsp;&nbsp;
		    <input type="checkBox" name="byWhat" value="feeDetail.createAt" checked>收费日期 &nbsp;&nbsp;&nbsp;
			<input type="checkBox" name="byWhat" value="feeDetail.std.code"><@msg.message key="attr.stdNo"/> &nbsp;&nbsp;&nbsp;
			<input type="checkBox" name="byWhat" value="feeDetail.invoiceCode"><@bean.message key="field.feeDetail.addFeeCondition.byInvoiceCode"/> 
		</td>
	</tr>
	<#assign stdTypeNullable=true>
	<#assign yearNullable=true>
	<#assign termNullable=true>
	<#include "/templates/calendarSelect.ftl"/>
</form>
</table>
