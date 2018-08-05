<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/tabpane.js"></script>
<script src='dwr/interface/teachPlanService.js'></script>

<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="planFormBar"></table>
<script>
 var bar= new ToolBar("planFormBar","修改培养计划",null,true,true);
 bar.setMessage('<@getMessage />');
 bar.addItem(" 刷新",'self.location.reload()','refresh.gif');
 bar.addItem("<@msg.message key="action.back"/>",'parent.searchTeachPlan()','backward.gif');
</script>

   <div class="dynamic-tab-pane-control tab-pane" id="tabPane1" >
     <script type="text/javascript">tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );</script>
    
     <div style="display: block;" class="tab-page" id="tabPage1">
      <h2 class="tab"><a href="#">基本信息</a></h2>
      <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage1" ) );</script>
      
     <table width="100%" align="left" class="formTable">
      <form action="teachPlan.do?method=save" name="planForm" method="post" onsubmit="return false;">
       <tr class="darkColumn">
         <td align="left" colspan="4"><B>培养计划基本信息</B></td>
       </tr>
       <#if teachPlan.std?exists>
	   <tr>
	     <td align="center"  class="grayStyle"><@bean.message key="std.code"/>:</td>
	     <td class="brightStyle">${teachPlan.std.code}</td>
	   	 <td align="center" class="grayStyle" ><@bean.message key="attr.personName"/>:</td>
	     <td class="brightStyle">${teachPlan.std.name}</td>
	   </tr>
	   </#if>
	   <tr>	   
	     <td align="center" id="f_enrollTurn" class="grayStyle"><@bean.message key="attr.enrollTurn"/><font color="red">*</font>:</td>
	     <td class="brightStyle">
	      <input type="hidden" name="teachPlan.id" value="${teachPlan.id?if_exists}"/>
	      <input type="text" maxlength="7" name="teachPlan.enrollTurn" value="${teachPlan.enrollTurn?if_exists}" size="7"/>
	      <input type="hidden" name="enrollTurn" value="${teachPlan.enrollTurn?if_exists}"/>
	     </td>
	   	 <td align="center" id="f_department" class="grayStyle"><@bean.message key="common.college"/><font color="red">*</font>:</td>
	     <td class="brightStyle">
	        <input type="hidden" name="department.id" value="${teachPlan.department.id}"/>
	        <select id="department" name="teachPlan.department.id" style="width:155px;">
	           <option value="${teachPlan.department.id}"><@bean.message key="common.selectPlease"/></option>
	        </select>
	     </td>
	   </tr>
	   <tr>
    	 <td align="center" id="f_studentType" class="grayStyle"><@bean.message key="entity.studentType"/><font color="red">*</font>:  </td>
         <td class="brightStyle">
            <input type="hidden" name="stdType.id" value="${teachPlan.stdType.id}"/>
            <select id="stdTypeOfSpeciality" name="teachPlan.stdType.id" style="width:100px;">
              <option value="${teachPlan.stdType.id}"></option>
            </select>
         </td>
         <td align="center" class="grayStyle"><@bean.message key="entity.speciality"/>:  </td>
	     <td align="left" id="f_speciality" class="brightStyle">
	        <input type="hidden" name="speciality.id" value="${teachPlan.speciality?if_exists.id?if_exists}"/>
	        <select id="speciality" name="teachPlan.speciality.id"  style="width:155px;">
	           <option value="${teachPlan.speciality?if_exists.id?if_exists}"><@bean.message key="common.selectPlease"/></option>
	        </select>
	     </td>
       </tr>
	   <tr>
         <td align="center" id="f_termsCount"  class="grayStyle">&nbsp;学期数<font color="red">*</font>:</td>
         <td class="brightStyle">${teachPlan.termsCount}</td>	   
	     <td align="center" class="grayStyle"><@bean.message key="entity.specialityAspect"/>&nbsp;&nbsp;:  </td>
	     <td align="left" id="f_specialityAspect" class="brightStyle">
   	        <input type="hidden" name="aspect.id" value="${teachPlan.aspect?if_exists.id?if_exists}"/>
	        <select id="specialityAspect" name="teachPlan.aspect.id"  style="width:200px;">        
	         <option value="${teachPlan.aspect?if_exists.id?if_exists}"><@bean.message key="common.selectPlease"/></option>
	        </select>
	     </td>
	   </tr>
	   <tr>
         <td align="center"  class="title" id="f_credit">总学分<font color="red">*</font>:</td>
         <td class="brightStyle">
            <input name="teachPlan.credit" id="teachPlan.credit" size=4 value="${teachPlan.credit?if_exists}" maxLength="3"/>
            <input type="button" class="buttonStyle" name="creditButton" value="重新统计总学分" onclick="statOverallCredit();">
         </td>
         <td align="center" class="grayStyle" id="f_creditHour">总学时<font color="red">*</font>:</td>
         <td class="brightStyle">
            <input name="teachPlan.creditHour" id="teachPlan.creditHour" size=4 value="${teachPlan.creditHour?if_exists}" maxLength="5"/>
            <input type="button" class="buttonStyle" name="creditButton" value="重新统计总学时" onclick="statOverallCreditHour();">
         </td>
       </tr>
	   <tr>
         <td align="center" class="grayStyle"  id="f_teacherNames">指导老师:</td>
         <td colspan="3" class="brightStyle">
              <textarea name="teachPlan.teacherNames" cols="40" rows="2" style="width:500px;height:40px;overflow-y:auto"/>${teachPlan.teacherNames?if_exists}</textarea>
         </td>
	   </tr>
	   <tr>
         <td align="center" class="grayStyle" id="f_remark"><@bean.message key="attr.remark"/>:</td>
         <td colspan="3" class="brightStyle">
             <textarea name="teachPlan.remark" cols="40" rows="2" style="width:500px;height:40px;overflow-y:auto"/>${teachPlan.remark?if_exists}</textarea>
         </td>
	   </tr>
	   <tr align="center"  class="darkColumn">
	     <td colspan="4">
           <input type="button" onClick='save(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
           <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
	     </td>
	   </tr>   
     </table>
  </form>
  </div>

  <div style="display: block;" class="tab-page" id="tabPage2">
    <h2 class="tab"><a href="#">课程分类</a></h2>
      <script type="text/javascript">tp1.addTabPage(document.getElementById("tabPage2"));</script>
      <#include "courseGroupList.ftl"/>
   </div>
     
  </div>
  
  <script language="JavaScript" type="text/JavaScript" src="scripts/course/TeachPlan.js"></script> 
  <script language="javascript">
     function save(form){
       collision=0;
       var a_fields = {
         'teachPlan.credit':{'l':'总学分','r':true,'t':'f_credit','f':'unsignedReal'},
         'teachPlan.creditHour':{'l':'总学时','r':true,'t':'f_creditHour','f':'unsigned'},
	     'teachPlan.enrollTurn':{'l':'所在年级','r':true, 't':'f_enrollTurn','f':'yearMonth'},
     	 'teachPlan.teacherNames':{'l':'指导老师', 'r':false, 't':'f_teacherNames', 'mx':100},
     	 'teachPlan.remark':{'l':'备注', 'r':false, 't':'f_remark', 'mx':100}
       };
       var v = new validator(form , a_fields, null);
       if (!v.exec())return;
       if((form['teachPlan.stdType.id'].value!=form['stdType.id'].value)
           ||(form['teachPlan.speciality.id'].value!=form['speciality.id'].value)
           ||(form['teachPlan.aspect.id'].value!=form['aspect.id'].value)
           ||(form['teachPlan.enrollTurn'].value!=form['enrollTurn'].value)){
          DWREngine.setAsync(false);
          checkExist(form);
          DWREngine.setAsync(true);
       }
       
       if(collision)return;
       else {
          form.submit();
       }
     }
     function statOverallCredit(){
         teachPlanService.statOverallCredit(setCredit,${teachPlan.id});
     }
     function setCredit(data){
        document.getElementById("teachPlan.credit").value=data;
     }
     function statOverallCreditHour(){
         teachPlanService.statOverallCreditHour(setCreditHour,${teachPlan.id});
     }
     function setCreditHour(data){
        document.getElementById("teachPlan.creditHour").value=data;
     }
     function hookFun(){
         f_frameStyleResize(self);
     }
 </script>
</body>
<#include "/templates/stdTypeDepart3Select.ftl"/>
