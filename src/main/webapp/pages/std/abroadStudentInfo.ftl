       <input type="hidden" name="abroadStudentInfo.id" value="${(result.student.abroadStudentInfo.id)?default('')}">
	   <tr class="darkColumn">
	     <td align="center" colspan="2">留学生信息</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;HSK等级</td>
	     <td class="brightStyle">
	      <select name="abroadStudentInfo.HSKDegree.id" style="width:100px;">
	       <option></option>
	       <#assign HSKDegreeId = (result.student.abroadStudentInfo.HSKDegree.id)?default(-1) />
	       <#list result.HSKDegreeList?if_exists?sort_by("name") as HSKDegree>
	       <option value="${HSKDegree.id}" <#if HSKDegreeId==HSKDegree.id>selected</#if>><@i18nName HSKDegree/></option>
	       </#list>
	      </select>
	     </td>	    
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;护照编号：
	     </td>
	     <td class="brightStyle">
	     	<input type="text" name="abroadStudentInfo.passportNo" size="25" maxlength="40" value="${(result.student.abroadStudentInfo.passportNo)?default('')}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;护照到期时间：
	     </td>
	     <td class="brightStyle">
	     	<input type="text" name="abroadStudentInfo.passportDeadline" maxlength="10" onfocus="calendar()" size="10" value="${(result.student.abroadStudentInfo.passportDeadline)?default('')}"/>
	     </td>
	   </tr>	
	   <tr>
	     <td class="grayStyle" width="25%" id="f_idCard">
	      &nbsp;护照类别：
	     </td>
	     <td class="brightStyle">
	      <select name="abroadStudentInfo.passportType.id" style="width:100px;">
	       <option></option>
	       <#assign passportTypeId = (result.student.abroadStudentInfo.passportType.id)?default(-1) />
	       <#list result.passportTypeList?if_exists?sort_by("name") as passportType>
	       <option value="${passportType.id}" <#if passportTypeId==passportType.id>selected</#if>><@i18nName passportType/></option>
	       </#list>
	      </select>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;签证编号：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="abroadStudentInfo.visaNo" size="25" maxlength="40" value="${(result.student.abroadStudentInfo.visaNo)?default('')}"/>
	     </td>	     
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;签证到期时间：
	     </td>
	     <td class="brightStyle">
	     	<input type="text" name="abroadStudentInfo.visaDeadline" maxlength="10" onfocus="calendar()" size="10" value="${(result.student.abroadStudentInfo.visaDeadline)?default('')}"/>
	     </td>
	   </tr>	
	   <tr>
	     <td class="grayStyle" width="25%" id="f_idCard">
	      &nbsp;签证类别：
	     </td>
	     <td class="brightStyle">
	      <select name="abroadStudentInfo.visaType.id" style="width:100px;">
	       <option></option>
	       <#assign visaTypeId = (result.student.abroadStudentInfo.visaType.id)?default(-1) />
	       <#list result.visaTypeList?if_exists?sort_by("name") as visaType>
	       <option value="${visaType.id}" <#if visaTypeId==visaType.id>selected</#if>><@i18nName visaType/></option>
	       </#list>
	      </select>
	     </td>
	   </tr>   
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;居住许可证编号：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="abroadStudentInfo.resideCaedNo" size="25" maxlength="40" value="${(result.student.abroadStudentInfo.resideCaedNo)?default('')}"/>
	     </td>	     
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;居住许可证到期时间：
	     </td>
	      <td class="brightStyle">
	     	<input type="text" name="abroadStudentInfo.resideCaedDeadline" maxlength="10" onfocus="calendar()" size="10" value="${(result.student.abroadStudentInfo.resideCaedDeadline)?default('')}"/>
	     </td>
	   </tr>  	   