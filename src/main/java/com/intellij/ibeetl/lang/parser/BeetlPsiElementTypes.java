package com.intellij.ibeetl.lang.parser;

import com.intellij.ibeetl.lang.base.BeetlIElementType;
import com.intellij.ibeetl.lang.psi.elements.BeetlBinaryExpression;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

public interface BeetlPsiElementTypes {
	BeetlIElementType NUMBER = new BeetlIElementType("Number");
	BeetlIElementType IDENTIFIER = new BeetlIElementType("Identifier");
	BeetlIElementType BEETL_BLOCK = new BeetlIElementType("BeetlSyntaxBlock");
	BeetlIElementType INTERPOLATION = new BeetlIElementType("BeetlPlaceholder");
	BeetlIElementType HTML_TAG = new BeetlIElementType("BeetlHtmlTagFunction");
	BeetlIElementType BINARY_EXPRESSION = new BeetlIElementType("BinaryExpression") {
		public PsiElement createPsiElement(ASTNode node) {
			return new BeetlBinaryExpression(node);
		}
	};

	BeetlIElementType ASSIGNMENT_EXPRESSION = new BeetlIElementType("AssignmentExpression");
	BeetlIElementType REFERENCE_EXPRESSION = new BeetlIElementType("ReferenceExpression");
}
