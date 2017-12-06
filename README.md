# ShadeView
自定义文字闪烁效果
通过Xfermode的ProterDuff.SRC_IN 模式来实现文字加载的效果
实现流程分析：

Step 1.首先，一个文字图片(透明背景)

Step 2.初始化画笔，背景图片(DST)，矩形Rect(SRC)

Step 3.先保存图层，接着先绘制背景图，设置混排模式，然后绘制Rect，清除混排模式 接着回复保存的图层，最后修改下Rect区域高度，调用invalidate()让View重绘！
