package com.intellij.ibeetl.lang.parser;

import com.intellij.ibeetl.lang.base.BeetlIElementType;
import com.intellij.ibeetl.lang.psi.elements.BeetlBinaryExpression;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

public interface BeetlPsiElementTypes {
	/*数值*/
	BeetlIElementType NUMBER = new BeetlIElementType("Number");
	/*字符字面量*/
	BeetlIElementType STRING_LITERAL = new BeetlIElementType("Number");

	/*占位符语法*/
	BeetlIElementType INTERPOLATION = new BeetlIElementType("BeetlPlaceholder");
	/*HTML标签语法*/
	BeetlIElementType HTML_TAG = new BeetlIElementType("BeetlHtmlTagFunction");
	/*二元表达式*/
	BeetlIElementType BINARY_EXPRESSION = new BeetlIElementType("BinaryExpression") {
		public PsiElement createPsiElement(ASTNode node) {
			return new BeetlBinaryExpression(node);
		}
	};
	/*集合定义语法*/
	BeetlIElementType LIST_DEFINITION = new BeetlIElementType("ListDefinition");
	/*集合索引语法*/
	BeetlIElementType INDEX_EXPRESSION = new BeetlIElementType("ListIndexExpression");
	/*变量定义语法*/
	BeetlIElementType VAR_DEFINITION_EXPRESSION = new BeetlIElementType("VarDefinitionExpression");
	/*变量赋值语法*/
	BeetlIElementType ASSIGNMENT_EXPRESSION = new BeetlIElementType("AssignmentExpression");
	/*引用语法*/
	BeetlIElementType REFERENCE_EXPRESSION = new BeetlIElementType("ReferenceExpression");
	/*圆括号：参数、if等语句的条件、普通优先级*/
	BeetlIElementType PARENTHESIZED_EXPRESSION = new BeetlIElementType("ReferenceExpression");

	BeetlIElementType SYNTAX_BODY = new BeetlIElementType("ReferenceExpression");

}
