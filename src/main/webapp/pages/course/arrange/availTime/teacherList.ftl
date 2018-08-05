<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
   	<table width="100%">
    	<tr>
      		<td class="infoTitle" align="left" valign="bottom">
       			<img src="${static_base}/images/action/info.gif" align="top"/>
          		<B><@bean.message key="entity.teacher"/><@bean.message key="common.list"/></B>
      		</td>
      	</tr>
    	<tr>
      		<td colspan="8" style="font-size:0px">
          		<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      		</td>
   		</tr>
  	</table>
	<#assign paginationName="teacherList"/>
   	<@table.table width="100%" id="teacherListTable">
     	<@table.thead>
      		<@table.td width="15%" text=""/>
      		<@table.td name="attr.personName"/>
     	</@>
     	<@table.tbody simplePageBar=true datas=teacherList;teacher,teacher_index>
      		<td>${teacher_index + 1}</td>
      		<td class="paddingp"<#if teacher_index=0> id="defaultTeacher"</#if> style="height:20px;width:100%" align="center" onclick="javascript:setSelectedRow(teacherListTable,this);availTimeInfo('${teacher.id}')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">${teacher.name}</td>
		</@>
	</@>
  	<script language="javascript">
        function pageGo(pageNo) {
            self.parent.pageGo(pageNo);
        }
    	function availTimeInfo(teacherId) {
       		self.parent.availTimeInfo(teacherId);
    	}
    	
   		<#if teacherList?size != 0> 
		defaultTeacher.onclick();
   		<#else>
		parent.availTimeFrame.window.location = "teacherAvailTime.do?method=teacherAvailTimeInfo";
   		</#if>
  	</script>
</body>
<#include "/templates/foot.ftl"/>