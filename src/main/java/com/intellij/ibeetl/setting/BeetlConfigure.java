package com.intellij.ibeetl.setting;

import com.intellij.ibeetl.lang.BeetlExternalAnnotator;
import com.intellij.ibeetl.lang.lexer.BeetlLexer;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import static com.intellij.ibeetl.BeetlBundle.message;
import static org.apache.commons.lang3.StringUtils.replacePattern;

/**
 * 向idea注册配置页
 */
public class BeetlConfigure implements SearchableConfigurable {
	public static String DELIMITER_PLACEHOLDER_START = message("DELIMITER_PLACEHOLDER_START");
	public static String DELIMITER_PLACEHOLDER_END = message("DELIMITER_PLACEHOLDER_END");

	public static String DELIMITER_STATEMENT_START = message("DELIMITER_STATEMENT_START");
	public static String DELIMITER_STATEMENT_END = message("DELIMITER_STATEMENT_END");

	public static String HTML_TAG_FLAG = message("HTML_TAG_FLAG");

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

	/**
	 * 懒得一个个纠正然后判断了。也就几个配置，直接每次保存算了。
	 *
	 * @return
	 */
	@Override
	public boolean isModified() {
		return true;
		/*return !DELIMITER_STATEMENT_START.equals(beetlSettingForm.lsstartField.getText())
				|| !DELIMITER_STATEMENT_END.equals(beetlSettingForm.rsstartField.getText())
				|| !DELIMITER_PLACEHOLDER_START.equals(beetlSettingForm.lpstartField.getText())
				|| !DELIMITER_PLACEHOLDER_END.equals(beetlSettingForm.rpstartField.getText())
				|| !HTML_TAG_FLAG.equals(beetlSettingForm.htagField.getText())
				;*/
	}

	@Override
	public void apply() throws ConfigurationException {
		DELIMITER_STATEMENT_START = replacePattern(beetlSettingForm.lsstartField.getText(), "\\s", "");
		DELIMITER_STATEMENT_END = replacePattern(beetlSettingForm.rsstartField.getText(), "\\s", "");
		DELIMITER_PLACEHOLDER_START = replacePattern(beetlSettingForm.lpstartField.getText(), "\\s", "");
		DELIMITER_PLACEHOLDER_END = replacePattern(beetlSettingForm.rpstartField.getText(), "\\s", "");
		HTML_TAG_FLAG = replacePattern(beetlSettingForm.htagField.getText(), "\\s", "");

		DELIMITER_PLACEHOLDER_START = DELIMITER_PLACEHOLDER_START.isEmpty() ? message("DELIMITER_PLACEHOLDER_START") : DELIMITER_PLACEHOLDER_START;
		DELIMITER_PLACEHOLDER_END = DELIMITER_PLACEHOLDER_END.isEmpty() ? message("DELIMITER_PLACEHOLDER_END") : DELIMITER_PLACEHOLDER_END;

		DELIMITER_STATEMENT_START = DELIMITER_STATEMENT_START.isEmpty() ? message("DELIMITER_STATEMENT_START") : DELIMITER_STATEMENT_START;
		DELIMITER_STATEMENT_END = DELIMITER_STATEMENT_END.isEmpty() ? StringUtils.LF : DELIMITER_STATEMENT_END;

		HTML_TAG_FLAG = HTML_TAG_FLAG.isEmpty() ? message("HTML_TAG_FLAG") : HTML_TAG_FLAG;

		BeetlLexer.resetConfig();
		BeetlExternalAnnotator.resetConfig();
	}

	@Override
	public void reset() {
		beetlSettingForm.lsstartField.setText(replacePattern(DELIMITER_STATEMENT_START, "\\s", ""));
		beetlSettingForm.rsstartField.setText(replacePattern(DELIMITER_STATEMENT_END, "\\s", ""));
		beetlSettingForm.lpstartField.setText(replacePattern(DELIMITER_PLACEHOLDER_START, "\\s", ""));
		beetlSettingForm.rpstartField.setText(replacePattern(DELIMITER_PLACEHOLDER_END, "\\s", ""));
		beetlSettingForm.htagField.setText(replacePattern(HTML_TAG_FLAG, "\\s", ""));
	}

	@Override
	public void disposeUIResources() {
		beetlSettingForm.mainPanel = null;
	}
}
