package com.intellij.ibeetl.lang.base;

import com.intellij.ibeetl.lang.BeetlLanguage;
import com.intellij.psi.tree.IElementType;

/**
 * 词法token类型, 非混合语言
 */
public class BeetlTokenType extends IElementType {

	public BeetlTokenType(String debugName) {
		super(debugName, BeetlLanguage.INSTANCE);
	}
}
