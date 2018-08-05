<#include "/templates/head.ftl"/>
<BODY>
<table id="toolbar" ></table>
  <@getMessage/>
  <@table.table id="listTable" width="100%" sortable="true">
    <@table.thead>
    	<@table.td text=""/>
        <@table.sortTd text="课程代码" id="newCourse.course.code"/>
        <@table.sortTd text="课程名称" id="newCourse.course.name"/>
        <@table.sortTd text="顺序号"   id="newCourse.ordernum"/>
        <@table.sortTd text="置顶"     id="newCourse.priority"/>
     </@>
     <#if newCourses??>
     <@table.tbody datas=newCourses;newCourse>
        <@table.selectTd id="newCourseId" value="${(newCourse.id)?if_exists}" type="radio"/>
        <td>${(newCourse.course.code)?if_exists}</td>
        <td><a href="courseSearch.do?method=info&type=course&id=${(newCourse.course.id)?if_exists}">${(newCourse.course.name)?if_exists}</a></td>
        <td>${(newCourse.ordernum)?if_exists}</td>
        <td>
        	<#if (newCourse.priority?if_exists)==0>否</#if>
        	<#if (newCourse.priority?if_exists)==1>是</#if>
        </td>
     </@>
     </#if>
   </@>
 <@htm.actionForm name="myForm" entity="newCourse" action="newCourse.do"/>
  <script language="javascript">
  bar= new ToolBar("toolbar","新开课程列表");
  bar.addItem("修改",edit);
  bar.addItem("添加",add);
  bar.addItem("删除",remove); 
  defaultOrderBy="${RequestParameters['orderBy']?default('null')}";  
  function getIds(){
 	 return(getRadioValue(document.getElementsByName("newCourseId")));
  }  
  function remove(){
  	var baseInfoIds = getIds();  	
    if(baseInfoIds==""){
       window.alert('请选择一个数据');
    }else {
       if(!confirm("确认删除操作?")){return;}
       addParamsInput(form,queryStr);
       form.action="newCourse.do?method=remove&type=newCourse&id="+baseInfoIds;       
       form.submit();
     }
  }
  </script>
</body>
<#include "/templates/foot.ftl"/>