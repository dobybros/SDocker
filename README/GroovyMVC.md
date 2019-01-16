### 数据格式
以json格式进行存储，每一个记录为一个文档，数据结构如下:
```
{
    "name":         "John Smith",
    "age":          42,
    "confirmed":    true,
    "join_date":    "2014-06-01",
    "home": {
        "lat":      51.5,
        "lon":      0.1
    },
    "accounts": [
        {
            "type": "facebook",
            "id":   "johnsmith"
        },
        {
            "type": "twitter",
            "id":   "johnsmith"
        }
    ]
}
```

### 文档元数据
一个文档不只有数据。它还包含了元数据(metadata)——关于文档的信息。三个必须的元数据节点是：
节点 | 说明
--- | ---
_index | 文档存储的地方 (数据库)
_type | 文档代表的对象 (表)
_id | 文档唯一标识 

### 操作
> 索引文档 (创建文档)
```
PUT /{index}/{type}/{id}
{
  "field": "value",
  ...
}

PUT /website/blog/123
{
  "title": "My first blog entry",
  "text":  "Just trying this out...",
  "date":  "2014/01/01"
}
```
es返回数据,携带索引，类型，id和版本号,如果索引成功，created为true,否则为false
```
{
   "_index":    "website",
   "_type":     "blog",
   "_id":       "123",
   "_version":  1,
   "created":   true
}
```
如果要es自动为文档生成id,需要post请求
```
POST /website/blog/
{
  "title": "My second blog entry",
  "text":  "Still trying this out...",
  "date":  "2014/01/01"
}
```

### 检索文档
```
GET /website/blog/123?pretty
```
需要指定_index, _type, _id， 添加pretty是为了输出让输出格式美观<br/>
响应包含了现在熟悉的元数据节点，增加了_source字段，它包含了在创建索引时我们发送给Elasticsearch的原始文档，查找成功found为true,否则为false。由于是自己定义的文档id,如果已经有相同id的文档，则会替换掉以前的数据
```
{
  "_index" :   "website",
  "_type" :    "blog",
  "_id" :      "123",
  "_version" : 1,
  "found" :    true,
  "_source" :  {
      "title": "My first blog entry",
      "text":  "Just trying this out...",
      "date":  "2014/01/01"
  }
}
```
只检索文档的一部分,需要指定_source，后面跟想要获取的字段
```
GET /website/blog/123?_source=title,text
```
只获取_source里的数据
```
GET /website/blog/123/_source

返回

{
   "title": "My first blog entry",
   "text":  "Just trying this out...",
   "date":  "2014/01/01"
}
```
### 检查文档是否存在
```
HEAD /website/blog/123
```
使用HEAD方法来代替GET，如果不存在，返回404

### 更新整个文档
```
不能直接对文档进行更新，而是可以重建索引替换掉它
PUT /website/blog/123
{
  "title": "My first blog entry",
  "text":  "I am starting to get the hang of this...",
  "date":  "2014/01/02"
}

返回

{
  "_index" :   "website",
  "_type" :    "blog",
  "_id" :      "123",
  "_version" : 2,
  "created":   false
}
因为已经有此id的文档,所以created为false,并且 _version 增加了
```
1. 从旧文档中检索JSON
2. 修改它
3. 删除旧文档
4. 索引新文档

### 创建新的文档
1. 当创建文档时，不确定是完全创建还是覆盖已经存在的，为了防止覆盖,可以有如下方法创建:
```
让es自动生成id
POST /website/blog/
{ ... }

指定参数
PUT /website/blog/123?op_type=create
{ ... }

或者
PUT /website/blog/123/_create
{ ... }
```
如果创建成功，返回201 Cretaed，否则返回409 Conflict 表示已经有相同id的文档

### 删除文档, 注意 _version增加，成功 200 OK, 找不到 404 Not Found
```
DELETE /website/blog/123

返回

{
  "found" :    true,
  "_index" :   "website",
  "_type" :    "blog",
  "_id" :      "123",
  "_version" : 3
}
```

### 版本控制
如果有并发读取修改同一个文档，会发生一致性问题，可能旧文档覆盖新文档，所以_version字段对保证文档一致性很重要。
1.  乐观并发控制
> 我们利用_version的这一优点确保数据不会因为修改冲突而丢失。我们可以指定文档的version来做想要的更改。如果那个版本号不是现在的，我们的请求就失败了。
```
PUT /website/blog/1?version=1
{
  "title": "My first blog entry",
  "text":  "Starting to get the hang of this..."
}

修改成功,返回

{
  "_index":   "website",
  "_type":    "blog",
  "_id":      "1",
  "_version": 2
  "created":  false
}

_version 变为 2
```

