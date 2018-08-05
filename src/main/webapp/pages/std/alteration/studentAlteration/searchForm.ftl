 <table width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@msg.message key="baseinfo.searchStudent"/></B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
	      </td>
	   </tr>	
  </table>
  <table width="100%" class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
    <input type="hidden" name="pageNo" value="1"/>
    <input type="hidden" name="std_state" value="1"/>
    	<tr>
	     <td class="infoTitle" width="40%"><@bean.message key="attr.stdNo"/>:</td>
	     <td>
	      <input type="text" name="std.code" size="10" value="${RequestParameters["std.code"]?if_exists}" maxlength="32" style="width:100px"/>
	     </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@msg.message key="attr.personName"/>:</td>
	     <td>
	      <input type="text" name="std.name" size="10" value="${RequestParameters["std.name"]?if_exists}" maxlength="20" style="width:100px"/>
	     </td>
		</tr>
	   <tr>
	     <td class="infoTitle">所在年级:</td>
	     <td><input type="text" name="std.enrollYear" id="std.enrollYear" style="width:100px" maxlength="7" value="${RequestParameters['std.enrollYear']?if_exists}"/></td>
	   </tr>
       <tr> 
	     <td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
	     <td>
	          <select id="std_stdTypeOfSpeciality" name="std.type.id" style="width:100px;">
	            <option value=""><@bean.message key="filed.choose"/></option>
	          </select>	 
         </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@bean.message key="common.college"/>:</td>
	     <td>
           <select id="std_department" name="std.department.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
	   <tr>
	     <td class="infoTitle"><@bean.message key="entity.speciality"/>:</td>
	     <td>
           <select id="std_speciality" name="std.firstMajor.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
	   <tr>
	     <td class="infoTitle"><@bean.message key="entity.specialityAspect"/>:</td>
	     <td>
           <select id="std_specialityAspect" name="std.firstAspect.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>         
        </tr>
    	<tr>
			<td class="title" id="f_alterationType">异动种类：</td>
			<td>
				<@htm.i18nSelect id="f_mode" datas=modes name="alteration.mode.id" style="width:100px;" selected=(alteration.mode.id)?default("")?string>
				  <option value=""><@msg.message key="common.all"/></option>
				</@>
			</td>
        </tr>
        <tr>
        	<td colspan="2">
				<fieldSet align=left style="width:150px">
			 		<legend style="font-weight:bold;font-size:12px">变动日期</legend>
                	<table width="100%" id ="menuTable" style="font-size:10pt">
	                    <tr>
	                        <td>从：</td>
	                        <td><input type="text" name="alterFromDate" onfocus="calendar()" maxlength="10" style="width:100px"/></td>
	                    </tr>
	                    <tr>
	                        <td>到：</td>
	                        <td><input type="text" name="alterToDate" onfocus="calendar()" maxlength="10" style="width:100px"/></td>
	                    </tr>
	            	</table>
	 			</fieldSet>
        	</td>
        </tr>
    	<tr>
	    <tr align="center" height="50px">
	     <td colspan="2">
		     <button style="width:60px" class="buttonStyle" onClick="search(1)"><@bean.message key="action.query"/></button>
		     <button style="width:60px" class="buttonStyle" onClick="stat()">统计</button>
	     </td>
	    </tr>
  </table>
<script>
    var sds = new StdTypeDepart3Select("std_stdTypeOfSpeciality","std_department","std_speciality","std_specialityAspect",true,true,true,true);
    sds.init(stdTypeArray,departArray);
    sds.firstSpeciality=1;
    function changeSpecialityType(event){
       var select = getEventTarget(event);
       sds.firstSpeciality=select.value;
       fireChange($("std_department"));
    }
</script> 
