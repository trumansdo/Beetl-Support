package com.intellij.ibeetl.lang.base;

import com.intellij.ibeetl.lang.BeetlLanguage;
import com.intellij.lang.ASTNode;
import com.intellij.lang.pratt.PrattTokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

/**
 * 词法token类型, 非混合语言
 */
public class BeetlTokenType extends PrattTokenType {

	public BeetlTokenType(String debugName) {
		super(debugName, BeetlLanguage.INSTANCE);
	}

	public PsiElement createPsiElement(ASTNode node) {
		return new BeetlCompositePsiElement(node);
	}
}
