<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign labInfo>选课筛选保留学生范围</#assign>
<#include "/templates/help.ftl"/>
    <form name="actionForm" method="post" action="reservedStudent.do?method=save" onsubmit="return false;">
    <input type="hidden" name="reservedStudent.calendar.id" value="${RequestParameters['calendar.id']}"/>
    <input type="hidden" name="params" value="&calendar.id=${RequestParameters['calendar.id']}"/>
    <table class="listTable" width="100%">
     <tr align="center" class="darkColumn">
      <td>年级</td>
      <td>专业</td>
      <td>专业方向</td>
      <td>操作</td>
     </tr>
     <#list reservedStudents as rs>
     <tr align="center">
      <td>${rs.grade?default('')}</td>
      <td><#if rs.major?exists>[${rs.major.code}]${(rs.major.name)}</#if></td>
      <td><#if rs.majorField?exists>[${rs.majorField.code}]${(rs.majorField.name)}</#if></td>
      <td><button onclick="remove(${rs.id})">删除</button></td>
     </tr>
     </#list>
     <tr align="center">
      <td><font color="red">*</font><input name="reservedStudent.grade" maxLength="10" style="width:100px"/></td>
      <td><select name="reservedStudent.major.id" style="width:200px">
          <option value="">...</option>
          <#list majors as major>
          <option value="${major.id}">${major.code}|${major.name}</option>
          </#list>
      </td>
      <td><select name="reservedStudent.majorField.id" style="width:200px">
          <option value="">...</option>
          <#list majorFields as majorField>
          <option value="${majorField.id}">${majorField.code}|${majorField.name}</option>
          </#list>
      <td><button onclick="save(this.form)">添加</button></td>
     </tr>
  </table>
  </form>
  <script>
      var form=document.actionForm;
      function save(form){
         if(form['reservedStudent.grade'].value==''){
             alert("请填写年级");
             return;
         }
         form.submit();
      }
      function remove(id){
        if(confirm("是否确定删除?")){
            form.action="reservedStudent.do?method=remove&reservedStudentId="+id;
            form.submit();
        }
      }
  </script>
</body>
<#include "/templates/foot.ftl"/>