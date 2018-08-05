 <table width="100%">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@msg.message key="baseinfo.searchStudent"/></B>      
	      </td>
	    </tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>	
  </table>
  <table width='100%' class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">	    
    	<tr>
	     <td  class="infoTitle" width="35%">学生类别:</td>
	     <td>
	     <@htm.i18nSelect datas=stdTypeList selected="" name="switch.teachCalendar.studentType.id" style="width:100%">
		<option value="">全部</option>
		</@>
	     </td>
		</tr>
    	<tr>
	     <td class="infoTitle">是否发布:</td>
	     <td>
	      <select name="courseArrangeSwitch.isPublished">
	        <option value="true">是</option>
	        <option value="false">否</option>
	     
	      </select>
         </td>
        </tr>
    	<tr>
	    <tr align="center">
	     <td colspan="2">
		     <button style="width:60px" class="buttonStyle" onClick="search(1)"><@bean.message key="action.query"/></button>
	     </td>               
	    </tr>
  </table>
