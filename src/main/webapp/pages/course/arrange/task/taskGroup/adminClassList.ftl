   <div style="display: block;" class="tab-page" id="tabPage3">
   <h2 class="tab"><a href="#"><@bean.message key="entity.adminClass" />(${taskGroup.adminClasses?size})</a></h2>     
   <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage3" ) );</script>
   <table width="100%" align="center" class="listTable">
    <tr align="center" class="darkColumn">
      <td width="2%"align="center" >
        <input type="radio" onClick="toggleCheckBox(document.getElementsByName('adminClassId'),event);">
      </td>
      <td width="5%">序号</td>
      <td width="9%"><@bean.message key="attr.code"/></td>
      <td width="15%"><@bean.message key="attr.infoname"/></td>
      <td width="15%"><@bean.message key="common.college"/></td>
      <td width="15%"><@bean.message key="entity.speciality"/></td>
  	  <td width="8%"><@bean.message key="adminClass.actualStdCount" /></td>
    </tr>
    <#list taskGroup.adminClasses?sort_by("name") as adminClass>
	  <#if adminClass_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if adminClass_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" onmouseover="swapOverTR(this,this.className)" 
       onmouseout="swapOutTR(this)" align="center"
       onclick="onRowChange(event)">
      <td  class="select">
         <input type="radio" name="adminClassId" value="${adminClass.id?if_exists}">
      </td>
      <td>${adminClass_index+1}</td>
      <td>${adminClass.code?if_exists}</td>
      <td><A href="adminClass.do?method=info&adminClass.id=${adminClass.id}">${adminClass.name?if_exists}</A></td>
      <td><@i18nName adminClass.department?if_exists/></td>	 
      <td><@i18nName adminClass.speciality?if_exists/></td>
      <td>${adminClass.actualStdCount?if_exists}</td>
    </tr>
    </#list>
  </table>
  </div>