<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar"></table>
     <@table.table width="100%" id="listTable" sortable="true" headIndex="1">
       <form name="actionForm" method="post" action="bookRequirement.do?method=chooseTextbook" onsubmit="return false;">
       <@searchParams/>
       <input name="taskIds" value="${RequestParameters['taskIds']}" type="hidden"/>
       <tr onkeypress="DWRUtil.onReturn(event, search)">
	     <td><img src="${static_base}/images/action/search.png"  align="top" onClick="search()" alt="<@bean.message key="info.filterInResult"/>"/></td>
	     <td><input name="textbook.code" maxlength="32" value="${RequestParameters['textbook.code']?default('')}" style="width:100%"/></td>
	     <td><input name="textbook.name" maxlength="20" value="${RequestParameters['textbook.name']?default('')}" style="width:100%"/></td>
	     <td><input name="textbook.auth" maxlength="20" value="${RequestParameters['textbook.auth']?default('')}" style="width:100%"/></td>
	     <td><input name="textbook.version" maxlength="3" value="${RequestParameters['textbook.version']?default('')}" style="width:100%"/></td>
	     <td><input name="textbook.press.name" maxlength="20" value="${RequestParameters['textbook.press.name']?default('')}" style="width:100%"/></td>
	   </tr>
       </form>
       <@table.thead>
	     <td class="select"></td>
	     <@table.sortTd width="10%" name="attr.code" id="textbook.code"/>
	     <@table.sortTd width="20%" name="attr.name" id="textbook.name"/>
	     <@table.sortTd width="15%" name="textbook.author" id="textbook.auth"/>
	     <@table.sortTd width="10%" name="textbook.version" id="textbook.version"/>
	     <@table.sortTd width="20%" name="entity.press" id="textbook.press.name"/>
	   </@>
	   <@table.tbody datas=textbooks;textbook>
	    <@table.selectTd type="radio" id='textbookId' value="${textbook.id}"/>
	    <td>&nbsp;${textbook.code?if_exists}</td>
	    <td><a href="textbook.do?method=info&textbookId=${textbook.id}">&nbsp;${textbook.name?if_exists}</a></td>	    
	    <td>&nbsp;${textbook.auth?if_exists}</td>
	    <td>&nbsp;${textbook.version?if_exists}</td>
	    <td>&nbsp;${textbook.press?if_exists.name?if_exists}</td>
	   </@>
     </@>
 </body>
  <script language="JavaScript">
    var bar = new ToolBar("myBar","<@msg.message key="textbook.search"/>",null,true,true);
    bar.addItem("<@msg.message key="action.query"/>","search()","search.png");
    bar.addItem("<@msg.message key="action.submit"/>","batchSet()");
    bar.addBack("<@msg.message key="action.back"/>");
    var form=document.actionForm;
    action="bookRequirement.do";
    function batchSet(){
       form.action=action+"?method=batchSetBook";
       submitId(form,"textbookId",false,null,"<@msg.message key="common.confirmAction"/>");
          }
		function  search(){
		    form.action="bookRequirement.do?method=chooseTextbook";
		    form.submit();
		}
   </script> 
<#include "/templates/foot.ftl"/>