/* 
 * Copyright 2012-2018 qifu, qifu2 of copyright Chen Xin Nien
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.qifu.base.Constants;
import org.qifu.base.exception.ServiceException;
import org.qifu.model.UploadTypes;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

public class PdfCopyPageBuilder {
	private byte[] sourceReportData = null; 
	private List<String> findWords = new ArrayList<String>();
	private String readPassword = "";
	private boolean modeByFindFound = true;
	private boolean forcePutAllPage = false;
	
	public static void main(String args[]) throws Exception {
		byte newPdfByte[] = PdfCopyPageBuilder.build()
				.addFindWord("初音未來")
				.addFindWord("白癡女神阿克婭超贊的說。不要跟我搶! ≧◇≦ ")
				.setForcePutAllPage(false)
				.setModeByFindFound(false)
				.setSourcePdfFile( new File("/tmp/play.pdf") )
				.getContent();
		FileUtils.writeByteArrayToFile(new File("/tmp/play-new.pdf"), newPdfByte);
	}
	
	public static PdfCopyPageBuilder build() {
		PdfCopyPageBuilder pdfCopyPageBuilder = new PdfCopyPageBuilder();
		return pdfCopyPageBuilder;
	}
	
	public PdfCopyPageBuilder setSourceReportData(byte[] content) throws Exception {
		if (null == content) {
			throw new Exception("null data.");
		}
		this.sourceReportData = content;
		return this;
	}
	
	public PdfCopyPageBuilder setSourcePdfFile(File file) throws Exception {
		if (null == file || !file.exists() || !file.isFile()) {
			throw new Exception("file args error.");
		}
		this.setSourceReportData( FileUtils.readFileToByteArray(file) );
		return this;
	}
	
	public PdfCopyPageBuilder addFindWord(String word) {
		if (StringUtils.isBlank(word)) {
			return this;
		}
		if (!this.findWords.contains(word)) {
			this.findWords.add(word);
		}
		return this;
	}
	
	public PdfCopyPageBuilder setReadPassword(String password) {
		this.readPassword = password;
		return this;
	}
	
	public PdfCopyPageBuilder setModeByFindFound(boolean mode) {
		this.modeByFindFound = mode;
		return this;
	}
	
	public PdfCopyPageBuilder setForcePutAllPage(boolean forcePutAllPage) {
		this.forcePutAllPage = forcePutAllPage;
		return this;
	}
	
	public String toUpload() throws ServiceException, Exception {
		return this.toUpload(Constants.getSystem(), UploadTypes.IS_TEMP);
	}
	
	public String toUpload(String system, String uploadType) throws ServiceException, Exception {
		byte newPdfByte[] = this.getContent();
		return UploadSupportUtils.create(system, uploadType, false, newPdfByte, SimpleUtils.getUUIDStr()+".pdf");
	}	
	
	public byte[] getContent() throws Exception {
		if (!this.modeByFindFound && this.findWords.size() > 1) {
			throw new Exception("modeByFindFound set to false, cannot add many find word.");
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PdfReader pdfReader = null;
		if (!StringUtils.isBlank(this.readPassword)) {
			pdfReader = new PdfReader(sourceReportData, readPassword.getBytes());
		} else {
			pdfReader = new PdfReader(sourceReportData);
		}
		Document document = new Document(pdfReader.getPageSizeWithRotation(1));
		PdfCopy pdfCopy = null;
		try {
			pdfCopy = new PdfCopy(document, os);
			document.open();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		PdfTextExtractor pdfTextExtractor = new PdfTextExtractor(pdfReader);
		boolean isCutPdf =false;
		boolean needPut = false;
		for (int i=1; i<=pdfReader.getNumberOfPages() && pdfCopy!=null; i++) {
        	needPut = false;
        	String text = pdfTextExtractor.getTextFromPage(i);
        	if (!this.forcePutAllPage) {
            	if (this.modeByFindFound) {
                	for (String word : this.findWords) {
                		if (text.indexOf(word) > -1) {
                			needPut = true;
                		}
                	}        		
            	} else {
            		for (String word : this.findWords) {
            			if (text.indexOf(word) == -1) {
            				needPut = true;
            			}
            		}
            	}        		
        	} else {
        		needPut = true;
        	}
        	if (needPut) {
        		try {
        			PdfImportedPage page = pdfCopy.getImportedPage(pdfReader, i);
        			pdfCopy.newPage();
        			pdfCopy.addPage(page); 
        			isCutPdf = true;
        		} catch (Exception e) {
        			e.printStackTrace();
        		}        		
        	}
        }		
        
        if (!isCutPdf) { // 沒劫到後, 產生空白的doc
        	document = null;
        	document = new Document();
        	
        	os.close();
    		os = null;
    		os = new ByteArrayOutputStream();
    		
        	try {
    			PdfWriter.getInstance( document, os );
    			document.open();
    			Paragraph paragraph = new Paragraph(" ");
    			paragraph.setAlignment(Element.ALIGN_RIGHT);
    			document.add(paragraph);
    			paragraph = new Paragraph(" ");
    			paragraph.setAlignment(Element.ALIGN_CENTER);
    			document.add(paragraph);
    			paragraph = new Paragraph(" ");
    			paragraph.setAlignment(Element.ALIGN_LEFT);
    			document.add(paragraph);
    			paragraph = new Paragraph(" ");
    			paragraph.setAlignment(Element.ALIGN_LEFT);
    			paragraph.setIndentationLeft(50);    			
    			document.add(paragraph);			
    		} catch (DocumentException e) {
    			e.printStackTrace();
    		}        	
        }		
        
        try {
        	if (document != null) {
        		document.close();
        	}            	
        } catch (Exception e) {
        	e.printStackTrace();
        }
        /*
        try {
        	if (pdfCopy != null && pdfCopy.getPageNumber() > 0) {
        		pdfCopy.close();
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        */
        try {
        	if (pdfReader != null) {
        		pdfReader.close();
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
		byte[] data = os.toByteArray();
		if (os != null) {
			os.close();
		}
		os = null;
		return data;
	}
	
}
