# SpringMVC 工作流程

## 概念

- Spring前端控制器（中央调度器）DispatcherServlet;
- 处理器映射器 HandlerMapping;
- 处理器 Handler;
- 执行链 HandlerExecutionChain（由目标handler和一组HandlerInterceptor组成）;
- 处理器适配器 HandlerAdapter;
- ModelAndView对象；
- 视图解析器 ViewResolver

## 注意

- HandlerExecutionChain并不负责真正的执行动作，也不知道怎么执行目标Handler，仅仅是一个保存这些对象的容器；
- ModelAndView对象中的View仅仅是view的名称，并不是真正的View对象；

## 工作流程

1. 用户向服务器发送请求，请求被Spring前端控制器（中央调度器）DispatcherServlet捕获；
2. DispatcherServlet对请求URL进行解析，得到请求资源标识符（URI）。然后根据URI调用处理器映射器（HandlerMapping）获得具体的处理器Handler，以执行链（HandlerExecutionChain）对象的形式，将处理器对象（Handler）及处理器拦截器（HandlerInterceptor）返回给DispatcherServlet；
3. DispatcherServlet根据获取的Handler，选择一个合适的HandlerAdapter。（如果成功获取HandlerAdaptor，将开始执行拦截器的preHandler()方法）；
4. 提取Request中的模型数据，填充Handler入参，开始执行Handler（即后端控制器Controller）；在填充Handler入参的过程中，根据配置Spring将做一些额外的工作：
    1. HttpMessageConveter：将请求消息（如Json、XML等数据）转换成一个对象，将对象转换为指定的响应消息；
    2. 数据转换：将请求消息进行数据转换。例如：String转换成Integer、Double等；
    3. 数据格式化：对请求消息进行数据格式化。例如：将字符串转换成格式化数字或格式化日期等；
    4. 数据验证：验证数据的有效性（长度、格式等），验证结果存储到BingdingResult或Error中；
5. Handler执行完成后，HandlerAdaptor向DispatcherServlet返回一个ModelAndView对象，其中view是视图名称，并不是真正的视图对象；
6. 根据返回的ModelAndView，选择一个合适的ViewResolver（视图解析器）返回给DispatcherServlet（必须是已经注册到Spring容器中的ViewResolver）；
7. DIspatcherServlet将ModelAndView传给ViewResolver视图解析器；
8. ViewResolver解析后返回具体的View；
9. DispatcherServlet对View进行渲染视图（将数据模型填充到视图中）；
10. DispatcherServlet对用户进行响应；