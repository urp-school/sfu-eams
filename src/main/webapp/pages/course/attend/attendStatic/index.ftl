<#include "/templates/head.ftl"/>
<BODY> 
    <table id="attendStaticBar" width="100%"></table>
    <table class="frameTable_title">
        <tr >
          <form name="attendStaticForm" target="attendStaticListForm" method="post" action="?method=index" onsubmit="return false;">
          <td style="width:35%;"></td>
          <input type="hidden" name="attendStatic.calendar.id" value="${calendar.id!}" />
          <input type="hidden" name="attendStatic.student.type.id" value="${studentType.id}"/>
          <input type="hidden" name="calendar.id" value="${calendar.id!}" />
          <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
    <table class="frameTable">
        <tr>
            <td valign="top" width="19%" class="frameTable_view">
              <table>
              	  <tr>
	              	<td>学生学号：</td>
	              	<td><input name="attendStatic.student.code" type="text" value="${RequestParameters["attendStatic.student.code"]?if_exists}" style="width:100px" maxlength="32"/></td>
	              </tr>
	              <tr>
	              	<td>学生姓名：</td>
	              	<td><input name="attendStatic.student.name" type="text" value="${RequestParameters["attendStatic.student.name"]?if_exists}" style="width:100px" maxlength="32"/></td>
	              </tr>
	              <tr>
	              	<td>班级名称：</td>
	              	<td><input name="adminClass.name" type="text" value="${RequestParameters["adminClass.name"]?if_exists}" style="width:100px" maxlength="32"/></td>
	              </tr>
	              <tr>
	              	<td>课程序号：</td>
	              	<td><input name="attendStatic.task.seqNo" type="text" value="${RequestParameters["attendStatic.task.seqNo"]?if_exists}" style="width:100px" maxlength="32"/></td>
	              </tr>
	              <tr>
	              	<td>课程名称：</td>
	              	<td><input name="attendStatic.course.name" type="text" value="${RequestParameters["attendStatic.course.name"]?if_exists}" style="width:100px" maxlength="32"/></td>
	              </tr>
	              <tr>
	              	<td>上课院系：</td>
	              	<td>
		              	<select name="attendStatic.department.id" value="${RequestParameters["attendStatic.department.id"]?if_exists}" style="width:100px">
			     			<option value=""><@bean.message key="common.all"/></option>
			     			<#list (departmentList)?sort_by("code") as depart>
	                		<option value=${depart.id!}><@i18nName depart/></option>
			     			</#list>
			     		</select>	
	              	</td>
	              </tr>
	              <tr>
	              	<td>上课教师：</td>
	              	<td>
	              	<input type="text" name="teacher.name" value="${RequestParameters["teacher.name"]?if_exists}" style="width:100px" maxlength="20"/>
	              	</td>
	              </tr>
	              <tr>
	              	<td>考勤日期：</td>
	              	<td>
	              	<input type="text" name="attenddate" value="${RequestParameters["attenddate"]?if_exists}" style="width:100px" maxlength="20"/>
	              	</td>
	              </tr>
	              <tr>
	              	<td>考勤时间：</td>
	              	<td><input type="text" name="attendStatic.attendtime" value="${RequestParameters["attendStatic.attendtime"]?if_exists}" style="width:100px" maxlength="20"/></td>
	              </tr>
	              <tr>
	              	<td>考勤类型：</td>
	              	<td>
	              	<select name="attendStatic.attendtype" value="$ {RequestParameters["attendStatic.attendtype"]?if_exists}" style="width:100px">
	              			<option value="" selected="selected">全部</option>
			     			<option value="1" >出勤</option>
					   		<option value="2" >缺勤</option>
					   		<option value="3" >迟到</option>
					   		<option value="4" >早退</option>
					   		<option value="5" >请假</option>
			     	</select>
	              	</td>
	              </tr>
	              <tr align="center">
	   					<td colspan="2">
	     					<button onClick="search();" accesskey="Q" class="buttonStyle" style="width:80px">
	      						 <@bean.message key="action.query"/>(<U>Q</U>)
	     					</button>
	    				</td>
	   				</tr>
              </table>
            </td>
    </form>
            <td valign="top" bgcolor="white">
                <iframe  src="#" id="attendStaticListForm" name="attendStaticListForm" scrolling="no" marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
	<script>
	
		var bar=new ToolBar("attendStaticBar","学生考勤明细",null,true,true);
		bar.addBlankItem();
        search();
        function search(){
            var form = document.attendStaticForm;
            attendStaticForm.action = "?method=search";
            form.target="attendStaticListForm";
            form.submit();
        }
        
        
	</script>
</body>
<#include "/templates/foot.ftl"/> 
  