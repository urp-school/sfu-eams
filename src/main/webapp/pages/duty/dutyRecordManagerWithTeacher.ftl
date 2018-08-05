<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table cellpadding="0" cellspacing="0" border="0" width="100%" >
   <tr>
    <td>
     <div id="searchBar" style="position:relative; visibility: hidden; display:none;"> 
     <form name="pageGoForm" method="post" action="dutyRecordManager.do" onsubmit="return false;">
     <table width="85%" align="center" class="listTable">
      <tr> 
       <td colspan="2" align="center" class="darkColumn"><B><@bean.message key="info.duty.classQuery"/></B></td>
      </tr>
      <tr>
	     <td class="grayStyle" width="25%" id="f_courseType">
	      &nbsp;<@bean.message key="entity.courseType"/>：
	     </td>
	     <td class="brightStyle">
	      <select name="courseTypeId" style="width:100px;" onfocus="this.blur();">
	       <option></option>
	       <#list result.courseTypeList?if_exists as courseType>
	       <option value="${courseType.id}"><@i18nName courseType?if_exists/></option>
	       </#list>
	      </select>
	     </td>
	     <script language="javascript">
	       choiceTargetSelectorOption("${RequestParameters["courseTypeId"]?if_exists}", "courseTypeId");
	     </script>
	  </tr>
      <tr>
	     <td class="grayStyle" width="25%" id="f_year">&nbsp;<@bean.message key="attr.year2year"/><font color="red">*</font>：</td>
	     <td class="brightStyle">
	      <select name="year" style="width:100px;" onfocus="this.blur();">
	       <option></option>
	       <option value="2002-2003">2002-2003</option>
	       <option value="2003-2004">2003-2004</option>
	       <option value="2004-2005">2004-2005</option>
	       <option value="2005-2006">2005-2006</option>
	       <option value="2006-2007">2006-2007</option>
	       <option value="2007-2008">2007-2008</option>
	       <option value="2008-2009">2008-2009</option>
	       <option value="2009-2010">2009-2010</option>
	      </select>
	     </td>
	     <script language="javascript">
	       choiceTargetSelectorOption("${RequestParameters['year']?if_exists}", "year");
	     </script>
	  </tr>
	  <tr>
	   <td class="grayStyle" width="25%" id="f_term">&nbsp;<@bean.message key="attr.term"/><font color="red">*</font>：</td>
	   <td class="brightStyle">
	      <select name="term" style="width:100px;" onfocus="this.blur();">
	       <option></option>
	       <#list result.teachCalendarTermList?if_exists as term>
	       <option value="${term}">${term}</option>
	       </#list>
	      </select>
	   </td>
	   <script language="javascript">
	       choiceTargetSelectorOption("${RequestParameters['term']?if_exists}", "term");
	   </script>
	  </tr>
	  <#assign moduleName="DutyRecordManager" />
	  <#--选择学生类别-->
	  <#assign studentTypeNeeded = true />
      <#assign studentTypeId = "studentTypeId" />
	  <#assign studentTypeDescriptions = "studentTypeDescriptions" />
	  <#include "/pages/selector/studentTypeSelectorBarWithAuthority.ftl" />
	  <#--开课院系-->	  
	  <#assign departmentId = "departmentId" />
	  <#assign departmentDescriptions = "departmentDescriptions" />
	  <#include "/pages/selector/singleDepartmentSelectorBarWithAuthority.ftl" />
	  <tr>
	   <td colspan="2" align="center" class="darkColumn">
	    <input type="hidden" name="teachTaskId" />
	    <input type="hidden" name="flag" value="search" />
	    <input type="hidden" name="method" value="${RequestParameters.method}" />
	    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />&nbsp;
	    <input type="button" onClick="submitResetForm()" value="<@bean.message key="system.button.reset" />" name="button2"  class="buttonStyle" />
       </td>
	  </tr>
     </table>
     </div>
    </td>
   </tr>   
  </table>
  </form>
  
  
  <table cellpadding="0" cellspacing="0" width="100%" border="0"> 
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="page.dutyRecordManage.title"/><@bean.message key="filed.courseList"/></B>
    </td>
   </tr>
   <tr>
    <td>
     <table width="85%" align="center" class="listTable">
       <form name="listForm" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td width="20%"><@bean.message key="attr.courseName"/></td>
	     <td width="12%"><@bean.message key="entity.courseType"/></td>
	     <td width="12%"><@bean.message key="attr.year2year"/></td>
	     <td width="10%"><@bean.message key="attr.term"/></td>
	     <td width="15%"><@bean.message key="entity.studentType"/></td>
	     <td width="20%"><@bean.message key="entity.teachClass"/></td>
	     <td align="center"><@bean.message key="system.button.modify"/></td>
	     <td align="center"><@bean.message key="action.input"/></td>
	   </tr>	   
	   <#list (result.teachTaskList?sort_by("seqNo"))?if_exists as teachTask>
	   <#if teachTask_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if teachTask_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td>&nbsp;<@i18nName teachTask.course?if_exists/></td>
	    <td>&nbsp;<@i18nName teachTask.courseType?if_exists/></td>
	    <td>&nbsp;${teachTask.calendar.year}</td>
	    <td>&nbsp;${teachTask.calendar.term}</td>
	    <td>&nbsp;<@i18nName teachTask.teachClass?if_exists.stdType?if_exists/><#--><@getTeacherNames teachTask.arrangeInfo.teachers/>--><#--<@eraseComma teachTask.arrangeInfo.teacherNames?if_exists/>--></td>
	    <td>&nbsp;<@i18nName teachTask.teachClass?if_exists/></td>
	    <td align="center">
	     <a href="javascript:inputForm('${teachTask.id}')" >
	      &lt;&lt;
	     </a>
	    </td>
	    <td align="center">
	     <a href="javascript:inputForm1('${teachTask.id}')" >
	      &lt;&lt;
	     </a>
	    </td>
	   </tr>
	   </#list>
	   </form>
     </table>
    </td>
   </tr>
  </table>  
  
  <form name="resetForm" method="post" action="dutyRecordManager.do"> 
    <input type="hidden" name="method" value="${RequestParameters.method}" />
  </form>
 </body>
 <script>
    function search(){
       var a_fields = {
         'year':{'l':'<@bean.message key="attr.year2year"/>', 'r':true, 't':'f_year'},
         'term':{'l':'<@bean.message key="attr.term"/>', 'r':true, 't':'f_term'},
         'studentTypeId':{'l':'<@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'}
       };
     
       var v = new validator(document.pageGoForm, a_fields, null);
       
       if (v.exec()) {
          var location = self.location.href;
          var action = location.split("/");
          document.pageGoForm.action = action[action.length-1];
          document.pageGoForm.submit();
       }
    }
    
    function submitResetForm(){
          var location = self.location.href;
          var action = location.split("/");
          document.resetForm.action = action[action.length-1];
          document.resetForm.submit();
    }
    
    function inputForm(teachTaskId){
        document.pageGoForm.method.value = "maintainRecordByTeachTask";
        document.pageGoForm.teachTaskId.value = teachTaskId;
        var location = self.location.href;
        var url = location.split("/");
        var action = url[url.length-1].split("?");        
        document.pageGoForm.action = action[0];
        document.pageGoForm.submit();
    }
    
    function inputForm1(teachTaskId){
        document.pageGoForm.method.value = "inputForm";
        document.pageGoForm.teachTaskId.value = teachTaskId;
        document.pageGoForm.action = "inputDutyRecordWithTeacher.do";
        document.pageGoForm.submit();
    }
 </script>
<#include "/templates/foot.ftl"/>