<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar" width="100%"></table>
	<script>
	 var bar = new ToolBar('backBar','管理评教',null,true,true);
	 bar.setMessage('<@getMessage/>');
	 bar.addItem("统计评教结果","statisticResult()");
	 bar.addItem("各院系评教比较","compareAllCollege()");
	 bar.addItem("各院系线图比较","lieChart()");
	 bar.addItem("<@msg.message key="action.export"/>","expFunction()","action.gif","导出的条件来自左边的查询条件")
	</script>
	<#include "../queryConditions.ftl">
	<script>
		var form = document.searchForm;
		var action="questionnaireStat.do";
		function search(pageNo,pageSize,orderBy){
		    form.action=action+"?method=search";
		    goToPage(form,pageNo,pageSize,orderBy);
		}
		function lieChart(){
			var errors =validateCalendar(true,true,true) 
			if(""!=errors){
				alert(errors);
				return;
			}
			form.action=action+"?method=lieOfEvaluate";
			form.submit();
		}
		function expFunction(){
			form.action=action+"?method=export";
			form.submit();
		}
		function compareAllCollege(){
			var errors =validateCalendar(true,true,true) 
			if(""!=errors){
				alert(errors);
				return;
			}
			form.action=action+"?method=departDistributeStat";
			form.submit();
		}
		function statisticResult(){
		    form.action=action+"?method=statHome";
		    form.target="";
		    form.submit();
		}
		function validateCalendar(stdTypeNull,yearNull,termNull){
			var errors="";
			if(stdTypeNull){
				var stdType = document.getElementById("stdType");
				if(""==stdType.value){
					errors+="学生类别不能为空\n";
				}
			}
			if(yearNull){
				var year = document.getElementById("year");
				if(""==year.value){
					errors+="学年度不能为空\n";
				}
			}
			if(termNull){
				var term = document.getElementById("term");
				if(""==term.value){
					errors+="学期不能为空\n";
				}
			}
			if(""!=errors){
				errors+="在左边的查询条件中";
			}
			return errors;
		}
	    search(1);
	</script>
<#include "/templates/foot.ftl"/>