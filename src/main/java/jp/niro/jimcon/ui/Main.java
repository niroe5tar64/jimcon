package jp.niro.jimcon.ui;

/**
 * Created by niro on 2017/05/18.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Properties;

/***
 * メインクラス
 */
public class Main {
    /**
     * ロガー
     */
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * メイン処理
     * @param args コマンドライン引数
     */
    public void execute(String[] args) {
        try {
            // メイン処理

            // システムパラメータ取得
            Properties props = new Properties();
            props.loadFromXML(Main.class.getClassLoader().getResourceAsStream("/jp/niro/jimcon/ui/systemconfig.xml"));

            String scpath = getScPath(props);
            logger.debug("scpath = " + scpath);
            String scconfig = getScConfig(props);
            logger.debug("scconfig = " + scconfig);

            // コマンドラインパラメータ取得
            if (args.length != 1) {
                logger.debug("args.lenghth = " + args.length);
                logger.error("usage: sclauncher FXMLファイル");
                System.exit(-1);
            }

            // FXMLファイルよりクラスパスを取得
            String classPath = getClassPath(args[0]);

            File scConfigFile = new File(scconfig);
            String backupName = scConfigFile.getName() + ".backup";
            File backupConfig = new File(scConfigFile.getParent(), backupName);
            logger.debug("backupConfig = " +backupConfig.getAbsolutePath());

            if (classPath != null) {
                // 構成ファイルをバックアップして書き換え
                scConfigFile.renameTo(backupConfig);

                String newline = System.getProperty("line.separator");
                BufferedReader br = new BufferedReader(new FileReader(backupConfig));
                try {
                    FileWriter fw = new FileWriter(scConfigFile);
                    try {
                        String line = br.readLine();
                        while (line != null) {
                            // クラスパスの行を書き換える。
                            if (line.trim().startsWith("app.classpath=")) {
                                line = "app.classpath=" + classPath;
                            }
                            fw.write(line + newline);
                            line = br.readLine();
                        }
                    } finally {
                        fw.close();
                    }

                } finally {
                    br.close();
                }
            }
            try {
                // SceneBuilderの実行。作業用フォルダが FXMLファイルのある場所
                ProcessBuilder pb = new ProcessBuilder(scpath, args[0]);
                Process process = pb.directory(new File(args[0]).getParentFile()).start();
                // 終了まで待つ
                process.waitFor();
            } finally {
                if (classPath != null) {
                    // 構成ファイルを元に戻す
                    scConfigFile.delete();
                    backupConfig.renameTo(scConfigFile);
                }
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            System.exit(-1);
        }
    }

    /**
     * プロパティから SceneBuilderのパスを取得する。
     * @param props プロパティ
     * @return SceneBuilderの実行ファイルのパス
     * @throws IOException エラー時
     */
    public String getScPath(Properties props) throws IOException {
        String scpath = props.getProperty("scenebuilder.path");
        if (scpath == null) {
            logger.error("systemconfig.xml.save に " +
                    "scenebuilder.path(SceneBuilderの実行パス) " +
                    "が設定されていません。");
            System.exit(-1);
        }
        return scpath;
    }

    /**
     * プロパティからSceneBuilderの構成ファイルのパスを取得する。
     * @param props プロパティ
     * @return SceneBuilderの構成ファイルのパス
     */
    public String getScConfig(Properties props) {
        String scconfig = props.getProperty("scenebuilder.cfgfile");
        if (scconfig == null) {
            logger.error("systemconfig.xml.save に " +
                    "scenebuilder.cfgfile(SceneBuilderの構成ファイルのパス) が" +
                    "設定されていません。");
            System.exit(-1);
        }
        return scconfig;
    }

    /**
     * FXMLファイルからクラスパスを取得.
     *
     * クラスパスは 絶対、または FXMLファイルに対する相対で指定すること。
     * クラスファイルフォルダは使用不能。jarファイルを指定すること。
     *
     * @param fxml FXMLのファイルパス
     * @return クラスパス
     * @throws SAXException エラー時
     * @throws IOException エラー時
     * @throws ParserConfigurationException エラー時
     */
    public String getClassPath(String fxml) throws SAXException, IOException, ParserConfigurationException {
        // FXMLをパース
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(fxml));

        //最上位ノードの子ノードを探索
        Node child = document.getFirstChild();
        while (child != null) {
            logger.debug("Node Type = " + child.getNodeType());
            logger.debug("Node Name = " + child.getNodeName());
            logger.debug("Node Value = " + child.getNodeValue());

            if ((child.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE) &&
                    ("classpath".equals(child.getNodeName()))) {
                // 最初の classpath 処理命令　を取得。後続は無視
                return child.getNodeValue();
            }
            child = child.getNextSibling();
        }
        return null;
    }

    /**
     * メイン関数
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        logger.info("アプリ開始");
        Main main = new Main();
        main.execute(args);
        logger.info("アプリ終了");
    }
}
