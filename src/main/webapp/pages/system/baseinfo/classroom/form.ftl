<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url"/>"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/ieemu.js"></script>
<body>
<#assign labInfo><@msg.message key="page.classroomForm.label"/></#assign>
<#include "/templates/back.ftl"/>
 <table width="90%" align="center" class="formTable">
  <form action="classroom.do?method=edit" name="classroomForm" method="post" onsubmit="return false;">
  <@searchParams/>
   <tr class="darkColumn">
     <td align="left" colspan="4"><B><@msg.message key="page.classroomForm.label"/></B></td>
   </tr>
   <tr>
     <td id="f_code" class="title"><font color="red">*</font><@msg.message key="attr.code"/>:</td>
     <td>
      <input id="codeValue" type="text" name="classroom.code" value="${classroom.code?if_exists}" maxLength="32" style="width:80px;"/>
      <#assign className="Classroom">
  	  <#include "/pages/system/checkCode.ftl"/>
      <input type="hidden" name="classroom.id" value="${classroom.id?if_exists}"/>
     </td>
     <td rowspan="5" id="f_departs" class="title"><font color="red">*</font><@msg.message key="common.departOfUsing"/>:</td>
     <td rowspan="5" bgcolor="#ffffff">
      <table height="80%" border="0" class="formTable">
      <tr>
      <td><@msg.message key="common.allDepartments"/></td><td>-</td><td><@msg.message key="common.departOfUsing"/>：</td>
      </tr>
      <tr>
       <td>
        <select name="ownDeparts" MULTIPLE size="8" style="width:100px;" onDblClick="JavaScript:moveSelectedOption(this.form['ownDeparts'], this.form['useDeparts'])">
         <#list roomDepartList?if_exists?sort_by("code") as depart>
          <option value="${depart.id}"><@i18nName depart/></option>
         </#list>
        </select>
       </td>
       <td valign="middle">
        <br><br>
        <input OnClick="JavaScript:moveSelectedOption(this.form['ownDeparts'], this.form['useDeparts'])" type="button" value="&gt;"/>
        <br><br>
        <input OnClick="JavaScript:moveSelectedOption(this.form['useDeparts'], this.form['ownDeparts'])" type="button" value="&lt;"/>
        <br>
       </td>
       <td class="normalTextStyle">
        <select name="useDeparts" MULTIPLE size="8" style="width:100px;" onDblClick="JavaScript:moveSelectedOption(this.form['useDeparts'], this.form['ownDeparts'])">
         <#list classroom.departments?sort_by("code") as depart>
          <option value="${depart.id}"><@i18nName depart/></option>
         </#list>
        </select>
       </td>
      </tr>
     </table>
    </td>
   </tr>
   <tr>
     <td id="f_name" class="title"><font color="red">*</font><@msg.message key="attr.infoname"/>:</td>
     <td><input type="text" name="classroom.name" value="${classroom.name?if_exists}" maxLength="20"/></td>
   </tr>
   <tr>
     <td id="f_engName" class="title"><@msg.message key="attr.engName"/>:</td>
     <td><input type="text" name="classroom.engName" value="${classroom.engName?if_exists}" maxLength="100"/> </td>
   </tr>
   <tr>
   	  <td id="f_district" class="title"><font color="red">*</font><@msg.message key="entity.schoolDistrict"/>&nbsp;&nbsp;:</td>
      <td>
   	     <select id="district" name="classroom.schoolDistrict.id" style="width:155px;">
           <option value="${(classroom.schoolDistrict.id)?if_exists}"></option>
         </select>
     </td>
   </tr>
   <tr>
      <td id="f_building" class="title"><@msg.message key="entity.building"/>&nbsp;&nbsp;:</td>
   	  <td>
       <select id="building" name="classroom.building.id" style="width:155px;">
           <option value="${(classroom.building.id)?if_exists}"></option>
       </select>
   	  </td>
   </tr>
   <tr>
     <td class="title"><font color="red">*</font><@msg.message key="entity.classroomConfigType"/>:</td>
     <td>
        <select name="classroom.configType.id" style="width:155px;">
        <#list  classroomConfigTypeList as configType>
        <option value="${configType.id}" <#if (classroom.configType.id)?exists><#if configType.id == classroom.configType.id>selected </#if></#if>><@i18nName configType/></option>
        </#list>
        </select>
     </td>
 	 <td id="f_floor" class="title"><@msg.message key="attr.floor"/>:</td>
     <td><input type="text" name="classroom.floor" value="${classroom.floor?if_exists}" maxLength="3"/></td>
   </tr>
   <tr>
     <td id="f_capacityOfExam" class="title"><font color="red">*</font><@msg.message key="attr.capacityOfExam"/>:</td>
     <td><input type="text" name="classroom.capacityOfExam" value="${classroom.capacityOfExam?if_exists}" maxLength="3"/></td>
   	 <td id="f_capacityOfCourse" class="title"><font color="red">*</font><@msg.message key="attr.capacityOfCourse"/>:</td>
     <td><input type="text" name="classroom.capacityOfCourse" value="${classroom.capacityOfCourse?if_exists}" maxLength="3"/></td>
   </tr>
   <tr>
     <td class="title">使用状态:</td>
     <td><@htm.radio2  name="classroom.state" value=classroom.state?default(true)/></td>
     <td class="title" id="f_capacity"><font color="red">*</font>容量:</td>
     <td><input type="text" name="classroom.capacity" value="${(classroom.capacity)?default(0)}" onblur="defaultCapacity(this, event)" maxlength="5"/></td>
   </tr>
  <tr>
     <td class="title">是否排课检查:</td>
     <td><@htm.radio2  name="classroom.isCheckActivity" value=classroom.isCheckActivity?default(true)/></td>
     <td rowspan="3" id="f_remark" class="title"><@msg.message key="common.remark"/>:</td>
     <td rowspan="3">
       <textarea name="classroom.remark" rows="3" cols="20">${classroom.remark?if_exists}</textarea>
     </td>
  </tr>
  <tr>
     <td class="title"><@msg.message key="attr.createAt"/>:</td>
     <td>${(classroom.createAt?string("yyyy-MM-dd"))?if_exists}</td>
  </tr>
  <tr>
     <td class="title"><@msg.message key="attr.modifyAt"/>:</td>
     <td>${(classroom.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
   </tr>
   <tr class="darkColumn" align="center">
     <td colspan="4">
       <input type="hidden" name="useDepartIds" value=""/>
       <input type="button" onClick='save(this.form)' value="<@msg.message key="system.button.submit"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
       <input type="button" onClick='save(this.form,"&addAnother=1")' value="保存并增加下一个" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
       <input type="button" onClick='reset()' value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
     </td>
   </tr>
   </form>
 </table>
  <script language="javascript"> 
     function reset(){
		document.classroomForm.reset();
     }
     function save(form,params){
      form.useDepartIds.value = getAllOptionValue(form.useDeparts);     
      if(form.useDepartIds.value =="") {
	      alert("<@msg.message key="error.usingDepart.setting"/>");
	      return;
      }
      var schoolId = $('district').value;
      var buildingId = $('building').value;
       var a_fields = {
         'classroom.name':{'l':'<@msg.message key="attr.infoname"/>', 'r':true, 't':'f_name'},
         'classroom.capacityOfExam':{'l':'<@msg.message key="attr.capacityOfExam"/>','r':true,'t':'f_capacityOfExam','f':'unsigned','mx':3},
         'classroom.capacityOfCourse':{'l':'<@msg.message key="attr.capacityOfCourse"/>','r':true,'t':'f_capacityOfCourse','f':'unsigned','mx':4},
         'classroom.floor':{'l':'<@msg.message key="attr.floor"/>','r':false,'t':'f_floor','f':'unsigned','mx':3},
         'classroom.capacity':{'l':'容量','r':true,'t':'f_capacity', 'f':'unsigned'},
         'classroom.remark':{'l':'<@msg.message key="common.remark"/>','r':false,'t':'f_remark','mx':100}
       };
       
       	var v = new validator(form , a_fields, null);
       	if (v.exec()) {
	       	if (null == schoolId || "" == schoolId) {
	       		alert("校区没有选择，或没有校区。");
	       		return;
		    }
	      	addInput(form, 'classroom.schoolDistrict.id', schoolId);
       		form['classroom.code'].value = cleanSpaces(form['classroom.code'].value);
         	form.action = "classroom.do?method=save";
         	if (null != params) {
             	form.action += params;
			}
         	form.submit();
     	}
   }
   
   function defaultCapacity(input, e) {
   	if (null == input.value || "" == input.value) {
   		input.value = "${(classroom.capacity)?default(0)}";
   	}
   }
 </script>
</body>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "/templates/districtBuildingSelect.ftl"/> 
<#include "/templates/foot.ftl"/>