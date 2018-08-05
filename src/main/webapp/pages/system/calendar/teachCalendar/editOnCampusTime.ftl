<#include "/templates/head.ftl"/>
<body >
<#assign labInfo>学生入学日历详细信息</#assign>
<#include "/templates/back.ftl"/>
 <table width="90%"  class="formTable" align="center">
   <form name="onCampusTimeForm" action="calendar.do?method=saveOnCampusTime" method="post" onsubmit="return false;">
   <tr class="darkColumn">
     <td  colspan="4">学生入学日历详细信息</td>
   </tr>
   <tr>
         <input type="hidden" value="${onCampusTime.stdType.id}" name="onCampusTime.stdType.id"/>
         <input type="hidden" value="${RequestParameters['studentType.id']}" name="studentType.id"/>
         <input type="hidden" value="${onCampusTime.stdType.name}" name="onCampusTime.stdType.name"/>
         <input type="hidden" value="${onCampusTime.id?if_exists}" name="onCampusTime.id"/>
 	     <td class="title" width="25%">&nbsp;<@msg.message key="entity.studentType"/>：</td>  
 	     <td>${onCampusTime.stdType.name}</td> 	     
 	     <td class="title" width="25%">&nbsp;所在年级：</td>  
 	     <td>
 	     <#if onCampusTime.id?exists&&onCampusTime.id!=0>
 	     <input name="onCampusTime.enrollTurn" value="${onCampusTime.enrollTurn}" type="hidden"/>
 	     ${onCampusTime.enrollTurn}
 	     <#else>
 	     <input name="onCampusTime.enrollTurn" value="" type="text" maxlength="7"/> 	     
 	     </#if>
 	     </td>
   </tr>
   <tr>
 	     <td class="title" width="25%" id="f_name">&nbsp;入学学期：</td> 	     
 	     <td  colspan="3">
 	         <select id="stdType" style="width:120px" name="onCampusTime.enrollCalendar.studentType.id">
 	             <option value="${onCampusTime.enrollCalendar?if_exists.id?if_exists}" ></option>
 	         </select>
 	         <select id="year" style="width:100px" name="onCampusTime.enrollCalendar.year">
 	             <option value="${onCampusTime.enrollCalendar?if_exists.year?if_exists}" ></option>
 	         </select>
 	         <select id="term" style="width:80px" name="onCampusTime.enrollCalendar.term">
 	             <option value="${onCampusTime.enrollCalendar?if_exists.term?if_exists}" ></option>
 	         </select>
 	     </td>
 </tr>
 <tr>
 	     <td class="title" width="25%" id="f_name">&nbsp;毕业学期：</td> 	     
 	     <td  colspan="3">
 	         <select id="stdType1" style="width:120px" name="onCampusTime.graduateCalendar.studentType.id">
 	             <option value="${onCampusTime.graduateCalendar?if_exists.id?if_exists}" ></option>
 	         </select>
 	         <select id="year1" style="width:100px" name="onCampusTime.graduateCalendar.year">
 	             <option value="${onCampusTime.graduateCalendar?if_exists.year?if_exists}" ></option>
 	         </select>
 	         <select id="term1" style="width:80px"  name="onCampusTime.graduateCalendar.term">
 	             <option value="${onCampusTime.graduateCalendar?if_exists.term?if_exists}" ></option>
 	         </select>
 	     </td>
   </tr>
   <tr class="darkColumn" align="center">
     <td colspan="6"  >
       <input type="button" value="提交" name="saveButton" onClick="save(this.form)" class="buttonStyle" />&nbsp;
       <input type="reset"  name="resetButton" value="重填" class="buttonStyle" />
     </td>
   </tr>
    </form>
 </table>

<script>
	function save(form){
	    var errors = "";
	    var enrollStdType = form['onCampusTime.enrollCalendar.studentType.id'].options[form['onCampusTime.enrollCalendar.studentType.id'].selectedIndex].innerHTML;
	    var graduateStdType =form['onCampusTime.graduateCalendar.studentType.id'].options[form['onCampusTime.graduateCalendar.studentType.id'].selectedIndex].innerHTML;
	    if(enrollStdType=="")errors+="入学学期不能为空!\n";
	    if(graduateStdType=="")errors+="毕业学期不能为空!\n";
	    if(enrollStdType!=graduateStdType) errors+="入学学期和毕业学期设置应在同一学生类别学期设置范围内!\n";
	    var enrollYear = form['onCampusTime.enrollCalendar.year'].options[form['onCampusTime.enrollCalendar.year'].selectedIndex].innerHTML;
	    var graduateYear = form['onCampusTime.graduateCalendar.year'].options[form['onCampusTime.graduateCalendar.year'].selectedIndex].innerHTML;
	    if(graduateYear<enrollYear)
	    errors +="毕业学年度应晚于入学学年度!\n";

        var enrollTerm = form['onCampusTime.enrollCalendar.term'].options[form['onCampusTime.enrollCalendar.term'].selectedIndex].value;
	    var graduateTerm = form['onCampusTime.graduateCalendar.term'].options[form['onCampusTime.graduateCalendar.term'].selectedIndex].value;
	    if(graduateTerm==enrollTerm)
        errors+="毕业学期不应和入学学期相同!\n";
	    <#if onCampusTime.id?exists>
	    if(!/^\d{4}-\d$/.test(form['onCampusTime.enrollTurn'].value))  errors+="所在年级学年度格式应为yyyy-p\n"; 
	    </#if>
  	    if(errors!="") {alert(errors);return;}  	    
  	    form.submit();
	}	
</script>

<script src='dwr/interface/calendarDAO.js'></script>
<script src='scripts/common/CalendarSelect.js'></script>
<script>
    var stdTypeArray = new Array();
    <#list stdTypeList as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    var dd = new CalendarSelect("stdType","year","term",false,false,false);
    dd.init(stdTypeArray);
    var dd1 = new CalendarSelect("stdType1","year1","term1",false,false,false);
    dd1.init(stdTypeArray);
</script>
</body>

<#include "/templates/foot.ftl"/>