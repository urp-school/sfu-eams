<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<@getMessage/>
     <@table.table width="100%" id="listTable" sortable="true">
       <@table.thead>
	     <td class="select"></td>
	     <@table.sortTd width="10%" name="attr.code" id="textbook.code"/>
	     <@table.sortTd width="20%" name="attr.name" id="textbook.name"/>
	     <@table.sortTd width="10%" name="textbook.author" id="textbook.auth"/>
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
<#include "/templates/foot.ftl"/>