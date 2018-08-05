<#include "/templates/head.ftl"/>
<body>
<table  width="60%" align="center">
    <tr>
      <td  class="infoTitle" style="height:22px;" >
       <img src="${static_base}/images/action/info.gif" align="top"/><B>
          <B><@msg.message key="course.electionResult"/></B>
      </td>
    </tr>
    <tr>
      <td style="font-size:0px" >
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
   <tr align="center">
     <td>
      <#if electResultInfo?index_of("error")==0>
      <font color="red">
      <#else>
      <font color="green">
      </#if>
      <@bean.message key="${electResultInfo}"/></font>
    </td>
   </tr>
   <#if courseTakeList?exists>
   <tr align="center">
      <select name="courseTake.id">
      <#list courseTakeList as courseTake>
          <option value="${courseTake.id}">${courseTake.name}</option>
      </#list>
      </select>
      <@bean.message key="prompt.timeCollisionSelect"/>
   </tr>
   </#if>
   </tr>
    <tr align="center">
      <td   style="font-size:10px" >
          <input value="<@msg.message key="course.continueElective"/>" type="button" class="buttonStyle" onClick="javascript:history.back();"/>
      </td>
   </tr>
  </table>
  </body>
  <#include "/templates/foot.ftl"/> 