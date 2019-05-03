# fundation-learning
  一些基础学习

##imitate-tomcat

用ServerSocket实现的简单的服务器

思路如下：
1. 监听端口，监听客户端的访问
2. 获取客户端的请求，解析并返回URI地址，IO读取磁盘对应的静态文件
3. 文件存在，返回200和文件正文，否则返回404