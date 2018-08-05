 <table width="90%"  align="center" class="formTable">
       
       <tr class="darkColumn">
         <td align="left" colspan="4"><B><@bean.message key="page.teacherForm.label" /></B></td>
       </tr>
	   
	   <div id= "baseinfo">
	   <tr>
         <td width="15%"  id="f_code"  class="title"><font color="red">*</font><@bean.message key="teacher.code"/>:</td>
	     <td width="35%"  >
	          <input type="text" id="codeInput" name="teacher.code" style="width:100px" value="${teacher.code?if_exists}" />
    	      <input type="hidden" name="teacher.id" value="${teacher.id?if_exists}" />
    	      <button onclick="autoGen();return false;">自动生成</button>
	     </td>
	     <td width="15%"  id="f_name"  class="title"><font color="red">*</font><@msg.message key="attr.name"/>:</td>
	     <td width="35%"  ><input type="text" name="teacher.name" value="${teacher.name?if_exists}" maxLength="50"/></td>
	   </tr>
	   <tr>
	   	 <td  id="f_gender"  class="title"><font color="red">*</font><@bean.message key="attr.gender" />:</td>
	     <td>
               <select name="teacher.gender.id" style="width:60px;">
               <#list genderList as gender>
               <#if teacher.gender.id?exists && gender.id = teacher.gender.id>
               <option value="${gender.id}" selected><@i18nName gender/></option>
               <#else>
               <option value="${gender.id}"><@i18nName gender/></option>
               </#if>
               </#list>
               </select>
	     </td>
	   	 <td  id="f_credentialNumber"  class="title"><@bean.message key="teacher.CredentialNumber" />:</td>
	   	 <td><input type="text" name="teacher.credentialNumber" value="${teacher.credentialNumber?if_exists}" maxLength="50"/></td>
	   </tr>
       <tr>
	   	 <td  id="f_country"  class="title"><font color="red">*</font><@bean.message key="entity.country" />:</td>
	     <td>
               <select name="teacher.country.id" style="width:150px">
               <#if teacher.country?if_exists.id?exists>
               <#else>
                <option value=""><@bean.message key="common.selectPlease" /></option>
               </#if>
               <#list countryList?sort_by("name") as country >
               <#if teacher.country?if_exists.id?exists && country.id == teacher.country.id>
               <option value="${country.id}" selected><@i18nName country/></option>
               <#else>
               <option value="${country.id}"><@i18nName country/></option>
               </#if>
               </#list>
               </select>
	     </td>
	   	 <td  id="f_department"  class="title"><font color="red">*</font><@bean.message key="entity.department" />:</td>
	     <td>
               <select name="teacher.department.id" style="width:150px">
               <#list departmentList as depart>
               <#if (teacher.department.id)?exists && depart.id == teacher.department.id>
                 <option value="${depart.id}" selected><@i18nName depart/>
               <#else>
                 <option value="${depart.id}"><@i18nName depart/>
               </#if>
               </#list>
	     </td>
	   </tr>
       <tr>
	   	 <td  id="f_nation"  class="title"><@bean.message key="entity.nation" />:</td>
	     <td>              
               <select name="teacher.nation.id" style="width:150px">
               <#if teacher.nation?if_exists.id?exists>
               <#else>
                <option value=""><@bean.message key="common.selectPlease" /></option>
               </#if>
               
               <#list nationList as nation>
               <#if teacher.nation?if_exists.id?exists && nation.id = teacher.nation.id >
               <option value="${nation.id}" selected><@i18nName nation/></option>
               <#else>
               <option value="${nation.id}"><@i18nName nation/></option>
               </#if>
               </#list>
               </select>
	     </td> 
	   	 <td  id="f_teacherType"  class="title"><font color="red">*</font><@bean.message key="entity.teacherType" />:</td>
	     <td>              
               <select name="teacher.teacherType.id" style="width:150px">
               <#if teacher.teacherType?if_exists.id?exists>
               <#else>
                <option value=""><@bean.message key="common.selectPlease" /></option>
               </#if>               
               <#list teacherTypeList as teacherType>
               <#if teacher.teacherType?if_exists.id?exists && teacherType.id == teacher.teacherType.id>
               <option value="${teacherType.id}" selected><@i18nName teacherType/></option>
               <#else>
               <option value="${teacherType.id}"><@i18nName teacherType/></option>
               </#if>
               </#list>
               </select>
	     </td>
	   </tr>
       <tr>
	   	 <td  id="f_birthday"  class="title"><@bean.message key="attr.birthday" />:</td>
         <td  colspan="3">
            <input type="text" name="teacher.birthday" id="birthday" value="${teacher.birthday?if_exists}"  onfocus='calendar()'/>
         </td>
       </tr>
       </div>
     </table>
     <script>
	 function autoGen(){
	     var codeInput=document.getElementById("codeInput");
	     if(codeInput.disabled){
	        codeInput.disabled=false;
	     }else{
	       codeInput.value="******";
	       codeInput.disabled=true;
	     }
	     return false;
	  }
	  <#if (teacher.code?exists&&teacher.code?length!=0)><#else>autoGen();</#if>
	</script>