package com.intellij.ibeetl.lang.lexer;

import com.intellij.ibeetl.generated.psi.BeetlTypes;
import com.intellij.ibeetl.lang.BeetlLanguage;
import com.intellij.psi.templateLanguages.TemplateDataElementType;
import com.intellij.psi.tree.IElementType;

public class BeetlFileElementType {
	private static final IElementType OUTER_ELEMENT_TYPE = new IElementType("BTL_FRAGMENT", BeetlLanguage.INSTANCE);
	public static final TemplateDataElementType TEMPLATE_DATA = new TemplateDataElementType("BTL_TEMPLATE_DATA", BeetlLanguage.INSTANCE, BeetlTypes.BTL_TEMPLATE_HTML_TEXT, OUTER_ELEMENT_TYPE);

}
