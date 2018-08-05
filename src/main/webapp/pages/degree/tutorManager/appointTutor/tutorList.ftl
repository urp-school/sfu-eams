<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
    <table id="appointTutor"></table>
	<table width="100%">
		<tr>
			<td class="grayStyle" align="left">选择的学生:<#list stdList?if_exists as std>
			    ${std.code?if_exists},${std.name?if_exists}&nbsp;&nbsp;&nbsp;&nbsp;
			</#list></td>
		</tr>
	</table>
    <@table.table sortable="true" id="listTable" align="center" width="100%" headIndex="1">
    <form name='appointTutorForm' method="post" action="" onsubmit="return false;">
       <tr align="center" class="darkColumn" bgcolor="#ffffff" onKeyDown="javascript:enterQuery(document.appointTutorForm)">
	     <td><img src="${static_base}/images/action/search.png" align="top" onClick="pageGoWithSize()" alt="在结果中过滤"/></td>
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
	   <input type="hidden" name="stdIdSeq" value="${stdIdSeq?if_exists}">
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
       		<td>${tutor.code}</td>
       		<td>${tutor.name}</td>
       		<td>${tutor.department.name}</td>
       		<td>${(tutor.speciality.name)?if_exists}</td>
       		<td>${(tutor.aspect.name)?if_exists}</td>
       		<td>${(tutor.tutorType.name)?if_exists}</td>
       		
       		<td><#if tutor.dateOfTutor?exists>${tutor.dateOfTutor?string("yyyy-MM-dd")}</#if></td>
       </@>
	</@>
	<script>
	    var bar = new ToolBar("appointTutor","指定导师",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    bar.addItem("指定导师",'appointTutor()');
	
		var form = document.appointTutorForm;
		function getIds(){
	        return(getRadioValue(document.getElementsByName("tutorId")));
	    }
		    
		function enterQuery() {
		    if (window.event.keyCode == 13)pageGoWithSize();
		}
		
		function pageGoWithSize(pageNo,pageSize){
			form.action="appointTutor.do?method=listTutor";
		    goToPage(form,pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
		}
		
		function appointTutor(){
	    	var id=getIds();
	    	if(""==id&&!confirm("你确定要置空这些学生的导师吗??")){
	    		return;
	    	}
	    	form.action="appointTutor.do?method=appointTutor";
	    	addInput(form,"requireId",id);
	    	form.submit();
	    }
	</script>
 </body>
<#include "/templates/foot.ftl"/>