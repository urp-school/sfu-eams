<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
    <table id="tutorManager"></table>
    <script>
	    var bar = new ToolBar("tutorManager","导师管理",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    bar.addItem("<@msg.message key="action.new"/>","add()");
	    bar.addItem("<@msg.message key="action.cancel.tutor"/>","cancel()");
	    bar.addItem("<@msg.message key="action.edit"/>","edit()");
	    bar.addItem("按导师类别导出","exportData()");
    </script>
     <@table.table sortable="true" id="listTable" align="center" width="100%" headIndex="1">
    <form name='appointStdForm' method="post" action="" onsubmit="return false;">
       <tr align="center" class="darkColumn" bgcolor="#ffffff" onKeyDown="javascript:enterQuery(document.appointStdForm)">
	     <td><img src="${static_base}/images/action/search.png"  align="top" onClick="query(document.appointStdForm)" alt="在结果中过滤"/></td>
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
	   		<@table.td text=""/>
	   		<@table.sortTd id="tutor.code" text="职工号"/>
	   		<@table.sortTd id="tutor.name" name="attr.personName"/>
	   		<@table.sortTd id="tutor.department.name" name="entity.department"/>
	   		<@table.sortTd id="tutor.speciality.name" name="entity.speciality"/>
	   		<@table.sortTd id="tutor.aspect.name" name="entity.specialityAspect"/>
	   		<@table.sortTd id="tutor.tutorType.name" text="导师类别"/>
	   		<@table.sortTd id="tutor.dateOfTutor" text="任职时间"/>
	   </@>
       <@table.tbody datas=tutors;tutor>
       		<@table.selectTd type="radio" id="tutorId" value=tutor.id/>
       		<td>${(tutor.code)?if_exists}</td>
       		<td><@i18nName (tutor)?if_exists/></td>
       		<td><@i18nName (tutor.department)?if_exists/></td>
       		<td><@i18nName (tutor.speciality)?if_exists/></td>
       		<td><@i18nName (tutor.aspect)?if_exists/></td>
       		<td><@i18nName (tutor.tutorType)?if_exists/></td>
       		<td>${(tutor.dateOfTutor?string("yyyy-MM-dd"))?if_exists}</td>
       </@>
	</@>
	<@htm.actionForm name="actionForm" action="tutorManager.do" entity="tutor" onsubmit="return false;">
		<input type="hidden" name="fileName" value=""/>
		<input type="hidden" name="titles" value="职工号,导师姓名,导师所在院系,导师专业,导师专业方向,导师类别,任职时间"/>
		<input type="hidden" name="keys" value="code,name,depratment.name,speciality.name,aspect.name,tutorType.name,tutor.dateOfTutor"/>
	</@>
<script>
 	function edit(){
       var tutorId = getRadioValue(document.getElementsByName("tutorId"));
       if(tutorId=="")
       window.alert('<@bean.message key="common.select"/>!');
       else {
         form.action="tutorManager.do?method=edit&tutorId=" +tutorId;
         form.target="";
       	 form.submit();
       }
    }
    function add(){
      form.action="tutorManager.do?method=doTeacherList";
      form.submit();
    }
    function enterQuery(form) {
       if (window.event.keyCode == 13)query(form);
    }
    function query(form){
        form.action="tutorManager.do?method=list";
    	form.submit();
    }
    function cancel(){
       var tutorId=getRadioValue(document.getElementsByName("tutorId"));
       if(tutorId=="")
       		window.alert('<@bean.message key="common.select"/>!');
       else {
	   		form.action="tutorManager.do?method=doCovertTutorToTeacher&teacherId="+tutorId;
	    	form.submit();
    	}
    }
    
    function exportData() {
    	var tutorType = document.appointStdForm["tutor.tutorType.id"];
    	var tutorTypeName = tutorType.options[tutorType.selectedIndex].text;
    	if (!confirm("当前选择的导师类型为“" + tutorTypeName + "”,要导出吗？")) {
    		return;
    	}
		form["fileName"].value = tutorTypeName + "类别的导师";
    	if (!(null == tutorType.value || "" == tutorType.value)) {
    		addInput(form, "tutor.tutorType.id", tutorType.value, "hidden");
    	}
    	exportList();
    }
</script>
 </body>
<#include "/templates/foot.ftl"/>
