  <table align="center" class="listTable" width="100%">
    <tr class="darkColumn">
        <td width="50px"></td>
     	<#list unitList?sort_by("id") as unit>
		<td>
		 <@i18nName unit/>
		</td>
		</#list>
    </tr>
	<#list weekList as week>
	<tr>
	    <td class="darkColumn"><@i18nName week/></td>
  	    <#list 0..unitList?size-1 as unit>
  	    	<#assign unitIndex=week_index*unitList?size + unit_index/>
   		<td id="${unitIndex}" <#if availTime.available[unitIndex..unitIndex]=="1"> style="backGround-Color:#94aef3" <#else>style="backGround-Color:yellow"</#if> onClick="javascript:changeAvailTime(this)" >
   		<input type="hidden" id="unit${unitIndex}" value="${availTime.available[unitIndex..unitIndex]}"/>
   		</td>
		</#list>
	</tr>
    </#list>
</table>
<table align="center" width="100%"border="0">
	<tr class="infoTitle">
	 <td ><@bean.message key="info.availTime.legend"/></td>
	 <td style="backGround-Color:#94aef3" width="60px"><@bean.message key="info.avaliTime.available"/></td>
	 <td style="backGround-Color:yellow" width="60px"><@bean.message key="info.avaliTime.unavailable"/></td>
	 <td align="right">
	     一次选择的节次：
	     <input type="radio" name="selectUnitNum" value='1'>单节
	     <input type="radio" name="selectUnitNum" value='2'>双节
	     <input type="radio" name="selectUnitNum" value='3'>三节
	     <input type="radio" name="selectUnitNum" value='4' checked>四节</td>
	 </td>
	</tr>
</table>
<table align="center" width="100%" class="formTable">
	<tr>
	 <td class="grayStyle">文字说明(最多50字):</td>
    </tr>
    <tr>
	 <td><textarea name="availTime.remark" id="availTime_remark" cols="70" rows="4">${availTime.remark?default("")}</textarea></td>
	</tr>
</table>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/availTime.js"></script>
<script>
   unitsPerDay=${unitList?size};
</script>