package com.intellij.ibeetl.lang.lexer;

import com.intellij.ibeetl.lang.BeetlLanguage;
import com.intellij.ibeetl.lang.base.BeetlTokenType;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.psi.templateLanguages.TemplateDataElementType;
import com.intellij.psi.tree.IElementType;

public interface BeetlIElementTypes {
	IElementType OUTER_ELEMENT_TYPE = new IElementType("BTL_FRAGMENT", BeetlLanguage.INSTANCE);
	IElementType BTL_TEMPLATE_HTML_TEXT = new IElementType("template_html_text", HTMLLanguage.INSTANCE);
	/*
	* 如果用psiviewer插件查看文件的psi树结构，会发现当你选择HTML语言时（当然假定是在HTML中使用的beetl），这个BTL_FRAGMENT会出现在HTML的语法树中。
	* 当你选择btl语言时，template_html_text会出现beetl的语法树中。
	* 其实就是混合语言，A（主语言）把B语言识别为template_html_text，B语言把A语言识别为BTL_FRAGMENT。
	* */
	TemplateDataElementType TEMPLATE_DATA = new TemplateDataElementType("BTL_TEMPLATE_DATA", BeetlLanguage.INSTANCE, BTL_TEMPLATE_HTML_TEXT, OUTER_ELEMENT_TYPE);
	BeetlTokenType VIRTUAL_ROOT=new BeetlTokenType("virtual_root");
}
