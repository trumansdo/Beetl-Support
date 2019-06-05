package com.intellij.ibeetl.lang.parser;

import com.intellij.ibeetl.lang.base.BeetlIElementType;
import com.intellij.ibeetl.lang.psi.elements.BeetlBinaryExpression;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

public interface BeetlPsiElementTypes {
	BeetlIElementType NUMBER = new BeetlIElementType("Number");
	BeetlIElementType BEETL_BLOCK = new BeetlIElementType("BeetlSyntaxBlock");
	BeetlIElementType BINARY_EXPRESSION = new BeetlIElementType("BinaryExpression") {
		public PsiElement createPsiElement(ASTNode node) {
			return new BeetlBinaryExpression(node);
		}
	};
}
