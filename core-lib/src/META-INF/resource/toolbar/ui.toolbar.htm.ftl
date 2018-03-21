<table border="0" width="100%" cellspacing="2" cellpadding="2" id="${id}" >
	<tr valign="middle" align="left">
		<td align="left" width="100%" height="32px" style="border:0px #ebeadb solid; border-radius: 7px; background: linear-gradient(to top, #dddddd , #FFFFFF);" >
			<div>
			
			
<#if refreshEnable == "Y" >					
			<img alt="refresh" title="Refresh" src="./images/refresh.png" onclick="${refreshJsMethod}"/>
			&nbsp;
</#if>				
			
<#if createNewEnable == "Y" >			
			<img alt="create" title="Create new" src="./images/create.png" onclick="${createNewJsMethod}"/>
			&nbsp;
</#if>			

<#if saveEnabel == "Y" >			
			<img alt="save" title="Save / Update" src="./images/save.png" onclick="${saveJsMethod}"/>
			&nbsp;
</#if>	
			
<#if cancelEnable == "Y" >			
			&nbsp;<font color="#BDBDBD">|</font>&nbsp;
			<img alt="close" title="Close" src="./images/close.png" onclick="${cancelJsMethod}"/>			
</#if>			
			
			
			</div>		
		</td>
	</tr>
</table>