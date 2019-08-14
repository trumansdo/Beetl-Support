package com.intellij.ibeetl.lang.psi.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.templateLanguages.DataLanguageBlockWrapper;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlock;
import com.intellij.formatting.templateLanguages.TemplateLanguageFormattingModelBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 对于模板语言，格式化模型实在无法完成，无法控制html主语言的格式模型。idea从未暴露主语言的block块
 */
public class BeetlFormattingModelBuilder extends TemplateLanguageFormattingModelBuilder {
	@Override
	public TemplateLanguageBlock createTemplateLanguageBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment, @Nullable List<DataLanguageBlockWrapper> foreignChildren, @NotNull CodeStyleSettings codeStyleSettings) {
		return new BeetlBlock(this, codeStyleSettings, node, foreignChildren);
	}
}
