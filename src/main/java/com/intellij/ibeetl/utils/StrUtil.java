package com.intellij.ibeetl.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StrUtil {
	public static int indexOfAny(final CharSequence str, final int startPos, final CharSequence... searchStrs) {
		if (str == null || searchStrs == null) {
			return -1;
		}
		final int sz = searchStrs.length;
		
		// String's can't have a MAX_VALUEth index.
		int ret = Integer.MAX_VALUE;
		
		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			final CharSequence search = searchStrs[i];
			if (search == null) {
				continue;
			}
			tmp = StringUtils.indexOf(str, search, startPos);
			if (tmp == -1) {
				continue;
			}
			
			if (tmp < ret) {
				ret = tmp;
			}
		}
		
		return ret == Integer.MAX_VALUE ? -1 : ret;
	}
	
	/**
	 * 以linux下的 \n 为换行，每个\n 就算一行
	 * @param content
	 * @return
	 */
	public static List<String> lines(String content){
		List<String> lines = new ArrayList<>();
		int i = content.indexOf(StringUtils.LF);
		int beginIndex = 0;
		while (-1!=i){
			lines.add(content.substring(beginIndex, i));
			beginIndex=i+1;
			i = content.indexOf(StringUtils.LF, beginIndex);
			if(i>30000) break;
		}
		lines.add(content.substring(beginIndex));
		return lines;
	}
}
