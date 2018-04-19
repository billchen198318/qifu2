<p style="margin-bottom: 8px"></p>		
<div class="app-title" style="background: linear-gradient(to top, #f8f9fa, #ffffff);">
	<div>
		<h1>${programName}</h1>
<#if description?? && description != "">
		<p>${description}</p>
</#if>		
		<div>		
			
<#if refreshEnable == "Y" >					
			<img class="btn btn-light btn-sm" alt="refresh" title="Refresh" src="./images/refresh.png" onclick="${refreshJsMethod}"/>
			&nbsp;
</#if>				
			
<#if createNewEnable == "Y" >			
			<img class="btn btn-light btn-sm" alt="create" title="Create new" src="./images/create.png" onclick="${createNewJsMethod}"/>
			&nbsp;
</#if>			

<#if saveEnabel == "Y" >			
			<img class="btn btn-light btn-sm" alt="save" title="Save / Update" src="./images/save.png" onclick="${saveJsMethod}"/>
			&nbsp;
</#if>	
			
<#if cancelEnable == "Y" >			
			&nbsp;<font color="#BDBDBD">|</font>&nbsp;
			<img class="btn btn-light btn-sm" alt="close" title="Close" src="./images/close.png" onclick="${cancelJsMethod}"/>			
</#if>			

		</div>
	</div>    
	
        <ul class="app-breadcrumb breadcrumb">
          <li class="breadcrumb-item" title="${programName}"><font color="#6c757d">${programId}</font></li>
        </ul>
        		
</div>        
