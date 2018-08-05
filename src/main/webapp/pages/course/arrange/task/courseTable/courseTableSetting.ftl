<#assign keyLine>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;" >
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
     </td>
   </tr>
</#assign>
<div id="reportSetting" style="display:none;width:500px;height:200px;position:absolute;top:28px;right:0px;border:solid;border-width:1px;background-color:white ">
 <form name="actionForm" method="post" target="_blank" onsubmit="return false;">
 <input type="hidden" name="calendar.id" value="${calendar.id}"/>
 <input type="hidden" name="activity.calendar.id" value="${calendar.id}"/>
 <input type="hidden" name="setting.orderBy" value="${RequestParameters['orderBy']?default('null')}"/>
 <input type="hidden" name="setting.kind" value="${courseTableType}"/>
 <input type="hidden" name="setting.ignoreTask" value="1"/>
 <input type="hidden" name="classroomIds" value=""/>
 <#list RequestParameters?keys as key>
    <#if (key?index_of("classroom.") >= 0)>
 <input type="hidden" name="activity.${key?replace("classroom.", "room.")}" value="${RequestParameters[key]}"/>
    </#if>
 </#list>
 <table class="settingTable">
   <tr>
	   <td>&nbsp;&nbsp;<@msg.message key="course.numberCurriculumsOfEachPage"/>：</td>
	   <td style="width:50px"><select name="setting.tablePerPage" style="width:100px"><#list 1..10 as i><option value="${i}">${i}</option></#list></select></td>
   </tr>
   ${keyLine}
   <tr>
	   <td>&nbsp;&nbsp;<@msg.message key="common.foneSize"/>：</td>
	   <td><input type="text" name="setting.fontSize" value="12" style="width:40px" maxlength="3"><@msg.message key="common.px"/></td>
   </tr>
   ${keyLine}
   <tr>
	   <td>&nbsp;&nbsp;<@msg.message key="course.limitedCurrentStdType"/>：</td>
	   <#if courseTableType='std' || courseTableType="teacher">
	   <td><@htm.radio2 name="setting.forCalendar" value=false/></td>
	   <#else>
	   <td><@htm.radio2 name="setting.forCalendar" value=true/></td>
	   </#if>
   </tr>
   ${keyLine}
   <tr>
	   <td>&nbsp;&nbsp;<@msg.message key="course.printStyleOfCurriculums"/>：</td>
	   <td>
	      <select name="setting.style" style="width:150px">
	         <option value="vertical"><@msg.message key="courseTable.separateVerticalStyle"/></option>
	         <option value="horizontal"><@msg.message key="courseTable.separateHorizontalStyle"/></option>
	         <option value="single"><@msg.message key="courseTable.combinedStyle"/></option>
	   </td>
   </tr>
   ${keyLine}
   <tr>
    <td colspan="2">&nbsp;&nbsp;<@msg.message key="grade.stdGradeReport.tip"/></td>
   </tr>
   <tr align="center">
      <td colspan="2"><button onclick="printCourseTable();closeSetting()" class="buttonStyle" accesskey="P"><@msg.message key="action.preview"/>(<U>P</U>)</button>
         &nbsp;<button accesskey="c" class="buttonStyle" onclick="closeSetting();"><@msg.message key="action.close"/>(<U>C</U>)</button></td>
   </tr>
   </form>
 </table>
  <script>
    var form = document.actionForm;
    function displaySetting(){
       if(reportSetting.style.display=="block"){
           reportSetting.style.display='none';
       }else{
	       reportSetting.style.display="block";
	       f_frameStyleResize(self);
       }
    }
    function closeSetting(){
       reportSetting.style.display='none';
    }
    var idMapping= new Object();
    idMapping['class']="adminClassId";
    idMapping['std']="stdId";
    idMapping['room']="classroomId";
    idMapping['teacher']="teacherId";
	function printCourseTable(){
	   form.action="?method=courseTable";
	   if(!/^\d+$/.test(form['setting.fontSize'].value)){alert("<@msg.message key="common.font.shouldBeInteger"/>");return;}
	   var ids = getSelectIds(idMapping["${courseTableType}"]);
	   if(""==ids){alert("<@msg.message key="common.print.oneOrMore"/>");return;}
       addInput(form,"ids",ids);
       if(form['setting.tablePerPage'].value=="1"){
          form['setting.ignoreTask'].value="0";
       }
       form.submit();
	}
</script>
</div>