2. 使用外部版本控制系统
> 使用一些其他的数据库做为主数据库,es只是用来创建索引,可以为文档指定外部版本号，外部版本号不仅在索引和删除请求中指定，也可以在创建(create)新文档中指定。
```
PUT /website/blog/2?version=5&version_type=external
{
  "title": "My first external blog entry",
  "text":  "Starting to get the hang of this..."
}

返回

{
  "_index":   "website",
  "_type":    "blog",
  "_id":      "2",
  "_version": 5,
  "created":  true
}
```

### 文档局部更新
 文档一旦创建，就是不可变的，如果改动，需要通过检索，修改，然后重建整文档的索引方法来更新文档。 使用update api 内部操作也是一样。
 
 update请求表单接受一个局部文档参数doc，它会合并到现有文档中——对象合并在一起，存在的标量字段被覆盖，新字段被添加
 ```
POST /website/blog/1/_update
{
   "doc" : {
      "tags" : [ "testing" ],
      "views": 0
   }
}

返回

{
   "_index" :   "website",
   "_id" :      "1",
   "_type" :    "blog",
   "_version" : 3
}

检索文档文档显示被更新的_source字段：

{
   "_index":    "website",
   "_type":     "blog",
   "_id":       "1",
   "_version":  3,
   "found":     true,
   "_source": {
      "title":  "My first blog entry",
      "text":   "Starting to get the hang of this...",
      "tags": [ "testing" ], <1>
      "views":  0 <1>
   }
}
 ```
 
更新不可能存在的文档,如果不存在，则创建，如果存在，按正常流程处理
```
POST /website/pageviews/1/_update
{
   "script" : "ctx._source.views+=1",
   "upsert": {
       "views": 1
   }
}
```
### 检索多个文档
合并多个请求可以避免每个请求单独的网络开销。

mget API参数是一个docs数组，数组的每个节点定义一个文档的_index、_type、_id元数据。如果你只想检索一个或几个确定的字段，也可以定义一个_source参数：
```
POST /_mget
{
   "docs" : [
      {
         "_index" : "website",
         "_type" :  "blog",
         "_id" :    2
      },
      {
         "_index" : "website",
         "_type" :  "pageviews",
         "_id" :    1,
         "_source": "views"
      }
   ]
}
```
如果你想检索的文档在同一个_index中（甚至在同一个_type中），你就可以在URL中定义一个默认的/_index或者/_index/_type
```
POST /website/blog/_mget
{
   "docs" : [
      { "_id" : 2 },
      { "_type" : "pageviews", "_id" :   1 }
   ]
}
```
如果所有文档具有相同_index和_type，你可以通过简单的ids数组来代替完整的docs数组
```
POST /website/blog/_mget
{
   "ids" : [ "2", "1" ]
}
```

### 更新时的批量操作
就像mget允许我们一次性检索多个文档一样，bulk API允许我们使用单一请求来实现多个文档的create、index、update或delete。

bulk请求体如下，它有一点不同寻常
```
{ action: { metadata }}\n
{ request body        }\n
{ action: { metadata }}\n
{ request body        }\n
...
```
行为 | 解释
--- | ---
create | 当文档不存在时创建
index | 创建新文档或替换已有文档
update | 局部更新文档
delete | 删除一个文档

删除
```
{ "delete": { "_index": "website", "_type": "blog", "_id": "123" }}
```
请求体(request body)由文档的_source组成——文档所包含的一些字段以及其值。它被index和create，update操作所必须

创建
```
{ "create":  { "_index": "website", "_type": "blog", "_id": "123" }}
{ "title":    "My first blog post" }
```

```
POST /_bulk
{ "delete": { "_index": "website", "_type": "blog", "_id": "123" }}
{ "create": { "_index": "website", "_type": "blog", "_id": "123" }}
{ "title":    "My first blog post" }
{ "index":  { "_index": "website", "_type": "blog" }}
{ "title":    "My second blog post" }
{ "update": { "_index": "website", "_type": "blog", "_id": "123", "_retry_on_conflict" : 3} }
{ "doc" : {"title" : "My updated blog post"} }

返回

{
   "took": 4,
   "errors": false, <1>
   "items": [
      {  "delete": {
            "_index":   "website",
            "_type":    "blog",
            "_id":      "123",
            "_version": 2,
            "status":   200,
            "found":    true
      }},
      {  "create": {
            "_index":   "website",
            "_type":    "blog",
            "_id":      "123",
            "_version": 3,
            "status":   201
      }},
      {  "create": {
            "_index":   "website",
            "_type":    "blog",
            "_id":      "EiwfApScQiiy7TIKFxRCTw",
            "_version": 1,
            "status":   201
      }},
      {  "update": {
            "_index":   "website",
            "_type":    "blog",
            "_id":      "123",
            "_version": 4,
            "status":   200
      }}
   ]
}}
```
记得最后一个换行符

