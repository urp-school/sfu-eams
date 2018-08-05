<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(self),f_frameStyleResize(parent)">
<table id="stdListBar"></table>
	<@table.table id="student" sortable="true" headIndex="1" width="100%">
		<form name="stdListForm" method="post" onsubmit="return false;" action="courseTakeForTask.do?method=stdList">
	    <tr bgcolor="#ffffff" onkeypress="DWRUtil.onReturn(event, query)">
	      	<td align="center" width="3%">
	        	<img src="${static_base}/images/action/search.gif"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/>
	      	</td>
	      	<td><input style="width:100%" type="text" name="std.code" maxlength="32" value="${RequestParameters['std.code']?if_exists}"/></td>
	      	<td><input style="width:100%" type="text" name="std.name" maxlength="20" value="${RequestParameters['std.name']?if_exists}"/></td>
	      	<td><select style="width:100%" name="std.basicInfo.gender.id"><option value="">..</option><#list genders as gender><option <#if gender.id?string==RequestParameters['std.basicInfo.gender.id']?default("")>selected</#if> value="${gender.id}"><@i18nName gender/></option></#list></select></td>
	      	<td><input style="width:100%" type="text" name="std.enrollYear" maxlength="7" value="${RequestParameters['std.enrollYear']?if_exists}"/></td>
	      	<td><input style="width:100%" type="text" name="std.department.name" maxlength="20" value="${RequestParameters['std.department.name']?if_exists}"/></td>      
	      	<td><input style="width:100%" type="text" name="std.firstMajor.name" maxlength="20" value="${RequestParameters['std.firstMajor.name']?if_exists}"/></td>    
	      	<td><input style="width:100%" type="text" name="std.firstAspect.name" maxlength="20" value="${RequestParameters['std.firstAspect.name']?if_exists}"/></td>    	      	
	      	<td><input style="width:100%" type="text" name="adminClassName" maxlength="20" value="${RequestParameters['adminClassName']?if_exists}"/></td>
		</tr>
		</form>
		<@table.thead>
			<@table.selectAllTd id="stdNo"/>
			<@table.sortTd name="attr.stdNo" id="std.code"/>
			<@table.sortTd name="attr.personName" id="std.name"/>
			<@table.sortTd name="entity.gender" id="std.basicInfo.gender.name"/>
			<@table.sortTd name="attr.enrollTurn" id="std.enrollYear"/>
			<@table.sortTd name="entity.college" id="std.department.name"/>
			<@table.sortTd name="entity.speciality" id="std.firstMajor.name"/>
			<@table.sortTd name="entity.specialityAspect" id="std.firstAspect.name"/>
			<@table.td name="entity.adminClass"/>
		</@>
		<@table.tbody datas=stdList;std>
			<@table.selectTd id="stdNo" value=std.code/>
		    <td>${std.code}</td>
	        <td><@i18nName std/></td>
	        <td><@i18nName std.basicInfo?if_exists.gender?if_exists/></td>
	        <td>${std.enrollYear}</td>
	        <td><@i18nName std.department/></td>
	        <td><@i18nName std.firstMajor?if_exists/></td>
	        <td><@i18nName std.firstAspect?if_exists/></td>
	        <td><@getBeanListNames std.adminClasses/></td> 
		</@>
	</@>
  <script>
    function query(pageNo,pageSize){
       var form = document.stdListForm;
       goToPage(form,pageNo,pageSize);
    }
    function selectStdNoOf(isOdd){
        var stdNos = document.getElementsByName("stdNo");
        for(var i=0;i<stdNos.length;i++){
           	var number = new Number(stdNos[i].value.substring(stdNos[i].value.length-1));
          	stdNos[i].checked = ((number % 2 != 0 && isOdd) || (number % 2 == 0 && !isOdd));
        }
    }
    function addSelected(){
       var stdNos = getCheckBoxValue(document.getElementsByName("stdNo"));
       if(stdNos==""){
          alert("请选择学生");return;
       }
       parent.addStdNos(stdNos);
    }
   var bar = new ToolBar('stdListBar','查询学号添加',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("单号","javascript:selectStdNoOf(true)",'update.gif');
   bar.addItem("双号","javascript:selectStdNoOf(false)",'update.gif');
   bar.addItem("添加学号","javascript:addSelected()",'new.gif');
</script>
 </body>
<#include "/templates/foot.ftl"/>