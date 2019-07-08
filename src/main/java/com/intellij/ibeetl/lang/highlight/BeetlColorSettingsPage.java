package com.intellij.ibeetl.lang.highlight;

import com.intellij.ibeetl.BeetlBundle;
import com.intellij.openapi.editor.colors.ColorKey;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.util.containers.ContainerUtil;
import icons.BeetlIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.Map;

import static com.intellij.ibeetl.lang.highlight.BeetlSyntaxHighlighterColors.*;

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
		return "<%\n var a=123,b='123',c=[1,2,3],d={'key':123,'key':'123'};\n if(var i=0;i<c.~size;i++){\n print(i); \n} \n%>\n" +
				"/*block comment*/\n" +
				"//line comment" +
				"";
	}

	@Nullable
	@Override
	public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
		return null;
	}

	@NotNull
	@Override
	public AttributesDescriptor[] getAttributeDescriptors() {
		return new AttributesDescriptor[0];
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
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("keywords", BTL_KEYWORD));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("operations", BTL_OPERATIONS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("string", BTL_STRING));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("literal", BTL_LITERALS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("delimiters", BTL_DELIMITERS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("placeholders", BTL_PLACEHOLDERS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("parenths", BTL_PARENTHS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("brackets", BTL_BRACKETS));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("braces", BTL_BRACES));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("semicolon", BTL_SEMICOLON));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("comma", BTL_COMMA));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("identifier", BTL_IDENT));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("attribute_name", BTL_REFERENCE));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("line_comment", BTL_LINE_COMMENT));
		ATTRIBUTES_DESCRIPTOR_LIST.add(new AttributesDescriptor("multiline_comment", BTL_BLOCK_COMMENT));
	}
}
