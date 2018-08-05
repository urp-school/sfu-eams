<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
    <table id="bar"></table>
    <table width="100%" class="frameTable">
        <tr>
            <td class="frameTable_view" width="24%">
	            <#include "searchForm.ftl">
            </td>
            <td valign="top">
                <iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no">
                </iframe>
            </td>
        </tr>
    </table>
    <script>
    	var DEFAULTPAGE = "defaultPage"
    	var FEEDEFDAULTPREFIX = "feeDetail.std.type";
    	var CREDITFEEDEFAULTPREFIX = "creditFeeDefault.stdType";
    	var selectDo = DEFAULTPAGE;
        var bar = new ToolBar("bar", "学分标准收费管理", null, true, true);
        bar.addItem("<@msg.message key="action.help"/>", "javascript:alert('正在建设中...')", "help.png");
       	    
        var form = document.feeDefaultConfigForm;
        
	    var feeType = $("feeTypeId");
	    feeType.selectedIndex = feeType.length - 1;
	    var stdType = $("stdTypeOfSpeciality");
	    stdType.selectedIndex = stdType.length - 1;
       	
       	function selectOnClick(selectPage) {
       		if (selectPage == null || selectPage == "") {
       			selectPage = DEFAULTPAGE;
       		}
       		if (selectDo != selectPage) {
   				if (selectDo == DEFAULTPAGE) {
   					$("viewFee").style.display = "none";
   					$("viewCredit").style.display = "block";
   				} else {
   					$("viewFee").style.display = "block";
   					$("viewCredit").style.display = "none";
   				}
   				stdType.selectedIndex = stdType.length - 1;
    			selectDo = selectPage;
       		}
       	}
        document.getElementById(selectDo).onclick();
        
        function toDefaultPage(td) {
            clearSelected(menuTable,td);
            setSelectedRow(menuTable,td);
        	selectOnClick(DEFAULTPAGE);
        }
        
        function toCreditPage(td) {
            clearSelected(menuTable,td);
            setSelectedRow(menuTable,td);
        	selectOnClick("creditPage");
        }
        
        function doFeeDafaultAction() {
            form.action = "feeDefault.do?method=search";
            form.target = "displayFrame";
            form.submit();
        }
        
        function doCreditFeeAction() {
            form.action = "creditFeeDefault.do?method=search";
            form.submit();
        }
    </script>
</BODY>
<#include "/templates/foot.ftl"/>