  <table  class="searchTable"  onkeypress="DWRUtil.onReturn(event, search)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@msg.message key="textbook.search"/></B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <tr>
      <td><@msg.message key="attr.code"/>:</td>      
      <td><input type="text" name="textbook.code"  style="width:100px;" /></td>
    </tr>
    <tr>
      <td><@msg.message key="attr.name"/>:</td>
      <td id="name"><input type="text" name="textbook.name" maxlength="20" style="width:100px;"/></td>
    </tr>
    <tr>
      <td><@msg.message key="textbook.author"/>:</td>
      <td id="auth"><input type="text" name="textbook.auth" maxlength="20" style="width:100px;"/></td>
    </tr>
    <tr>
      <td><@msg.message key="entity.press"/>:</td>
      <td id="press.id">      
         <@htm.i18nSelect datas=presses selected="" name="textbook.press.id" style="width:100px;">
         <option value=""><@msg.message key="common.all"/></option>
         </@>
      </td>
    </tr>
    <tr>
      <td><@msg.message key="entity.textbookAwardLevel"/>:</td>
      <td>      
         <@htm.i18nSelect datas=awardLevels selected="" name="textbook.awardLevel.id" style="width:100px;">
         <option value=""><@msg.message key="common.all"/></option>
         </@>
      </td>
    </tr>
    <tr  align="center">
      <td colspan="2">
    	  <button onclick="search();" ><@msg.message key="action.query"/></button>
      </td>
    </tr>
  </table>