	<table class="searchTable">
	    <form name="planSearchForm" method="post" action="" onsubmit="return false;">
	    <tr>
	     	<td class="infoTitle"><@bean.message key="std.code"/></td>
	     	<td><input name="substitutionCourse.std.code" maxlength="32" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td class="infoTitle"><@msg.message key="attr.personName"/></td>
	     	<td><input name="substitutionCourse.std.name" maxlength="20" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr align="center">
	     	<td colspan="2">
		        <input type="reset" class="buttonStyle" value="<@msg.message key="action.reset"/>" style="width:60px"/>
			    <input type="submit" onClick="doSearch()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>		    
	     	</td>
	    </tr>
	    </form>
	</table>
	<#include "/templates/stdTypeDepart3Select.ftl"/>
	<script>
	    var form = document.planSearchForm;
	    function doSearch() {
	    	var form = document.planSearchForm;
	    	form.target = "stdSubstitutionCourseFrame";
	    	form.action = "stdSubstitutionCourse.do?method=search";
	    	form.submit();
	    }
	</script>