# GifText
两种方式实现富文本在TextView 中显示图文混排（可显示gif图片）

第一种 自己通过gifDecode 异步去解析gif图片的每一帧 然后刷新invalidate 效果差 不容易控制图片解析的每一帧 容易oom

第二种 通过fresco 解析本地gif得到一个animatedDrawable 来实现这种效果  实现复用 结束释放内存基本没出现oom。s
