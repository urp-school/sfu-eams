<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/textbookDAO.js'></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0"  >

	<table id="bookRequirementBar"></table>
    <table class="listTable" width="100%" align="center" >
       <tr><td colspan="5"><@msg.message key="attr.taskNo"/>${teachTask.seqNo},<@msg.message key="attr.courseName"/> <@i18nName teachTask.course/>
       学年学期 :<@i18nName teachTask.calendar.studentType/> 学年度:${teachTask.calendar.year} 学期:${teachTask.calendar.term}
       </td></tr>
       <tr class="darkColumn" align="center">
        <td width="50%">教材名</td>
        <td>出版社</td>
        <td>订购数量</td>
        <td>操作</td>
       </tr>
       <#list requires as require>
       <tr>
        <td><@i18nName require.textbook/></td>
        <td><@i18nName require.textbook.press/></td>
        <td><input id="require${require.id}count" maxlength="5" value="${require.count?if_exists}" style="width:60px"/></td>
        <td><button class="buttonStyle" onclick="saveCount('${require.id}',this)"><@msg.message key="action.save"/></button>&nbsp;<button onclick="remove('${require.id}',this)" class="buttonStyle"><@msg.message key="action.delete"/></button></td>
       </tr>
       </#list>
    </table>

	 <table class="formTable" width="100%" align="center">	
	   <form action="bookRequirement.do?method=save" name="bookRequirementForm" method="post" onsubmit="return false;">	
	   <input type="hidden" name="require.id" value=""/>
	   <input type="hidden" name="require.task.id" value="${teachTask.id}"/>
	   <input type="hidden" name="params" value="${RequestParameters['params']}"/>
	   <input type="hidden" name="forward" value="${RequestParameters['forward']}"/>
		<tr class="darkColumn">
        <td colspan="4">填写教材需求信息</td>
        </tr>
		<tr>  
		<td>教材名称<font color="red">*</font></td>
		<td><input type="text" name="textbookName" maxlength="20" onKeyDown="javascript:enterQuery(event)"/><button class="buttonStyle" onclick="getTextbook()" >查找</button>(直接回车也可)<br>
			<select name="book.id" id="bookNameChoice" onchange="change(this.value)" style = "width:300px">
			</select>
		</td>
		<td>定购数量<font color="red">*</font></td>
		<td><input type="text" name="require.count" maxlength="5" value="${teachTask.teachClass.planStdCount}"/></td>
		</tr>
		<tr>  
		<td>作者<font color="red">*</font></td>
		<td><input type="text" name="book.auth" maxlength="20" style="background-color:#cccccc" /></td> 
		<td>出版社<font color="red">*</font></td>
		<td><select  name="book.press.id" id="pressSelect" />
		   <#list presses as press>
		    <option value="${press.id}"><@i18nName press/></option>
		   </#list>
		    </select>
		</td>
		</tr>
		<tr>  
			<td>版次</td>
			<td><input type="text" name="book.version" style="background-color:#cccccc" maxlength="5"/></td>
			<td>单价(元)<font color="red">*</font></td>
			<td><input type="text" name="book.price" style="background-color:#cccccc" maxlength="10"/></td>
		</tr>
		<tr>
			<td>备注</td>
			<td colspan="3"><input type="text" name="book.remark" style="background-color:#cccccc" maxlength="200"/></td>
		</tr>		
		<tr class="darkColumn" >  
		 <td colspan="4" align="center"><input type="button" name="button1" value="新增" onclick="formCheck();"class="buttonStyle"/>
            <input type="reset" name="reset" value="取消" class="buttonStyle"/>
         </td>
		</tr>
		</table>		
	   </form> 

 </body>
 <script language="javascript">
 var bar=new ToolBar('bookRequirementBar','需求教材详细情况',null,true,true);
 bar.addBack("<@bean.message key="action.back"/>");
    function enterQuery(event) {
        if (portableEvent(event).keyCode == 13){
                 getTextbook();
        } 
    }
 function saveCount(requireId,obj){
   var form=document.bookRequirementForm;
   addInput(form,"require.id",requireId);
   var count=document.getElementById("require"+requireId+"count").value;

   if(!(/^\d+$/.test(count))){
     alert("请填写订购数量(正整数)");
 	 return false;
   }
   addInput(form,"require.count",count);
   form.action="bookRequirement.do?method=update";
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
 
 function formCheck() {
    var form=document.bookRequirementForm;
 	if(form['textbookName'].value==""&&form['book.id'].value==""){
 		alert("请选择教材或直接输入教材名称");
 		return false;
 	}
 	if(!(/^\d+$/.test(form['require.count'].value))){
 	    alert("请填写订购数量(正整数)");
 		return false;
 	}
 	if(!(/^\d+\.?\d*$/.test(form['book.price'].value))){
 	    alert("请填写单价(正实数)");
 		return false;
 	}
 	if(""==form['book.auth'].value){
 	    alert("请填写作者");
 	}
    document.bookRequirementForm.submit();
 }
	 function change(listvalue){
	    textbookDAO.getTextbook(setBookInfo,listvalue);
	 }

	 function setBookInfo(data){
		var form=document.bookRequirementForm;
		
		for(var att in data){
		   if(null!=form["book."+att]){
		     form["book."+att].value=data[att];
		   }
		}
		setSelected($("pressSelect"),data['press'].id);
	}
	 function getTextbook(){    
	     var form =document.bookRequirementForm;
	     var name=form.textbookName.value;
	     textbookDAO.getTextbooksByName(setTextbookInfo,name);
	 }
  
    function setTextbookInfo(data){
    if(data.length==0){       
       alert("没有相关教材,你可以新增一个教材!");
       var form =document.bookRequirementForm;
       for(var attr in form){
          if(attr.indexOf("book")==0){
             form[attr].value="";
          }          
       }
     }else{
     DWRUtil.removeAllOptions('bookNameChoice');
     var books =new Array();
     for(var i=0;i<data.length;i++){
        books[i]={'id':data[i][0],'name':data[i][1]};
     }
     
  	 DWRUtil.addOptions('bookNameChoice',books,"id","name");
  	 change(document.bookRequirementForm.bookNameChoice.value);
     }     
   }
 </script>

<#include "/templates/foot.ftl"/>