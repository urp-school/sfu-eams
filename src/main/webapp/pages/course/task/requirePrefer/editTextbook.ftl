<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/textbookDAO.js'></script>
<script type='text/javascript' src='scripts/course/Textbook.js'></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0"  >

	<table id="bookBar"></table>
    <table class="listTable" width="100%" align="center" >
       <tr><td colspan="5">
       <#if RequestParameters['requirementType']=="inTask"><@msg.message key="attr.taskNo"/>${teachTask.seqNo},<@msg.message key="attr.courseName"/> <@i18nName teachTask.course/>
       学年学期 :<@i18nName teachTask.calendar.studentType/> 学年度:${teachTask.calendar.year} 学期:${teachTask.calendar.term}
         <#assign textbooks=teachTask.requirement.textbooks >
       <#else>
         <@msg.message key="attr.courseName"/> <@i18nName requirePrefer.course/><#assign textbooks=requirePrefer.require.textbooks >
       </#if>
       </td></tr>
       <tr class="darkColumn" align="center">
        <td width="50%"><@msg.message key="entity.textbook"/></td>
        <td><@msg.message key="entity.press"/></td>
        <td>操作</td>
       </tr>
       <#list textbooks as textbook>
       <tr align="center">
        <td><@i18nName textbook/></td>
        <td><@i18nName textbook.press/></td>
        <td><button onclick="remove('${textbook.id}')" class="buttonStyle"><@msg.message key="action.delete"/></button></td>
       </tr>
       </#list>
    </table>

	 <table class="formTable" width="100%" align="center">	
	   <form action="requirePrefer.do?method=saveTextbook" name="requirePreferForm" method="post" onsubmit="return false;">
	   <input type="hidden" name="task.id" value="${teachTask?if_exists.id?if_exists}">
	   <input type="hidden" name="requirePrefer.id" value="${requirePrefer?if_exists.id?if_exists}">
	   <input type="hidden" name="requirementType" value="${RequestParameters['requirementType']}">
	   <input type="hidden" name="forward" value="${RequestParameters['forward']}"/>
		<tr class="darkColumn">
        	<td colspan="4">填写教材需求信息</td>
        </tr>
		<tr>
			<td>教材名称<font color="red">*</font></td>
			<td><input type="text" maxlength="20" name="textbookName" onKeyDown="javascript:enterQuery(event,this.form)"/>
			    <button class="buttonStyle" onclick="getTextbookList(this.form)" >查找</button>(直接回车也可)<br>
				<select name="book.id" id="bookNameChoice" onchange="change(this.value)" style = "width:300px">
				</select>
			</td>
			<td class="title"><@msg.message key="textbook.author"/></td>
			<td id="auth"></td> 
		</tr>
		<tr>
			<td class="title"><@msg.message key="textbook.version"/></td>
			<td id="version"></td>
            <td class="title"><@msg.message key="entity.press"/></td>
		    <td id="press.name"></td>
		</tr>
		<tr>  
			<td class="title">教材种类</td>
			<td id="bookType.name"></td>
			<td class="title">单价(元)</td>
			<td id="price"></td>
		</tr>
		<tr>
			<td class="title"><@msg.message key="attr.remark"/></td>
			<td id="remark"></td>
			<td class="title">出版年月</td>
			<td id="publishedYearMonth"></td>
		</tr>	
		<tr class="darkColumn" >  
		 <td colspan="4" align="center"><button onclick="save(this.form);">添加教材</button></td>
		</tr>
		</table>		
	   </form> 

 </body>
 <script language="javascript">
 var bar=new ToolBar('bookBar','教材详细情况',null,true,true);
 bar.addBack("<@bean.message key="action.back"/>");
 function remove(bookId){
    var form=document.requirePreferForm;
    if(confirm("删除信息不可恢复,确认删除")){
	    form.action="requirePrefer.do?method=removeTextbook&book.id="+bookId;
	    form.submit();
    }
 }
 </script>

<#include "/templates/foot.ftl"/>