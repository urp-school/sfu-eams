  <td class="infoTitle"  style="width:60px;"><@bean.message key="entity.studentType"/>:</td>
  <td align="bottom"  style="width:100px;">
      <select id="stdType" name="calendar.studentType.id" style="width:100px;">               
        <option value="${RequestParameters['calendar.studentType.id']?if_exists}"></option>
      </select>
   </td>
   <td class="infoTitle" style="width:60px;"><@bean.message key="attr.year2year"/>:</td>
   <td style="width:100px;">
     <select id="year" name="calendar.year"  style="width:100px;">                
        <option value="${RequestParameters['calendar.year']?if_exists}"></option>
      </select>
    </td>
    <td class="infoTitle" style="width:50px;"><@bean.message key="attr.term"/>:</td>
    <td style="width:50px;">     
     <select id="term" name="calendar.term" style="width:80px;">
        <option value="${RequestParameters['calendar.term']?if_exists}"></option>
      </select>
   </td>