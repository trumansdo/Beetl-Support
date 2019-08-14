package com.intellij.ibeetl.lang.psi.formatter;

import com.intellij.formatting.*;
import com.intellij.formatting.templateLanguages.DataLanguageBlockWrapper;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlock;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlockFactory;
import com.intellij.ibeetl.lang.lexer.BeetlIElementTypes;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.tree.IElementType;
import gherkin.lexer.Tr;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * beetl 语言代码块
 */
public class BeetlBlock extends TemplateLanguageBlock {
	private ASTNode myNode;
	
	protected BeetlBlock(@NotNull TemplateLanguageBlockFactory blockFactory, @NotNull CodeStyleSettings settings, @NotNull ASTNode node, @Nullable List<DataLanguageBlockWrapper> foreignChildren) {
		super(blockFactory, settings, node, foreignChildren);
		myNode = super.getNode();
		System.out.println(myNode.getPsi()+" :=: "+myNode.getText()+" :=: "+myNode.getTextRange());
	}
	
	@Nullable
	@Override
	public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
		Spacing spacing = super.getSpacing(child1, child2);
		return null;
	}

	@Nullable
	@Override
	public Spacing getRightNeighborSpacing(@NotNull Block rightNeighbor, @NotNull DataLanguageBlockWrapper parent, int thisBlockIndex) {
		Spacing spacing = Spacing.createSpacing(0, 0, 0, false, 0, 0);
		return null;
	}

	@Nullable
	@Override
	public Spacing getLeftNeighborSpacing(@Nullable Block leftNeighbor, @NotNull DataLanguageBlockWrapper parent, int thisBlockIndex) {
		Spacing spacing = Spacing.createDependentLFSpacing(4, 4, new TextRange(0, 0), true, 0);
		return null;
	}
	
	@Nullable
	@Override
	public Wrap getWrap() {
		Wrap wrap = Wrap.createWrap(WrapType.NONE, true);
		return wrap;
	}
	
	@Nullable
	@Override
	public Alignment getAlignment() {
		Alignment alignment = Alignment.createAlignment(false, Alignment.Anchor.LEFT);
		return alignment;
	}
	
	@Override
	public Indent getIndent() {
		Indent indent = Indent.getNoneIndent();
		return indent;
	}

	@Override
	protected List<Block> buildChildren() {
		return super.buildChildren();
	}

	@Override
	protected Wrap createChildWrap(ASTNode child) {
		Wrap wrap = Wrap.createWrap(WrapType.NONE, true);
		return wrap;
	}

	@Override
	protected Alignment createChildAlignment(ASTNode child) {
		Alignment alignment = Alignment.createAlignment(true, Alignment.Anchor.RIGHT);
		return alignment;
	}

	@Override
	protected IElementType getTemplateTextElementType() {
		return BeetlIElementTypes.BTL_TEMPLATE_HTML_TEXT;
	}
	
	@Nullable
	@Override
	public String getDebugName() {
		return "--> beetl block";
	}
}
