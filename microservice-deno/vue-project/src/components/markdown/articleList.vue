<style>

</style>

<template>
    <div>
        <el-row>
            <el-col v-for="article in articleList" :key="article.id" style="margin: 10px;">
                <el-card @click="tranToArticle(article.name)" class="test">
                    <template #header>
                        <div>
                            <span>{{article.articleName}}</span>
                        </div>
                    </template>
                    <div>
                        {{ article.time }}
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>


<script type="text/ecmascript-6">

  import axios from "axios";

  export default {
    name: 'ArticleList',
    data() {
        return {
            articleList: [
                {
                    id: '1',
                    name: '2023-03-25-微信小程序实战.md',
                    articleName: '微信小程序实战',
                    time: '2023-03-25'
                },{
                    id: '2',
                    name: '2023-03-20-云计算安全.md',
                    articleName: '云计算安全',
                    time: '2023-03-20'
                },{
                    id: '3',
                    name: '2023-03-30-瑞吉外卖.md',
                    articleName: '瑞吉外卖',
                    time: '2023-03-30'
                },{
                    id: '4',
                    name: '2023-03-19-spring security.md',
                    articleName: 'spring security',
                    time: '2023-03-19'
                },{
                    id: '5',
                    name: '2023-03-20-git命令.md',
                    articleName: 'git命令',
                    time: '2023-03-20'
                },{
                    id: '6',
                    name: '2023-03-21-CSS.md',
                    articleName: 'CSS',
                    time: '2023-03-21'
                },{
                    id: '7',
                    name: '2023-03-22-JavaScript DOM.md',
                    articleName: 'JavaScript DOM',
                    time: '2023-03-22'
                },{
                    id: '8',
                    name: '2023-03-22-JavaScript基础.md',
                    articleName: 'JavaScript基础',
                    time: '2023-03-22'
                },{
                    id: '9',
                    name: '2023-03-22-微信小程序组件使用.md',
                    articleName: '微信小程序组件使用',
                    time: '2023-03-22'
                },{
                    id: '10',
                    name: '2023-03-24-实验1-组件.md',
                    articleName: '实验1-组件',
                    time: '2023-03-30'
                },{
                    id: '11',
                    name: '2023-04-01-自定义页面导航.md',
                    articleName: '自定义页面导航',
                    time: '2023-04-01'
                },{
                    id: '12',
                    name: '2023-03-26-（书）Java编程的逻辑.md',
                    articleName: '（书）Java编程的逻辑',
                    time: '2023-03-26'
                },{
                    id: '13',
                    name: '2023-03-26-Java API.md',
                    articleName: 'Java API',
                    time: '2023-03-26'
                },{
                    id: '14',
                    name: '2023-03-31-泛型.md',
                    articleName: '泛型',
                    time: '2023-03-31'
                }
            ]
        };
    },
    mounted() {

    },
    methods: {
        tranToArticle(name) {
            this.$router.push({ path: '/article', query: { name }})
        }
    }
  };

</script>