package icons;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.LayeredIcon;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Beetl相关的预定义图标。也许其中有些没用到
 * 例如：文件图标
 * 配置文件在左侧行号的图标
 */
public interface BeetlIcons {
	Icon ICON = IconLoader.findIcon("/icons/beetl.png");
	//	Icon TYPE = IconLoader.findIcon("/icons/type.png");
	Icon APPLICATION_RUN = Helper.createIconWithShift(ICON, AllIcons.Nodes.RunnableMark);
	Icon TEST_RUN = Helper.createIconWithShift(ICON, AllIcons.Nodes.JunitTestMark);
	Icon METHOD = AllIcons.Nodes.Method;
	Icon FUNCTION = AllIcons.Nodes.Function;
	Icon VARIABLE = AllIcons.Nodes.Variable;
	//	Icon CONSTANT = IconLoader.findIcon("/icons/constant.png");
	Icon PARAMETER = AllIcons.Nodes.Parameter;
	Icon FIELD = AllIcons.Nodes.Field;
	Icon LABEL = null; // todo: we need an icon here!
	Icon RECEIVER = AllIcons.Nodes.Parameter;
	Icon PACKAGE = AllIcons.Nodes.Package;
	Icon DEBUG = ICON;

	class Helper {
		private Helper() {
		}

		@NotNull
		public static LayeredIcon createIconWithShift(@NotNull Icon base, Icon mark) {
			LayeredIcon icon = new LayeredIcon(2) {
				@Override
				public int getIconHeight() {
					return base.getIconHeight();
				}
			};
			icon.setIcon(base, 0);
			icon.setIcon(mark, 1, 0, base.getIconWidth() / 2);
			return icon;
		}
	}
}
