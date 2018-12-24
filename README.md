# BlogSpider
模拟登陆csdn博客，抓取自己的博客文章到本地写成Markdown文档

### 我的需求
因为在csdn写过博客，csdn的Markdown写作体验还是可以的，不过还是不太喜欢在网页上去写，现在一般是在本地写好Markdown通过hexo部署到github page。
因此想把之前在csdn中写的markdown文档全部抓取到我本地来。

### 过程
- 浏览器F12查看了登陆时需要的Cookie参数
- 代码中模拟登陆获取成功返回的UserToken
- 访问我的文章列表获取所有Markdown类型文章
- 把文章中的图片下载上传[微博图床]并把新链接替换到原文档位置
- 把文章写成MD文件到本地按分类生成目录存放

### TODO
- [x] 模拟登陆
- [x] 获取博文写MD
- [x] 解决csdn图片防盗链
