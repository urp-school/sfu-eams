<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url"/>"></script>
<body  >
<#assign labInfo>新开课程基本信息</#assign>  
<#include "/templates/back.ftl"/>   
     <table width="90%" align="center"  class="formTable">
       <tr class="darkColumn">
         <td align="left" colspan="4"><B>新开课程</B></td>
       </tr>
	   <form action="newCourse.do?method=edit" name="departmentForm" method="post" onsubmit="return false;">
	   <@searchParams/>
	   <tr>
	     <td id="f_code" class="title"><font color="red">*</font>新开课程号码:</td>
	     <td>
	     <input id="codeValue" type="text"  name="newCourse.course.code" value="${(newCourse.course.code)?if_exists}" maxLength="19" style="width:80px;"/>
         <input type="hidden"  name="newCourse.id" value="${(newCourse.id)?if_exists}"/>
        </td>
	     <td id="f_name" class="title"></td>
	     <td></td>
	   </tr>
	   <tr>
	     <td id="f_ordernum" class="title">顺序号:</td>
	     <td><input type="text" name="newCourse.ordernum" value="${(newCourse.ordernum)?if_exists}" maxLength="20"/></td>
	     <td id="f_priority" class="title">置顶:</td>
	     <td><input type="radio" name="newCourse.priority"  value="0" >否</input>
	     	<input type="radio" name="newCourse.priority"  value="1" checked>是</input></td>
	   </tr>	   
	   
	   <tr>
	     <td colspan="4" align="center" class="darkColumn">
   	       <input type="hidden" name="stdTypeIdSeq" value=""/>
           <input type="button" onClick='save(this.form)' value="<@msg.message key="system.button.submit"/>"  class="buttonStyle"/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <input type="button" onClick='reset()' value="<@msg.message key="system.button.reset"/>"  class="buttonStyle"/> 
	     </td>
	   </tr>
	   </form> 
     </table>
    </td>
   </tr>
  </table>
   <script language="javascript" > 
     function reset(){
       	   document.departmentForm.reset();
     }
     function save(form){
        	form.action = "newCourse.do?method=save"
        	form.submit();     	
     }
     <#if (newCourse.priority?if_exists)??>
     prioritydefault();
     function　 prioritydefault(){
     	var pri=document.getElementsByName("newCourse.priority");
     	pri[${newCourse.priority}].checked=true;
     }
     </#if>  
 </script>
 </body> 
</html>