功能：提供HQL及SQL的条件拼接功能
目前只支持各个条件的条件"与"and

在前台页面中支持2种方式的查询：
1 
【示例】
<input name="conditions[age,int,>=]" value="1">
<input name="conditions[obj.age,int,>=]" value="1">
<input name="orders[e.id,,]" value="1">

【说明】
input框中name的格式写法为“conditions[名称，类型,操作符]”
1）名称 为 “字段名” 或 “对象名.字段名” 或“对象名.别名.字段名”
支持通过别名查询！！！
2）操作符 为eq  ge  gt le  lt ne  like llike rlike in
        或为=   >=  >  <=  <  <>  
3) 类型  支持 string int long float double date boolean

2【示例】
<input name="search_age_int_eq" value="1">
<input name="order_age" value="asc">



