# 组件使用实例

## 创建组件

```vue

<style></style>

<template></template>


<script type="text/ecmascript-6">

    // 通过 props 传递参数
    export default {
        name: 'Content',
        props: {
            id: {
                type: String,
                required: true
            }
        },
        mounted() {
            let id = this.id
            alert(id)
        } 
    };

</script>

```

## 组件使用

```vue
<template>
    <!-- id 传递需要使用字符串，否则会存在精度丢失的问题 -->
    <Content :id="'231414135135413513'"></Content>
</template>


<script type="text/ecmascript-6">
    // 注册组件
    import Content from '../components/markdown/content.vue'
    export default {
        components: {
        Content
        }
    };
</script>

```
