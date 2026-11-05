# EasyExchange · 校园二手交易平台

一个用纯 Java SE 写的校园二手交易系统练习项目。核心思路是：

> 用户提交**请求（request）** → 进入待处理队列 → **管理员统一审批** → 通过后进入**仓库（WareHouse）**。

项目只依赖 JDK，不引入任何第三方库，不使用框架，目的是把 Java 基础（面向对象、集合、Stream、序列化）真正用起来。

---

## 目录结构

```
EasyExchange/
├── src/                    # 全部源码（当前未分包，均为默认包）
│   ├── User.java           # 普通用户：上架、购买、查询
│   ├── Administrator.java  # 管理员：审批请求、处理购买
│   ├── product.java        # 商品实体（可序列化）
│   ├── request.java        # 请求实体：动作 + 商品
│   ├── buy_put.java        # 枚举：请求的两种动作
│   ├── WareHouse.java      # 仓库：id 索引 + 名称索引
│   ├── request_lite.java   # 待处理请求队列
│   ├── Main.java           # 目前为空（预留主入口）
│   └── test.java           # 临时测试入口，目前靠它跑通全流程
├── .gitignore
└── README.md
```

---

## 如何运行

源码是 **UTF-8**，而 Windows 上 `javac` 默认用 GBK，所以**必须带 `-encoding UTF-8`**，否则中文注释会报 `unmappable character`。

```bash
cd D:\EasyExchange
javac -encoding UTF-8 -d out src\*.java
java -cp out test
```

在 IDEA 中运行则无需额外参数（IDEA 自己会传编码）。

---

## 核心设计：请求与审批

系统里**没有“直接上架”这条路**。用户的一切操作都先变成一条 `request`，由管理员批量审批后才生效。

这样设计的好处：

- 用户和管理员**解耦**，用户不需要知道仓库长什么样
- 所有操作可被**统一拦截**（未来可加审核、限额、风控）
- 请求队列天然对应真实业务里的“待办工单”

两种请求动作由枚举 `buy_put` 表示：

| 枚举常量 | `action` 值 | 含义 |
| --- | --- | --- |
| `buy_put.A` | `"buy"` | 购买请求 |
| `buy_put.B` | `"put"` | 上架请求 |

---

## 类与字段说明

### `product` —— 商品

| 字段 | 类型 | 作用 |
| --- | --- | --- |
| `name` | `String` | 商品名，同时作为名称索引的 key |
| `price` | `int` | 价格（单位：元，整型） |
| `id` | `int` | 商品唯一编号，由静态计数器分配 |
| `data` | `static int` | **全局自增计数器**，每创建一个商品 +1，构成 id |
| `serialVersionUID` | `static final long` | 序列化版本号，固定为 `1L`，避免类改动后反序列化失败 |

> 注意：`data` 是 `static`，**JVM 重启后会归零**，因此 id 在多次运行之间会重复。

### `request` —— 请求

| 字段 | 类型 | 作用 |
| --- | --- | --- |
| `request` | `String` | 动作标识，取值 `"buy"` 或 `"put"` |
| `p` | `product` | 该请求携带的商品对象 |

一条请求 = 一个动作 + 一个商品，本质是“待办工单”。

### `User` —— 普通用户

| 字段 | 类型 | 作用 |
| --- | --- | --- |
| `name` | `String` | 用户名 |
| `password` | `String` | 密码（当前明文存储，未加密） |

### `Administrator` —— 管理员

| 字段 | 类型 | 作用 |
| --- | --- | --- |
| `name` | `String` | 管理员名 |
| `password` | `String` | 密码（当前明文存储） |

### `WareHouse` —— 仓库（两个静态索引）

| 字段 | 类型 | 作用 |
| --- | --- | --- |
| `warehouse` | `static HashMap<Integer, product>` | **主索引**：商品 id → 商品对象，用于按 id 精确查找、删除 |
| `reverse` | `static HashMap<String, List<product>>` | **名称索引**：商品名 → 同名商品列表，用于按名字模糊查找。同名商品可有多件，所以 value 是 `List` |

两个 Map 指向**同一批 `product` 对象**（存的是引用，不是副本），所以改一处两边都变。

> `reverse` 就是数据库里“二级索引”的手工版，代价是需要自己保证两个索引同步。

### `request_lite` —— 待处理请求队列

| 字段 | 类型 | 作用 |
| --- | --- | --- |
| `request_lite` | `static LinkedList<request>` | 存放所有**尚未审批**的请求，管理员审批后从中移除 |

用 `LinkedList` 而不是 `ArrayList`，因为这里以“头部取出 + 中间删除”为主。

---

## 方法说明

### `product`

| 方法 | 功能 |
| --- | --- |
| `private product(String name, int price)` | 私有构造，禁止外部 `new`，强制走工厂方法 |
| `static product creat(String name, int price)` | 工厂方法：创建商品并自动分配自增 id |
| `String getName()` | 取商品名 |
| `int getPrice()` | 取价格 |
| `int getId()` | 取商品编号 |

