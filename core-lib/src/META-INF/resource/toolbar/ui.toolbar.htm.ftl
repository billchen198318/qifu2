<br/>		
<div class="app-title">
	<div>
		<h1>${programName}</h1>
		<p>${programId}</p>  
		
		<div>		
			
<#if refreshEnable == "Y" >					
			<img class="btn btn-secondary btn-sm" alt="refresh" title="Refresh" src="./images/refresh.png" onclick="${refreshJsMethod}"/>
			&nbsp;
</#if>				
			
<#if createNewEnable == "Y" >			
			<img class="btn btn-secondary btn-sm" alt="create" title="Create new" src="./images/create.png" onclick="${createNewJsMethod}"/>
			&nbsp;
</#if>			

<#if saveEnabel == "Y" >			
			<img class="btn btn-secondary btn-sm" alt="save" title="Save / Update" src="./images/save.png" onclick="${saveJsMethod}"/>
			&nbsp;
</#if>	
			
<#if cancelEnable == "Y" >			
			&nbsp;<font color="#BDBDBD">|</font>&nbsp;
			<img class="btn btn-secondary btn-sm" alt="close" title="Close" src="./images/close.png" onclick="${cancelJsMethod}"/>			
</#if>			

		</div>
	</div>    
</div>        
