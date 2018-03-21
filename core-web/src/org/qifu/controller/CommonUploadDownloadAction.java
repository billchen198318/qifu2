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
package org.qifu.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.qifu.base.Constants;
import org.qifu.base.SysMessageUtil;
import org.qifu.base.SysMsgConstants;
import org.qifu.base.controller.BaseController;
import org.qifu.base.exception.AuthorityException;
import org.qifu.base.exception.ControllerException;
import org.qifu.base.exception.ServiceException;
import org.qifu.base.model.ControllerMethodAuthority;
import org.qifu.base.model.DefaultControllerJsonResultObj;
import org.qifu.base.model.DefaultResult;
import org.qifu.model.UploadTypes;
import org.qifu.po.TbSysUpload;
import org.qifu.service.ISysUploadService;
import org.qifu.util.FSUtils;
import org.qifu.util.UploadSupportUtils;
import org.qifu.vo.SysUploadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
public class CommonUploadDownloadAction extends BaseController {
	private static final long UPLOAD_MAX_SIZE = UploadSupportUtils.UPLOAD_MAX_SIZE;
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	
	public ISysUploadService<SysUploadVO, TbSysUpload, String> getSysUploadService() {
		return sysUploadService;
	}

	@Autowired
	@Resource(name="core.service.SysUploadService")
	@Required		
	public void setSysUploadService(ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService) {
		this.sysUploadService = sysUploadService;
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROGCOMM0003Q")
	@RequestMapping(value = "/core.commonCheckUploadFileJson.do")		
	public @ResponseBody DefaultControllerJsonResultObj<String> checkUpload(HttpServletResponse response, @RequestParam("oid") String oid) {
		DefaultControllerJsonResultObj<String> result = this.getDefaultJsonResult("CORE_PROGCOMM0003Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}
		if (StringUtils.isBlank(oid)) {
			result.setMessage( SysMessageUtil.get(SysMsgConstants.PARAMS_BLANK) );
			return result;
		}
		try {
			if ( this.sysUploadService.countByPKng(oid) == 1 ) {
				result.setValue( oid );
				result.setSuccess( YES );
				result.setMessage( SysMessageUtil.get(SysMsgConstants.DATA_IS_EXIST) );
			} else {
				result.setMessage( SysMessageUtil.get(SysMsgConstants.DATA_NO_EXIST) );
			}
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			exceptionResult(result, e);
		}
		return result;
	}

	@ControllerMethodAuthority(check = true, programId = "CORE_PROGCOMM0003Q")
	@RequestMapping(value = "/core.commonDownloadFile.do")
	public void downloadFile(HttpServletResponse response, @RequestParam("oid") String oid) throws UnsupportedEncodingException, IOException {
		TbSysUpload uploadData = new TbSysUpload();
		uploadData.setOid(oid);
		String fileName = "";
		byte[] content = null;		
		try {
			DefaultResult<TbSysUpload> result = sysUploadService.findEntityByOid(uploadData);
			if (result.getValue() != null) {
				uploadData = result.getValue();
				fileName = UploadSupportUtils.generateRealFileName( uploadData.getShowName() );
				content = uploadData.getContent();
				if (content == null && YES.equals(uploadData.getIsFile())) { // 檔案模式, 所以沒有byte content
					content = UploadSupportUtils.getDataBytes(oid);
				}
			}
		} catch (AuthorityException | ServiceException | ControllerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ( content == null ) { // 沒有資料
            OutputStream outputStream = response.getOutputStream();
            outputStream.write( SysMessageUtil.get(SysMsgConstants.DATA_NO_EXIST).getBytes(Constants.BASE_ENCODING) );
            outputStream.close();
            return;				
		}
		response.setContentType( "application/octet-stream" );
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName + "\""));
		response.setContentLength( content.length );
		FileCopyUtils.copy(content, response.getOutputStream());
	}
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROGCOMM0003Q")
	@RequestMapping(value = "/core.commonViewFile.do")
	public void viewFile(HttpServletResponse response, @RequestParam("oid") String oid) throws UnsupportedEncodingException, IOException {
		TbSysUpload uploadData = new TbSysUpload();
		uploadData.setOid(oid);
		String fileName = "";
		String mimeType = "";
		byte[] content = null;		
		try {
			DefaultResult<TbSysUpload> result = sysUploadService.findEntityByOid(uploadData);
			if (result.getValue() != null) {
				uploadData = result.getValue();
				//fileName = UploadSupportUtils.generateRealFileName( uploadData.getShowName() );
				fileName = uploadData.getShowName();
				content = uploadData.getContent();
				if (content == null && YES.equals(uploadData.getIsFile())) { // 檔案模式, 所以沒有byte content
					content = UploadSupportUtils.getDataBytes(oid);
				}
				mimeType = FSUtils.getMimeType(fileName);
			}
		} catch (AuthorityException | ServiceException | ControllerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ( content == null ) { // 沒有資料
            OutputStream outputStream = response.getOutputStream();
            outputStream.write( SysMessageUtil.get(SysMsgConstants.DATA_NO_EXIST).getBytes(Constants.BASE_ENCODING) );
            outputStream.close();
            return;				
		}
		if (StringUtils.isBlank(mimeType)) {
			mimeType = "application/octet-stream";
		}
		response.setContentType( mimeType );
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + fileName + "\""));
		response.setContentLength( content.length );
		FileCopyUtils.copy(content, response.getOutputStream());
	}	
	
	@ControllerMethodAuthority(check = true, programId = "CORE_PROGCOMM0003Q")
	@RequestMapping(value = "/core.commonUploadFileJson.do", method = { RequestMethod.POST }, headers = "content-type=multipart/*" )		
	public @ResponseBody DefaultControllerJsonResultObj<String> uploadFile(
			@RequestParam("commonUploadFile") MultipartFile file, 
			@RequestParam("commonUploadFileType") String type, 
			@RequestParam("commonUploadFileIsFileMode") String isFile,
			@RequestParam("commonUploadFileSystem") String system) {
		
		DefaultControllerJsonResultObj<String> result = this.getDefaultJsonResult("CORE_PROGCOMM0003Q");
		if (!this.isAuthorizeAndLoginFromControllerJsonResult(result)) {
			return result;
		}	
		if (null == file || file.getSize() < 1) {
			result.setMessage( SysMessageUtil.get(SysMsgConstants.UPLOAD_FILE_NO_SELECT) );
			return result;			
		}
		if (file.getSize() > UPLOAD_MAX_SIZE) {
			result.setMessage( "File max size only " + UPLOAD_MAX_SIZE + " bytes!"  );
			return result;
		}
		if (!UploadTypes.check(type)) {
			result.setMessage( SysMessageUtil.get(SysMsgConstants.UPLOAD_FILE_TYPE_ERROR) );
			return result;
		}
		try {
			String uploadOid = UploadSupportUtils.create(system, type, ( YES.equals(isFile) ? true : false ), file.getBytes(), file.getOriginalFilename());
			if (!StringUtils.isBlank(uploadOid)) {
				result.setSuccess( YES );
				result.setValue(uploadOid);
				result.setMessage( SysMessageUtil.get(SysMsgConstants.INSERT_SUCCESS) );
			} else {
				result.setMessage( SysMessageUtil.get(SysMsgConstants.INSERT_FAIL) );
			}
		} catch (AuthorityException | ServiceException | ControllerException e) {
			result.setMessage( e.getMessage().toString() );			
		} catch (Exception e) {
			exceptionResult(result, e);
		}		
		return result;
	}

}