### `request`

| 方法 | 功能 |
| --- | --- |
| `private request(String request, product p)` | 私有构造 |
| `static request creat(String request, product p)` | 工厂方法：组装一条请求 |
| `String getRequest()` | 取动作标识（`"buy"` / `"put"`） |
| `product getP()` | 取请求携带的商品 |

### `User`

| 方法 | 功能 |
| --- | --- |
| `private User(String name, String password)` | 私有构造 |
| `static User creat(String name, String password)` | 工厂方法：创建用户 |
| `void put(String name, int price)` | **上架**：创建商品 → 包装成 `"put"` 请求 → 加入待处理队列 |
| `void buy(product p)` | **购买**：把传入的商品包装成 `"buy"` 请求 → 加入待处理队列 |
| `void check(String name)` | **查询**：按商品名查 `WareHouse.reverse`，用 Stream 把结果拼成 `(id, name, price)` 形式打印；查不到时输出提示 |

### `Administrator`

| 方法 | 功能 |
| --- | --- |
| `private Administrator(String name, String password)` | 私有构造 |
| `static Administrator creat(String name, String password)` | 工厂方法 |
| `void address()` | **审批入口**：遍历待处理队列，按 `request` 字段分发到 `buy()` 或 `put()` |
| `void put(request request)` | **批准上架**：写入 `warehouse` 主索引、维护 `reverse` 名称索引、从队列移除该请求 |
| `void buy(request request)` | **批准购买**：从 `warehouse` 按 id 删除商品；找不到则输出“没有找到该商品” |

### `test`

| 方法 | 功能 |
| --- | --- |
| `static void main(String[] args)` | 临时入口：建用户和管理员 → 上架两件商品 → 打印队列 → 两次审批 → 打印两个索引 → 查询两种商品 |

---

## 三条主流程

**1. 上架**

```
User.put("手机", 3999)
  └─ product.creat()           分配 id
  └─ request.creat("put", p)   包装成请求
  └─ request_lite.add()        进入待处理队列
```

**2. 审批**

```
Administrator.address()
  └─ 遍历 request_lite
      ├─ "put" → put()  → warehouse.put(id, p)
      │                 → reverse 追加到同名 List
      └─ "buy" → buy()  → warehouse.remove(id)
      └─ 从队列移除该请求
```

**3. 查询**

```
User.check("手机")
  └─ WareHouse.reverse.get("手机")
  └─ stream().map(...).collect(joining(", "))   拼成 (0, 手机, 3999), (1, 手机, 2999)
```

---

## 当前完成度

| 模块 | 进度 | 说明 |
| --- | --- | --- |
| 实体建模 | 85% | 四个实体 + 一个枚举，全部用私有构造 + 工厂方法 |
| 内存仓库 | 60% | 双索引可查，但全是 `static`，尚无 `Repository<T>` 抽象 |
| 上架流程 | 80% | 提交 → 审批 → 入库 链路已通 |
| 购买流程 | 15% | 只是删除商品，**没有订单、没有库存、没有金额结算** |
| 查询 | 90% | 支持同名商品多条结果 |
| 用户体系 | 20% | 有账号密码字段，但无注册、无登录、无重名校验、密码明文 |
| 数据持久化 | 0% | **程序退出数据全部丢失** |
| 主入口 | 10% | `Main` 为空，实际入口是 `test` |

综合约 **35%**。

---

## 已知问题

1. **`Administrator.address()` 边遍历边删除**
   `for (request re : request_lite.request_lite)` 内部 `put()` 会 `remove()`，导致：
   - 2 条请求时**静默丢数据**（循环提前结束，不报错）
   - 3 条请求时抛 `ConcurrentModificationException`

   修法：遍历副本 `new ArrayList<>(request_lite.request_lite)`。

2. **id 会重复** —— `product.data` 是静态计数器，重启归零。

3. **无持久化** —— 所有数据在内存，退出即清空。

4. **全部为默认包** —— 一旦要分包，默认包里的类无法被其他包 `import`。

5. **命名不规范** —— `product`、`request`、`buy_put` 应为 `Product`、`Request`、`BuyPut`；`creat` 应为 `create`。

6. **`request` 缺少 `serialVersionUID`** —— 编译器有 `[serial]` 警告。

---

## 后续计划

- [ ] 修复 `address()` 遍历删除问题
- [ ] 把 `test` 换成带菜单的 `Main`
- [ ] 抽出 `Repository<T>` 接口，实现 `InMemoryRepository<T>` 与 `FileRepository<T>`
- [ ] 补齐订单（`Order`）、库存（`stock`）、余额（`balance`）
- [ ] 用户注册 / 登录 / 密码加密
- [ ] 按 `entity` / `service` / `repository` 分包
- [ ] 学完 SQL 后，把 `FileRepository` 换成 `JdbcRepository`，接 MySQL
