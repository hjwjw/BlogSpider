# BlogSpider
模拟登陆csdn博客，抓取自己的博客文章到本地写成Markdown文档

### 我的需求
因为在csdn写过博客，csdn的Markdown写作体验还是可以的，不过还是不太喜欢在网页上去写，现在一般是在本地写好Markdown通过hexo部署到github page。
因此想把之前在csdn中写的markdown文档全部抓取到我本地来。

### 过程
- 打开登陆页面会生成Cookie，取得Cookie中的几个参数
- 登陆并带上前面获取的参数
- 获取登陆成功的Cookie，成功会返回UserToken等参数，后面访问任何页面都要带上这些参数。
- 访问我的文章列表获取所有的文章信息
- 把所有文章写成MD文档按分类生成目录存放

### TODO
- [x] 模拟登陆
- [x] 获取博文写MD
- [x] 解决csdn图片防盗链
