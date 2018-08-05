<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" > 
<table id="myBar"></table>
<table width="100%" align="center" class="formTable">
  <form name="gradeForm" action="studentAuditOperation.do?method=saveCourseType" method="post" onsubmit="return false;">
   <input type="hidden" name="studentId" value="${RequestParameters['studentId']}">
   <input type="hidden" name="courseId" value="${RequestParameters['courseId']}">
    <tr>
  	 <td class="title" width="20%"><input type="checkbox" onclick="changeState(this,'courseType.id')"/>&nbsp;课程类别：</td>
     <td><@htm.i18nSelect datas=courseTypeList?sort_by("name") selected="" name="courseType.id" style="width:150px" disabled="true"/>
     </td>
    </tr>
</form> 
 </table>

</body>
<script>
   var bar = new ToolBar("myBar","调整课程类别）",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("提交修改","save()");
   bar.addBack("<@msg.message key="action.back"/>");
   var form= document.gradeForm; 
   function save(){
      if(confirm("确定要调整吗?")){
         form.submit();
      }
   }
   function changeState(check,name){ 
       form[name].disabled=!check.checked;
       if(form[name].type=="text"){
          form[name].value=infos[name];
       }
    }
</script>
<#include "/templates/foot.ftl"/>