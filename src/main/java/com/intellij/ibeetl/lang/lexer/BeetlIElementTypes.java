package com.intellij.ibeetl.lang.lexer;

import com.intellij.ibeetl.lang.BeetlLanguage;
import com.intellij.ibeetl.lang.base.BeetlTokenType;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.psi.templateLanguages.TemplateDataElementType;
import com.intellij.psi.tree.IElementType;

public interface BeetlIElementTypes {
	IElementType OUTER_ELEMENT_TYPE = new IElementType("BTL_FRAGMENT", BeetlLanguage.INSTANCE);
	IElementType BTL_TEMPLATE_HTML_TEXT = new IElementType("template_html_text", HTMLLanguage.INSTANCE);
	TemplateDataElementType TEMPLATE_DATA = new TemplateDataElementType("BTL_TEMPLATE_DATA", BeetlLanguage.INSTANCE, BTL_TEMPLATE_HTML_TEXT, OUTER_ELEMENT_TYPE);
	BeetlTokenType VIRTUAL_ROOT=new BeetlTokenType("virtual_root");
}
