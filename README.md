# SwaggerApp
对HTML格式API文档的抓取，并自动化转换为Swagger框架下的API文档。

Java6.0环境
所需开源开发包: org.jsoup.Jsoup  net.sf.json

工具使用步骤：
在Eclipse中导入项目后，以Java Application运行。
出现GUI界面，最底下一栏有四个按钮。

点击按钮“Weibo”，将调用函数WeiboIR.wbIR()，抓取微博的HTML格式API文档，并转化为Swagger规范的API文档显示在GUI界面中。失败将在GUI界面报错。

点击按钮“Youku”，将调用函数YoukuIR.ykIR()，抓取优酷的HTML格式API文档，并转化为Swagger规范的API文档显示在GUI界面中。失败将在GUI界面报错。

点击按钮“Youtube”，将调用函数YoutubeIR.ytIR()，抓取YouTube的HTML格式API文档，并转化为Swagger规范的API文档显示在GUI界面中。失败将在GUI界面报错。（此步骤需保证计算机翻墙，能访问YouTube的API文档页面）

点击按钮“自定义”，将弹出一个新的GUI界面，可在其中的多个JTextField中添加API对应的参数，自定义Swagger规范的API文档，完成后，将在主界面显示自定义生成的Swagger规范的API文档。
