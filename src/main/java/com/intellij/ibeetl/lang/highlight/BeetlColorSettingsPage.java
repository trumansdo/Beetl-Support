package com.intellij.ibeetl.lang.highlight;

import com.intellij.ibeetl.BeetlBundle;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.util.containers.ContainerUtil;
import icons.BeetlIcons;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.Map;

import static com.intellij.ibeetl.lang.highlight.BeetlSyntaxHighlighterColors.*;
import static com.intellij.ibeetl.setting.BeetlConfigure.*;

/**
 * beetl 颜色配置页
 */
public class BeetlColorSettingsPage implements ColorSettingsPage {
	private static final List<AttributesDescriptor> ATTRIBUTES_DESCRIPTOR_LIST = ContainerUtil.<AttributesDescriptor>newArrayList();

	@Nullable
	@Override
	public Icon getIcon() {
		return BeetlIcons.ICON;
	}

	@NotNull
	@Override
	public SyntaxHighlighter getHighlighter() {
		return new BeetlSyntaxHighlighter();
	}

	/**
	 * @return 返回配置界面中的文本实例
	 */
	@NotNull
	@Override
	public String getDemoText() {
		String text = DELIMITER_STATEMENT_START + " var a=123,b='123',c=[1,2,3],d={'key':123,'key':'123'}; " + DELIMITER_STATEMENT_END + StringUtils.LF +
				DELIMITER_STATEMENT_START + " if(var i=0;i<c.~size;i++){ " + DELIMITER_STATEMENT_END + StringUtils.LF +
				DELIMITER_STATEMENT_START + " \t print(i); " + DELIMITER_STATEMENT_END + StringUtils.LF +
				DELIMITER_STATEMENT_START + " } " + DELIMITER_STATEMENT_END + StringUtils.LF +
				"/*block comment*/\n" +
				"//line comment\n" +
				"<" + HTML_TAG_FLAG + "tagname att_name=\"ss" + DELIMITER_PLACEHOLDER_START + "date,dateformat=''" + DELIMITER_PLACEHOLDER_END + "ss\"/>";
		return text;
	}

	@Nullable
	@Override
	public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
		return null;
	}

	@NotNull
	@Override
	public AttributesDescriptor[] getAttributeDescriptors() {
		return ATTRIBUTES_DESCRIPTOR_LIST.toArray(new AttributesDescriptor[0]);
	}

	@NotNull
	@Override
	public ColorDescriptor[] getColorDescriptors() {
		return ColorDescriptor.EMPTY_ARRAY;
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return BeetlBundle.message("file.type.description");
	}

	static {
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("DELIMITERS", BTL_DELIMITERS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("HTMLTAG", BTL_HTMLTAGS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("PLACEHOLDERS", BTL_PLACEHOLDERS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("KEYWORDS", BTL_KEYWORD));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("OPERATIONS", BTL_OPERATIONS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("STRING", BTL_STRING));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("LITERAL", BTL_LITERALS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("PARENTHS", BTL_PARENTHS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("BRACKETS", BTL_BRACKETS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("BRACES", BTL_BRACES));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("SEMICOLON", BTL_SEMICOLON));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("COMMA", BTL_COMMA));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("IDENTIFIER", BTL_IDENT));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("ATTRIBUTE_NAME", BTL_REFERENCE));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("LINE_COMMENT", BTL_LINE_COMMENT));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("BLOCK_COMMENT", BTL_BLOCK_COMMENT));
	}
}
