package com.intellij.ibeetl.setting;

import com.intellij.ibeetl.BeetlBundle;
import com.intellij.ibeetl.lang.lexer.BeetlLexer;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class BeetlConfigure implements SearchableConfigurable {
	public static String DELIMITER_PLACEHOLDER_START = BeetlBundle.message("DELIMITER_PLACEHOLDER_START");
	public static String DELIMITER_PLACEHOLDER_END = BeetlBundle.message("DELIMITER_PLACEHOLDER_END");

	public static String DELIMITER_STATEMENT_START = BeetlBundle.message("DELIMITER_STATEMENT_START");
	public static String DELIMITER_STATEMENT_END = BeetlBundle.message("DELIMITER_STATEMENT_END");

	public static String HTML_TAG_FLAG = BeetlBundle.message("HTML_TAG_FLAG");

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
		return !DELIMITER_STATEMENT_START.equals(beetlSettingForm.lsstartField.getText())
				|| !DELIMITER_STATEMENT_END.equals(beetlSettingForm.rsstartField.getText())
				|| !DELIMITER_PLACEHOLDER_START.equals(beetlSettingForm.lpstartField.getText())
				|| !DELIMITER_PLACEHOLDER_END.equals(beetlSettingForm.rpstartField.getText())
				|| !HTML_TAG_FLAG.equals(beetlSettingForm.htagField.getText())
				;
	}

	@Override
	public void apply() throws ConfigurationException {
		DELIMITER_STATEMENT_START = beetlSettingForm.lsstartField.getText();
		DELIMITER_STATEMENT_END = beetlSettingForm.rsstartField.getText();
		DELIMITER_PLACEHOLDER_START = beetlSettingForm.lpstartField.getText();
		DELIMITER_PLACEHOLDER_END = beetlSettingForm.rpstartField.getText();
		HTML_TAG_FLAG = beetlSettingForm.htagField.getText();
		BeetlLexer.resetConfig();
	}

	@Override
	public void reset() {
		beetlSettingForm.lsstartField.setText(DELIMITER_STATEMENT_START);
		beetlSettingForm.rsstartField.setText(DELIMITER_STATEMENT_END);
		beetlSettingForm.lpstartField.setText(DELIMITER_PLACEHOLDER_START);
		beetlSettingForm.rpstartField.setText(DELIMITER_PLACEHOLDER_END);
		beetlSettingForm.htagField.setText(HTML_TAG_FLAG);
	}

	@Override
	public void disposeUIResources() {
		beetlSettingForm.mainPanel = null;
	}
}