不要重复,就像mget API，bulk请求也可以在URL中使用/_index或/_index/_type:
```
POST /website/log/_bulk
{ "index": {}}
{ "event": "User logged in" }
{ "index": { "_type": "blog" }}
{ "title": "Overriding the default type" }
```

### 搜索
#### 空搜索 <br/>
> 最基本的搜索API表单是空搜索(empty search)，它没有指定任何的查询条件，只返回集群索引中的所有文档：
```
GET /_search

返回

{
   "hits" : {
      "total" :       14,
      "hits" : [
        {
          "_index":   "us",
          "_type":    "tweet",
          "_id":      "7",
          "_score":   1,
          "_source": {
             "date":    "2014-09-17",
             "name":    "John Smith",
             "tweet":   "The Query DSL is really powerful and flexible",
             "user_id": 2
          }
       },
        ... 9 RESULTS REMOVED ...
      ],
      "max_score" :   1
   },
   "took" :           4,
   "_shards" : {
      "failed" :      0,
      "successful" :  10,
      "total" :       10
   },
   "timed_out" :      false
}
```
- hits里, _score字段，这是相关性得分(relevance score)，它衡量了文档与查询的匹配程度，按照_score降序排列。max_score指的是所有文档匹配查询中_score的最大值。
- took代表整个搜索请求花费的毫秒数
- _shards节点告诉我们参与查询的分片数（total字段），有多少是成功的（successful字段），有多少的是失败的（failed字段）。
- time_out值告诉我们查询超时与否,如果响应速度比完整的结果更重要,可以定义timeout参数
```
GET /_search?timeout=10ms
```
#### 多索引和多类别
空搜索的结果中含有不同索引和类型的文档。<br/>
可以指定搜索范围
```
/_search
在所有索引的所有类型中搜索 

/gb/_search
在索引gb的所有类型中搜索

/gb,us/_search
在索引gb和us的所有类型中搜索

/g*,u*/_search
在以g或u开头的索引的所有类型中搜索

/gb/user/_search
在索引gb的类型user中搜索

/gb,us/user,tweet/_search
在索引gb和us的类型为user和tweet中搜索

/_all/user,tweet/_search
在所有索引的user和tweet中搜索 search types user and tweet in all indices
```
当你搜索包含单一索引时，Elasticsearch转发搜索请求到这个索引的主分片或每个分片的复制分片上，然后聚集每个分片的结果。搜索包含多个索引也是同样的方式——只不过或有更多的分片被关联。


#### 分页
Elasticsearch接受from和size参数 <br/>
```
size: 结果数，默认10 <br/>
from: 跳过开始的结果数，默认0 <br/>
```
如果你想每页显示5个结果，页码从1到3，那请求如下
```
GET /_search?size=5
GET /_search?size=5&from=5
GET /_search?size=5&from=10
```

++一个搜索请求常常涉及多个分片。每个分片生成自己排好序的结果，它们接着需要集中起来排序以确保整体排序正确。++
> ### 在集群系统中深度分页
> 为了理解为什么深度分页是有问题的，让我们假设在一个有5个主分片的索引中搜索。当我们请求结果的第一页（结果1到10）时，每个分片产生自己最顶端10个结果然后返回它们给请求节点(requesting node)，它再排序这所有的50个结果以选出顶端的10个结果。 <br/><br/>
现在假设我们请求第1000页——结果10001到10010。工作方式都相同，不同的是每个分片都必须产生顶端的10010个结果。然后请求节点排序这50050个结果并丢弃50040个！<br/><br/>
你可以看到在分布式系统中，排序结果的花费随着分页的深入而成倍增长。这也是为什么网络搜索引擎中任何语句不能返回多于1000个结果的原因。

#### 简易搜索
search有两种，一种是**查询字符串**,一种是**JSON完整的请求体**，也叫做结构化查询语句(DSL)。
```
在tweet字段中包含elasticsearch字符的文档:

GET /_all/tweet/_search?q=tweet:elasticsearch

查找name字段中包含"john"和tweet字段包含"mary"的结果:
GET /_all/tweet/_search?q=+name:john +tweet:mary

name字段包含"mary"或"john"
date晚于2014-09-10
_all字段包含"aggregations"或"geo"
GET /_all/tweet/_search?q=+name:(mary john) +date:>2014-09-10 +(aggregations geo)

```



















