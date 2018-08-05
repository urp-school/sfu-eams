	<table class="searchTable">
	    <form name="planSearchForm" method="post" action="" onsubmit="return false;">
	    <tr>
	     	<td class="infoTitle">课程代码</td>
	     	<td><input name="examinationPaper.course.code" maxlength="32" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td class="infoTitle">课程名称</td>
	     	<td><input name="examinationPaper.course.name" maxlength="20" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr align="center">
	     	<td colspan="2">
		        <input type="reset" class="buttonStyle" value="<@msg.message key="action.reset"/>" style="width:60px"/>
			    <input type="submit" onClick="searchExaminationPaper()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>		    
	     	</td>
	    </tr>
	    </form>
	</table>
	<#include "/templates/stdTypeDepart3Select.ftl"/>