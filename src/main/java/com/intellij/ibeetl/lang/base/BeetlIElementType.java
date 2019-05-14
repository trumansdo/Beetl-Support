package com.intellij.ibeetl.lang.base;

import com.intellij.ibeetl.lang.BeetlLanguage;
import com.intellij.psi.tree.IElementType;

/**
 * 词法token类型, 非混合语言
 */
public class BeetlIElementType extends IElementType {

	public BeetlIElementType(String debugName) {
		super(debugName, BeetlLanguage.INSTANCE);
	}
}
