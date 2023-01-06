![Fluocean](https://picture.hanbings.com/2023/01/06/db6ea1fb54490.png)

<h1 align="center">🌊 Fluocean</h1>
<h5 align="center">海纳百川 有容乃大！</h5>

## 🍀 这是什么？

**洋流**是一条 OAuth 客户端的辅助工具，通过低耦合的设计给用户高自定义度的接口，并且洋流集成了常见 OAuth 平台的封装，提供了光速集成第三方登录的能力。

**洋流**的主要特性如下：

- 常见的 OAuth 平台适配
- 支持自定义 State 生成器、自定义 Http 客户端、自定义 Json 解析器
- 提供通用默认实现以便支持未适配 OAuth 平台
- 默认 Http 客户端实现支持 Socks 代理
- 可爱

## ⚡️ 快速开始

**Github OAuth 示例**

```java
// 创建 OAuth 原始处理器
OAuth<GithubAccess, GithubAccess.Wrong> oauth = new GithubOAuth(
        "id",
        "secret",
        "https://exmaple.com/api/v0/login/oauth/github/callback"
);

// 生成授权 url
String url = oauth.authorize();
// 生成带参数或指定 scope
String spec = oauth.authorize(List.of("email"), Map.of("Accept", "application/json"));
        
//解析回调的 url 并获取 token
// 输入原始 url 自动解析 code 以及 state
oauth.token("");
// 更改回调地址
oauth.token("","");
// 手动指定参数
oauth.token("", "","");
        
// 处理返回值
oauth.token("code", "state", "callback")
        .succeed(data -> System.out.println(data.accessToken()))
        .fail(wrong -> System.out.println(wrong.errorDescription()))
        .except(throwable -> System.out.println(throwable.getMessage()));
        
// 假设请求成功 直接获取数据
GithubAccess access = oauth.token("code", "state", "callback").data();
```

**使用 Socks 代理**

```java

```

**更换 State 生成器**

```java

```

**更换 Http 客户端**

```java

```

**更换 Json 解析器**

```java

```

## ⚖ 开源许可

本项目使用 [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.html) 许可协议进行开源。

本项目是**非盈利性**项目。

## 🍀 关于开源

开源是一种精神。

开源运动所坚持的原则：

1. 坚持开放与共享，鼓励最大化的参与与协作。
2. 尊重作者权益，保证软件程序完整的同时，鼓励修改的自由以及衍生创新。
3. 保持独立性和中立性。

与来自五湖四海的开发者共同**讨论**技术问题，**解决**技术难题，**促进**应用的发展是开源的本质目的。

**众人拾柴火焰高，开源需要依靠大家的努力，请自觉遵守开源协议，弘扬开源精神，共建开源社区！**