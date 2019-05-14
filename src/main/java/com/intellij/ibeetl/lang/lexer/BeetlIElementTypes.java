package com.intellij.ibeetl.lang.lexer;

import com.intellij.ibeetl.generated.psi.BeetlTypes;
import com.intellij.ibeetl.lang.BeetlLanguage;
import com.intellij.ibeetl.lang.psi.base.BeetlTokenType;
import com.intellij.psi.templateLanguages.TemplateDataElementType;
import com.intellij.psi.tree.IElementType;
import org.apache.commons.lang3.StringUtils;

public class BeetlIElementTypes {
	private static final IElementType OUTER_ELEMENT_TYPE = new IElementType("BTL_FRAGMENT", BeetlLanguage.INSTANCE);
	public static final TemplateDataElementType TEMPLATE_DATA = new TemplateDataElementType("BTL_TEMPLATE_DATA", BeetlLanguage.INSTANCE, BeetlTypes.BTL_TEMPLATE_HTML_TEXT, OUTER_ELEMENT_TYPE);
	public static final BeetlTokenType VIRTUAL_ROOT=new BeetlTokenType("virtual_root");
}
