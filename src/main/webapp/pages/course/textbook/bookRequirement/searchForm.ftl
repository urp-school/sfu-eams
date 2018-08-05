<div style="display: block;" id="view0">
    <form name="requireSearchForm" action="bookRequirement.do?method=requirementList" method="post" target="contentFrame" onsubmit="return false;">
     	<#include "../requireSearchTable.ftl"/>
 	</form>
</div>
 
  <div style="display: none;" id="view1">
   <table  width="100%">
    <tr>
      <td  class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>未登记教材任务查询</B>      
      </td>
    <tr>
      <td  colspan="8" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
  </table>  
  <table class="searchTable">
    <form name="taskSearchForm" action="bookRequirement.do?method=taskList" method="post" target="contentFrame" onsubmit="return false;">
    <input type="hidden" name="pageNo" value="1" />
    <input type="hidden" name="calendar.studentType.id" value="${studentType.id}">
    <input type="hidden" name="task.calendar.id" value="${calendar.id}">
    <input name="searchWhat" value="taskList" type="hidden"/>
    <tr><td class="infoTitle"><@msg.message key="attr.taskNo"/>:</td><td><input type="text" style="width:60px" name="task.seqNo" value="" maxlength="32"/></td></tr>    
    <tr><td class="infoTitle"><@msg.message key="attr.courseName"/>:</td><td><input type="text" style="width:100px" name="task.course.name"  value="" maxlength="20"/></td></tr>    
    <tr><td class="infoTitle"><@msg.message key="attr.courseNo"/>:</td><td><input type="text" style="width:100px" name="task.course.code"  value="" maxlength="32"/></td></tr>
    <tr><td class="infoTitle"><@msg.message key="entity.courseType"/>:</td><td>
         <select name="task.courseType.id" style="width:100px;">
         <option value=""><@msg.message key="common.all"/></option>
         <#list courseTypes as courseType>
         <option value="${courseType.id}" ><@i18nName courseType/></option>
		 </#list>
         </select>
      </td>
    </tr>
    <tr><td  class="infoTitle">开课院系:</td><td>
         <select name="task.arrangeInfo.teachDepart.id" style="width:100px;">
         <option value=""><@msg.message key="common.all"/></option>
         <#list teachDeparts as depart>
         <option value="${depart.id}" ><@i18nName depart/></option>
		 </#list>
         </select>              
      </td>
    </tr>
    <tr>
      <td align="center" colspan="2">
    	  <input type="button"  onclick="search('taskList',1,null,null);"  value="查询" class="buttonStyle" />
      </td>
    </tr>
    </form>
  </table>
 </div>