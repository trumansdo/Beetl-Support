package com.intellij.ibeetl.lang.highlight;

import com.intellij.openapi.editor.colors.ColorKey;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import icons.BeetlIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class BeetlColorSettingsPage implements ColorSettingsPage {
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
				"//line comment";
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
		return "Beetl";
	}

}
