<#include "/templates/head.ftl"/>
 <script language="javascript">
 	function displayDiv(divId){
       var div = document.getElementById(divId);

       if (div.style.display=="block"){
        div.style.display="none";
       }else{
         div.style.display="block";  
       }
   }
   function getYear(){
      return document.getElementById("year");
   }
 </script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<#include "../teachWorkload/searchForm.ftl"/>
<script>
	var form = document.teachWorkloadForm;
	var action="teachWorkloadSearch.do";
	function search(pageNo,pageSize,orderBy){
	    form.action=action+"?method=search";
	    goToPage(form,pageNo,pageSize,orderBy);
	}
    search(1);
</script>
</body>
<#include "/templates/foot.ftl"/>