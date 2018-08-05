<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','排考结果查询',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
  <table  class="frameTable_title">
    <tr>
    <form name="examTableForm" target="statFrame" method="post" action="teacherExamTable.do?method=index" onsubmit="return false;">
     <td >考试类型:
           <#assign examTypes=examTypes?sort_by("code")>
           <select name="examType.id" >
             <#list examTypes as examType>
             <option value="${examType.id}"><@i18nName examType/></option>	
             </#list>
          </select>
     </td>
       <td class="separator">|</td>
      <input type="hidden" name="kind" value="1" >
      <#include "/pages/course/calendar.ftl"/>
       </form>
      </tr>
  </table>
     
   <table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="20%" style="font-size:10pt">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
      <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>监考分类</B>      
	      </td>
	  <tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <tr>
         <td class="padding" id="defaultItem" onclick="examTable(this,1)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" >我的课程
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="examTable(this,2)"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">我是主考
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="examTable(this,3)"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">我是监考
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="examTable(this,4)"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">全&nbsp;&nbsp;&nbsp;部
         </td>
       </tr>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="statFrame" src="#" width="100%" frameborder="0" scrolling="no">
     	</iframe>
	</td>
</table>
<script>
   document.getElementById("defaultItem").onclick();   
   function examTable(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      var form = document.examTableForm;
      form.action="teacherExamTable.do?method=examAtivities&kind="+kind;
      form.submit();
   }
</script>
</body>
<#include "/templates/foot.ftl"/>