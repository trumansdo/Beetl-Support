package com.intellij.ibeetl.lang.psi.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.templateLanguages.DataLanguageBlockWrapper;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlock;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlockFactory;
import com.intellij.ibeetl.generated.psi.BeetlTypes;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * beetl 语言代码块
 */
public class BeetlBlock extends TemplateLanguageBlock {
	protected BeetlBlock(@NotNull TemplateLanguageBlockFactory blockFactory, @NotNull CodeStyleSettings settings, @NotNull ASTNode node, @Nullable List<DataLanguageBlockWrapper> foreignChildren) {
		super(blockFactory, settings, node, foreignChildren);
	}

	@Override
	public Indent getIndent() {
		return Indent.getNormalIndent();
	}

	@Override
	protected IElementType getTemplateTextElementType() {
		return BeetlTypes.BTL_TEMPLATE_HTML_TEXT;
	}

	@Nullable
	@Override
	public String getDebugName() {
		return "--> beetl block";
	}
}
