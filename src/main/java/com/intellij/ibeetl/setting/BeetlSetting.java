package com.intellij.ibeetl.setting;

import com.intellij.ibeetl.BeetlBundle;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.intellij.ibeetl.setting.BeetlConfigure.*;

@State(
		name = "BeetlSetting",
		storages = @Storage(file = "$APP_CONFIG$/beetl.xml"))
public class BeetlSetting implements PersistentStateComponent<Element> {

	private Logger logger=Logger.getInstance("#com.intellij.ibeetl.persistent");

	public BeetlSetting() {
		logger.info("beetl.xml persistent component create successfully.");
	}

	/**
	 * 如果将BeetlSetting在plugin.xml中注册为service，必须要在某处调用该方法从servicemanage中获取一次才能创建成component。
	 * 所以建议还是直接注册成component，因为没有其他地方用到这个用于持久化的类。
	 * @return
	 * */
	public static BeetlSetting getInstance() {
		return ServiceManager.getService(BeetlSetting.class);
	}

	@Nullable
	@Override
	public Element getState() {
		Element element = new Element("BeetlSetting");
		element.setAttribute("DELIMITER_PLACEHOLDER_START", DELIMITER_PLACEHOLDER_START);
		element.setAttribute("DELIMITER_PLACEHOLDER_END", DELIMITER_PLACEHOLDER_END);
		element.setAttribute("DELIMITER_STATEMENT_START", DELIMITER_STATEMENT_START);
		element.setAttribute("DELIMITER_STATEMENT_END", DELIMITER_STATEMENT_END);
		element.setAttribute("HTML_TAG_FLAG", HTML_TAG_FLAG);
		return element;
	}

	@Override
	public void loadState(@NotNull Element element) {
		DELIMITER_PLACEHOLDER_START = Optional.ofNullable(element.getAttributeValue("DELIMITER_PLACEHOLDER_START")).orElse(BeetlBundle.message("DELIMITER_PLACEHOLDER_START"));
		DELIMITER_PLACEHOLDER_END = Optional.ofNullable(element.getAttributeValue("DELIMITER_PLACEHOLDER_END")).orElse(BeetlBundle.message("DELIMITER_PLACEHOLDER_END"));
		DELIMITER_STATEMENT_START = Optional.ofNullable(element.getAttributeValue("DELIMITER_STATEMENT_START")).orElse(BeetlBundle.message("DELIMITER_STATEMENT_START"));
		DELIMITER_STATEMENT_END = Optional.ofNullable(element.getAttributeValue("DELIMITER_STATEMENT_END")).orElse(BeetlBundle.message("DELIMITER_STATEMENT_END"));
		HTML_TAG_FLAG = Optional.ofNullable(element.getAttributeValue("HTML_TAG_FLAG")).orElse(BeetlBundle.message("HTML_TAG_FLAG"));
	}
}
