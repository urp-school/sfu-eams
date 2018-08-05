 <table  width="90%" align="center" class="formTable">
   <tr class="darkColumn">
      <td colspan="4"><input type="checkbox"  onClick="displayDiv(event,'degreeInfo')"/><@msg.message key="attr.subjectInfo"/></td>
   </tr>
  </table>

   <div id="degreeInfo" style="display:none">
   <table  width="90%" align="center"   class="formTable">
   <tr>
   	 <td width="15%"  id="f_eduDegree" class="title"><@msg.message key="common.eduDegree" />:</td>
     <td width="35%"  >
         <@htm.i18nSelect  name="teacher.degreeInfo.eduDegreeInside.id" datas=eduDegreeList selected=(teacher.degreeInfo.eduDegreeInside.id)?default("")?string  style="width:100px">
            <option value=""><@msg.message key="common.selectPlease" /></option>
         </@>
     </td>
   	 <td width="15%"  id="f_dateOfEduDegree" class="title"><@msg.message key="teacher.dateOfEduDegree" />:</td>
     <td width="35%"  >
       <input type="text" name="teacher.degreeInfo.dateOfEduDegreeInside" 
              value="${(teacher.degreeInfo.dateOfEduDegreeInside)?if_exists}" onfocus='calendar()'>
     </td>
   </tr>
   <tr>
   	 <td  id="f_degree" class="title"><@msg.message key="common.degree" />:</td>
     <td> <@htm.i18nSelect  name="teacher.degreeInfo.degree.id" style="width:100px" datas=degreeList selected=(teacher.degreeInfo.degree.id)?default("")?string >
            <option value=""><@msg.message key="common.selectPlease" /></option>
          </@>
     </td>
   	 <td  id="f_dateOfDegree" class="title"><@msg.message key="teacher.dateOfDegree" />:</td>
     <td>
        <input type="text" name="teacher.degreeInfo.dateOfDegree" 
               value="${(teacher.degreeInfo.dateOfDegree)?if_exists}" onfocus='calendar()'>
     </td>
   </tr>
   <tr>
   	 <td  id="f_graduateSchool" class="title"><@msg.message key="teacher.graduateSchool" />:</td>
     <td colspan="3" >
      <@htm.i18nSelect  name="teacher.degreeInfo.graduateSchool.id"  datas=schoolList selected=(teacher.degreeInfo.graduateSchool.id)?default("")?string style="width:522px">
       <option value=""><@msg.message key="common.selectPlease" /></option>
      </@>
     </td>
   </tr>
   <tr>
   	 <td  id="f_title" class="title"><@msg.message key="common.teacherTitle" />:</td>
     <td> <@htm.i18nSelect  name="teacher.title.id" datas=titleList style="width:100px" selected=(teacher.title.id)?default("")?string>
            <option value=""><@msg.message key="common.selectPlease" /></option>
          </@>
     </td>
   	 <td  id="f_dateOfTitle" class="title"><@msg.message key="teacher.dateOfTitle" />:</td>
     <td>
          <input type="text" name="teacher.dateOfTitle" 
          value="${teacher.dateOfTitle?if_exists}" onfocus='calendar()'>
     </td>
   </tr>
   </table>
 </div>
