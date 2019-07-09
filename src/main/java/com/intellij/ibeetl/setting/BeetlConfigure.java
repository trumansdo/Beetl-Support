package com.intellij.ibeetl.setting;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class BeetlConfigure implements SearchableConfigurable {
	private BeetlSettingForm beetlSettingForm;
	@NotNull
	@Override
	public String getId() {
		return "Beetl";
	}

	@Nls(capitalization = Nls.Capitalization.Title)
	@Override
	public String getDisplayName() {
		return getId();
	}

	@Nullable
	@Override
	public String getHelpTopic() {
		return getId();
	}

	@Nullable
	@Override
	public JComponent createComponent() {
		if (null == beetlSettingForm) {
			this.beetlSettingForm = new BeetlSettingForm();
		}
		return beetlSettingForm.mainPanel;
	}

	@Override
	public boolean isModified() {
		return false;
	}

	@Override
	public void apply() throws ConfigurationException {

	}
}
