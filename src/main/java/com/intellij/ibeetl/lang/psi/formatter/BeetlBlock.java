package com.intellij.ibeetl.lang.psi.formatter;

import com.intellij.formatting.*;
import com.intellij.formatting.templateLanguages.DataLanguageBlockWrapper;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlock;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlockFactory;
import com.intellij.ibeetl.lang.lexer.BeetlIElementTypes;
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
	private ASTNode myNode;
	
	protected BeetlBlock(@NotNull TemplateLanguageBlockFactory blockFactory, @NotNull CodeStyleSettings settings, @NotNull ASTNode node, @Nullable List<DataLanguageBlockWrapper> foreignChildren) {
		super(blockFactory, settings, node, foreignChildren);
		myNode = super.getNode();
	}
	
	@Nullable
	@Override
	public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
		Spacing spacing = super.getSpacing(child1, child2);
		return spacing;
	}
	
	/**
	 * Invoked when the current base language block is located inside a template data language block to determine the spacing after the current block.
	 *
	 * @param rightNeighbor  the block to the right of the current one
	 * @param parent         the parent block
	 * @param thisBlockIndex the index of the current block in the parent block sub-blocks
	 * @return the spacing between the current block and its right neighbor
	 */
	@Nullable
	@Override
	public Spacing getRightNeighborSpacing(@NotNull Block rightNeighbor, @NotNull DataLanguageBlockWrapper parent, int thisBlockIndex) {
		Spacing spacing = Spacing.createSpacing(0, 0, 0, false, 0, 0);
		return spacing;
	}
	
	/**
	 * Invoked when the current base language block is located inside a template data language block to determine the spacing before the current block.
	 *
	 * @param leftNeighbor   the block to the left of the current one, or null if the current block is first child
	 * @param parent         the parent block
	 * @param thisBlockIndex the index of the current block in the parent block sub-blocks
	 * @return the spacing between the current block and its left neighbor
	 */
	@Nullable
	@Override
	public Spacing getLeftNeighborSpacing(@Nullable Block leftNeighbor, @NotNull DataLanguageBlockWrapper parent, int thisBlockIndex) {
		Spacing spacing = Spacing.createSpacing(0, 0, 0, false, 0, 0);
		return spacing;
	}
	
	@Nullable
	@Override
	public Wrap getWrap() {
		Wrap wrap = Wrap.createWrap(WrapType.NONE, false);
		return wrap;
	}
	
	@Nullable
	@Override
	public Alignment getAlignment() {
		Alignment alignment = Alignment.createAlignment(false, Alignment.Anchor.RIGHT);
		return alignment;
	}
	
	@Override
	public Indent getIndent() {
		Indent indent = Indent.getNoneIndent();
		return indent;
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
