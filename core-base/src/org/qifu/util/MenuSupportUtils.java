/* 
 * Copyright 2012-2017 qifu of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package org.qifu.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.qifu.base.AppContext;
import org.qifu.base.Constants;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.DefaultResult;
import org.qifu.base.model.YesNo;
import org.qifu.model.MenuItemType;
import org.qifu.model.MenuResultObj;
import org.qifu.po.TbSys;
import org.qifu.po.TbSysMenu;
import org.qifu.po.TbSysProg;
import org.qifu.service.ISysMenuService;
import org.qifu.service.ISysProgService;
import org.qifu.service.ISysService;
import org.qifu.vo.SysMenuVO;
import org.qifu.vo.SysProgVO;
import org.qifu.vo.SysVO;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@SuppressWarnings("unchecked")
public class MenuSupportUtils {
	private static final String _MODAL_HTML_RES = "META-INF/resource/modal.htm.ftl";
	private static ISysService<SysVO, TbSys, String> sysService;
	private static ISysMenuService<SysMenuVO, TbSysMenu, String> sysMenuService;
	private static ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	private static String _MODAL_TEMPLATE_STR = "";
	
	static {
		InputStream is = null;
		try {
			is = MenuSupportUtils.class.getClassLoader().getResource( _MODAL_HTML_RES ).openStream();
			_MODAL_TEMPLATE_STR = IOUtils.toString(is, Constants.BASE_ENCODING);			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
			is = null;			
		}
		sysService = (ISysService<SysVO, TbSys, String>)AppContext.getBean("core.service.SysService");		
		sysMenuService = (ISysMenuService<SysMenuVO, TbSysMenu, String>)AppContext.getBean("core.service.SysMenuService");
		sysProgService = (ISysProgService<SysProgVO, TbSysProg, String>)AppContext.getBean("core.service.SysProgService");		
	}
	
	private static String getModalHtml(TbSysProg prog) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prog", prog);
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		templateLoader.putTemplate("resourceTemplate", _MODAL_TEMPLATE_STR );
		Configuration cfg = new Configuration( Configuration.VERSION_2_3_21 );
		cfg.setTemplateLoader(templateLoader);
		Template template = cfg.getTemplate("resourceTemplate", Constants.BASE_ENCODING);
		Writer out = new StringWriter();
		template.process(paramMap, out);
		paramMap.clear();
		paramMap = null;
		return out.toString();
	}
	
	public static TbSysProg loadSysProg(String progId) throws ServiceException, Exception {
		TbSysProg tbSysProg = new TbSysProg();
		tbSysProg.setProgId(progId);
		tbSysProg = sysProgService.findByEntityUK(tbSysProg);
		if (null == tbSysProg) {
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.DATA_ERRORS));
		}
		return tbSysProg;
	}	
	
	public static String getUrl(String basePath, TbSys sys, TbSysProg sysProg) throws Exception {
		String url = "";
		if (StringUtils.isBlank(sysProg.getUrl())) {
			return url;
		}
		if (YesNo.YES.equals(sys.getIsLocal())) {
			url = basePath + "/" + sysProg.getUrl() + ( (sysProg.getUrl().indexOf("?")>0 || sysProg.getUrl().indexOf("&")>0) ? "&" : "?" ) + Constants.QIFU_PAGE_IN_TAB_IFRAME + "=" + YesNo.YES;
		} else {
			String head = "http://";
			if (basePath.startsWith("https")) {
				head = "https://";
			}
			url = head + sys.getHost() + "/" + sys.getContextPath() + "/" + sysProg.getUrl()
					+ ( (sysProg.getUrl().indexOf("?")>0 || sysProg.getUrl().indexOf("&")>0) ? "&" : "?" ) + Constants.QIFU_PAGE_IN_TAB_IFRAME + "=" + YesNo.YES;
		}
		return url;
	}	
	
	public static String getFirstLoadJavascript() throws ServiceException, Exception {		
		return SystemSettingConfigureUtils.getFirstLoadJavascriptValue();
	}	
	
	public static MenuResultObj getMenuData(String basePath) throws ServiceException, Exception {
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("name", "asc");
		List<TbSys> sysList = sysService.findListByParams(null, null, orderParams);
		if (sysList==null || sysList.size()<1) { // 必需要有 TB_SYS 資料
			throw new ServiceException(SysMessageUtil.get(SysMsgConstants.DATA_ERRORS));
		}
		MenuResultObj resultObj = new MenuResultObj();
		StringBuilder jsSb = new StringBuilder();
		StringBuilder dropdownHtmlSb = new StringBuilder();
		StringBuilder navHtmlSb = new StringBuilder();
		StringBuilder modalHtmlSb = new StringBuilder();
		jsSb.append("var _prog = []; ").append("\n");
		for (TbSys sys : sysList) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("progSystem", sys.getSysId());
			List<TbSysProg> sysProgList = sysProgService.findListByParams(params);
			for (int i=0; sysProgList!=null && i<sysProgList.size(); i++) {
				TbSysProg sysProg = sysProgList.get(i);
				jsSb.append("_prog.push({\"id\" : \"" + sysProg.getProgId() + "\", \"itemType\" : \"" + sysProg.getItemType() + "\", \"name\" : \"" + sysProg.getName() + "\", \"icon\" : \"" + IconUtils.getUrl(basePath, sysProg.getIcon()) + "\", \"url\" : \"" + getUrl(basePath, sys, sysProg) + "\"});").append("\n");
				
				if (YesNo.YES.equals(sysProg.getIsDialog())) {
					modalHtmlSb.append( getModalHtml(sysProg) );
				}
				
			}
			
			Subject subject = SecurityUtils.getSubject();
			String account = (String) subject.getPrincipal();
			if (subject.hasRole(Constants.SUPER_ROLE_ADMIN) || subject.hasRole(Constants.SUPER_ROLE_ALL)) {
				account = null;
			} 			
			DefaultResult<List<SysMenuVO>> menuResult = sysMenuService.findForMenuGenerator(sys.getSysId(), account);
			if (menuResult.getValue() == null) {
				continue;
			}
			List<SysMenuVO> menuList = menuResult.getValue();
			List<SysMenuVO> parentSysMenuList = searchFolder(menuList);
			for (SysMenuVO pMenu : parentSysMenuList) {
				List<SysMenuVO> childSysMenuList = searchItem(pMenu.getOid(), menuList);
				if (childSysMenuList==null || childSysMenuList.size()<1) {
					continue;
				}		
				TbSysProg pSysProg = searchProg(pMenu, sysProgList);
				if (null == pSysProg) {
					throw new ServiceException(SysMessageUtil.get(SysMsgConstants.DATA_ERRORS));
				}
				
				dropdownHtmlSb.append(IconUtils.getHtmlImg(basePath, pSysProg.getIcon()) + "&nbsp;<font color=\"#848484\"><b>" + pSysProg.getName() + "</b></font>");
				
				navHtmlSb.append("<ul class=\"nav nav-pills flex-column\">");
				navHtmlSb.append("<li class=\"nav-item\">");
				navHtmlSb.append(IconUtils.getHtmlImg(basePath, pSysProg.getIcon()) + "&nbsp;<font color=\"#848484\"><b>" + pSysProg.getName() + "</b></font>");
				navHtmlSb.append("</li>");
				
				for (SysMenuVO cMenu : childSysMenuList) {
					TbSysProg cSysProg = searchProg(cMenu, sysProgList);
					if (null == cSysProg) {
						throw new ServiceException(SysMessageUtil.get(SysMsgConstants.DATA_ERRORS));
					}
					dropdownHtmlSb.append("<a class=\"dropdown-item\" href=\"#\" onclick=\"addTab('" + cSysProg.getProgId() + "', null);\">" + IconUtils.getHtmlImg(basePath, cSysProg.getIcon()) + "&nbsp;&nbsp;" + cSysProg.getName() + "</a>");
					
					navHtmlSb.append("<li class=\"nav-item\">");
					navHtmlSb.append("<a class=\"nav-link\" href=\"#\" onclick=\"addTab('" + cSysProg.getProgId() + "', null);\">" + IconUtils.getHtmlImg(basePath, cSysProg.getIcon()) + "&nbsp;&nbsp;" + cSysProg.getName() + "</a>");
					navHtmlSb.append("</li>");
					
				}
				
				dropdownHtmlSb.append("<div class=\"dropdown-divider\"></div>");
				
				navHtmlSb.append("</ul>");
				
			}
			
		}
		
		resultObj.setDropdownHtmlData(dropdownHtmlSb.toString());
		resultObj.setNavItemHtmlData(navHtmlSb.toString());
		resultObj.setJavascriptData(jsSb.toString());
		resultObj.setModalHtmlData(modalHtmlSb.toString());
		return resultObj;
	}
	
	public static String getProgramName(String progId) {
		String name = "unknown-program";
		if (StringUtils.isBlank(progId)) {
			return name;
		}
		try {
			name = sysProgService.findNameForProgId(progId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}	
	
	protected static TbSysProg searchProg(SysMenuVO menu, List<TbSysProg> sysProgList) throws Exception {
		TbSysProg prog = null;
		for (int i=0; i<sysProgList.size() && prog == null; i++) {
			if ( sysProgList.get(i).getProgId().equals(menu.getProgId()) ) {
				prog = sysProgList.get(i);
				i = sysProgList.size();
			}
		}
		return prog;
	}
	
	/**
	 * 取是目錄選單的資料
	 * 
	 * @param sysMenuList
	 * @return
	 * @throws Exception
	 */
	protected static List<SysMenuVO> searchFolder(List<SysMenuVO> sysMenuList) throws Exception {
		List<SysMenuVO> folderList = new ArrayList<SysMenuVO>();
		for (SysMenuVO sysMenu : sysMenuList) {
			if (MenuItemType.FOLDER.equals(sysMenu.getItemType()) && YesNo.YES.equals(sysMenu.getEnableFlag()) ) {
				folderList.add(sysMenu);
			}
		}
		return folderList;
	}
	
	/**
	 * 取目錄下的選單項目
	 * 
	 * @param parentOid
	 * @param sysMenuList
	 * @return
	 * @throws Exception
	 */
	protected static List<SysMenuVO> searchItem(String parentOid, List<SysMenuVO> sysMenuList) throws Exception {
		List<SysMenuVO> folderList = new ArrayList<SysMenuVO>();
		for (SysMenuVO sysMenu : sysMenuList) {
			if (MenuItemType.ITEM.equals(sysMenu.getItemType()) && parentOid.equals(sysMenu.getParentOid())
					&& YesNo.YES.equals(sysMenu.getEnableFlag()) ) {
				folderList.add(sysMenu);
			}
		}
		return folderList;		
	}	
	
}
