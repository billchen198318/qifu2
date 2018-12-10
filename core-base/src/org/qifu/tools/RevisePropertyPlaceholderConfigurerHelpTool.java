package org.qifu.tools;

import org.qifu.base.Constants;
import org.qifu.util.EncryptorUtils;
import org.qifu.util.SimpleUtils;

public class RevisePropertyPlaceholderConfigurerHelpTool {
	
	public static void main(String args[]) throws Exception {
		if (args != null && args.length<1) {
			System.out.println( "RevisePropertyPlaceholderConfigurerHelpTool [INPUT_VALUE]" );
			System.out.println( "Example : " );
			System.out.println( "RevisePropertyPlaceholderConfigurerHelpTool test" );
			System.exit(1);
		}
		System.out.println( SimpleUtils.toHex(EncryptorUtils.encrypt(Constants.ENCRYPTOR_KEY1, Constants.ENCRYPTOR_KEY2, args[0])) );
	}
	
}
