<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/textbookDAO.js'></script>
<script type='text/javascript' src='scripts/course/Textbook.js'></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0"  >
	<table id="bookRequirementBar"></table>
    <table class="listTable" width="100%" align="center" >
       <tr><td colspan="6"><@msg.message key="attr.taskNo"/>:${teachTask.seqNo},<@msg.message key="attr.courseName"/> <@i18nName teachTask.course/>
       <@msg.message key="entity.studentType"/> :<@i18nName teachTask.calendar.studentType/> <@msg.message key="attr.year2year"/>:${teachTask.calendar.year} <@msg.message key="attr.term"/>:${teachTask.calendar.term}
       </td></tr>
       <tr class="darkColumn" align="center">
        <td width="35%"><@msg.message key="attr.name"/></td>
        <td><@msg.message key="entity.press"/></td>
        <td width="10%">学生用书</td>
        <td width="10%">教师用书</td>
        <td width="10%">备注</td>
        <td><@msg.message key="info.operation"/></td>
       </tr>
       <#list requires as require>
       <tr>
        <td><@i18nName require.textbook/></td>
        <td><@i18nName require.textbook.press/></td>
        <td><input id="require${require.id}countForStd" maxlength="10" value="${require.countForStd?if_exists}" style="width:100%"/></td>
        <td><input id="require${require.id}countForTeacher" maxlength="20" value="${require.countForTeacher?if_exists}" style="width:100%"/></td>
        <td><input id="require${require.id}remark" maxlength="200" value="${require.remark?if_exists}" style="width:100%"/></td>
        <td><button class="buttonStyle" onclick="saveCount('${require.id}',this)"><@msg.message key="action.save"/></button>&nbsp;<button onclick="remove('${require.id}',this)" class="buttonStyle"><@msg.message key="action.delete"/></button></td>
       </tr>
       </#list>
    </table>
    <table id="newRequireBar"></table>
	 <table class="formTable" width="100%" align="center">	
	   <form action="bookRequirement.do?method=save" name="bookRequirementForm" method="post" onsubmit="return false;">	
	   <input type="hidden" name="require.id" value=""/>
	   <input type="hidden" name="require.task.id" value="${teachTask.id}"/>
	   <input type="hidden" name="params" value="${RequestParameters['params']}"/>
		<tr>  
			<td class="title"><@msg.message key="attr.name"/><font color="red">*</font></td>
			<td><input type="text" name="textbookName" maxlength="20" onKeyDown="javascript:enterQuery(event,this.form)"/>
			    <button class="buttonStyle" onclick="getTextbookList(this.form)" ><@msg.message key="action.query"/></button>(直接回车也可)<br>
				<select name="book.id" id="bookNameChoice" onchange="change(this.value)" style = "width:300px"></select>
			</td>
			<td class="title"><font color="red">*</font>学生用书：</td>
			<td><input type="text" name="require.countForStd" maxlength="7" value="${teachTask.teachClass.planStdCount}"/></td>
		</tr>
		<tr>  
			<td class="title"><@msg.message key="textbook.author"/></td>
			<td id="auth"></td> 
			<td class="title">教师用书：</td>
			<td><input type="text" name="require.countForTeacher" maxlength="7" value="0"/></td>
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
		 <td colspan="4" align="center"><button onclick="save(this.form);">添加教材</button>
            <input type="reset" name="reset" value="取消" class="buttonStyle"/>
         </td>
		</tr>
		</table>		
	   </form> 
 </body>
 <script language="javascript">
 var bar=new ToolBar('bookRequirementBar','<@msg.message key="textbook.requireInfo"/>',null,true,true);
 bar.addBack("<@bean.message key="action.back"/>");
 bar=new ToolBar('newRequireBar','<@msg.message key="textbook.requireSearchAndNew"/>',null,true,true);
 bar.addBack("<@bean.message key="action.back"/>");

 function saveCount(requireId,obj){
   var form=document.bookRequirementForm;
   addInput(form,"requireId",requireId);
   var count=document.getElementById("require"+requireId+"countForStd").value;
   if(!(/^\d+$/.test(count))){
     alert("请填写订购数量(正整数)");
 	 return false;
   }
   addInput(form,"require.countForStd",count);
   count=document.getElementById("require"+requireId+"countForTeacher").value;
   if(!(/^\d*$/.test(count))){
     alert("请填写订购数量(正整数)");
 	 return false;
   }
   addInput(form,"require.countForTeacher",count);
   addInput(form,"require.remark",document.getElementById("require"+requireId+"remark").value);
   form.action="bookRequirement.do?method=updateCount";
   form.submit();
 }
 
 function remove(requireId){
    var form=document.bookRequirementForm;
    addInput(form,"requireIds",requireId);
    if(confirm("删除信息不可恢复,确认删除")){
	    form.action="bookRequirement.do?method=remove";
	    form.submit();
    }
 }
 
 </script>

<#include "/templates/foot.ftl"/>