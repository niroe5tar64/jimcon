package jp.niro.jimcon.ui;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by niro on 2017/04/21.
 */
public class ResourceBundleWithUtf8 {
    public static final String TEXT_NAME = "TextName";
    private static File dicDir = Paths.get(System.getProperty("user.dir"), "\\src\\main\\resources\\jp\\niro\\jimcon").toFile();
    private static URLClassLoader urlLoader;

    static ResourceBundle.Control UTF8_ENCODING_CONTROL = new ResourceBundle.Control() {
        /**
         * UTF-8 エンコーディングのプロパティファイルから ResourceBundle オブジェクトを生成します。
         * <p>
         * 参考 :
         * <a href="http://jgloss.sourceforge.net/jgloss-core/jacoco/jgloss.util/UTF8ResourceBundleControl.java.html">
         * http://jgloss.sourceforge.net/jgloss-core/jacoco/jgloss.util/UTF8ResourceBundleControl.java.html
         * </a>
         * </p>
         *
         * @throws IllegalAccessException
         * @throws InstantiationException
         * @throws IOException
         */
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");

            try (InputStream is = loader.getResourceAsStream(resourceName);
                 InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                 BufferedReader reader = new BufferedReader(isr)) {
                return new PropertyResourceBundle(reader);
            }
        }
    };

    public static ResourceBundle create(String bundleName) {
        try {
            urlLoader = new URLClassLoader(new URL[]{dicDir.toURI().toURL()});
            return ResourceBundle.getBundle(bundleName, Locale.getDefault(), urlLoader, UTF8_ENCODING_CONTROL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
