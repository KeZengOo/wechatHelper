package com.nuoxin.virtual.rep.api.common.util;

import java.util.Random;

/**
 * 验证码码生成器，算法原理：<br/>
 * 1) 获取id: 1127738 <br/>
 * 2) 使用自定义进制转为：gpm6 <br/>
 * 3) 转为字符串，并在后面加'o'字符：gpm6o <br/>
 * 4）在后面随机产生若干个随机数字字符：gpm6o7 <br/>
 * 转为自定义进制后就不会出现o这个字符，然后在后面加个'o'，这样就能确定唯一性。最后在后面产生一些随机字符进行补全。<br/>
 * @author jiayu.qiu
 */
public class ValidationCode {
	private static final char[] r=new char[]{'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h'};
	private static final char b='o';
	private static final int binLen=r.length;
	private static final int s=4;
	public static String toSerialCode(long id) {
		char[] buf=new char[32];
		int charPos=32;

		while((id / binLen) > 0) {
			int ind=(int)(id % binLen);
			// System.out.println(num + "-->" + ind);
			buf[--charPos]=r[ind];
			id /= binLen;
		}
		buf[--charPos]=r[(int)(id % binLen)];
		// System.out.println(num + "-->" + num % binLen);
		String str=new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if(str.length() < s) {
			StringBuilder sb=new StringBuilder();
			sb.append(b);
			Random rnd=new Random();
			for(int i=1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str+=sb.toString();
		}
		return str;
	}

	public static long codeToId(String code) {
		char chs[]=code.toCharArray();
		long res=0L;
		for(int i=0; i < chs.length; i++) {
			int ind=0;
			for(int j=0; j < binLen; j++) {
				if(chs[i] == r[j]) {
					ind=j;
					break;
				}
			}
			if(chs[i] == b) {
				break;
			}
			if(i > 0) {
				res=res * binLen + ind;
			} else {
				res=ind;
			}
			// System.out.println(ind + "-->" + res);
		}
		return res;
	}
	
	/**
	 * 五位邀请码   数字
	 * @return
	 */
	public static String getCode(){
		Random random = new Random();
		StringBuffer sb = new StringBuffer("");
		Integer s = 0;
		for (int i = 0; i < 5; i++) {
			s = Math.abs(random.nextInt()) % 10;
			sb.append(s.toString());
		}
		return sb.toString();
	}
	
}