package com.intellij.ibeetl.lang.highlight;

import com.intellij.ide.highlighter.JavaHighlightingColors;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

public class BeetlSyntaxHighlighterColors {

	/*第一个参数是外部xml配置文件的key；第二个参数是如果外部没有，则作为备用。*/
	/*背景色*/
	public static TextAttributesKey BTL_BACKGROUND = TextAttributesKey.createTextAttributesKey("BTL_BACKGROUND", DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR);

	public static TextAttributesKey BTL_IDENT = TextAttributesKey.createTextAttributesKey("BTL_IDENT", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
	public static TextAttributesKey BTL_KEYWORD = TextAttributesKey.createTextAttributesKey("BTL_KEYWORD", JavaHighlightingColors.KEYWORD);
	/*字符*/
	public static TextAttributesKey BTL_STRING = TextAttributesKey.createTextAttributesKey("BTL_STRING", JavaHighlightingColors.STRING);
	/*数字*/
	public static TextAttributesKey BTL_NUMBER = TextAttributesKey.createTextAttributesKey("BTL_NUMBER", JavaHighlightingColors.NUMBER);
	/*定界符*/
	public static TextAttributesKey BTL_DELIMITERS = TextAttributesKey.createTextAttributesKey("BTL_DELIMITERS", DefaultLanguageHighlighterColors.CONSTANT);
	/*占位符*/
	public static TextAttributesKey BTL_PLACEHOLDERS = TextAttributesKey.createTextAttributesKey("BTL_PLACEHOLDERS", DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE);
	/*圆括号*/
	public static TextAttributesKey BTL_PARENTHS = TextAttributesKey.createTextAttributesKey("BTL_PARENTHS", JavaHighlightingColors.PARENTHESES);
	/*中括号*/
	public static TextAttributesKey BTL_BRACKETS = TextAttributesKey.createTextAttributesKey("BTL_BRACKETS", JavaHighlightingColors.BRACKETS);
	/*大括号*/
	public static TextAttributesKey BTL_BRACES = TextAttributesKey.createTextAttributesKey("BTL_BRACES", JavaHighlightingColors.BRACES);
	/*小数点*/
	public static TextAttributesKey BTL_DOT = TextAttributesKey.createTextAttributesKey("BTL_DOT", JavaHighlightingColors.DOT);
	/*逗号*/
	public static TextAttributesKey BTL_COMMA = TextAttributesKey.createTextAttributesKey("BTL_COMMA", JavaHighlightingColors.COMMA);
	/*分号*/
	public static TextAttributesKey BTL_SEMICOLON = TextAttributesKey.createTextAttributesKey("BTL_SEMICOLON", JavaHighlightingColors.JAVA_SEMICOLON);
	/*所有操作符*/
	public static TextAttributesKey BTL_OPERATIONS = TextAttributesKey.createTextAttributesKey("BTL_OPERATIONS", JavaHighlightingColors.OPERATION_SIGN);
	/*直接字面量，true false这类*/
	public static TextAttributesKey BTL_LITERALS = TextAttributesKey.createTextAttributesKey("BTL_LITERALS", JavaHighlightingColors.KEYWORD);
	/*占位符中的引用变量*/
	public static TextAttributesKey BTL_REFERENCE = TextAttributesKey.createTextAttributesKey("BTL_REFERENCE", DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE);
	/*注释*/
	public static TextAttributesKey BTL_LINE_COMMENT = TextAttributesKey.createTextAttributesKey("BTL_LINE_COMMENT", JavaHighlightingColors.LINE_COMMENT);
	public static TextAttributesKey BTL_BLOCK_COMMENT = TextAttributesKey.createTextAttributesKey("BTL_BLOCK_COMMENT", JavaHighlightingColors.JAVA_BLOCK_COMMENT);

}
