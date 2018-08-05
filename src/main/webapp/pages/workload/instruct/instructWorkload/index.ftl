<#include "/templates/head.ftl"/>
<BODY>
    <table id="backBar" width="100%"></table>
    <#include "searchCondition.ftl"/>
	<script>
        var bar = new ToolBar('backBar','教师指导工作量维护',null,true,true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("工作量系数", "toModulusPage()");
        bar.addItem("统计工作量","statWorkLoad()");

		var form = document.instructWorkloadForm;
		var action="instructWorkload.do";
		function search(pageNo,pageSize,orderBy){
		    form.action=action+"?method=search";
		    goToPage(form,pageNo,pageSize,orderBy);
		}
	    search(1);
	    function statWorkLoad(){
	       stdTypeId = form['instructWorkload.studentType.id'].value;
	       year = form['instructWorkload.teachCalendar.year'].value;
	       term=form['instructWorkload.teachCalendar.term'].value;
	       var itemType=form['instructWorkload.modulus.itemType'].value;
	       var calendarStr="&calendar.studentType.id="+stdTypeId+"&calendar.year="+year+"&calendar.term="+term+"&instructModulus.itemType="+itemType;
	       window.open('instructWorkload.do?method=statHome'+calendarStr);
	    }
	    function toModulusPage() {
            var iWidth = 800;
            var iHeight = 600;
            var iLeft = (window.screen.width - iWidth) / 2;
            var iTop = (window.screen.height - iHeight) / 2;
	        window.open("instructModulus.do?method=search", "list", "Scrollbars=no,Toolbar=no,Location=no,Direction=no,Resizeable=no,Width=" + iWidth + ",Height=" + iHeight + ",top=" + iTop + ",left=" + iLeft);
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>