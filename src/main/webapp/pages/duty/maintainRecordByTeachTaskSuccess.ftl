<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table  width="100%" cellpadding="0" cellspacing="0">
    <tr>
      <td  class="infoTitle" style="height:22px;" >
       <BR>
      </td>
    </tr>
    <tr>
      <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
          <B><@bean.message key="info.action.success"/></B>
      </td>
   </tr>
   <tr>
     <td id="errorTD"><font color="green">
           <@html.errors />
      <#if RequestParameters.messages?exists>
      <#list RequestParameters.messages?split(",") as message>
         <#if (message?length>2)><@bean.message key="${message}"/></#if>&nbsp;
      </#list>
      </#if>
      </font>
      <BR><BR>
      </td>
   </tr>
   <tr>
         <td style="font-size:12px">该页面将在三秒钟后自动关闭，如果没有自动关闭，请点击
         <a href="javascript: self.window.close();" >这里</a>
         </td>
   </tr>
  </table>
  <script>
  	try{
    	window.top.opener.showMessage();
    }catch(e){
    }
	setTimeout('self.window.close()',3000); 
  </script>
 </body>
<#include "/templates/foot.ftl"/>