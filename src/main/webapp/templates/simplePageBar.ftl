	  <script type="text/javascript">
			//跳转到指定的页面
            function getPageSize(){
              return document.getElementById('pageSize').value;
            }
            function go(pageNo){
                if(null==pageNo)
                   pageNo = getPageNo();
                var number = pageNoValidator(pageNo);
                   pageSize = ${RequestParameters["pageSize"]?default(pageSize)};
                if(typeof pageGoWithSize=="function")
                  pageGoWithSize(number,pageSize);
                else
                  pageGo(number);
            }
            function setpageNo(pageNo){
			    var number = pageNoValidator(pageNo);
			    pageGo(number);
			}
			
			function pageNoValidator(pageNo){
			    var value = pageNo;
				if( isNaN(parseInt(value)) ){
					value = 1;
				}else{
					value = parseInt(value);
				}
				if(value <= 0)
					value = 1;
				if(value > ${(result[paginationName].maxPageNo)?default(maxPageNo)})
					value = ${(result[paginationName].maxPageNo)?default(maxPageNo)};
			    return value;
			}
	   </script>
	   <form name="pageSetForm" method="post" action="" onSubmit="return false;">
	   <tr class="darkColumn">
	    <td colspan="13" align="center">
	          <img src="${static_base}/images/action/firstPage.gif"title="第一页" onclick="javascript:go(1);"/>
	          <img src="${static_base}/images/action/prevPage.gif" title="前一页" onclick="javascript:go(${(result[paginationName].previousPageNo)?default(previousPageNo)});"/>
	          <img src="${static_base}/images/action/nextPage.gif" title="下一页" onclick="javascript:go(${(result[paginationName].nextPageNo)?default(nextPageNo)})"/>
	          <img src="${static_base}/images/action/lastPage.gif" title="最后页" onclick="javascript:go(${(result[paginationName].maxPageNo)?default(maxPageNo)})"/>
	       ${(result[paginationName].pageNo)?default(pageNo)}/${(result[paginationName].maxPageNo)?default(maxPageNo)}
	    </td>
	   </tr>
	   </form>