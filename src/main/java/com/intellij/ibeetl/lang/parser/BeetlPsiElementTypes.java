package com.intellij.ibeetl.lang.parser;

import com.intellij.ibeetl.lang.base.BeetlIElementType;
import com.intellij.ibeetl.lang.psi.elements.BeetlBinaryExpression;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

public interface BeetlPsiElementTypes {
	/*数值*/
	BeetlIElementType NUMBER = new BeetlIElementType("Number");
	/*字符字面量*/
	BeetlIElementType STRING_LITERAL = new BeetlIElementType("StringLiteral");
	/*自叠加运算：自增自减*/
	BeetlIElementType SELF_OVERLAY_EXPRESSION = new BeetlIElementType("SelfOverlayExpression");

	/*占位符语法*/
	BeetlIElementType INTERPOLATION = new BeetlIElementType("BeetlPlaceholder");
	/*HTML标签语法*/
	BeetlIElementType HTML_TAG = new BeetlIElementType("BeetlHtmlTagFunction");
	/*二元表达式（不包括逻辑表达式）*/
	BeetlIElementType BINARY_EXPRESSION = new BeetlIElementType("BinaryExpression") {
		public PsiElement createPsiElement(ASTNode node) {
			return new BeetlBinaryExpression(node);
		}
	};
	BeetlIElementType LOGICAL_EXPRESSION = new BeetlIElementType("LogicalExpression");
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
	/*方法调用*/
	BeetlIElementType METHOD_CALLED = new BeetlIElementType("MethodCalled");
	/*圆括号：if、select、等的条件表达式*/
	BeetlIElementType PARENTHESIZED_EXPRESSION = new BeetlIElementType("ParenthesizedExpression");
	/*圆括号：函数调用的参数列表*/
	BeetlIElementType PARAMETER_LIST = new BeetlIElementType("ParameterList");
	/*圆括号：安全抑制表达式*/
	BeetlIElementType SAFETY_OUTPUT = new BeetlIElementType("SafetyOutput");
	/*大括号的语法块*/
	BeetlIElementType SYNTAX_BODY = new BeetlIElementType("SyntaxBody");
	/*json定义*/
	BeetlIElementType JSON_EXPRESSION = new BeetlIElementType("JsonExpression");
	/*以冒号分隔的键值对*/
	BeetlIElementType KEY_VALUE_PAIR = new BeetlIElementType("KeyValuePair");
	/*if语句*/
	BeetlIElementType IF_STATEMENT = new BeetlIElementType("IfStatement");
	BeetlIElementType WHILE_STATEMENT = new BeetlIElementType("WhileStatement");
	BeetlIElementType FOR_STATEMENT = new BeetlIElementType("ForStatement");
	BeetlIElementType SELECT_STATEMENT = new BeetlIElementType("SelectStatement");
	BeetlIElementType SWITCH_STATEMENT = new BeetlIElementType("SwitchStatement");
}
