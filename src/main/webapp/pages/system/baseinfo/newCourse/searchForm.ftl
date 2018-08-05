  <table width="100%" onkeypress="DWRUtil.onReturn(event, searchDepartment)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>新开课程查询</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
   </tr>
    <form name="searchForm" action="newCourse.do?method=search" target="contentListFrame" method="post" onsubmit="return false;">
    <tr><td>课程代码:</td><td><input type="text" name="newCourse.course.code" style="width:100px;"/></td></tr>
   	<tr><td>课程名称:</td><td><input type="text" name="newCourse.course.name" style="width:100px;"/></td></tr>
   	<tr><td>顺序号:</td><td><input type="text" name="newCourse.ordernum" style="width:100px;"/></td></tr>
   	<tr><td>置顶:</td><td><select id="newCourse.priority" name="newCourse.priority">
			  <option value="" selected>...</option>
			  <option value ="1" >是</option>
			  <option value ="0">否</option>			  
		  	</select>   	
   	</td></tr>
    <#include "../../base.ftl"/>
    <tr height="50px"><td align="center" colspan="2"><input type="button" onclick="search();" value="查询" class="buttonStyle"/></td></tr>
    </form>
  </table>
