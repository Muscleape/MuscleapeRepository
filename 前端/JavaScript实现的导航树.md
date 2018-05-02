## JS导航树

> 整理之前的小代码片段，放到博客，便于以后完善查看；
> 该JS导航树实际效果，【GSP+社区网站专题课程页面导航树】地址：http://gsp.inspur.com/knowledge/zhuanti/gsp_dev_topics

```
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>JavaScript导航树-GSP+专题导航树</title>
    <script src="head/jquery.min.js"></script>
    <style type="text/css">
        <!-- 
        #navigation {
            font-family: Microsoft yahei;
            border-right: 1px solid #ccc;
            border-left: 1px solid #ccc;
            border-bottom: 1px solid #ccc;
            font-size: 14px;
            background-color: #f4f4f4;
        }

        #navigation ul {
            list-style-type: none;
            margin: 0px;
            padding: 0px;
        }

        #navigation a {
            display: block;
            text-decoration: none;
        }

        #navigation li {
            border-top: 1px solid #ccc;
        }

        #navigation a.oneLayer {
            padding: 3px 3px 3px 0.5em;
        }

        #navigation a.twoLayer {
            padding: 3px 3px 3px 2em;
        }

        #navigation a.threeLayer {
            padding: 3px 3px 3px 3em;
        }

        #navigation a.fourLayer {
            padding: 3px 3px 3px 4em;
        }

        #navigation a:link,
        a:visited {
            background-color: #f4f4f4;
            color: #0088cc;
        }

        #navigation a:hover {
            background-color: #ccc;
            color: #000;
        }

        .arrow {
            width: 0;
            height: 0;
            border: 6px solid transparent;
            border-left-color: #0088cc;
            display: inline-block;
            * position: absolute;
            * margin-top: 3px;
            margin-left: -2px;
            font-size: 1px;
            line-height: 0
        }

        .myActivedNode a {
            background-color: #0088cc !important;
            color: #ffffff !important;
        }
        -->
    </style>
    <script language="javascript">
        function myClick() {
            //this 点选的<a>
            //上级UL节点
            var myParentUL = $(this).parent().parent();
            var myFlag = myParentUL.attr("flag");
            //下级UL节点
            var myNextUL = $(this).next();
            var myNextFlag = myNextUL.attr("flag");
            //上级LI节点
            var myPrevLI = $(this).parent();
            var myPrevLIId = myPrevLI.attr("id");

            if (myNextFlag == 0) {
                //alert("flag " + myFlag);
                myNextUL.toggle();
                myNextUL.attr("flag", "1");
                //$(this).find("span").attr("class","arrowOpen");
                //节点ID写入cookie
                $.cookie('wzqOpenNode', myPrevLIId, { path: '/' });
            } else if (myNextFlag == 1) {
                //alert("flag" + myFlag);
                myNextUL.toggle();
                myNextUL.attr("flag", "0");
                //$(this).find("span").attr("class","arrow");
                //关闭所有子节点
                var muSonUL = myNextUL.find("ul");
                muSonUL.hide();
                muSonUL.attr("flag", "0");
                myParentUL.find("span").attr("class", "arrow");
            } else {
                $.cookie('wzqOpenNode', myPrevLIId, { path: '/' });
            }

        }
        function openPage() {

            $("#navigation ul").attr("flag", "0");//添加属性flag,表示节点是否展开
            $("#navigation").find("a").click(myClick);//添加单击事件
            $("#navigation li:has(ul)").find("ul").hide();//页面加载时所有菜单默认为折叠

            var myNodeId = $.cookie('wzqOpenNode').toString();
            //$.cookie('wzqOpenNode',null,{path:'/'})
            if (myNodeId.length == 8) {
                var myArrayID = myNodeId.split("");
                var myArrayIDOne = myArrayID[0] + myArrayID[1];
                var myArrayIDTwo = myArrayID[2] + myArrayID[3];
                var myArrayIDThree = myArrayID[4] + myArrayID[5];
                var myArrayIDFour = myArrayID[6] + myArrayID[7];

                $('#' + myNodeId).find(">ul").attr("flag", "1");
                $('#' + myNodeId).find(">ul").show();

                if (myArrayIDFour != "00") {

                    var fourNode = $('#' + myNodeId);
                    fourNode.parent().attr("flag", "1");
                    fourNode.parent().attr("style", "display:block");

                    var threeID = myArrayIDOne + myArrayIDTwo + myArrayIDThree + "00";
                    var threeNode = $('#' + threeID);
                    threeNode.parent().attr("flag", "1");
                    threeNode.parent().attr("style", "display:block");

                    var twoID = myArrayIDOne + myArrayIDTwo + "00" + "00";
                    var twoNode = $('#' + twoID);
                    twoNode.parent().attr("flag", "1");
                    twoNode.parent().attr("style", "display:block");

                    var oneID = myArrayIDOne + "00" + "00" + "00";
                    var oneNode = $('#' + oneID);
                    oneNode.parent().attr("flag", "1");
                    oneNode.parent().attr("style", "display:block");

                } else if (myArrayIDFour == "00" && myArrayIDThree != "00") {

                    var threeNode = $('#' + myNodeId);
                    threeNode.parent().attr("flag", "1");
                    threeNode.parent().attr("style", "display:block");

                    var twoID = myArrayIDOne + myArrayIDTwo + "00" + "00";
                    var twoNode = $('#' + twoID);
                    twoNode.parent().attr("flag", "1");
                    twoNode.parent().attr("style", "display:block");

                    var oneID = myArrayIDOne + "00" + "00" + "00";
                    var oneNode = $('#' + oneID);
                    oneNode.parent().attr("flag", "1");
                    oneNode.parent().attr("style", "display:block");

                } else if (myArrayIDThree == "00" && myArrayIDTwo != "00") {

                    var twoNode = $('#' + myNodeId);
                    twoNode.parent().attr("flag", "1");
                    twoNode.parent().attr("style", "display:block");

                }
            }
        }
        $(document).ready(openPage);
    </script>

</head>

<body>
    <div id="navigation">
        <ul id="listUL" style="display:block">
            <li id="01000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>开发规范</a>
                <ul>
                    <li id="01010000"><a class="twoLayer" href="http://数据库设计规范">数据库设计规范</a></li>
                    <li id="01020000"><a class="twoLayer" href="http://数据对象使用规范">数据对象使用规范</a></li>
                    <li id="01030000"><a class="twoLayer" href="http://数据模型使用规范">数据模型使用规范</a></li>
                    <li id="01040000"><a class="twoLayer" href="http://元数据命名规范">元数据命名规范</a></li>
                    <!--<li id="01060000"><a class="twoLayer" href="http://vs开发规范">VS.NET开发规范</a></li>-->
                    <!--<li id="01070000"><a class="twoLayer" href="http://性能优化">性能优化</a></li>-->
                    <!--<li id="01080000"><a class="twoLayer" href="http://控件开发规范">控件开发规范</a></li>-->
                    <!--<li id="01090000"><a class="twoLayer" href="http://数据库建议设计">数据库建议设计</a></li>-->
                </ul>
            </li>
            <li id="02000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>WinForm开发</a>
                <ul>
                    <li id="02010000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>表单模板与控制器使用场景</a>
                        <ul>
                            <li id="02010100"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>卡片类</a>
                                <ul>
                                    <li id="02010101"><a class="fourLayer" href="http://基本卡片">基本卡片</a></li>
                                    <li id="02010102"><a class="fourLayer" href="http://带分录的卡片">带分录的卡片</a></li>
                                </ul>
                            </li>
                            <li id="02010200"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>列表类</a>
                                <ul>
                                    <li id="02010201"><a class="fourLayer" href="http://基本管理列表">基本管理列表</a></li>
                                    <li id="02010202"><a class="fourLayer" href="http://列表单行编辑">列表单行编辑</a></li>
                                    <li id="02010203"><a class="fourLayer" href="http://列表多行编辑">列表多行编辑</a></li>
                                    <li id="02010204"><a class="fourLayer" href="http://双列表管理">双列表管理</a></li>
                                    <li id="02010205"><a class="fourLayer" href="http://双列表编辑（主表单行编辑）">双列表编辑（主表单行编辑）</a></li>
                                    <li id="02010206"><a class="fourLayer" href="http://双列表编辑（主表多行编辑）">双列表编辑（主表多行编辑）</a></li>
                                    <li id="02010207"><a class="fourLayer" href="http://三列表">三列表</a></li>
                                    <li id="02010208"><a class="fourLayer" href="http://基本左列表右卡片">基本左列表右卡片</a></li>
                                    <li id="02010209"><a class="fourLayer" href="http://左列表右上卡片右下列表">左列表右上卡片右下列表</a></li>
                                </ul>
                            </li>
                            <li id="02010300"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>树类</a>
                                <ul>
                                    <li id="02010301"><a class="fourLayer" href="http://基本左树右列表(管理界面)">基本左树右列表（管理界面）</a></li>
                                    <li id="02010302"><a class="fourLayer" href="http://基本左树右卡片">基本左树右卡片</a></li>
                                    <li id="02010303"><a class="fourLayer" href="http://左树右列表(列表可编辑)">左树右列表（列表可编辑）</a></li>
                                    <li id="02010304"><a class="fourLayer" href="http://左树右上卡片右下列表">左树右上卡片右下列表</a></li>
                                </ul>
                            </li>
                            <li id="02010400"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>Tab页</a>
                                <ul>
                                    <li id="02010401"><a class="fourLayer" href="http://tab页展示的列表卡片">tab页展示的列表卡片</a></li>
                                    <li id="02010402"><a class="fourLayer" href="http://tab页展示的高级列表卡片">tab页展示的高级列表卡片</a></li>
                                    <li id="02010403"><a class="fourLayer" href="http://tab页展示的基本树卡片">tab页展示的基本树卡片</a></li>
                                    <li id="02010404"><a class="fourLayer" href="http://tab页展示的高级树卡片">tab页展示的高级树卡片</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li id="02020000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>WinForm构件开发实例</a>
                        <ul>
                            <li id="02020100"><a class="threeLayer" href="http://控制器扩展差别">GS5.2及GS6.0控制器差别</a></li>
                            <li id="02020200"><a class="threeLayer" href="http://构件代码中表单控件类型">构件代码中表单控件类型</a></li>
                            <li id="02020300"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>构件操作步骤</a>
                                <ul>
                                    <li id="02020301"><a class="fourLayer" href="http://构件功能概述">功能概述</a></li>
                                    <li id="02020302"><a class="fourLayer" href="http://构件注册">构件注册</a></li>
                                    <li id="02020303"><a class="fourLayer" href="http://构件维护">构件维护</a></li>
                                    <li id="02020304"><a class="fourLayer" href="http://构件使用">构件使用</a></li>
                                    <li id="02020305"><a class="fourLayer" href="http://自定义构件调试">自定义构件调试</a></li>
                                    <li id="02020306"><a class="fourLayer" href="http://构件开发最佳实践">弹出&ldquo;Hello World&rdquo; 示例</a></li>
                                </ul>
                            </li>
                            <li id="02020400"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>智能帮助相关</a>
                                <ul>
                                    <li id="02020401"><a class="fourLayer" href="http://从菜单或点击按钮直接进入帮助">从菜单(或点击按钮)直接进入帮助</a></li>
                                    <li id="02020402"><a class="fourLayer" href="http://多值帮助设置在表格中">多值帮助设置在表格中</a></li>
                                    <li id="02020403"><a class="fourLayer" href="http://表格中动态更新智能帮助的过滤条件">表格中动态更新智能帮助的过滤条件</a></li>
                                    <li id="02020404"><a class="fourLayer" href="http://同一个字段展现不同的帮助(列表中多值帮助)">同一个字段展现不同的帮助(列表中多值帮助)</a></li>
                                    <li id="02020405"><a class="fourLayer" href="http://同一个字段展现不同的帮助(卡片中单值帮助)">同一个字段展现不同的帮助(卡片中单值帮助)</a></li>
                                    <li id="02020406"><a class="fourLayer" href="http://动态生成智能帮助">动态生成智能帮助</a></li>
                                    <li id="02020407"><a class="fourLayer" href="http://帮助过滤条件">帮助过滤条件优化</a></li>
                                    <li id="02020408"><a class="fourLayer" href="http://帮助映射实现依赖表达式效果">通过帮助映射实现依赖表达式效果</a></li>
                                </ul>
                            </li>
                            <li id="02020500"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>列表类表单相关</a>
                                <ul>
                                    <li id="02020501"><a class="fourLayer" href="http://简单的带过滤的列表">简单的带过滤的列表</a></li>
                                    <li id="02020502"><a class="fourLayer" href="http://复杂的带过滤的列表">复杂的带过滤的列表</a></li>
                                    <li id="02020503"><a class="fourLayer" href="http://子表过滤的列表">子表过滤的列表</a></li>
                                    <li id="02020504"><a class="fourLayer" href="http://高级列表卡片表单的过滤">高级列表卡片表单的过滤 </a></li>
                                    <li id="02020505"><a class="fourLayer" href="http://按条件设置行或者单元格样式">按条件设置行或者单元格样式</a></li>
                                    <li id="02020507"><a class="fourLayer" href="http://列表多级表头">列表多级表头 </a></li>
                                    <li id="02020508"><a class="fourLayer" href="http://列表toolbar菜单">列表toolbar菜单</a></li>
                                    <li id="02020509"><a class="fourLayer" href="http://将左侧列表中的数据分配给右侧列表中">将左侧列表中的数据分配给右侧列表中 </a></li>
                                    <li id="02020510"><a class="fourLayer" href="http://从列表弹出子表编辑页面，点击“确定”后返回，将值回写到列表">从列表弹出子表编辑页面，点击&ldquo;确定&rdquo;后返回，将值回写到列表 </a></li>
                                    <li id="02020511"><a class="fourLayer" href="http://控制表格中某一行是否可以被编辑">控制表格中某一行是否可以被编辑 </a></li>
                                    <li id="02020512"><a class="fourLayer" href="http://控制表格中某个单元格是否可以被编辑">控制表格中某个单元格是否可以被编辑 </a></li>
                                    <li id="02020513"><a class="fourLayer" href="http://动态设置表格格式">动态设置表格格式</a></li>
                                    <li id="02020515"><a class="fourLayer" href="http://gridview单元格验证的相关处理程序">gridView单元格验证的相关处理程序</a></li>
                                    <li id="02020516"><a class="fourLayer" href="http://在列表中指定行插入一行">在列表中指定行插入一行</a></li>
                                    <li id="02020517"><a class="fourLayer" href="http://多tab页多列表表单实现方式">多Tab页多列表表单实现方式</a></li>
                                    <li id="02020518"><a class="fourLayer" href="http://如何定位和查找指定列显示值的行">如何定位和查找指定列显示值的行</a></li>
                                </ul>
                            </li>
                            <li id="02020600"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>卡片类表单相关</a>
                                <ul>
                                    <li id="02020601"><a class="fourLayer" href="http://卡片表单导航">卡片表单导航</a></li>
                                    <li id="02020602"><a class="fourLayer" href="http://附件操作二进制数据库字段">附件操作_二进制数据库字段</a></li>
                                    <li id="02020603"><a class="fourLayer" href="http://附件操作物理文件">附件操作_物理文件</a></li>
                                </ul>
                            </li>
                            <li id="02020700"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>树类表单相关</a>
                                <ul>
                                    <li id="02020701"><a class="fourLayer" href="http://带过滤的树实现方式">带过滤的树实现方式</a></li>
                                    <li id="02020702"><a class="fourLayer" href="http://列表带过滤的左树右列表实现方式">列表带过滤的左树右列表实现方式</a></li>
                                    <li id="02020703"><a class="fourLayer" href="http://树手工绑定数据源">树手工绑定数据源</a></li>
                                </ul>
                            </li>
                            <li id="02020800"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>打开窗口相关</a>
                                <ul>
                                    <li id="02020801"><a class="fourLayer" href="http://弹出模态窗口">弹出模态窗口</a></li>
                                    <li id="02020802"><a class="fourLayer" href="http://弹出非模态窗口">弹出非模态窗口</a></li>
                                    <li id="02020803"><a class="fourLayer" href="http://框架中打开窗口">框架中打开窗口</a></li>
                                </ul>
                            </li>
                            <li id="02020900"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>综合表单</a>
                                <ul>
                                    <li id="02020901"><a class="fourLayer" href="http://通过关联关系表实现一对多对应的表单设计与实现">通过关联关系表实现一对多对应的表单设计与实现</a></li>
                                    <li id="02020902"><a class="fourLayer" href="http://自定义控件的使用">自定义控件的使用</a></li>
                                    <li id="02020903"><a class="fourLayer" href="http://从表过滤后可编辑的实现方法灵活使用自定义控件">从表过滤后可编辑的实现方法_灵活使用自定义控件</a></li>
                                    <li id="02020904"><a class="fourLayer" href="http://动态切换自定义控件中的表单">动态切换自定义控件中的表单</a></li>
                                </ul>
                            </li>
                            <li id="02021000"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>其他</a>
                                <ul>
                                    <li id="02021001"><a class="fourLayer" href="http://发送消息预警">发送消息预警</a></li>
                                    <li id="02021002"><a class="fourLayer" href="http://固定发送邮件">固定发送邮件</a></li>
                                    <li id="02021003"><a class="fourLayer" href="http://动态发送邮件">动态发送邮件</a></li>
                                    <li id="02021004"><a class="fourLayer" href="http://不满足校验规则的以红色叉号来标记">不满足校验规则的以红色叉号来标记</a></li>
                                    <li id="02021005"><a class="fourLayer" href="http://数据验证的两种方式">数据验证的两种方式</a></li>
                                    <li id="02021006"><a class="fourLayer" href="http://权限扩展">权限扩展</a></li>
                                    <li id="02021007"><a class="fourLayer" href="http://列表中数据上下移动">列表中数据上下移动</a></li>
                                    <li id="02021008"><a class="fourLayer" href="http://单据复制">单据复制</a></li>
                                    <li id="02021009"><a class="fourLayer" href="http://WinForm窗体发布成功能菜单">WinForm窗体发布成功能菜单</a></li>
                                    <li id="02021010"><a class="fourLayer" href="http://表格中数值或下拉选择控件禁用鼠标滚轮操作">表格中数值或下拉选择控件禁用鼠标滚轮操作</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li id="02030000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>智能帮助</a>
                        <ul>
                            <li id="02030100"><a class="threeLayer" href="http://功能概述">功能概述</a></li>
                            <li id="02030300"><a class="threeLayer" href="http://如何创建及使用智能帮助">如何创建及使用智能帮助</a></li>
                            <li id="02030400"><a class="threeLayer" href="http://如何设置帮助过滤">如何设置帮助过滤</a></li>
                            <li id="02030500"><a class="threeLayer" href="http://如何设置权限">如何设置权限</a></li>
                            <li id="02030600"><a class="threeLayer" href="http://基本扩展场景示例">基本扩展场景示例</a></li>
                            <li id="02030700"><a class="threeLayer" href="http://如何在构件中调用智能帮助">如何在构件中调用智能帮助</a></li>
                            <li id="02030800"><a class="threeLayer" href="http://其他应用场景及开发使用说明">其他应用场景及开发使用说明</a></li>
                            <li id="02030900"><a class="threeLayer" href="http://使用技巧">使用技巧</a></li>
                            <li id="02031000"><a class="threeLayer" href="http://注意事项">注意事项</a></li>
                        </ul>
                    </li>
                    <li id="02040000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>表单样式</a>
                        <ul>
                            <li id="02040100"><a class="threeLayer" href="http://添加工具栏菜单项">添加工具栏菜单项</a></li>
                            <li id="02040200"><a class="threeLayer" href="http://添加工具栏面板">添加工具栏面板</a></li>
                            <li id="02040300"><a class="threeLayer" href="http://添加方案">添加方案</a></li>
                            <li id="02040400"><a class="threeLayer" href="http://添加行号">添加行号</a></li>
                            <li id="02040500"><a class="threeLayer" href="http://增加分组显示面板">增加分组显示面板</a></li>
                            <li id="02040600"><a class="threeLayer" href="http://显示合计">显示合计</a></li>
                            <li id="02040700"><a class="threeLayer" href="http://自动过滤行">自动过滤行</a></li>
                            <li id="02040800"><a class="threeLayer" href="http://滚动条效果">固定列</a></li>
                            <li id="02040900"><a class="threeLayer" href="http://隔行变色">隔行变色</a></li>
                            <li id="02041000"><a class="threeLayer" href="http://条件样式">条件样式</a></li>
                            <li id="02041100"><a class="threeLayer" href="http://分组及分组小计">分组及分组小计</a></li>
                            <li id="02041200"><a class="threeLayer" href="http://表单默认值">表单默认值</a></li>
                        </ul>
                    </li>
                    <li id="02050000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>表达式</a>
                        <ul>
                            <li id="02050100"><a class="threeLayer" href="http://表达式概述">表达式概述</a></li>
                            <li id="02050200"><a class="threeLayer" href="http://表达式应用实例">表达式应用实例</a></li>
                        </ul>
                    </li>
                    <li id="02060000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>编码规则</a>
                        <ul>
                            <li id="02060100"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>编码规则定义</a>
                                <ul>
                                    <li id="02060101"><a class="fourLayer" href="http://编码规则导航">编码规则导航</a></li>
                                    <li id="02060102"><a class="fourLayer" href="http://编码规则属性描述">编码规则属性描述</a></li>
                                    <li id="02060103"><a class="fourLayer" href="http://普通编码规则使用场景">普通编码规则使用场景</a></li>
                                    <li id="02060104"><a class="fourLayer" href="http://分级编码规则使用场景">分级编码规则使用场景</a></li>
                                </ul>
                            </li>
                            <li id="02060200"><a class="threeLayer" href="http://编码规则分配">编码规则分配</a></li>
                            <li id="02060300"><a class="threeLayer" href="http://调用接口说明">调用接口说明</a></li>
                            <li id="02060400"><a class="threeLayer" href="http://调用接口示例">调用接口示例</a></li>
                            <li id="02060500"><a class="threeLayer" href="http://初始化编码顺序号">初始化编码顺序号</a></li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li id="03000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>WebForm开发</a>
                <ul>
                    <li id="03010000"><a class="twoLayer" href="http://webform/专题web表单打印">web表单打印</a></li>
                    <li id="03020000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>web表单智能帮助</a>
                        <ul>
                            <li id="03020100"><a class="threeLayer" href="http://webform/专题web表单智能帮助">开发指南</a></li>
                            <li id="03020200"><a class="threeLayer" href="http://webform/智能帮助属性设置说明">智能帮助属性设置说明</a></li>
                            <li id="03020300"><a class="threeLayer" href="http://webform/手工绑定帮助">手工绑定帮助</a></li>
                        </ul>
                    </li>
                    <li id="03030000"><a class="twoLayer" href="http://webform/专题web表单提交审批">web表单提交审批</a></li>
                    <li id="03040000"><a class="twoLayer" href="http://webform/专题web表单数据格式化">web数据格式化</a></li>
                    <li id="03050000"><a class="twoLayer" href="http://webform/专题web表单按钮操作权限">web按钮操作权限</a></li>
                    <li id="03060000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>web表单事件扩展</a>
                        <ul>
                            <li id="03060100"><a class="threeLayer" href="http://webform/不同控制器方法调用">不同控制器间方法调用</a></li>
                            <li id="03060200"><a class="threeLayer" href="http://webform/Web表单如何调用构件">如何调用构件</a></li>
                            <li id="03060300"><a class="threeLayer" href="http://webform/工具栏按钮状态控制">工具栏按钮状态控制</a></li>
                            <li id="03060400"><a class="threeLayer" href="http://webform/弹出Dialog">弹出Dialog</a></li>
                            <li id="03060500"><a class="threeLayer" href="http://webform/列表弹出卡片细节控制">列表弹出卡片细节控制</a></li>
                            <li id="03060600"><a class="threeLayer" href="http://webform/Web表单如何调用脚本">Web表单如何调用脚本</a></li>
                            <li id="03060700"><a class="threeLayer" href="http://webform/Web表单设置上下文参数">Web表单设置上下文参数</a></li>
                        </ul>
                    </li>
                    <li id="03070000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>手动操作数据源</a>
                        <ul>
                            <li id="03070100"><a class="threeLayer" href="http://webform/为列表添加复制方法">为列表添加复制方法</a></li>
                            <li id="03070200"><a class="threeLayer" href="http://webform/判断数据源是否有修改">判断数据源是否有修改</a></li>
                        </ul>
                    </li>
                    <li id="03080000"><a class="twoLayer" href="http://webform/专题web表单状态机">web表单状态机</a></li>
                    <li id="03090000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>web表单附件上传</a>
                        <ul>
                            <li id="03090100"><a class="threeLayer" href="http://webform/专题web表单附件上传">不带预览上传</a></li>
                            <li id="03090200"><a class="threeLayer" href="http://webform/带预览方式上传">带预览方式上传</a></li>
                            <li id="03090300"><a class="threeLayer" href="http://webform/点击文件名下载附件">点击文件名下载附件</a></li>
                        </ul>
                    </li>
                    <li id="03100000"><a class="twoLayer" href="http://webform/专题web表单事件通讯">web表单事件通讯</a></li>
                    <li id="03110000"><a class="twoLayer" href="http://webform/专题web表单运行时定制">web表单运行时定制</a></li>
                    <li id="03120000"><a class="twoLayer" href="http://webform/专题web表单事件注册">web表单事件注册</a></li>
                    <li id="03130000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>web表单权限</a>
                        <ul>
                            <li id="03130100"><a class="threeLayer" href="http://webform/web表单列权限">web表单列权限</a></li>
                            <li id="03130200"><a class="threeLayer" href="http://webform/web表单行权限">web表单行权限</a></li>
                            <li id="03130300"><a class="threeLayer" href="http://webform/web表单操作权限">web表单操作权限</a></li>
                        </ul>
                    </li>
                    <li id="03140000"><a class="twoLayer" href="http://webform/专题web表单表达式定义">web表单表达式定义</a></li>
                    <li id="03150000"><a class="twoLayer" href="http://webform/专题web公共函数">web公共函数</a></li>
                    <li id="03160000"><a class="twoLayer" href="http://webform/专题web交叉表查询">web表单交叉表查询</a></li>
                    <li id="03170000"><a class="twoLayer" href="http://webform/全局变量">框架中全局变量列表</a></li>
                    <li id="03180000"><a class="twoLayer" href="http://webform/嵌入WinForm">将Web页面嵌入到WinForm功能菜单中</a></li>
                    <li id="03190000"><a class="twoLayer" href="http://webform/Web审批中心设置">配置Web审批中心</a></li>
                    <li id="03200000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>web表单使用脚本控制控件</a>
                        <ul>
                            <li id="03200100"><a class="threeLayer" href="http://webform/web表单执行构件加载下拉列表">web表单执行构件加载下拉列表</a></li>
                        </ul>
                    </li>
                    <li id="03210000"><a class="twoLayer" href="http://webform/控制控件CSS样式">控制控件CSS样式</a></li>
                    <li id="03220000"><a class="twoLayer" href="http://webform/自定义查询列表">web表单自定义查询列表</a></li>
                    <li id="03230000"><a class="twoLayer" href="http://webform/Web表单启用静态页">Web表单启用静态页</a></li>
                    <li id="03240000"><a class="twoLayer" href="http://webform/Web表单草稿箱">Web表单启用草稿箱</a></li>
                    <li id="03250000"><a class="twoLayer" href="http://webform/Web表单启用多语言">Web表单启用多语言</a></li>
                </ul>
            </li>
            <li id="04000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>工作流</a>
                <ul>
                    <li id="04010000"><a class="twoLayer" href="http://工作流">工作流定义</a></li>
                    <li id="04020000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>移动审批定义</a>
                        <ul>
                            <li id="04020100"><a class="threeLayer" href="http://移动审批补丁安装">移动审批补丁安装</a></li>
                            <li id="04020200"><a class="threeLayer" href="http://移动审批环境配置">移动审批环境配置</a></li>
                            <li id="04020300"><a class="threeLayer" href="http://移动审批操作说明">移动审批操作说明</a></li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>

        <ul>
            <li id="05000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>查询</a>

                <ul>
                    <li id="05010000"><a class="twoLayer" href="http://查询元数据">查询元数据</a></li>
                    <li id="05020000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>统一查询平台</a>
                        <ul>
                            <li id="05020100"><a class="threeLayer" href="http://统一查询平台">开发步骤</a></li>
                            <li id="05020200"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>最佳实践</a>
                                <ul>
                                    <li id="05020201"><a class="fourLayer" href="http://树形查询展现合计值">树形查询展现合计值</a></li>
                                    <li id="05020202"><a class="fourLayer" href="http://列表查询结果联查字典维护表单">列表查询结果联查字典维护表单</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li id="06000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>数据交换</a>
                <ul>
                    <li id="06010000"><a class="twoLayer" href="http://数据导入">数据导入</a></li>
                    <li id="06020000"><a class="twoLayer" href="http://数据导出">数据导出</a></li>
                    <li id="06030000"><a class="twoLayer" href="http://数据同步">数据同步</a></li>
                </ul>
            </li>
            <li id="07000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>预警</a>
                <ul>
                    <li id="07010000"><a class="twoLayer" href="http://预警">预警定义</a></li>
                </ul>
            </li>
            <li id="08000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>打印</a>
                <ul>
                    <li id="08010000"><a class="twoLayer" href="http://打印">报表定义</a></li>
                    <li id="08020000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>最佳实践</a>
                        <ul>
                            <li id="08020100"><a class="threeLayer" href="http://快速画表格线、控件对齐">快速画表格线、控件对齐</a></li>
                            <li id="08020200"><a class="threeLayer" href="http://显示行号、页码">显示行号、页码</a></li>
                            <li id="08020300"><a class="threeLayer" href="http://套打金额到凭证纸上">套打金额到凭证纸上</a></li>
                            <li id="08020400"><a class="threeLayer" href="http://显示金额财务大写">显示金额财务大写</a></li>
                            <li id="08020500"><a class="threeLayer" href="http://显示汉字数量">显示汉字数量</a></li>
                            <li id="08020600"><a class="threeLayer" href="http://显示汉字日期格式">显示汉字日期格式</a></li>
                            <li id="08020700"><a class="threeLayer" href="http://将20130727显示为汉字日期格式">将20130727显示为汉字日期格式</a></li>
                            <li id="08020800"><a class="threeLayer" href="http://解决偶尔打印或导出乱码">解决偶尔打印或导出乱码</a></li>
                            <li id="08020900"><a class="threeLayer" href="http://设置较长字段自动延展及相关字段延展为最高">设置较长字段自动延展及相关字段延展为最高</a></li>
                            <li id="08021000"><a class="threeLayer" href="http://打印图片">打印图片</a></li>
                            <li id="08021100"><a class="threeLayer" href="http://打印条码">打印条码</a></li>
                            <li id="08021200"><a class="threeLayer" href="http://设计打印大表">设计打印大表</a></li>
                            <li id="08021300"><a class="threeLayer" href="http://折行分栏目打印">折行分栏目打印</a></li>
                            <li id="08021400"><a class="threeLayer" href="http://进行分组小计和总合计">进行分组小计和总合计</a></li>
                            <li id="08021500"><a class="threeLayer" href="http://对打印数据进行排序显示">对打印数据进行排序显示</a></li>
                            <li id="08021600"><a class="threeLayer" href="http://填充空白行">填充空白行</a></li>
                            <li id="08021700"><a class="threeLayer" href="http://解决中英文混排下打印、换行、导出异常">解决中英文混排下打印、换行、导出异常</a></li>
                            <li id="08021800"><a class="threeLayer" href="http://Header中取合计值">Header中取合计值</a></li>
                            <li id="08021900"><a class="threeLayer" href="http://明细区为空时打印表尾">明细区为空时打印表尾</a></li>
                            <li id="08022000"><a class="threeLayer" href="http://循环打印审批人">循环打印审批人</a></li>
                            <li id="08022100"><a class="threeLayer" href="http://将审批日志中不同手签图片在同一行上显示">将审批日志中不同手签图片在同一行上显示</a></li>
                            <li id="08022200"><a class="threeLayer" href="http://对子表垂直打印">对子表垂直打印</a></li>
                            <li id="08022300"><a class="threeLayer" href="http://各客户端均可根据自定义纸张设置打印">各客户端均可根据自定义纸张设置打印</a></li>
                            <li id="08022400"><a class="threeLayer" href="http://采用ADO方式取数并带参数">采用ADO方式取数并带参数</a></li>
                            <li id="08022500"><a class="threeLayer" href="http://根据审批节点不同打印不同信息">根据审批节点不同打印不同信息</a></li>
                            <li id="08022600"><a class="threeLayer" href="http://将数字年月日显示为汉字形式">将数字年月日显示为汉字形式</a></li>
                            <li id="08022700"><a class="threeLayer" href="http://分栏目显示且自动填充空白行">分栏目显示且自动填充空白行</a></li>
                            <li id="08022800"><a class="threeLayer" href="http://在有限的空间内自动缩小字体打印较多内容">在有限的空间内自动缩小字体打印较多内容</a></li>
                            <li id="08022900"><a class="threeLayer" href="http://对业务传递过来的数据过滤后再打印">对业务传递过来的数据过滤后再打印</a></li>
                            <li id="08023000"><a class="threeLayer" href="http://控制只在最后一页打印页脚或页脚中内容">控制只在最后一页打印页脚或页脚中内容</a></li>
                            <li id="08023100"><a class="threeLayer" href="http://完全通过代码控制打印及强制换页">完全通过代码控制打印及强制换页</a></li>
                        </ul>
                    </li>
                    <li id="08030000"><a class="twoLayer" href="http://打印跟踪技巧">使用技巧</a></li>
                </ul>
            </li>
            <li id="09000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>权限管理</a>
                <ul>
                    <li id="09010000"><a class="twoLayer" href="http://功能权限">功能权限</a></li>
                    <li id="09020000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>数据权限</a>
                        <ul>
                            <li id="09020100"><a class="threeLayer" href="http://数据行权限">数据行权限</a></li>
                            <li id="09020200"><a class="threeLayer" href="http://数据列权限">数据列权限</a></li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li id="10000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>消息队列服务</a>
                <ul>
                    <li id="10010000"><a class="twoLayer" href="http://系统概述">系统概述</a></li>
                    <li id="10020000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>安装ActiveMQ服务</a>
                        <ul>
                            <li id="10020100"><a class="threeLayer" href="http://安装jdk">安装jdk</a></li>
                            <li id="10020200"><a class="threeLayer" href="http://启动消息队列服务环境">启动消息队列服务环境</a></li>
                            <li id="10020300"><a class="threeLayer" href="http://修改配置文件">修改配置文件</a></li>
                            <li id="10020400"><a class="threeLayer" href="http://启动MQ消息队列应用程序">启动MQ消息队列应用程序</a></li>
                            <li id="10020500"><a class="threeLayer" href="http://ActiveMQ注册为Windows服务">ActiveMQ注册为Windows服务</a></li>
                            <li id="10020600"><a class="threeLayer" href="http://验证是否运行成功">验证是否运行成功</a></li>
                        </ul>
                    </li>
                    <li id="10030000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>配置队列参数</a>
                        <ul>
                            <li id="10030100"><a class="threeLayer" href="http://新增修改队列配置">新增、修改队列配置</a></li>
                            <li id="10030200"><a class="threeLayer" href="http://删除队列">删除队列</a></li>
                            <li id="10030300"><a class="threeLayer" href="http://查看队列">查看队列</a></li>
                            <li id="10030400"><a class="threeLayer" href="http://启用队列">启用队列</a></li>
                            <li id="10030500"><a class="threeLayer" href="http://停用队列">停用队列</a></li>
                        </ul>
                    </li>
                    <li id="10040000"><a class="twoLayer" href="http://队列监控">队列监控</a></li>
                    <li id="10050000"><a class="twoLayer" href="http://队列日志">队列日志</a></li>
                    <li id="10060000"><a class="twoLayer" href="http://队列监听服务控制台">队列监听服务控制台</a></li>
                </ul>
            </li>
            <li id="11000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>性能优化</a>
                <ul>
                    <li id="11020000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>SQL规范</a>
                        <ul>
                            <li id="11020100"><a class="threeLayer" href="http://SQL规范">SQL规范</a></li>
                            <li id="11020200"><a class="threeLayer" href="http://数据连接方式">数据连接方式</a></li>
                            <li id="11020300"><a class="threeLayer" href="http://使用where子句的技巧">使用where子句的技巧</a></li>
                            <li id="11020400"><a class="threeLayer" href="http://连接查询">连接查询</a></li>
                            <li id="11020500"><a class="threeLayer" href="http://联合查询">联合查询</a></li>
                            <li id="11020600"><a class="threeLayer" href="http://其他">其他</a></li>
                            <li id="11020700"><a class="threeLayer" href="http://分析SQL语句的执行效率">分析SQL语句的执行效率</a></li>
                            <li id="11020800"><a class="threeLayer" href="http://SQL规范总结">SQL规范总结</a></li>
                        </ul>
                    </li>
                    <li id="11030000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>巧用索引</a>
                        <ul>
                            <li id="11030100"><a class="threeLayer" href="http://索引">索引</a></li>
                            <li id="11030200"><a class="threeLayer" href="http://索引使用原则">索引使用原则</a></li>
                            <li id="11030300"><a class="threeLayer" href="http://索引应用技巧">索引应用技巧</a></li>
                            <li id="11030400"><a class="threeLayer" href="http://索引维护">索引维护</a></li>
                            <li id="11030500"><a class="threeLayer" href="http://索引总结">索引总结</a></li>
                        </ul>
                    </li>
                    <li id="11040000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>存储过程</a>
                        <ul>
                            <li id="11040100"><a class="threeLayer" href="http://存储过程">存储过程</a></li>
                            <li id="11040200"><a class="threeLayer" href="http://应用策略">应用策略</a></li>
                            <li id="11040300"><a class="threeLayer" href="http://存储过程语法规则">存储过程语法规则</a></li>
                            <li id="11040400"><a class="threeLayer" href="http://存储过程总结">存储过程总结</a></li>
                        </ul>
                    </li>
                    <li id="11050000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>视图</a>
                        <ul>
                            <li id="11050100"><a class="threeLayer" href="http://视图">视图</a></li>
                            <li id="11050200"><a class="threeLayer" href="http://视图优点">视图优点</a></li>
                            <li id="11050300"><a class="threeLayer" href="http://视图语法规则">视图语法规则</a></li>
                            <li id="11050400"><a class="threeLayer" href="http://索引视图">索引视图</a></li>
                            <li id="11050500"><a class="threeLayer" href="http://视图总结">视图总结</a></li>
                        </ul>
                    </li>
                    <li id="11060000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>触发器</a>
                        <ul>
                            <li id="11060100"><a class="threeLayer" href="http://触发器">触发器</a></li>
                            <li id="11060200"><a class="threeLayer" href="http://触发器作用">触发器作用</a></li>
                            <li id="11060300"><a class="threeLayer" href="http://触发器语法">触发器语法</a></li>
                            <li id="11060400"><a class="threeLayer" href="http://触发器总结">触发器总结</a></li>
                        </ul>
                    </li>
                    <li id="11070000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>设计、算法</a>
                        <ul>
                            <li id="11070100"><a class="threeLayer" href="http://设计算法">设计算法</a></li>
                            <li id="11070200"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>算法</a>
                                <ul>
                                    <li id="11070201"><a class="fourLayer" href="http://日期的处理">日期的处理</a></li>
                                    <li id="11070202"><a class="fourLayer" href="http://计量单位的处理">计量单位的处理</a></li>
                                    <li id="11070203"><a class="fourLayer" href="http://查询统计格式">查询统计格式</a></li>
                                    <li id="11070204"><a class="fourLayer" href="http://任意统计组件">任意统计组件</a></li>
                                    <li id="11070205"><a class="fourLayer" href="http://库存收发存汇总">库存收发存汇总</a></li>
                                    <li id="11070206"><a class="fourLayer" href="http://单据打开增加翻页">单据打开增加翻页</a></li>
                                    <li id="11070207"><a class="fourLayer" href="http://单据保存">单据保存</a></li>
                                    <li id="11070208"><a class="fourLayer" href="http://发票与回款单核销打开核销">发票与回款单核销打开核销</a></li>
                                    <li id="11070209"><a class="fourLayer" href="http://辅助管理往来核销">辅助管理往来核销</a></li>
                                    <li id="11070210"><a class="fourLayer" href="http://维护工具">维护工具</a></li>
                                </ul>
                            </li>
                            <li id="11070300"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>数据库设计</a>
                                <ul>
                                    <li id="11070301"><a class="fourLayer" href="http://数据库设计">数据库设计</a></li>
                                    <li id="11070302"><a class="fourLayer" href="http://设计准则">设计准则</a></li>
                                    <li id="11070303"><a class="fourLayer" href="http://逻辑库规范化问题">逻辑库规范化问题</a></li>
                                    <li id="11070304"><a class="fourLayer" href="http://生成物理数据库">生成物理数据库</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li id="11080000"><a class="twoLayer" href="http://webservice性能优化">WebService性能优化</a></li>
                    <li id="11090000"><a class="twoLayer" href="http://表单性能优化设置">表单性能优化设置</a></li>
                </ul>
            </li>
            <li id="12000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>运行框架</a>
                <ul>
                    <li id="12010000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>资源文件替换</a>
                        <ul>
                            <li id="12010100"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>替换图片</a>
                                <ul>
                                    <li id="12010101"><a class="fourLayer" href="http://资源文件浏览工具">资源文件浏览工具</a></li>
                                    <li id="12010102"><a class="fourLayer" href="http://替换登录界面图片">替换登录界面图片</a></li>
                                    <li id="12010103"><a class="fourLayer" href="http://替换闪屏文件">替换闪屏界面图片</a></li>
                                    <li id="12010104"><a class="fourLayer" href="http://替换登录界面GS图标">替换登录界面GS图标</a></li>
                                </ul>
                            </li>
                            <li id="12010200"><a class="threeLayer" href="###"><span class="arrow">&nbsp;</span>修改标题</a>
                                <ul>
                                    <li id="12010201"><a class="fourLayer" href="http://准备">准备</a></li>
                                    <li id="12010202"><a class="fourLayer" href="http://修改登录界面标题">修改登录界面标题</a></li>
                                    <li id="12010203"><a class="fourLayer" href="http://修改主框架标题">修改主框架标题</a></li>
                                </ul>
                            </li>
                            <li id="12010300"><a class="threeLayer" href="http://更换皮肤">更换皮肤</a></li>
                        </ul>
                    </li>
                    <li id="12020000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>快捷桌面</a>
                        <ul>
                            <li id="12020100"><a class="threeLayer" href="http://快捷桌面概述">快捷桌面概述</a></li>
                            <li id="12020200"><a class="threeLayer" href="http://快捷桌面启用">启用快捷桌面</a></li>
                            <li id="12020300"><a class="threeLayer" href="http://快捷桌面制作与修改">快捷桌面制作与修改</a></li>
                            <li id="12020400"><a class="threeLayer" href="http://快捷桌面使用权限">快捷桌面使用权限</a></li>
                            <li id="12020500"><a class="threeLayer" href="http://快捷桌面维护权限">快捷桌面维护权限</a></li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li id="13000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>补丁</a>
                <ul>
                    <li id="13010000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>补丁制作工具使用规范</a>
                        <ul>
                            <li id="13010100"><a class="threeLayer" href="http://总则">总则</a></li>
                            <li id="13010200"><a class="threeLayer" href="http://补丁制作工具入口">补丁制作工具入口</a></li>
                            <li id="13010300"><a class="threeLayer" href="http://补丁基本信息填写">补丁基本信息填写</a></li>
                            <li id="13010400"><a class="threeLayer" href="http://补丁应用程序数据库升级内容设置">补丁应用程序、数据库升级内容设置</a></li>
                            <li id="13010500"><a class="threeLayer" href="http://元数据制作">元数据制作</a></li>
                            <li id="13010600"><a class="threeLayer" href="http://补丁上传">补丁上传</a></li>
                            <li id="13010700"><a class="threeLayer" href="http://模块初始补丁制作">模块初始补丁制作</a></li>
                            <li id="13010800"><a class="threeLayer" href="http://小版本升级补丁制作">GS6.0系列小版本升级补丁</a></li>
                        </ul>
                    </li>
                    <li id="13020000"><a class="twoLayer" href="http://制作补丁">制作补丁</a></li>
                </ul>
            </li>
            <li id="14000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>数据访问引擎使用指南</a>
                <ul>
                    <li id="14010000"><a class="twoLayer" href="http://数据访问引擎概述">数据访问引擎概述</a></li>
                    <li id="14020000"><a class="twoLayer" href="http://客户端接口">客户端接口</a></li>
                    <li id="14030000"><a class="twoLayer" href="http://服务端接口">服务端接口</a></li>
                    <li id="14040000"><a class="twoLayer" href="http://数据访问引擎常用场景">常用场景</a></li>
                </ul>
            </li>
            <li id="15000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>运行时定制</a>
                <ul>
                    <li id="15010000"><a class="twoLayer" href="http://运行时定制概述">运行时定制概述</a></li>
                    <li id="15020000"><a class="twoLayer" href="http://运行时定制表单制作">运行时定制表单制作</a></li>
                    <li id="15030000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>运行时定制分配（供实施使用）</a>
                        <ul>
                            <li id="15030100"><a class="threeLayer" href="http://扩展表单分配">扩展表单分配</a></li>
                            <li id="15030200"><a class="threeLayer" href="http://打印格式分配">打印格式分配</a></li>
                            <li id="15030300"><a class="threeLayer" href="http://业务流程分配">业务流程分配</a></li>
                        </ul>
                    </li>
                    <li id="15040000"><a class="twoLayer" href="http://对外接口">对外接口</a></li>
                </ul>
            </li>
            <li id="16000000"><a class="oneLayer" href="###"><span class="arrow">&nbsp;</span>移动开发</a>
                <ul>
                    <li id="16010000"><a class="twoLayer" href="http://管理功能权限分配">管理功能权限分配</a></li>
                    <li id="16020000"><a class="twoLayer" href="http://移动应用权限设置">移动应用权限设置</a></li>
                    <li id="16030000"><a class="twoLayer" href="http://扫描二维码">扫描二维码</a></li>
                    <li id="16040000"><a class="twoLayer" href="http://向移动端推送消息">向移动端推送消息</a></li>
                    <li id="16050000"><a class="twoLayer" href="http://移动端修改密码监听事件">移动端修改密码监听事件</a></li>
                    <li id="16060000"><a class="twoLayer" href="http://修改移动端首页">修改移动端首页</a></li>
                    <li id="16070000"><a class="twoLayer" href="###"><span class="arrow">&nbsp;</span>移动查询单据</a>
                        <ul>
                            <li id="16070100"><a class="threeLayer" href="http://webform/使用数据模型查询">使用数据模型查询</a></li>
                            <li id="16070200"><a class="threeLayer" href="http://webform/使用存储过程查询">使用存储过程查询</a></li>
                            <li id="16070300"><a class="threeLayer" href="http://webform/使用构件进行查询">使用构件进行查询</a></li>
                        </ul>
                    </li>
                    <li id="16070000"><a class="twoLayer" href="http://移动应用分组">移动应用分组</a></li>
                </ul>
            </li>
        </ul>
    </div>
</body>
<script language="javascript">
        var myNodeId = $.cookie('wzqOpenNode').toString();
        $('#' + myNodeId).attr("class", "myActivedNode");
</script>
</html>
```