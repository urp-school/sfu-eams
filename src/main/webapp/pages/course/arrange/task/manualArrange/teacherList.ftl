<div id="teacherListDiv" style="position:absolute;top:34px;left:302px;width:250px;background-color:#ffffff;display:none">
<table id="arrangeTeacherBar"></table>  
  <table width="100%" align="center" class="listTable" id="teacherListTable" style="font-size:12px" >
  	<form name="teacherListForm" method="post" action="" onsubmit="return false;"> 
	<tr align="center" class="darkColumn">
      <td align="center" width="10%">
        <input type="radio" onClick="toggleCheckBox(document.getElementsByName('teacherId'),event);">
      </td>
      <td width="50%"><@bean.message key="attr.teacherId"/></td>
      <td width="40%"><@bean.message key="attr.personName"/></td>
    </tr>
    <#list task.arrangeInfo.teachers?sort_by("name") as teacher>
	  <#if teacher_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if teacher_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" onmouseover="swapOverTR(this,this.className)" 
                          onmouseout="swapOutTR(this)" align="center"
                          onclick="onRowChange(event)"
                          >
      <td class="select"><input type="radio" name="teacherId" value="${teacher.id}"></td>
      <td>${teacher.code?if_exists}</td>
      <td>${teacher.name}</td>
    </tr>
    </#list>
	</form>
  </table>
</div>
<script>
    var teachers = new Array();
    <#list task.arrangeInfo.teachers?sort_by("name") as teacher>
     teachers[${teacher_index}]={'id':'${teacher.id}','name':'${teacher.name}'};
    </#list>
    function setTeacher(){
        var teacherIds=  document.getElementsByName("teacherId");
        var teacherId ="";
        var teacherName="";
        for(var i=0;i<teacherIds.length;i++){
            if(teacherIds[i].checked){
             	teacherId= teacherIds[i].value;
             	teacherName =teachers[i].name;
            }
        }
        if(teacherId=="") {alert("请选择一个教师!");return;}
        setResourse(teacherId,teacherName,"teacher");
        teacherListDiv.style.display="none";
    }
    function closeTeacherDiv(){
        teacherListDiv.style.display="none";
    }
   var bar = new ToolBar('arrangeTeacherBar','<@bean.message key="entity.teacher"/><@bean.message key="common.list"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.confirm"/>",'setTeacher();closeTeacherDiv();','backward.gif');
   bar.addItem("<@bean.message key="action.cancel"/>",'closeTeacherDiv()','backward.gif');    
</script>