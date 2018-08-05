<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
    <table id="appointStd"></table>
    <script> 
    function getIds(){
       return(getRadioValue(document.getElementsByName("tutorId")));
    }    
    function appointStd(form){
       ids = getIds();
       if(ids=="") {alert("请先选择导师!");return;}
       form.action="appointStd.do?method=appointStd&tutorId=" + ids;
       form.submit();  
    }  
    function searchStd(form){
       ids = getIds();
       if(ids=="") {alert("请先选择导师!");return;}
       form.action="appointStd.do?method=searchStd&tutorId=" + ids;
       form.submit();    
    }   
    var bar = new ToolBar("appointStd","指定学生",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("指定学生",'appointStd(document.appointStdForm)');
    bar.addItem("所带学生查询",'searchStd(document.appointStdForm)');  
    </script>
     <@table.table sortable="true" id="listTable" align="center" width="100%" headIndex="1">
    <form name='appointStdForm' method="post" action="" onsubmit="return false;">
       <tr align="center" class="darkColumn" bgcolor="#ffffff" onKeyDown="javascript:enterQuery(document.appointTutorForm)">
	     <td><img src="${static_base}/images/action/search.png"  align="top" onClick="pageGoWithSize()" alt="在结果中过滤"/></td>
	     <td><input type='text' name='tutor.code' maxlength="32" value='${RequestParameters['tutor.code']?if_exists}' style="width:100%"></td>	     
         <td><input type='text' name='tutor.name' maxlength="20" value='${RequestParameters['tutor.name']?if_exists}' style="width:100%"></td>
	     <td>
	          <input type="text" name="tutor.department.name" maxlength="20" style="width:100%" value="${RequestParameters['tutor.department.name']?if_exists}">	     
	     </td>
	     <td>
	          <input type="text" name="tutor.speciality.name" maxlength="25" style="width:100%" value="${RequestParameters['tutor.speciality.name']?if_exists}">	     
	     </td>
	     <td width="15%">
	           <input type="text" name="tutor.aspect.name" maxlength="20" style="width:100%" value="${RequestParameters['tutor.aspect.name']?if_exists}">	     
	     </td> 
         <td width="15%">
         		<@htm.i18nSelect datas=tutorTypeList selected="${RequestParameters['tutor.tutorType.id']?if_exists}" style="width:100%" name="tutor.tutorType.id">
                	<option value="">全部</option>
                </@>  
         </td>	         
	     
	   	 <td></td>
	   </tr>
    </form>
	   <@table.thead>
	   		<td class="select">&nbsp;</td>
	   		<@table.sortTd id="tutor.code" text="职工号"/>
	   		<@table.sortTd id="tutor.name" name="attr.personName"/>
	   		<@table.sortTd id="tutor.department.name" name="entity.department"/>
	   		<@table.sortTd id="tutor.speciality.name" name="entity.speciality"/>
	   		<@table.sortTd id="tutor.aspect.name" name="entity.specialityAspect"/>
	   		<@table.sortTd id="tutor.tutorType.name" text="导师类别"/>
	   		<@table.sortTd id="tutor.dateOfTutor" text="任职时间"/>
	   </@>
       <@table.tbody datas=tutors;tutor>
       		<td class="select"><input type="radio" name="tutorId" value="${tutor.id}"></td>
       		<td>${(tutor.code)?if_exists}</td>
       		<td>${(tutor.name)?if_exists}</td>
       		<td>${(tutor.department.name)?if_exists}</td>
       		<td>${(tutor.speciality.name)?if_exists}</td>
       		<td>${(tutor.aspect.name)?if_exists}</td>
       		<td>${(tutor.tutorType.name)?if_exists}</td>
       		
       		<td><#if tutor.dateOfTutor?exists>${tutor.dateOfTutor?string("yyyy-MM-dd")}</#if></td>
       </@>
	   </@>
 </body>
  <script language="JavaScript">
  var form = document.appointStdForm;
function enterQuery() {
     if (window.event.keyCode == 13)pageGoWithSize();
}
function pageGoWithSize(pageNo,pageSize){
	form.action="appointStd.do?method=list";
    goToPage(form,pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
}
   </script>
<#include "/templates/foot.ftl"/> 