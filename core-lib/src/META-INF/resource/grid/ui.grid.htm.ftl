<table id="${id}Toolbar" width="100%" border="0" cellspacing="0" cellpadding="1" style="display:none; border:1px #ebeadb solid; background: linear-gradient(to top, #f1eee5 , #fafafa);">
  <tr>
  	
  	<td width="100px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center" >
  		<span class="badge badge-secondary">Total</span><span id="rowCount" class="badge badge-info"/>	
  	</td>
  	<td width="10px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center">&nbsp;</td>        
    <td width="20px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="right"><a href="javascript:changeQueryGridToFirst();"><img src="./icons/stock_first.png" border="0" class="btn btn-light btn-sm" alt="F" title="First page"/></a></td>
    <td width="20px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="right"><a href="javascript:changeQueryGridToPrev();"><img src="./icons/stock_left.png" border="0" class="btn btn-light btn-sm" alt="P" title="Previous"/></a></td>
    <td width="200px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center">
    <!-- pageOf.size -->
    <input type="hidden" name="pageSize" id="pageSize" value="1"/>
    	<span class="badge badge-secondary">Page</span>
    	<!-- pageOf.select -->
		<input id="select" name="select" maxlength="6" type="text" value="1" style="width: 60px; border: none; border:solid 1px #ccc; border-radius: 3px;" onChange="changeQueryGridPageOfSelect();"></input>	
			
    	&nbsp;/&nbsp;<span id="sizeShow"/>
    </td>    
    <td width="20px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="left"><a href="javascript:changeQueryGridToNext();"><img src="./icons/stock_right.png" border="0" class="btn btn-light btn-sm" alt="N" title="Next"/></a></td>
    <td width="20px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="left"><a href="javascript:changeQueryGridToLast();"><img src="./icons/stock_last.png" border="0" class="btn btn-light btn-sm" alt="L" title="Last page"/></a></td>
    <td width="10px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center">&nbsp;</td>
    <td width="200px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center">
    	<span class="badge badge-secondary">Row</span>
    	<!-- pageOf.showRow -->
    	<select name="showRow" id="showRow" style="width: 75px; border: none; border:solid 1px #ccc; border-radius: 3px;"
    			onChange="changeQueryGridPageOfShowRow();" >
    			<option value="10">10</option>
    			<option value="20">20</option>
    			<option value="30">30</option>
    			<option value="50">50</option>
    			<option value="75">75</option>
    			<option value="100">100</option>
    	</select> 
		    	    	
    </td>    
    <td style="background: linear-gradient(to top, #f1eee5 , #fafafa);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table> 
<div id="${id}">
</div>
