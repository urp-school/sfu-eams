<#include "/templates/head.ftl"/>
<script src='dwr/interface/teacherDAO.js'></script>
<script src='scripts/departTeacher2Select.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script language="javascript">
 function addTeacher(){
       var teacher = document.getElementById('teacher');
       if(teacher.value!=""){
           if(document.teachProductForm['teacherIds'].value.indexOf(teacher.value)==-1){
              document.teachProductForm['teacherIds'].value+=teacher.value +",";
              if(document.teachProductForm['teachProduct.cooperateOfTeacher'].value==""){
                 document.teachProductForm['teachProduct.cooperateOfTeacher'].value=DWRUtil.getText('teacher');
              }else{
                 document.teachProductForm['teachProduct.cooperateOfTeacher'].value+=","+DWRUtil.getText('teacher');
              }
           }
       }
   }
</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<#assign labInfo><@msg.message key="field.teachProduct.modifyTeachProduct"/></#assign>
   <#include "/templates/back.ftl">
	<table width="100%"  class="formTable">
		<form name="teachProductForm" method="post" action="" onsubmit="return false;">
		<tr><td align="center" class="darkColumn" colspan="4">教学成果信息</td></tr>
		<tr>
			<td id="f_departmentId"class="title"><@msg.message key="field.teachProduct.selectCollege"/><font color="red">*</font>:</td>
			<td>
			 <@htm.i18nSelect name="teachProduct.department.id" datas=departmentList selected=(teachProduct.department.id)?default("")?string style="width:150px;">
				<option value="">...</option>
   		     </@>
			</td>
			<td id="f_productName" class="title"><@msg.message key="field.teachProduct.productName"/><font color="red">*</font>:</td>
			<td><input name="teachProduct.productName" maxlength="100" value="${teachProduct.productName?if_exists}"/></td>
		</tr>
		<tr>
			<td id="f_teachers" class="title"><@msg.message key="field.teachProduct.teachers"/><font color="red">*</font>:</td>
			<td colspan="3">
				<input type="hidden" name="teacherIds" value="<#list teachProduct.teacherRank?sort_by("rank") as teacherRank>${teacherRank.teacher.id?if_exists}<#if teacherRank_has_next>,</#if></#list>"/>
	       		<textarea name="teachProduct.cooperateOfTeacher" rows="2" cols="300" style="width:300px;" readOnly="true"><#list teachProduct.teacherRank?sort_by("rank") as teacherRank>${teacherRank.teacher.name?if_exists}<#if teacherRank_has_next>,</#if></#list></textarea><br>
	       		<select id="teachDepartment" style="width:150px">
   	         		<option value="${teacherDepart?if_exists.id?if_exists}"></option>
	       		</select>
   	       		<select id="teacher" name="teacherId" style="width:150px">
   	         		<option value="${teacher?if_exists.id?if_exists}"></option>
	       		</select>
               <input type="button"  class="buttonStyle" value="<@msg.message key="action.add"/>" onClick="addTeacher();"/>
   	       	   <input type="button"  class="buttonStyle" value="<@msg.message key="action.clear"/>" onClick="this.form['teacherIds'].value='';this.form['teachProduct.cooperateOfTeacher'].value='';"/> 
			</td>
		</tr>
		<tr>
			<td  class="title" id="f_awardName">获奖名称<font color="red">*</font>:</td>
			<td><input name="teachProduct.awardName" value="${teachProduct.awardName?if_exists}"/></td>
			<td  class="title" id="f_giveAwardPlace">颁奖机构名称:</td>
			<td><input name="teachProduct.giveAwardPlace" style="width:150px;" value="${teachProduct.giveAwardPlace?if_exists}"/></td>
		</tr>
		<tr>
			<td id="f_productionType" class="title"><@msg.message key="field.teachProduct.selectProductType"/><font color="red">*</font>:</td>
			<td>
			    <@htm.i18nSelect datas=productionTypes selected=(teachProduct.productionType.id)?default("")?string name="teachProduct.productionType.id" style="width:150px;">
					<option value=""><@msg.message key="field.teachProduct.selectProductType"/></option>
				</@>
			</td>
			<td id="f_productionAwardType" class="title">
				<@msg.message key="field.teachProduct.selectProductionAwardType"/><font color="red">*</font>:
			</td>
			<td>
				<@htm.i18nSelect datas=productionAwardTypes selected=(teachProduct.productionAwardType.id)?default("")?string name="teachProduct.productionAwardType.id" style="width:150px;">
					<option value=""><@msg.message key="field.teachProduct.selectProductionAwardType"/></option>
				</@>
			</td>
	   </tr>
	   <tr>
			<td id="f_productionAwardlevel" class="title">
				<@msg.message key="field.teachProduct.selectProductionAwardLevel"/><font color="red">*</font>:
			</td>
			<td>
				<@htm.i18nSelect datas=productionAwardLevels selected =(teachProduct.productionAwardLevel.id)?default("")?string name="teachProduct.productionAwardLevel.id" style="width:150px;">
					<option value=""><@msg.message key="field.teachProduct.selectProductionAwardLevel"/></option>
				</@>
			</td>
			<td id="f_time" class="title">
				<@msg.message key="field.teachProduct.awardTime"/><font color="red">*</font>:
			</td>
			<td>
				<input type="text" name="teachProduct.awardTime" maxlength="10" onfocus="calendar()" style="width:150px;" value="<#if teachProduct.awardName?exists>${teachProduct.awardTime?string("yyyy-MM-dd")}</#if>">
			</td>
		</tr>
		<tr>
			<td id="f_remark" class="title"><@msg.message key="field.teachProduct.remark"/></td>
			<td colspan="3">
				<textarea name="teachProduct.remark" rows="2" cols="50">${teachProduct.remark?if_exists}</textarea>
			</td>
		</tr>
		<tr class="darkColumn">
			<td colspan="4" align="center">
			<input type="hidden" name="teachProduct.id" value="${teachProduct.id?default("")}">
	       <input type="button" value="<@msg.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset"  name="reset1" value="<@msg.message key="system.button.reset"/>" class="buttonStyle" />
	     </td>
		</tr>
	 </form>
	</table>
   <script language="javascript" >
   function doAction(form){
     var a_fields = {
          'teachProduct.cooperateOfTeacher':{'l':'<@msg.message key="field.teachProduct.teachers"/>', 'r':true, 't':'f_teachers','mx':100},
          'teachProduct.productName':{'l':'<@msg.message key="field.teachProduct.productName"/>', 'r':true, 't':'f_productName','mx':100},
          'teachProduct.department.id':{'l':'<@msg.message key="field.teachProduct.selectCollege"/>', 'r':true, 't':'f_departmentId'},
          'teachProduct.productionType.id':{'l':'<@msg.message key="field.teachProduct.selectProductType"/>', 'r':true, 't':'f_productionType'},
          'teachProduct.productionAwardType.id':{'l':'<@msg.message key="field.teachProduct.selectProductionAwardType"/>', 'r':true, 't':'f_productionAwardType'},
          'teachProduct.productionAwardLevel.id':{'l':'<@msg.message key="field.teachProduct.selectProductionAwardLevel"/>', 'r':true, 't':'f_productionAwardlevel'},
          'teachProduct.awardTime':{'l':'<@msg.message key="field.teachProduct.awardTime"/>', 'r':true, 't':'f_time'},
          'teachProduct.remark':{'l':'<@msg.message key="field.teachProduct.remark"/>', 't':'f_remark','mx':200}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.action="teachProduct.do?method=save";
        form.submit();
     }
   }
   
    <#if result?if_exists.departmentList?exists>
    <#assign departmentList=result.departmentList>
    </#if>
    var departArray = new Array();
    <#list departmentList?sort_by("name")  as depart>
    departArray[departArray.length]={'id':'${depart.id?if_exists}','name':'<@i18nName depart/>'};
    </#list>
    var t1=new DepartTeacher2Select('teachDepartment','teacher',false, true, 0);
    t1.initTeachDepartSelect(departArray);
    t1.initTeacherSelect();
  </script>
</body>
<#include "/templates/foot.ftl"/>