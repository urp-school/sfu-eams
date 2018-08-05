  <table width="100%">
    <tr>
      <td class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@msg.message key="textbook.requireSearch"/></B>      
      </td>
    <tr>
      <td colspan="8" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>    
  </table>  
  <table  class="searchTable">
    <input type="hidden" name="pageNo" value="1"/>  
    <input type="hidden" name="calendar.studentType.id" value="${studentType.id}"> 
    <input type="hidden" name="require.task.calendar.id" value="${calendar.id}">
    <input name="searchWhat" value="requirementList" type="hidden"/>
    <tr><td class="infoTitle"><@msg.message key="attr.taskNo"/>:</td><td><input type="text" style="width:60px;" name="require.task.seqNo" maxlength="32" value=""/></td></tr>    
    <tr><td class="infoTitle"><@msg.message key="attr.courseNo"/>:</td><td><input type="text" style="width:100px" name="require.task.course.code" maxlength="32" value=""/></td></tr>
    <tr><td class="infoTitle"><@msg.message key="attr.courseName"/>:</td><td><input type="text" style="width:100px" name="require.task.course.name" maxlength="20" value=""/></td></tr>    
    <tr><td class="infoTitle"><@msg.message key="entity.textbook"/>:</td><td><input type="text" style="width:100px" name="require.textbook.name" maxlength="20" value=""/></td></tr>
    <tr><td class="infoTitle"><@msg.message key="entity.teacher"/>:</td><td><input type="text" style="width:100px" name="arrangeInfo.teacherName" maxlength="20" value=""/></td></tr>
    <tr><td class="infoTitle"><@msg.message key="entity.adminClass"/>:</td><td><input type="text" style="width:100px" name="teachClass.adminClassName" maxlength="20" value=""/></td></tr>
    <tr><td class="infoTitle"><@msg.message key="entity.textbookAwardLevel"/>:</td><td>
         <select name="require.textbook.awardLevel.id" style="width:100px;">
         <option value=""><@msg.message key="common.all"/></option>
         <#list awardLevels as level>
         <option value="${level.id}" ><@i18nName level/></option>
		 </#list>
         </select>
      </td>
    </tr>
    <tr><td  class="infoTitle">开课院系:</td><td>
         <select name="require.task.arrangeInfo.teachDepart.id" style="width:100px;">
         <option value=""><@msg.message key="common.all"/></option>
         <#list teachDeparts as depart>
         <option value="${depart.id}" ><@i18nName depart/></option>
		 </#list>
         </select>
      </td>
    </tr>
	<tr>
	     <td class="infoTitle"><@bean.message key="attr.GP"/>:</td>
	     <td>
	        <select name="require.task.requirement.isGuaPai" style="width:60px">
	           <option value=""><@bean.message key="common.all"/></option>
	           <option value="1"><@bean.message key="common.yes"/></option>
	           <option value="0"><@bean.message key="common.no"/></option>
	        </select>
	     </td>
	</tr>
	<tr>
	     <td class="infoTitle">审核状态:</td>
	     <td>
	        <select name="checkStatus" style="width:100px">
	           <option value=""><@bean.message key="common.all"/></option>
	           <option value="null">未审核</option>
	           <option value="true">审核通过</option>
	           <option value="false">审核不通过</option>
	        </select>
	     </td>
	</tr>
    <tr>
      <td align="center" colspan="2">
    	  <input type="submit" onclick="searchRequires();" value="查询" class="buttonStyle"/>
      </td>
    </tr>
  </table>