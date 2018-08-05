<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bar"></table>
<script>
  var bar =new ToolBar("bar","<@bean.message key="entity.courseTable.std"/>",null,true,true);
  bar.addPrint("<@msg.message key="action.print"/>");
  bar.addBack("<@msg.message key="action.back"/>");
</script>
<table class="frameTable_title">
     <tr>
      <td id="viewTD0" class="transfer" width="60px" onclick="javascript:getCourseTable('std','${std.id}',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
        <font color="blue"><@bean.message key="entity.courseTable.person"/></font>
      </td>
      <#list std.adminClasses as adminClass>
      <td id="viewTD${adminClass_index+1}" class="padding" onclick="javascript:getCourseTable('class','${adminClass.id}',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
      	  <font color="blue">${adminClass.name}</font>
      </td>
      </#list>
      <td>|</td>
      <form name="courseTableForm" method="post" action="courseTableForStd.do?method=courseTable&setting.forCalendar=0" target="contentListFrame" onsubmit="return false;">
      		<input type="hidden" name="ignoreHead" value="1"/>
	      <input type="hidden" name="calendar.id" value="${calendar.id}"/>
	      <select id="stdType" name="calendar.studentType.id" style="width:100px;display:none">
	        <option value="${studentType.id}"></option>
	      </select>
			   <td class="infoTitle" align="right"><@bean.message key="attr.year2year"/></td>
			   <td style="width:100px;">
			     <select id="year" name="calendar.year" style="width:100px;">
			        <option value="${calendar.year}"></option>
			      </select>
			    </td>
			    <td class="infoTitle" align="right"><@bean.message key="attr.term"/></td>
			    <td style="width:60px;">
			     <select id="term" name="calendar.term" style="width:60px;">
			        <option value="${calendar.term}"></option>
			      </select>
			   </td>
    <#include "/templates/calendarSelect.ftl"/>
    <td  class="infoTitle">
       <input type="button" name="query" onclick="javascript:changeCalendar(this.form);"class="buttonStyle" value=" go "/>
    </td>
    <td class="infoTitle" style="width:50px;"> <@bean.message key="attr.startWeek"/></td>
    <td>
    <select name="startWeek" onchange="searchWeek(this.form)">
    <#list 1..calendar.weeks as i><option value="${i}"><@bean.message key="common.kWeek" arg0=i?string/></option></#list>
    </select>
    </td>
    <td><#if !courseTableCheck.isConfirm><button onclick="confirmCourseTable()"><@msg.message key="common.toSure"/></button><#else><@msg.message key="common.toBeSure"/></#if></td>
   </tr>
   </form>
  </table>
  <table class="frameTable" height="85%">
    <tr>
     <td class="frameTable_content">
	     <iframe src="courseTableForStd.do?method=courseTable&setting.forCalendar=0&setting.kind=std&calendar.id=${calendar.id}&ids=${std.id}&ignoreHead=1"
	     id="contentListFrame" name="contentListFrame"
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0" height="100%" width="100%">
	     </iframe>
     </td>
  </tr>
</table>
 <script>
   var form = document.courseTableForm;
   function getCourseTable(courseTableType,id,event){
       changeView(getEventTarget(event));
       form.action="courseTableForStd.do?method=courseTable&setting.forCalendar=0&setting.kind=" +courseTableType + "&ids=" +id;
       form.target="contentListFrame";
       form.submit();
   }
   var viewNum=${std.adminClasses?size+1}; 
   
   function searchWeek(form){
       form.target="contentListFrame";
       form.action="courseTableForStd.do?method=courseTable&setting.forCalendar=0";
       searchTable(form);
   }
   function changeCalendar(form){
       form.target="_self";
       form.action="courseTableForStd.do?method=stdHome&setting.forCalendar=0";
       searchTable(form);
   }
  function searchTable(form){
       for(var i=0;i<viewNum;i++){
          var td = document.getElementById("viewTD"+i);
          if(td.className="transfer"){
               if(i==0) form.action+="&setting.kind=std&ids="+"${std.id}";
               else form.action+="&setting.kind=class";
               <#if std.firstMajorClass?exists>
               if(i==1) form.action+="&ids=${std.firstMajorClass.id}";
               </#if>
               <#if std.secondMajorClass?exists>
               if(i==2)
               form.action+="&ids=${std.secondMajorClass.id}";
               </#if>
               break;
          }
       }
       form.submit();
   }
   function confirmCourseTable(){
      form.target="";
      form.action="courseTableCheck.do?method=confirmCourseTable&courseTableCheckId=${courseTableCheck.id}";
      form.submit();
   }
 </script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 
</body>
<#include "/templates/foot.ftl"/> 
