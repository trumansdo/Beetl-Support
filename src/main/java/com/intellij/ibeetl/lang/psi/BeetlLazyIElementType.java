package com.intellij.ibeetl.lang.psi;

import com.intellij.ibeetl.lang.BeetlLanguage;
import com.intellij.psi.tree.ILazyParseableElementType;

/**
 * 用于词法token类型，但是这个是作为混合语言的使用，可能用不到它
 */
public class BeetlLazyIElementType extends ILazyParseableElementType {
	public BeetlLazyIElementType(String debugName) {
		super(debugName, BeetlLanguage.INSTANCE);
	}
}
